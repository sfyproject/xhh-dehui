package com.store.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.store.module.session.AuthSession;
import com.store.module.sso.AdminUser;
import com.store.module.sso.AuthUser;

@SuppressWarnings("deprecation")
public class Utils {

	public static boolean isNullStr(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNullList(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	public static String getUniqueFileName() {
		String fileName = getFormatCurrentDate();
		fileName = fileName + ".jpg";
		return Utils.UUID() + "_" + fileName;
	}

	private static String getFormatCurrentDate() {
		String dateFormat = "yyyyMMddHHmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	public static String getYYYYMMDDCurrentDate() {
		String dateFormat = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	//图片上传
	public static String storeImage(String path, String relativePath, MultipartFile imageFile) {
		try {
			String imageFileName = Utils.getUniqueFileName();
			relativePath = relativePath + imageFileName;
			File fileImage = new File(path, imageFileName);
			FileUtils.copyInputStreamToFile(imageFile.getInputStream(), fileImage);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return relativePath;
	}

	public static String getIpAddr(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	public static AuthUser getAuthUser(HttpServletRequest req) {
		SecurityContext securityContext = AuthSession.getSecurityContext(req);
		if (null == securityContext) {
			return null;
		}
		Authentication authentication = securityContext.getAuthentication();
		if (null == authentication) {
			return null;
		}
		AuthUser authUser = (AuthUser) authentication.getPrincipal();
		return authUser;
	}

	public static String getAuthUserId(HttpServletRequest req) {
		AuthUser authUser = Utils.getAuthUser(req);
		if (null == authUser) {
			return null;
		}
		return authUser.getUserId();
	}

	public static String getOrderNo() {
		return "ON" + getUniqueNum();
	}

	public static String getVipNo() {
		return "VIP" + getUniqueNum();
	}

	public static String getCardNo() {
		return "CN" + getUniqueNum();
	}

	private static String getUniqueNum() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random r = new Random();
		int i = 0;
		while (true) {
			i = r.nextInt();
			if (i <= 0) {
				continue;
			}
			break;
		}
		key = key + i;
		key = key.substring(0, 15);
		return key;
	}

	public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (StringUtils.isNotBlank(item.getKey())) {
					String key = item.getKey();
					String val = item.getValue();
					if (urlEncode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					if (keyToLower) {
						buf.append(key.toLowerCase() + "=" + val);
					} else {
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}
			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}

	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getRemotePortData(String urls, String param) {
		try {
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setRequestMethod("POST");
			if (StringUtils.isNotBlank(param)) {
				conn.setRequestProperty("Origin", "https://sirius.searates.com");// 主要参数
				conn.setRequestProperty("Referer",
						"https://sirius.searates.com/cn/port?A=ChIJP1j2OhRahjURNsllbOuKc3Y&D=567&G=16959&shipment=1&container=20st&weight=1&product=0&request=&weightcargo=1&");
				conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");// 主要参数
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			if (StringUtils.isNotBlank(param)) {
				OutputStream dos = conn.getOutputStream();
				dos.write(param.getBytes("UTF-8"));
				dos.flush();
				dos.close();
			}
			InputStream input = conn.getInputStream();
			int resLen = 0;
			byte[] res = new byte[1024];
			StringBuilder sb = new StringBuilder();
			while ((resLen = input.read(res)) != -1) {
				sb.append(new String(res, 0, resLen));
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Map<String, String> doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<?> list = root.getChildren();
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List<?> children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}
		in.close();
		return m;
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	public static String getChildrenText(List<?> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator<?> it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<?> list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	public static String UUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp.substring(16);
	}

	public static String doRefund(String url, String data) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(new File("/opt/cert/apiclient_cert.p12"));
		try {
			keyStore.load(is, "1511963621".toCharArray());
		} finally {
			is.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1511963621".toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();

				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXmlToMap(String xml) {
		SortedMap<String, String> retMap = new TreeMap<String, String>();
		try {
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(source);
			Element root = (Element) doc.getRootElement();
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	public static HttpResponse doGet(String host, String path, String method, Map<String, String> headers,
			Map<String, String> querys) throws Exception {
		HttpClient httpClient = wrapClient(host);
		HttpGet request = new HttpGet(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}
		return httpClient.execute(request);
	}

	public static HttpResponse doPost(String host, String path, String method, Map<String, String> headers,
			Map<String, String> querys, Map<String, String> bodys) throws Exception {
		HttpClient httpClient = wrapClient(host);
		HttpPost request = new HttpPost(buildUrl(host, path, querys));
		for (Map.Entry<String, String> e : headers.entrySet()) {
			request.addHeader(e.getKey(), e.getValue());
		}
		if (bodys != null) {
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			for (String key : bodys.keySet()) {
				nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
			request.setEntity(formEntity);
		}
		return httpClient.execute(request);
	}

	private static String buildUrl(String host, String path, Map<String, String> querys)
			throws UnsupportedEncodingException {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(host);
		if (!StringUtils.isBlank(path)) {
			sbUrl.append(path);
		}
		if (null != querys) {
			StringBuilder sbQuery = new StringBuilder();
			for (Map.Entry<String, String> query : querys.entrySet()) {
				if (0 < sbQuery.length()) {
					sbQuery.append("&");
				}
				if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
					sbQuery.append(query.getValue());
				}
				if (!StringUtils.isBlank(query.getKey())) {
					sbQuery.append(query.getKey());
					if (!StringUtils.isBlank(query.getValue())) {
						sbQuery.append("=");
						sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
					}
				}
			}
			if (0 < sbQuery.length()) {
				sbUrl.append("?").append(sbQuery);
			}
		}
		return sbUrl.toString();
	}

	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		return httpClient;
	}

	private static void sslClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] xcs, String str) {

				}

				public void checkServerTrusted(X509Certificate[] xcs, String str) {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
		} catch (KeyManagementException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static SendSmsResponse sendSms(String phone, String templateCode, String code) {
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIRxlEtpiuWAYH",
					"GMA8n0Z8CtiR7HSgjc35KeoFDzaBDa");
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
			IAcsClient acsClient = new DefaultAcsClient(profile);
			SendSmsRequest request = new SendSmsRequest();
			request.setPhoneNumbers(phone);
			request.setSignName("C8货滴");
			request.setTemplateCode(templateCode);
			request.setTemplateParam(code);
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			return sendSmsResponse;
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSmsCode() {
		String smsCode = String.valueOf(new Random().nextInt(899999) + 100000);
		return smsCode;
	}

	public static AdminUser getAuthAdmin(HttpServletRequest req) {
		SecurityContext securityContext = AuthSession.getSecurityContext(req);
		if (null == securityContext) {
			return null;
		}
		Authentication authentication = securityContext.getAuthentication();
		if (null == authentication) {
			return null;
		}
		AdminUser adminUser = (AdminUser) authentication.getPrincipal();
		return adminUser;
	}
	/**
	 * 将加密后的密码解密
	 * @param encryptPwd 加密后的密码
	 * @param charset 编码格式
	 * @return 密码明文
	 */
	public static String decryptByBase64(String encryptPwd, String charset) {// UTF-8
	    String password = null;
	    byte[] bytes = Base64.getDecoder().decode(encryptPwd);
	    try {
	        password = new String(bytes, charset);
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return password;
	}
	/**
	 * 将密码 加密
	 * @param password 密码明文
	 * @param charset 编码格式
	 * @return 加密后的密码
	 */
	public static String encryptByBase64(String password, String charset) {// UTF-8
	    String encryptPwd_jdk8 = null;
	    try {
	        encryptPwd_jdk8 = Base64.getEncoder().encodeToString(password.getBytes(charset));
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return encryptPwd_jdk8;
	}
	
	
	/** 
	   *  根据路径删除指定的目录或文件，无论存在与否 
	   *@param sPath  要删除的目录或文件 
	   *@return 删除成功返回 true，否则返回 false。 
	   */  
	  public static boolean DeleteFolder(String sPath) {  
		 boolean flag = false;  
		 File  file = new File(sPath);  
	     // 判断目录或文件是否存在  
	     if (!file.exists()) {  // 不存在返回 false  
	         return true;  
	     } else {  
	         // 判断是否为文件  
	         if (file.isFile()) { 
	        	 String parent = file.getParent();
	        	 File  parentfile = new File(parent);// 为文件时调用删除文件方法  
	        	 File[] files = parentfile.listFiles();
	        	 if(files.length>1) {
	        		 flag=deleteFile(sPath);
	        	 }else {
	        		 flag= deleteDirectory(parent);
	        	 }
	             return flag;  
	         } 
	     }  
	     return flag; 
	}
	  /** 
	   * 删除单个文件 
	   * @param   sPath    被删除文件的文件名 
	   * @return 单个文件删除成功返回true，否则返回false 
	   */  
	  public static boolean deleteFile(String sPath) {  
		  boolean flag = false;  
		  File file = new File(sPath);  
	      // 路径为文件且不为空则进行删除  
	      if (file.isFile() && file.exists()) {  
	          file.delete();  
	          flag = true;  
	      }  
	      return flag;  
	  }
	  /** 
	   * 删除目录（文件夹）以及目录下的文件 
	   * @param   sPath 被删除目录的文件路径 
	   * @return  目录删除成功返回true，否则返回false 
	   */  
	  public static boolean deleteDirectory(String sPath) {  
	      //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	      if (!sPath.endsWith(File.separator)) {  
	          sPath = sPath + File.separator;  
	      }  
	      File dirFile = new File(sPath);  
	      //如果dir对应的文件不存在，或者不是一个目录，则退出  
	      if (!dirFile.exists() || !dirFile.isDirectory()) {  
	          return false;  
	      }  
	      boolean flag = true;  
	      //删除文件夹下的所有文件(包括子目录)  
	      File[] files = dirFile.listFiles();  
	      for (int i = 0; i < files.length; i++) {  
	          //删除子文件  
	          if (files[i].isFile()) {  
	              flag = deleteFile(files[i].getAbsolutePath());  
	              if (!flag) break;  
	          } //删除子目录  
	          else {  
	              flag = deleteDirectory(files[i].getAbsolutePath());  
	              if (!flag) break;  
	          }  
	      }  
	      if (!flag) return false;  
	      //删除当前目录  
	      if (dirFile.delete()) {  
	          return true;  
	      } else {  
	          return false;  
	      }  
	  }
}