package com.zcbspay.platform.cnaps.bean.utils;

import java.io.ByteArrayInputStream;
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
	public static MessageBean parseToBean(String xml,MessageTypeEnum messageTypeEnum) throws JAXBException {
		Object message = null;
		JAXBContext jaxbContext = null;
		switch (messageTypeEnum) {
			case BEPS381:
				jaxbContext = JAXBContext.newInstance(com.zcbspay.platform.cnaps.beps.bean.batchcollectionchargesresponse.Document.class);
				break;
			case CCMS900:
				jaxbContext = JAXBContext.newInstance(com.zcbspay.platform.cnaps.ccms.bean.CommonConfirmation.Document.class);
				break;
			default:
				break;
		}
		Unmarshaller um = jaxbContext.createUnmarshaller();
		message = um.unmarshal(new ByteArrayInputStream(xml.getBytes()));
		return new DefaultMessageBean(message, messageTypeEnum);

		/*
		 * try { JAXBContext jaxbContext =
		 * JAXBContext.newInstance(Object.class); Unmarshaller um =
		 * ); requestXml =
		 * (Object)um.unmarshal(new ByteArrayInputStream(xmlstr.getBytes())); }
		 * catch (JAXBException e) { e.getMessage(); } return requestXml;
		 */
	}
}
