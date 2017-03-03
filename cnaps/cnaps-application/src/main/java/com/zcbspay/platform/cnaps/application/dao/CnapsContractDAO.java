package com.zcbspay.platform.cnaps.application.dao;

import java.util.List;

import com.zcbspay.platform.cnaps.application.bean.ContractBean;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsContractDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface CnapsContractDAO extends BaseDAO<CnapsContractDO>{

	/**
	 * 保存合同协议
	 * @param cnapsContract
	 */
	public void saveContract(CnapsContractDO cnapsContract);
	
	/**
	 * 通过协议合同号查询协议信息
	 * @param contractNo
	 * @return
	 */
	public CnapsContractDO queryContractByContractNo(String contractNo);
	
	/**
	 * 查询协议信息
	 * @param contractBean 
	 * @return
	 */
	public List<CnapsContractDO> queryCnapsContract(ContractBean contractBean);
	
	/**
	 * 更新合同协议状态
	 * @param contractNo
	 * @param status
	 */
	public void updateContractStatus(String contractNo,String status);
}
