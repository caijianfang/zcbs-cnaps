package com.zcbspay.platform.cnaps.application.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zcbspay.platform.cnaps.application.dao.MerchBankAccountDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.MerchBankAccountDO;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

@Repository
public class MerchBankAccountImpl extends HibernateBaseDAOImpl<MerchBankAccountDO> implements MerchBankAccountDAO {

	@Override
	public MerchBankAccountDO getBankAccountByMerchNoAndChannlCode(
			String merchNo, String channelCode) {
		Criteria criteria = getSession().createCriteria(MerchBankAccountDO.class);
		criteria.add(Restrictions.eq("merchno", merchNo));
		criteria.add(Restrictions.eq("channelcode", channelCode));
		MerchBankAccountDO uniqueResult = (MerchBankAccountDO)criteria.uniqueResult();
		return uniqueResult;
	}

}
