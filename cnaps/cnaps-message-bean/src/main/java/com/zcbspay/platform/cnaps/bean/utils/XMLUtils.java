package com.zcbspay.platform.cnaps.bean.utils;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.zcbspay.platform.cnaps.bean.DefaultMessageBean;
import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;

public class XMLUtils {

	public static String toXML(Object object) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
		// marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
		// "");
		// marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "");
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(object, stringWriter);
		return stringWriter.toString();
	}

	// xml转换成bean
	public static MessageBean parseToBean(String xml, MessageTypeEnum messageTypeEnum) throws JAXBException {
		Object message = null;
		JAXBContext jaxbContext = null;
		switch (messageTypeEnum) {
		case BEPS380:
			jaxbContext = JAXBContext
					.newInstance(com.zcbspay.platform.cnaps.beps.bean.batchcollectioncharges.Document.class);
			break;
		case BEPS381:
			jaxbContext = JAXBContext
					.newInstance(com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document.class);
			break;
		case BEPS382:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagency.Document.class);
			break;
		case BEPS383:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.batchpaymentbyagencyresponse.Document.class);
			break;
		case BEPS384:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionCharges.Document.class);
			break;
		case BEPS385:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.RealTimeCollectionChargesResponse.Document.class);
			break;
		case BEPS386:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgency.Document.class);
			break;
		case BEPS387:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.RealTimePaymentByAgencyResponse.Document.class);
			break;
		case BEPS388:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BusinessRejectNotice.Document.class);
			break;
		case BEPS389:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BusinessConfirmation.Document.class);
			break;
		case BEPS390:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationRequest.Document.class);
			break;
		case BEPS391:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.CustomerPaymentCancellationResponse.Document.class);
			break;
		case BEPS392:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageRequest.Document.class);
			break;
		case BEPS393:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BatchCustomersContractManageResponse.Document.class);
			break;
		case BEPS394:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryRequest.Document.class);
			break;
		case BEPS395:
			jaxbContext = JAXBContext
			.newInstance(com.zcbspay.platform.cnaps.beps.bean.BatchCustomersAccountQueryResponse.Document.class);
			break;
		case CCMS900:
			jaxbContext = JAXBContext
					.newInstance(com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.Document.class);
			break;
		default:
			break;
		}
		Unmarshaller um = jaxbContext.createUnmarshaller();
		// message = um.unmarshal(new ByteArrayInputStream(xml.getBytes()));
		message = um.unmarshal(new File(xml));
		return new DefaultMessageBean(message, messageTypeEnum);

		/*
		 * try { JAXBContext jaxbContext =
		 * JAXBContext.newInstance(Object.class); Unmarshaller um = );
		 * requestXml = (Object)um.unmarshal(new
		 * ByteArrayInputStream(xmlstr.getBytes())); } catch (JAXBException e) {
		 * e.getMessage(); } return requestXml;
		 */
	}

}
