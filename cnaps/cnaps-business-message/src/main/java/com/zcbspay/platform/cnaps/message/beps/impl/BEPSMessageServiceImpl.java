package com.zcbspay.platform.cnaps.message.beps.impl;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
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
import com.zcbspay.platform.cnaps.message.beps.assembly.WithholdBatchMsgAssembly;
import com.zcbspay.platform.cnaps.message.beps.assembly.WithholdRealTimeMsgAssembly;
import com.zcbspay.platform.cnaps.message.beps.result.CollectionChargesMessageResult;
import com.zcbspay.platform.cnaps.message.beps.result.PaymentMessageResult;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsCollectSingleLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentBatchLogDAO;
import com.zcbspay.platform.cnaps.message.dao.CnapsPaymentSingleLogDAO;
import com.zcbspay.platform.cnaps.utils.BeanCopyUtil;

@Service
public class BEPSMessageServiceImpl implements BEPSMessageService {

    private static final Logger logger = LoggerFactory.getLogger(WithholdBatchMsgAssembly.class);

    @Autowired
    private CnapsCollectBatchLogDAO cnapsCollectBatchLogDAO;
    @Autowired
    private CnapsPaymentBatchLogDAO cnapsPaymentBatchLogDAO;
    @Autowired
    private CollectionChargesMessageResult collectionChargesMessageResult;
    @Autowired
    private PaymentMessageResult paymentMessageResult;
    @Autowired
    private CnapsCollectSingleLogDAO cnapsCollectSingleLogDAO;
    @Autowired
    private CnapsPaymentSingleLogDAO cnapsPaymentSingleLogDAO;
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
        try {
            // 应答报文原始字符串转换为messagebean
            MessageBean messageBean = XMLUtils.parseToBean(message, MessageTypeEnum.BEPS381);
            // MessageBean转换为Document
            com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document) messageBean
                    .getCNAPSMessageBean();
            // 更新对应报文的应答信息
            cnapsCollectBatchLogDAO.updateBatchCollectionChargesRSP(document);
        }
        catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public ResultBean batchPaymentByAgencyRequest(PaymentTotalBean totalBean) {
        // 组装报文
        MessageBean messageBean = WithholdBatchMsgAssembly.batchWithholding(totalBean);
        // 保存交易流水
        cnapsPaymentBatchLogDAO.savePaymentBatchSerialInfo(totalBean);
        // 发送报文
        SendResult sendResult = null;
        try {
            sendResult = messageSend.send(XMLUtils.toXML(messageBean.getCNAPSMessageBean()), MessageCodeEnum.BEPS);
        }
        catch (JAXBException e) {
            // TODO
            logger.error("msg parse failed!!!", e);
        }
        // 获取最后交易结果
        com.zcbspay.platform.cnaps.common.bean.ResultBean batchPaymentChargesResult = paymentMessageResult.batchPaymentResult(sendResult);
        return BeanCopyUtil.copyBean(ResultBean.class, batchPaymentChargesResult);
    }

    @Override
    public ResultBean batchPaymentByAgencyResponse(String message) {
        try {
            // 应答报文原始字符串转换为messagebean
            MessageBean messageBean = XMLUtils.parseToBean(message, MessageTypeEnum.BEPS383);
            // MessageBean转换为Document
            com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document) messageBean
                    .getCNAPSMessageBean();
            // 更新对应报文的应答信息
            cnapsPaymentBatchLogDAO.updateBatchCollectionChargesRSP(document);
        }
        catch (JAXBException e) {
            // TODO
            logger.error("msg parse failed!!!", e);
        }
        return null;

    }

    @Override
    public ResultBean realTimeCollectionChargesRequest(SingleCollectionChargesDetailBean singleBean) {
        // 实时代收报文组装
        MessageBean messageBean = CollectionChargesMessageAssembly.realTimeCollectionCharges(singleBean);
        // 保存实时代收交易流水
        cnapsCollectSingleLogDAO.saveCollectSingleLog(singleBean);
        // 发送报文
        SendResult sendResult = null;
        try {
            sendResult = messageSend.send(XMLUtils.toXML(messageBean.getCNAPSMessageBean()), MessageCodeEnum.BEPS);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        // 获取最后交易结果
        com.zcbspay.platform.cnaps.common.bean.ResultBean resultBean = collectionChargesMessageResult.realTimeCollectionChargesResult(sendResult);
        return BeanCopyUtil.copyBean(ResultBean.class, resultBean);
    }

    @Override
    public ResultBean realTimeCollectionChargesResponse(String message) {
        try {
            // 应答报文原始字符串转换为messagebean
            MessageBean messageBean = XMLUtils.parseToBean(message, MessageTypeEnum.BEPS385);
            // MessageBean转换为Document
            com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document) messageBean
                    .getCNAPSMessageBean();
            // 更新对应报文的应答信息
            cnapsCollectSingleLogDAO.updateRealTimeCollectionChargesRSP(document);
        }
        catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public ResultBean realTimePaymentByAgencyRequest(SinglePaymentBean singleBean) {
        // 实时代收报文组装
        MessageBean messageBean = WithholdRealTimeMsgAssembly.realTimePayment(singleBean);
        // 保存实时代收交易流水
        cnapsPaymentSingleLogDAO.savePaymentSingleLog(singleBean);
        // 发送报文
        SendResult sendResult = null;
        try {
            sendResult = messageSend.send(XMLUtils.toXML(messageBean.getCNAPSMessageBean()), MessageCodeEnum.BEPS);
        }
        catch (JAXBException e) {
            // TODO
            logger.error("msg parse failed!!!", e);
        }
        // 获取最后交易结果
        com.zcbspay.platform.cnaps.common.bean.ResultBean resultBean = paymentMessageResult.realTimePaymentResult(sendResult);
        return BeanCopyUtil.copyBean(ResultBean.class, resultBean);
    }

    @Override
    public ResultBean realTimePaymentByAgencyResponse(String message) {
        try {
            // 应答报文原始字符串转换为messagebean
            MessageBean messageBean = XMLUtils.parseToBean(message, MessageTypeEnum.BEPS387);
            // MessageBean转换为Document
            com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document document = (com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document) messageBean
                    .getCNAPSMessageBean();
            // 更新对应报文的应答信息
            cnapsPaymentSingleLogDAO.updateRealTimePaymentRSP(document);
        }
        catch (JAXBException e) {
            // TODO
            logger.error("msg parse failed!!!", e);
        }
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
