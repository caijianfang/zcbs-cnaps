package com.zcbspay.platform.cnaps.application;

import com.zcbspay.platform.cnaps.application.bean.AccountBean;
import com.zcbspay.platform.cnaps.application.bean.ContractBean;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;

/**
 * 辅助类业务接口
 * 1.批量客户签约协议管(新增，撤销，查询，调整)
 * 2.批量账户信息查询
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:14:51
 * @since
 */
public interface AssistBusinesService {
	
	/**
	 * 查询合同协议
		1.根据商户号和协议号查询T_CNAPS_MERCH_CONTRACT表中是否有数据，如果有数据返回ResultBean,没有进入2
		2.赋值ContractOperationEnum枚举为查询
		3.调用batchCustomersContractManage方法
		4.如果查询成功但数据库中没有，则记录此协议数据到T_CNAPS_MERCH_CONTRACT
		5.返回ResultBean
	 * @param contractBean
	 * @return
	 */
	public ResultBean queryContract(ContractBean contractBean);
	
	/**
	 * 新增合同协议
		1.根据商户号和协议号查询T_CNAPS_MERCH_CONTRACT表中是否有数据，如果有数据返回ResultBean,没有进入2
		2.赋值ContractOperationEnum枚举为查询
		3.调用batchCustomersContractManage方法
		4.如果查询成功但数据库中没有，则记录此协议数据到T_CNAPS_MERCH_CONTRACT
		5.返回ResultBean
	 * @param contractBean
	 * @return
	 */
	public ResultBean addContract(ContractBean contractBean);
	
	/**
	 *  撤销合同协议
		1.根据商户号和协议号查询T_CNAPS_MERCH_CONTRACT表中是否有数据，如果有数据返回ResultBean进入2，没有返回异常
		2.赋值ContractOperationEnum枚举为撤销
		3.调用batchCustomersContractManage方法
		4.更新记录此协议数据到T_CNAPS_MERCH_CONTRACT
		5.返回ResultBean
	 * @param contractBean
	 * @return
	 */
	public ResultBean repealContract(ContractBean contractBean);
	
	/**
	 * 查询账户信息
		1.查询T_CNAPS_CUSTOMER_ACCOUNT是否有数据存在，如果没有进入2，有则返回账户信息
		2.调用batchCustomersAccountQuery()方法
		3.根据2的返回值返回ResultBean
	 * @param accountBean
	 * @return
	 */
	public ResultBean queryAccount(AccountBean accountBean);
}
