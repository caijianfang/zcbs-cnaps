package com.zcbspay.platform.cnaps.transfer.message.assemble.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zcbspay.platform.cnaps.bean.MessageBean;
import com.zcbspay.platform.cnaps.bean.MessageTypeEnum;
import com.zcbspay.platform.cnaps.bean.utils.XMLUtils;
import com.zcbspay.platform.cnaps.transfer.message.assemble.MessageAssemble;
import com.zcbspay.platform.cnaps.transfer.message.bean.MessageHeadBean;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("/spring-context.xml")
public class MessageAssembleImplTest {
	@Autowired
	private MessageAssemble messageAssemble;
	@Test
	public void testCreateMessageHead() {
		try {
			MessageBean messageBean = XMLUtils.parseToBean("D:/xmldemo/beps.395.001.01.xml", MessageTypeEnum.BEPS395);
//			MessageHeadBean messageHeadBean = messageAssemble.createMessageHead(messageBean);
//			String msgHeadString = messageHeadBean.toString();
//			System.out.println("报文头长度：" + msgHeadString.length());
			String signString = messageAssemble.signature(messageBean);
			System.out.println("签名串：" + signString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSignature() {
		fail("Not yet implemented");
	}

	@Test
	public void testAssemble() {
		fail("Not yet implemented");
	}

}
