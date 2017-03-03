package com.zcbspay.platform.cnaps.bean;

public class DefaultMessageBean implements MessageBean{

	private MessageTypeEnum messageTypeEnum;
	/**
	 * CNAPS报文体bean
	 */
	private Object messageBean;
	@Override
	public MessageTypeEnum getBeanType() {
		// TODO Auto-generated method stub
		return this.messageTypeEnum;
	}

	@Override
	public Object getCNAPSMessageBean() {
		// TODO Auto-generated method stub
		return messageBean;
	}

	public void setMessageTypeEnum(MessageTypeEnum messageTypeEnum) {
		this.messageTypeEnum = messageTypeEnum;
	}

	public void setMessageBean(Object messageBean) {
		this.messageBean = messageBean;
	}

	public DefaultMessageBean(Object messageBean,MessageTypeEnum messageTypeEnum){
		super();
	}
}
