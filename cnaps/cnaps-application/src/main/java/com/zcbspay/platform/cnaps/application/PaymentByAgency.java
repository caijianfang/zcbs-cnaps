package com.zcbspay.platform.cnaps.application;

import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;


/**
 * 代付接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:38:39
 * @since
 */
public interface PaymentByAgency {

	/**
	 * 批量代付
		从数据库中读取的代收数据可能有多家不同银行卡，这里需要将同一家银行的银行卡进行打包赋值到bean中，这里会有一个分批算法处理读取出来的代收数据
		1.根据批次号获取代付批次数据
		2.根据批次号获取代付的明细数据
		3.根据代收的商户号查询出，收款账号的信息及其签约信息，如果商户未签署代付协议拒绝交易
		4.赋值PaymentTotalBean和PaymentDetailBean
		5.调用batchPaymentByAgencyRequest方法
		6.根据4的返回值返回ResultBean
	 * @param batchNo
	 * @return
	 */
	public ResultBean batchPaymentByAgency(String batchNo);
	
	/**
	 * 批量代付查询
		调用人行CCMS接口,查询单笔的代收交易状态，调用此方法钱需从代收明细表中取得明细标识号。这块有些不明确，这个明细标识号可能是批次报文的标示号，也可能是明细交易的标示号，甚至有可能两者都可以，联调时需要注意确定一下
		1.根据交易流水号取得交易流水数据
		2.根据交易流水号取得代付的批次信息和明细信息，人行的总流水信息
		3.取得需要的标示号，报文类型固定，发送方
		4.赋值BusiQueryBean
		5.调用queryTransactionRequest方法
		6.根据返回结果赋值ResultBean
	 * @param msgId
	 * @return
	 */
	public ResultBean batchPaymentByAgencyQuery(String msgId);
	
	/**
	 * 实时代付
		1.根据收款商户获取其收款账户信息，判断商户是否开通代付协议，未开通则拒绝交易
		2.赋值SinglePaymentBean
		3.调用realTimeCollectionChargesRequest方法
		4.根据3的返回值返回ResultBean
	 * @param tradeBean
	 * @return
	 */
	public ResultBean realTimePaymentByAgency(TradeBean tradeBean);
	
	/**
	 * 实时代付查询
		1.根据交易流水号取得交易流水数据
		2.根据交易流水号取得单笔实时代收的原渠道交易流水
		3.取得需要的标示号，报文类型固定，发送方
		4.赋值BusiQueryBean
		5.调用queryTransactionRequest方法
		6.根据返回结果赋值ResultBean
	 * @param txnseqno
	 * @return
	 */
	public ResultBean realTimePaymentByAgencyQuery(String txnseqno);
}
