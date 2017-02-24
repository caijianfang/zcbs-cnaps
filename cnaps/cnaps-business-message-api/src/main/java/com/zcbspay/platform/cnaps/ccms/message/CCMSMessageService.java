package com.zcbspay.platform.cnaps.ccms.message;

/**
 * 公共控制系统报文组装和接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午5:13:50
 * @since
 */
public interface CCMSMessageService {

	/**
	 * 自由格式报文
	 */
	public void freeFormat();
	/**
	 * 业务撤销申请报文
	 */
	public void fiToFIPaymentCancellationRequest();
	/**
	 * 业务撤销应答报文
	 */
	public void fiToFIPaymentStatuReport();
	/**
	 * 通用非签名信息业务报文
	 */
	public void commonNotSignatureInformationBusiness();
	/**
	 * 通用非签名信息业务应答报文
	 */
	public void commonNotSignatureInformationBusiNessResponse();
	/**
	 * 通用签名信息业务报文
	 */
	public void commonSignatureInformationBusiness();
	/**
	 * 通用签名信息业务应答报文
	 */
	public void commonSignatureInformationBusiNessResponse();
	/**
	 * 业务查询报文
	 */
	public void businessQueryRequest();
	/**
	 * 业务查复报文
	 */
	public void businessQueryResponse();
	/**
	 * 业务状态查询申请报文
	 */
	public void queryTransactionRequest();
	/**
	 * 业务状态查询应答报文
	 */
	public void queryTransactionResponse();
	/**
	 * 业务退回申请报文
	 */
	public void paymentReturnRequest();
	/**
	 * 业务退回应答报文
	 */
	public void paymentReturnResponse();
	/**
	 * 系统状态变更通知报文
	 */
	public void systemStatusNotification();
	/**
	 * 参与机构状态变更通知报文
	 */
	public void partOrgStatusNotification();
	/**
	 * 登录/退出申请报文
	 */
	public void loginRequest();
	/**
	 * 登录/退出应答报文
	 */
	public void loginResponse();
	/**
	 * 强制离线通知报文
	 */
	public void kickoutNotification();
	/**
	 * 停启运通知报文
	 */
	public void stoppingNotification();
	/**
	 * ACS专用时间结束通知报文
	 */
	public void ACSSpecialTimeEndNotification();
	/**
	 * 通用处理确认报文
	 */
	public void commonConfirmation(String message);
	/**
	 * 数字证书绑定通知报文
	 */
	public void certificateNotification();
	/**
	 * 业务种类与类型管理报文
	 */
	public void businessClassTypeManagement();
	/**
	 * 系统参数变更通知报文
	 */
	public void systemParameterNotification();
	/**
	 * 报文丢弃通知报文
	 */
	public void discardMessageNotification();
	/**
	 * CIS代理行变更通知报文
	 */
	public void CISAgencyChangeNotification();
	/**
	 * 业务权限变更通知报文
	 */
	public void authorityChangeNotification();
	/**
	 * 行名行号变更通知报文
	 */
	public void bankCodeChangeNotification();
	/**
	 * 基础数据变更通知报文
	 */
	public void basisChangeNotification();
	/**
	 * 数字证书下载申请报文
	 */
	public void certificateDownLoadApply();
	/**
	 * 数字证书下载应答报文
	 */
	public void certificateDownLoadResponse();
	/**
	 * 业务金额上限下发报文
	 */
	public void transactionAmontUpperLimit();
	/**
	 * 通信级确认报文
	 */
	public void communicationConfirmation();
	/**
	 * 探测请求报文
	 */
	public void checkRequest();
	/**
	 * 探测回应报文
	 */
	public void checkResponse();
}
