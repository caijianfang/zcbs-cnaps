package com.zcbspay.platform.cnaps.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.zcbspay.platform.cnaps.application.CheckInformation;
import com.zcbspay.platform.cnaps.application.bean.DetailCheckBean;
import com.zcbspay.platform.cnaps.application.bean.ResultBean;
import com.zcbspay.platform.cnaps.application.service.CheckBillService;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;

@Service(retries=0,version="1.0",timeout=60000)
@org.springframework.stereotype.Service
public class CheckBillServiceImpl implements CheckBillService {

	@Autowired
	private CheckInformation checkInformation;
	
	
	@Override
	public ResultBean totalCheckInformation(String checkDate) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, checkInformation.totalCheckInformation(checkDate));
	}

	@Override
	public ResultBean detailCheck(DetailCheckBean detailCheckBean) {
		// TODO Auto-generated method stub
		return BeanCopyUtil.copyBean(ResultBean.class, checkInformation.detailCheck(detailCheckBean));
	}

}
