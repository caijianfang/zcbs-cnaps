package com.zcbspay.platform.cnaps.application.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zcbspay.platform.cnaps.application.bean.ContractBean;
import com.zcbspay.platform.cnaps.application.dao.CnapsContractDAO;
import com.zcbspay.platform.cnaps.application.dao.pojo.CnapsContractDO;
import com.zcbspay.platform.cnaps.common.dao.impl.HibernateBaseDAOImpl;

@Repository
public class CnapsContractDAOImpl extends HibernateBaseDAOImpl<CnapsContractDO> implements CnapsContractDAO{

	private static final Logger logger = LoggerFactory.getLogger(CnapsContractDAOImpl.class);
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveContract(CnapsContractDO cnapsContract) {
		saveEntity(cnapsContract);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public CnapsContractDO queryContractByContractNo(String contractNo) {
		Criteria criteria = getSession().createCriteria(CnapsContractDO.class);
		criteria.add(Restrictions.eq("agreementnumber", contractNo));
		CnapsContractDO uniqueResult = (CnapsContractDO)criteria.uniqueResult();
		return uniqueResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public List<CnapsContractDO> queryCnapsContract(ContractBean contractBean) {
		Criteria criteria = getSession().createCriteria(CnapsContractDO.class);
		if(!StringUtils.isEmpty(contractBean.getContractType())){
			criteria.add(Restrictions.eq("contractagreementtype", contractBean.getContractType()));
		}
		if(!StringUtils.isEmpty(contractBean.getAgreementNumber())){
			criteria.add(Restrictions.eq("agreementnumber", contractBean.getAgreementNumber()));
		}
		if(!StringUtils.isEmpty(contractBean.getDebtorName())){
			criteria.add(Restrictions.eq("debtorname", contractBean.getDebtorName()));
		}
		if(!StringUtils.isEmpty(contractBean.getDebtorAccountNo())){
			criteria.add(Restrictions.eq("debtoraccountno", contractBean.getDebtorAccountNo()));
		}
		if(!StringUtils.isEmpty(contractBean.getDebtorIssuerCode())){
			criteria.add(Restrictions.eq("debtorissuercode", contractBean.getDebtorIssuerCode()));
		}
		if(!StringUtils.isEmpty(contractBean.getDebtorAgentCode())){
			criteria.add(Restrictions.eq("debtorAgentCode", contractBean.getDebtorAgentCode()));
		}
		if(!StringUtils.isEmpty(contractBean.getDebtorBranchCode())){
			criteria.add(Restrictions.eq("debtorBranchCode", contractBean.getDebtorBranchCode()));
		}
		if(!StringUtils.isEmpty(contractBean.getCreditorName())){
			criteria.add(Restrictions.eq("creditorName", contractBean.getCreditorName()));
		}
		return criteria.list();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateContractStatus(String contractNo, String status) {
		// TODO Auto-generated method stub
		String hql = "update CnapsContractDO set accountstatus = ? where agreementnumber = ?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, contractNo);
		query.setParameter(1, status);
		int rows = query.executeUpdate();
		logger.info("method updateContractStatus effect rows:"+rows);
	}

	
}
