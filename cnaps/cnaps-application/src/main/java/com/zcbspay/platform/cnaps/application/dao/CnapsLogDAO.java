package com.zcbspay.platform.cnaps.application.dao;

import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsLogDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface CnapsLogDAO extends BaseDAO<CnapsLogDO>{

	/**
	 * 通过报文标示号获取核心流水文件
	 * @param msgid
	 * @return
	 */
	public CnapsLogDO getCNAPSLogByMsgid(String msgid);
}
