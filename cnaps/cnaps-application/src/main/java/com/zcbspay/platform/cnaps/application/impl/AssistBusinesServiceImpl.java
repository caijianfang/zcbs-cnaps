package com.zcbspay.platform.cnaps.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.cnaps.application.AssistBusinesService;
import com.zcbspay.platform.cnaps.application.bean.AccountBean;
import com.zcbspay.platform.cnaps.application.bean.ContractBean;
import com.zcbspay.platform.cnaps.application.dao.CnapsContractDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsContractDO;
import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.AccountDetails1;
import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.Document;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.message.bean.ContractOperationEnum;
import com.zcbspay.platform.cnaps.message.beps.BEPSMessageService;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;

@Service
public class AssistBusinesServiceImpl implements AssistBusinesService {

	@Autowired
	private CnapsContractDAO cnapsContractDAO;
	
	@Reference(version="1.0")
	private BEPSMessageService bepsMessageService;
	@Override
	public ResultBean queryContract(ContractBean contractBean) {
		List<CnapsContractDO> contractList = cnapsContractDAO.queryCnapsContract(contractBean);
		if(contractList!=null){
			if(contractList.size()>0){
				return new ResultBean(contractList);
			}
		}
		//调用CNAPS接口查询
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersContractManage(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.message.bean.ContractBean.class, contractBean), ContractOperationEnum.QUERY);
		if(resultBean.isResultBool()){
			//需根据返回报文进行赋值处理
			Document doc = (Document) resultBean.getResultObj();
			List<AccountDetails1> acctDtls = doc.getBtchCstmrsCtrctMgRspn().getBtchCstmrsCtrctMgRspnInf().getAcctDtls();
			if(acctDtls.size()>0){
				String status = acctDtls.get(0).getAcctSts().value();
				CnapsContractDO cnapsContract = BeanCopyUtil.copyBean(CnapsContractDO.class, contractBean);
				cnapsContract.setAccountstatus(status);
				cnapsContractDAO.saveContract(cnapsContract);
			}
		}else{
			
		}
		return null;
	}

	@Override
	public ResultBean addContract(ContractBean contractBean) {
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersContractManage(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.message.bean.ContractBean.class, contractBean), ContractOperationEnum.ADD);
		if(resultBean.isResultBool()){
			//需根据返回报文进行赋值处理
			Document doc = (Document) resultBean.getResultObj();
			List<AccountDetails1> acctDtls = doc.getBtchCstmrsCtrctMgRspn().getBtchCstmrsCtrctMgRspnInf().getAcctDtls();
			if(acctDtls.size()>0){
				String status = acctDtls.get(0).getAcctSts().value();
				CnapsContractDO cnapsContract = BeanCopyUtil.copyBean(CnapsContractDO.class, contractBean);
				cnapsContract.setAccountstatus(status);
				cnapsContractDAO.saveContract(cnapsContract);
			}
 		}else{
			
		}
		
		return null;
	}

	@Override
	public ResultBean repealContract(ContractBean contractBean) {
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersContractManage(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.message.bean.ContractBean.class, contractBean), ContractOperationEnum.CANCEL);
		if(resultBean.isResultBool()){
			//CnapsContractDO cnapsContract = BeanCopyUtil.copyBean(CnapsContractDO.class, contractBean);
			//需根据返回报文进行赋值处理
			Document doc = (Document) resultBean.getResultObj();
			List<AccountDetails1> acctDtls = doc.getBtchCstmrsCtrctMgRspn().getBtchCstmrsCtrctMgRspnInf().getAcctDtls();
			if(acctDtls.size()>0){
				String status = acctDtls.get(0).getAcctSts().value();
				cnapsContractDAO.updateContractStatus(contractBean.getAgreementNumber(), status);
			}
			
		}else{
			
		}
		return null;
	}

	@Override
	public ResultBean queryAccount(AccountBean accountBean) {
		com.zcbspay.platform.cnaps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersAccountQuery(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.message.bean.AccountBean.class, accountBean));
		if(resultBean.isResultBool()){
			com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document) resultBean.getResultObj();
			List<com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.AccountDetails1> acctDtls = document.getBtchCstmrsAcctQryRspn().getBtchCstmrsAcctQryRspnInf().getAcctDtls();
			if(acctDtls.size()>0){
				String status = acctDtls.get(0).getAcctSts().value();
				accountBean.setAccountStatus(status);
				return new ResultBean(accountBean);
			}
		}
		return null;
	}

}
