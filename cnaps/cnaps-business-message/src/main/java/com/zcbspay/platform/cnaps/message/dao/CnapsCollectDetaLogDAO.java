package com.zcbspay.platform.cnaps.message.dao;

import com.zcbspay.platform.cnaps.common.dao.BaseDAO;
import com.zcbspay.platform.cnaps.message.pojo.CnapsCollectDetaLogDO;

public interface CnapsCollectDetaLogDAO extends BaseDAO<CnapsCollectDetaLogDO> {

	/**
	 * 保存代收明细交易数据
	 * @param cnapsCollectDetaLog
	 */
	public void saveCollectDetaLog(CnapsCollectDetaLogDO cnapsCollectDetaLog);
}
