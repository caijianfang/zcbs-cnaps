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
import com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document;
import com.zcbspay.platform.cnaps.beps.message.BEPSMessageService;
import com.zcbspay.platform.cnaps.beps.message.bean.ContractOperationEnum;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;
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
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersContractManage(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.beps.message.bean.ContractBean.class, contractBean), ContractOperationEnum.QUERY);
		if(resultBean.isResultBool()){
			//需根据返回报文进行赋值处理
			
		}else{
			
		}
		return null;
	}

	@Override
	public ResultBean addContract(ContractBean contractBean) {
		com.zcbspay.platform.cnaps.beps.message.bean.ResultBean resultBean = bepsMessageService.batchCustomersContractManage(BeanCopyUtil.copyBean(com.zcbspay.platform.cnaps.beps.message.bean.ContractBean.class, contractBean), ContractOperationEnum.ADD);
		if(resultBean.isResultBool()){
			CnapsContractDO cnapsContract = BeanCopyUtil.copyBean(CnapsContractDO.class, contractBean);
			//需根据返回报文进行赋值处理
			//Document doc = (Document) resultBean.getResultObj();
			//cnapsContract.setAccountstatus(doc.getBtchCstmrsAcctQryRspn().getBtchCstmrsAcctQryRspnInf().getAcctDtls().get(0).getAcctSts().value());
			cnapsContractDAO.saveContract(cnapsContract);
		}else{
			
		}
		
		return null;
	}

	@Override
	public ResultBean repealContract(ContractBean contractBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean queryAccount(AccountBean accountBean) {
		// TODO Auto-generated method stub
		return null;
	}

}
