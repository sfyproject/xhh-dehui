package com.store.back.controller.operationRecord;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/operationRecord")
public class PageOperationRecordController {
	@RequiresPermissions("back:operationRecord:list")
	@RequestMapping("/list")
	public String list() {
		return "operationRecord/list";
	}
	@RequiresPermissions("back:operationRecord:lowerLimit")
	@RequestMapping("/lowerLimit")
	public String lowerLimitList() {
		return "operationRecord/lowerLimit";
	}
	
}
