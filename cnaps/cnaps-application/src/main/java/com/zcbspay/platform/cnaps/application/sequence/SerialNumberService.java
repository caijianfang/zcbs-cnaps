package com.zcbspay.platform.cnaps.application.sequence;

public interface SerialNumberService {

	/**
	 * 生成人行报文标示号
	 * @return
	 */
	public String generateMsgId();
	/**
	 * 生成批量代收批次序号
	 * @return
	 */
	public String generateBatchCollectSerialNo();
	
	/**
	 * 生成批量代收批次明细序号
	 * @return
	 */
	public String generateCollectDetaSerialNo();
	
	/**
	 * 生成实时代收明细序号
	 * @return
	 */
	public String generateRealTimeCollectSerialNo();
	/**
	 * 生成实时代收批次号
	 * @return
	 */
	public String generateRealTimeCollectBatchNo();
	
	
	
	
	/**
	 * 生成批量代收批次序号
	 * @return
	 */
	public String generateBatchPaymentSerialNo();
	
	/**
	 * 生成批量代收批次明细序号
	 * @return
	 */
	public String generatePaymentDetaSerialNo();
	
	/**
	 * 生成实时代收明细序号
	 * @return
	 */
	public String generateRealTimePaymentSerialNo();
	/**
	 * 生成实时代收批次号
	 * @return
	 */
	public String generateRealTimePaymentBatchNo();
}
