package com.zcbspay.platform.cnaps.application;

import com.zcbspay.platform.cnaps.application.bean.DetailCheckBean;
import com.zcbspay.platform.cnaps.common.bean.ResultBean;

/**
 * 
 * 对账接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:35:23
 * @since
 */
public interface CheckInformation {

	/**
	 * 小额业务对账申请(总账)
		1.调用totalCheckInformationRequest方法
		2.根据返回值（报文的应答数据）返回总账数据包括资金类的和非资金的
	 * @param checkDate
	 * @return
	 */
	public ResultBean totalCheckInformation(String checkDate);
	
	/**
	 * 小额业务包明细核对(明细对账文件)
		1.这里只会下载资金类的对账文件，所以DetailCheckBean中只有DetailCheckPaymentInformation的数据
		2.调用detailCheckRequest方法
		3.根据返回值保持对账明细数据到T_CNAPS_DETAIL_BILL表中
	 * @param detailCheckBean
	 * @return
	 */
	public ResultBean detailCheck(DetailCheckBean detailCheckBean);
	
	/**
	 * 小额业务包下载，暂时不开发
	 */
	public void transactionDownloadRequestInformation();
}
