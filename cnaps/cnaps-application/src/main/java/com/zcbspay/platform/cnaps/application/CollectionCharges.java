package com.zcbspay.platform.cnaps.application;

import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.common.bean.TradeBean;


/**
 * 代收业务接口
 *
 * @author guojia
 * @version
 * @date 2017年2月23日 下午3:08:10
 * @since
 */
public interface CollectionCharges {
	/**
	 * 批量代收，从批量表和明细表中获取数据，将明细表中的数据赋值进bean中，调用batchCollectionChargesRequest()方法<br>
		1.根据批次号获取代收批次数据<br>
		2.根据批次号获取代收的明细数据<br>
		3.根据代收的商户号查询出，收款账号的信息及其签约信息，如果付款账户为和商户签约拒绝交易<br>
		4.赋值CollectionChargesTotalBean和CollectionChargesDetailBean<br>
		5.调用batchCollectionChargesRequest方法<br>
		6.根据4的返回值返回ResultBean<br>
	 * @param batchNo
	 * @return ResultBean
	 */
	public ResultBean batchCollectionCharges(String batchNo);
	
	/**
	 *  代收交易状态，调用此方法钱需从代收明细表中取得明细标识号。这块有些不明确，这个明细标识号可能是批次报文的标示号，也可能是明细交易的标示号，甚至有可能两者都可以，联调时需要注意确定一下<br>
		1.根据交易流水号取得交易流水数据<br>
		2.根据交易流水号取得代收的批次信息和明细信息，人行的总流水信息<br>
		3.取得需要的标示号，报文类型固定，发送方<br>
		4.赋值BusiQueryBean<br>
		5.调用queryTransactionRequest方法<br>
		6.根据返回结果赋值ResultBean<br>
	 * @param txnseqno
	 * @return
	 */
	public ResultBean batchCollectionChargesQuery(String txnseqno);
	
	/**
	 * 实时代收,<br>
		1.根据收款商户获取其收款账户信息，判断付款账户是否签约，未签约拒绝交易<br>
		2.赋值SingleCollectionChargesDetailBean<br>
		3.调用realTimeCollectionChargesRequest方法<br>
		4.根据3的返回值返回ResultBean<br>
	 * @param tradeBean
	 * @return
	 */
	public ResultBean realTimeCollectionCharges(TradeBean tradeBean);
	
	/**
	 * 实时代收交易查询<br>
	 *  1.根据交易流水号取得交易流水数据<br>
		2.根据交易流水号取得单笔实时代收的原渠道交易流水<br>
		3.取得需要的标示号，报文类型固定，发送方<br>
		4.赋值BusiQueryBean<br>
		5.调用queryTransactionRequest方法<br>
		6.根据返回结果赋值ResultBean<br>
	 * @param txnseqno
	 * @return
	 */
	public ResultBean realTimeCollectionChargesQuery(String txnseqno);
}
