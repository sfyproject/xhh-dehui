package com.store.back.apicontroller.consume;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.store.model.Consume;
import com.store.model.req.PageQuery;
import com.store.service.ConsumeService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/consume")
public class BackConsumeController {
	Logger log = LoggerFactory.getLogger(BackConsumeController.class);
	
	@Autowired
	private ConsumeService consumeService;

	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param goods
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/account/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		Consume consume= JSON.parseObject(JSON.toJSONString(params), Consume.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Consume> pageInfo = consumeService.page(query, consume);
		List<Consume> consumeList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(consumeList, total);
		return pageUtil;
	}

}