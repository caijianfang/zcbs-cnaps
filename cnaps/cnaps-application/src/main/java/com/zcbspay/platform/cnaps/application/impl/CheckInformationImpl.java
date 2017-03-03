package com.zcbspay.platform.cnaps.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.zcbspay.platform.cnaps.application.CheckInformation;
import com.zcbspay.platform.cnaps.application.bean.DetailCheckBean;
import com.zcbspay.platform.cnaps.application.bean.TotalCheckBean;
import com.zcbspay.platform.cnaps.application.bean.TotalCheckMessageBean;
import com.zcbspay.platform.cnaps.application.bean.TotalCheckPaymentBean;
import com.zcbspay.platform.cnaps.bean.utils.XMLDateUtils;
import com.zcbspay.platform.cnaps.beps.bean.TotalCheckInformationResponse.CheckMessageDetails1;
import com.zcbspay.platform.cnaps.beps.bean.TotalCheckInformationResponse.CheckPaymentInformationDetails1;
import com.zcbspay.platform.cnaps.beps.bean.TotalCheckInformationResponse.Document;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.message.beps.BEPSMessageService;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;
import com.zcbspay.platform.cnaps.utils.DateUtil;

@Service
public class CheckInformationImpl implements CheckInformation {

	@Reference(version="1.0")
	private BEPSMessageService bepsMessageService;
	
	@Override
	public ResultBean totalCheckInformation(String checkDate) {
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.totalCheckInformationRequest(checkDate);
		if(resultBean.isResultBool()){
			Document document = (Document) resultBean.getResultObj();
			TotalCheckBean totalCheckBean = new TotalCheckBean();
			try {
				totalCheckBean.setCheckDate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(document.getTtlChck().getTtlChckInf().getChckDt())));
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<CheckPaymentInformationDetails1> chckPmtInfDtls = document.getTtlChck().getTtlChckInf().getChckPmtInf().getChckPmtInfDtls();
			List<TotalCheckPaymentBean> checkPaymentBeanList = Lists.newArrayList();
			for(CheckPaymentInformationDetails1 detail : chckPmtInfDtls){
				TotalCheckPaymentBean bean = new TotalCheckPaymentBean();
				bean.setMessageType(detail.getMT());
				bean.setProcessStatus(detail.getPrcSts());
				bean.setReceiveTotalAmount(detail.getRcvTtlAmt());
				bean.setReceiveTotalCount(detail.getRcvTtlCnt());
				bean.setSendTotalAmount(detail.getSndTtlAmt());
				bean.setSendTotalCount(detail.getSndTtlCnt());
				try {
					bean.setTransactionNettingDate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(detail.getTxNetgDt())));
				} catch (Exception e) {
					e.printStackTrace();
				}
				bean.setTransactionNettingRound(detail.getTxNetgRnd());
				checkPaymentBeanList.add(bean);
			}
			
			List<CheckMessageDetails1> chckMsgDtls = document.getTtlChck().getTtlChckInf().getChckMsg().getChckMsgDtls();
			List<TotalCheckMessageBean> totalCheckMessageList = Lists.newArrayList();
			for(CheckMessageDetails1 detail : chckMsgDtls){
				TotalCheckMessageBean bean = new TotalCheckMessageBean();
				bean.setMessageType(detail.getMT());
				try {
					bean.setReceiveDate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, XMLDateUtils.convertToDate(detail.getRcvDt())));
				} catch (Exception e) {
					e.printStackTrace();
				}
				bean.setReceiveTotalCount(detail.getRcvTtlCnt());
				bean.setSendTotalCount(detail.getSndTtlCnt());
				totalCheckMessageList.add(bean);
			}
			totalCheckBean.setTotalCheckMessageList(totalCheckMessageList);
			totalCheckBean.setTotalCheckPaymentList(checkPaymentBeanList);
			return new ResultBean(totalCheckBean);
		}
		return BeanCopyUtil.copyBean(ResultBean.class, resultBean);
	}

	@Override
	public ResultBean detailCheck(DetailCheckBean detailCheckBean) {
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.detailCheckRequest(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.message.bean.DetailCheckBean.class, detailCheckBean));
		return BeanCopyUtil.copyBean(ResultBean.class, resultBean);
	}

	@Override
	public void transactionDownloadRequestInformation() {
		// TODO Auto-generated method stub

	}

}
