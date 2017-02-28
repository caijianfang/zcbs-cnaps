package com.zcbspay.platform.cnaps.application.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;



public class Constant {
	private String channelCode;
	private boolean canRun;
    private String refresh_interval;
    private static Constant constant;
    public static synchronized Constant getInstance(){
		if(constant==null){
			constant = new Constant();
		}
		return constant;
	}
    
	private Constant(){
		refresh();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (canRun) {
					try {
						refresh();
						int interval = NumberUtils.toInt(refresh_interval, 60) * 1000;// 刷新间隔，单位：秒
						Thread.sleep(interval);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
	public void refresh(){
		try {
			String path = "/home/channel/cnaps/";
			File file = new File(path+ "constant.properties");
			if(!file.exists()){
			    path = getClass().getResource("/").getPath();
			    file = null;
			}
			Properties prop = new Properties();
			InputStream stream = null;
			stream = new BufferedInputStream(new FileInputStream(new File(path+ "constant.properties")));
			prop.load(stream);
			
			channelCode = prop.getProperty("channelCode");;
			canRun = true;
			refresh_interval = prop.getProperty("refresh_interval");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public boolean isCanRun() {
		return canRun;
	}

	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	public String getRefresh_interval() {
		return refresh_interval;
	}

	public void setRefresh_interval(String refresh_interval) {
		this.refresh_interval = refresh_interval;
	}
	
}
