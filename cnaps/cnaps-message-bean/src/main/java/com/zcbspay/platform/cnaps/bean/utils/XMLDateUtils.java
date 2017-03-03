package com.zcbspay.platform.cnaps.bean.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLDateUtils {

	/**
	 * 日期时间转换为XMLGregorianCalendar
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar convert2XMLGregorianCalendar(Date date){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xmlGregorianCalendar;
	}
	
	/**
	 * XMLGregorianCalendar转换为日期时间
	 * @param cal
	 * @return
	 * @throws Exception
	 */
	public static Date convertToDate(XMLGregorianCalendar cal) throws Exception{
         GregorianCalendar ca = cal.toGregorianCalendar();
         return ca.getTime();
    }
}
