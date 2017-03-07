package com.zcbspay.platform.cnaps.message.beps.impl;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.utils.XMLUtils;
import com.zcbspay.platform.cnaps.fe.send.MessageSend;
import com.zcbspay.platform.cnaps.fe.send.bean.MessageCodeEnum;
import com.zcbspay.platform.cnaps.fe.send.bean.SendResult;
import com.zcbspay.platform.cnaps.message.bean.AccountBean;
import com.zcbspay.platform.cnaps.message.bean.CollectionChargesTotalBean;
import com.zcbspay.platform.cnaps.message.bean.ContractBean;
import com.zcbspay.platform.cnaps.message.bean.ContractOperationEnum;
import com.zcbspay.platform.cnaps.message.bean.DetailCheckBean;
import com.zcbspay.platform.cnaps.message.bean.PaymentTotalBean;
import com.zcbspay.platform.cnaps.message.bean.ResultBean;
import com.zcbspay.platform.cnaps.message.bean.SingleCollectionChargesDetailBean;
import com.zcbspay.platform.cnaps.message.bean.SinglePaymentBean;
import com.zcbspay.platform.cnaps.message.beps.BEPSMessageService;
import com.zcbspay.platform.cnaps.message.beps.assembly.CollectionChargesMessageAssembly;
import com.zcbspay.platform.cnaps.message.beps.assembly.WithholdMessageAssembly;
import com.zcbspay.platform.cnaps.message.beps.result.CollectionChargesMessageResult;
import com.zcbspay.platform.cnaps.message.beps.result.PaymentMessageResult;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentBatchLogDAO;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;

@Service
public class BEPSMessageServiceImpl implements BEPSMessageService {

    private static final Logger logger = LoggerFactory.getLogger(WithholdMessageAssembly.class);

    @Autowired
    private CnapsCollectBatchLogDAO cnapsCollectBatchLogDAO;
    @Autowired
    private CnapsPaymentBatchLogDAO cnapsPaymentBatchLogDAO;
    @Autowired
    private CollectionChargesMessageResult collectionChargesMessageResult;
    @Autowired
    private PaymentMessageResult paymentMessageResult;

    @Reference(version = "1.0")
    private MessageSend messageSend;

    @Override
    public ResultBean batchCollectionChargesRequest(CollectionChargesTotalBean totalBean) {
        // 组装报文
        MessageBean messageBean = CollectionChargesMessageAssembly.batchCollectionCharges(totalBean);
        // 保存交易流水
        cnapsCollectBatchLogDAO.saveBatchCollectionCharges(totalBean);
        // 发送报文
        SendResult sendResult = null;
        try {
            sendResult = messageSend.send(XMLUtils.toXML(messageBean.getCNAPSMessageBean()), MessageCodeEnum.BEPS);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        // 获取最后交易结果
        com.zcbspay.platform.cnaps.common.bean.ResultBean batchCollectionChargesResult = collectionChargesMessageResult.batchCollectionChargesResult(sendResult);
        return BeanCopyUtil.copyBean(ResultBean.class, batchCollectionChargesResult);
    }

    @Override
    public ResultBean batchCollectionChargesResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean batchPaymentByAgencyRequest(PaymentTotalBean totalBean) {
        // 组装报文
        MessageBean messageBean = WithholdMessageAssembly.batchWithholding(totalBean);
        // 保存交易流水
        cnapsPaymentBatchLogDAO.savePaymentBatchSerialInfo(totalBean);
        // 发送报文
        SendResult sendResult = null;
        try {
            sendResult = messageSend.send(XMLUtils.toXML(messageBean.getCNAPSMessageBean()), MessageCodeEnum.BEPS);
        }
        catch (JAXBException e) {
            // TODO
            logger.error("send msg to cnaps failed!!!", e);
        }
        // 获取最后交易结果
        com.zcbspay.platform.cnaps.common.bean.ResultBean batchPaymentChargesResult = paymentMessageResult.batchPaymentResult(sendResult);
        return BeanCopyUtil.copyBean(ResultBean.class, batchPaymentChargesResult);
    }

    @Override
    public ResultBean batchPaymentByAgencyResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean realTimeCollectionChargesRequest(SingleCollectionChargesDetailBean singleBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean realTimeCollectionChargesResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean realTimePaymentByAgencyRequest(SinglePaymentBean singleBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean realTimePaymentByAgencyResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean businessRejectNotice(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean businessConfirmation(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean customerPaymentCancellationRequest(String txnseqno) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean customerPaymentCancellationResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean batchCustomersContractManage(ContractBean contractBean, ContractOperationEnum enums) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean batchCustomersContractManageResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean batchCustomersAccountQuery(AccountBean accountBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean batchCustomersAccountQueryResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean totalCheckInformationRequest(String checkDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean totalCheckInformationResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean detailCheckRequest(DetailCheckBean detailcheckbean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultBean detailCheckResponse(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transactionDownloadRequest() {
        // TODO Auto-generated method stub

    }

    @Override
    public void transactionDownloadResponse() {
        // TODO Auto-generated method stub

    }

    @Override
    public void accountCheck() {
        // TODO Auto-generated method stub

    }

}
