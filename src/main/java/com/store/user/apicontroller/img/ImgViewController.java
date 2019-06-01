package com.store.user.apicontroller.img;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class ImgViewController {

	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;

	/**
	 * 获取图片
	 * 
	 * @param pathdate
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/{pathdate}/{fileName:.+}")
	public void getImage(@PathVariable("pathdate") String pathdate, @PathVariable("fileName") String fileName,
			HttpServletResponse response) throws IOException {
		String relativeFileName = "/" + pathdate + "/" + fileName;
		OutputStream os = response.getOutputStream();
		try {
			response.reset();
			response.setContentType("image/png; charset=utf-8");
			os.write(FileUtils.readFileToByteArray(new File(imgPathPrefix, relativeFileName)));
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

}