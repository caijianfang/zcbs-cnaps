package com.zcbspay.platform.cnaps.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcbspay.platform.cnaps.application.AssistBusinesService;
import com.zcbspay.platform.cnaps.application.bean.AccountBean;
import com.zcbspay.platform.cnaps.application.bean.ContractBean;
import com.zcbspay.platform.cnaps.application.bean.ResultBean;
import com.zcbspay.platform.cnaps.application.service.AssistApplicationService;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;

@Service(retries=0,version="1.0",timeout=60000)
@org.springframework.stereotype.Service
public class AssistApplicationServiceImpl implements AssistApplicationService {

	@Autowired
	private AssistBusinesService assistBusinesService;
	@Override
	public ResultBean queryContract(ContractBean contractBean) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, assistBusinesService.queryContract(contractBean));
	}

	@Override
	public ResultBean addContract(ContractBean contractBean) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, assistBusinesService.addContract(contractBean));
	}

	@Override
	public ResultBean repealContract(ContractBean contractBean) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, assistBusinesService.repealContract(contractBean));
	}

	@Override
	public ResultBean queryAccount(AccountBean accountBean) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, assistBusinesService.queryAccount(accountBean));
	}

}
