package com.zcbspay.platform.cnaps.application.dao;

import com.zcbspay.platform.cnaps.application.dao.pojo.MerchBankAccountDO;
import com.zcbspay.platform.cnaps.common.dao.BaseDAO;

public interface MerchBankAccountDAO extends BaseDAO<MerchBankAccountDO> {

	/**
	 * 根据商户号和渠道代码获取商户对应的收付款账户
	 * @param merchNo 商户号
	 * @param channelCode 渠道代码
	 * @return
	 */
	public MerchBankAccountDO getBankAccountByMerchNoAndChannlCode(String merchNo,String channelCode);
}
