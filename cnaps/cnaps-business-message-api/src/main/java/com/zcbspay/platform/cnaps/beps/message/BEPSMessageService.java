package com.zcbspay.platform.cnaps.beps.message;

import com.zcbspay.platform.cnaps.beps.message.bean.AccountBean;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.beps.message.bean.ContractBean;
import com.zcbspay.platform.cnaps.beps.message.bean.ContractOperationEnum;
import com.zcbspay.platform.cnaps.beps.message.bean.DetailCheckBean;
import com.zcbspay.platform.cnaps.beps.message.bean.PaymentDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.beps.message.bean.ResultBean;
import com.zcbspay.platform.cnaps.beps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.beps.message.bean.SinglePaymentBean;

/**
 * 小额支付系统报文组装和解析接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午4:56:08
 * @since
 */
public interface BEPSMessageService {

	/**
	 *  批量代收请求方法<br>
	 *	根据汇总bean和明细bean的数据,组装报文bean，保存CNAPS代收批次流水和明细流水，<br>
	 *	发送至队列<br>
	 *	1.根据totalBean赋值批量代收报文bean的收付款信息相关的域，汇总信息域<br>
	 *	2.根据detailBean赋值批量代收报文bean的收款信息详细域<br>
	 *	3.保存T_TXNS_CNAPS_LOG数据，保存T_TXNS_CNAPS_COLLECT_BATCH_LOG和T_TXNS_CNAPS_COLLECT_DETA_LOG<br>
	 *	4.调用assemble方法包装报文返回报文字符串<br>
	 *	5.调用报文发送方法<br>
	 *	6.40s轮询批次表获取批次状态<br>
	 *	注意：现在设计中均不涉及账务，账务相关的需要确认交易模式后才能确定<br>
	 * @param totalBean
	 * @return
	 */
	public ResultBean batchCollectionChargesRequest(CollectionChargesTotalBean totalBean);
	
	/**
	 * 报文接收模块接收到消息后，分析报文类型后，调用此方法解析批量代收回执报文
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean batchCollectionChargesResponse(String message);
	
	/**
	 * 批量代付
		根据汇总bean和明细bean的数据,组装报文bean，保存CNAPS代付批次流水和明细流水，
		发送至队列
		1.根据totalBean赋值批量代收报文bean的收付款信息相关的域，汇总信息域
		2.根据detailBean赋值批量代收报文bean的收款信息详细域
		3.保存T_TXNS_CNAPS_LOG数据，保存T_TXNS_CNAPS_PAYMENT_BATCH_LOG和T_TXNS_CNAPS_PAYMENT_DETA_LOG
		4.调用assemble方法包装报文返回报文字符串
		5.调用报文发送方法
		6.40s轮询批次表获取批次状态
	 * @param totalBean
	 * @param detailBean
	 * @return
	 */
	public ResultBean batchPaymentByAgencyRequest(PaymentTotalBean totalBean, PaymentDetailBean detailBean);
	
	/**
	 * 报文接收模块接收到消息后，分析报文类型后，调用此方法解析批量代收回执报文
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean batchPaymentByAgencyResponse(String message);
	
	/**
	 * 单笔实时代收
		1.根据singleBean的字段组装报文体
		2.记录单笔代收的交易流水，核心流水和实时代收流水
		3.调用组装报文方法
		4,调用报文的发送方法
	 * @param singleBean
	 * @return
	 */
	public ResultBean realTimeCollectionChargesRequest(SingleCollectionChargesDetailBean singleBean);
	
	/**
	 * 报文接收模块接收到消息后，分析报文类型后，调用此方法解析实时代收回执报文
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param String
	 * @return
	 */
	public ResultBean realTimeCollectionChargesResponse(String message);
	
	/**
	 * 单笔实时代付
		1.根据singleBean的字段组装报文体
		2.记录单笔代付的交易流水，核心流水和实时代收流水
		3.调用组装报文方法
		4,调用报文的发送方法
	 * @param singleBean
	 * @return
	 */
	public ResultBean realTimePaymentByAgencyRequest(SinglePaymentBean singleBean);
	
	/**
	 * 报文接收模块接收到消息后，分析报文类型后，调用此方法解析实时代收回执报文
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean realTimePaymentByAgencyResponse(String message);
	
	/**
	 * 代收付业务拒绝通知处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
		注意：代收交易会有多种报文返回，所以在代收付的表中针对每种返回的报文都预留了相应的字段，更新是只更新对应的字段，其他字段不更新，在开发DAO时需要开发相对应的更新方法
	 * @param message
	 * @return
	 */
	public ResultBean businessRejectNotice(String message);
	
	/**
	 * 代收付业务收付款确认处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean businessConfirmation(String message);
	
	/**
	 * 代收代付撤销申请
		1.通过交易序列号取得原交易的人行核心流水数据
		2.根据原报文的msgid和报文类型，组装撤销申请bean
		3.记录流水到T_TXNS_PAYMENT_CANCEL_LOG表中
	 * @param txnseqno
	 * @return
	 */
	public ResultBean customerPaymentCancellationRequest(String txnseqno);
	
	/**
	 * 代收付业务撤销应答报文处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean customerPaymentCancellationResponse(String message);
	
	/**
	 * 批量客户签约协议管理
		现在此方法暂时先设计为单笔的，批量的以后再做
		1.根据contractBean的字段组和枚举enum组装报文体
		2.记录签约的交易流水，核心流水和批量流水流水
		3.调用组装报文方法
		4,调用报文的发送方法
	 * @param contractBean
	 * @param enums
	 * @return
	 */
	public ResultBean batchCustomersContractManage(ContractBean contractBean,ContractOperationEnum enums);
	
	/**
	 * 批量客户签约协议管理应答报文处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean batchCustomersContractManageResponse(String message);
	
	/**
	 * 批量客户账户信息查询
		1.根据accountBean组装报文体
		2.记录交易流水T_SYS_CNAPS_MERCH_CONTRACT_BATCH_LOG和T_TXNS_CNAPS_PAYMENT_DETA_LOG
		3.调用组装报文方法
		4。调用报文的发送方法
	 * @param AccountBean
	 * @return
	 */
	public ResultBean batchCustomersAccountQuery(AccountBean accountBean);
	
	/**
	 * 批量客户账户查询应答报文处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean batchCustomersAccountQueryResponse(String message);
	
	/**
	 * 小额业务对账申请报文
		1.使用对账日期组装报文体
		2.记录交易流水T_CNAPS_TOTAL_BILL
		3.调用组装报文方法
		4,调用报文的发送方法
	 * @param checkDate
	 * @return
	 */
	public ResultBean totalCheckInformationRequest(String checkDate);
	
	/**
	 * 小额业务包汇总核对报文处理方法
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean totalCheckInformationResponse(String message);
	
	/**
	 * 额业务包明细核对申请报文
		此方法的参数有总账中的数据决定，调用此方法前必须调用总账处理方法
		1.根据detailcheckbean组装报文体
		2.记录交易流水T_CNAPS_DETAIL_BILL_APPLY
		3.调用组装报文方法
		4,调用报文的发送方法
	 * @param detailcheckbean
	 * @return
	 */
	public ResultBean detailCheckRequest(DetailCheckBean detailcheckbean);
	
	/**
	 * 小额业务包明细核对应答报文
		1.解析报文头，组装成MessageHeadBean
		2.验签报文体
		3.将报文体组装成MessageBean
		4.更新原交易应答信息的字段
	 * @param message
	 * @return
	 */
	public ResultBean detailCheckResponse(String message);
	
	public void transactionDownloadRequest();
	
	public void transactionDownloadResponse();
	
	public void accountCheck();
	
}
