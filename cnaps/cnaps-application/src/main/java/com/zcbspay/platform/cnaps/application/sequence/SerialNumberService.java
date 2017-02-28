package com.zcbspay.platform.cnaps.application.sequence;

public interface SerialNumberService {

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
}
