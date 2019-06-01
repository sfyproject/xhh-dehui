package com.store.module.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.store.service.ShareService;

@Component
public class ScheduledHandleClearShare {

	@Autowired
	ShareService shareService;

	@Scheduled(cron = "0 0 0 1/1 * ?")
	public void clearShare() throws Exception {
		shareService.deleteAll();
	}

}