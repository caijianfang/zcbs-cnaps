package com.zcbspay.platform.cnaps.application.sequence.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.zcbspay.platform.cnaps.application.sequence.SerialNumberService;

@Service
public class RedisSerialNumberServiceImpl implements SerialNumberService{
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private static final String MSGID="SEQUENCE:CNAPS:MSGID";
	private static final String BATCHCOLLECT="SEQUENCE:BEPS:BATCHCOLLECT";
	private static final String BATCHCOLLECTDETA="SEQUENCE:BEPS:BATCHCOLLECTDETA";
	private static final String REALTIMECOLLECT="SEQUENCE:BEPS:REALTIMECOLLECT";
	private static final String REALTIMECOLLECTBATCH="SEQUENCE:BEPS:REALTIMECOLLECT:BATCH";
	public String formateSequence(String key){
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		Long increment = opsForValue.increment(key, 1);
		if(increment>=99999999){
			opsForValue.set(key, "0");
		}
		DecimalFormat df = new DecimalFormat("00000000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String seqNo = sdf.format(new Date()) + df.format(increment);
		return seqNo;
	}
	
	@Override
	public String generateBatchCollectSerialNo() {
		String seqNo = formateSequence(BATCHCOLLECT);
		return seqNo.substring(0, 6) + "BEPS380" + seqNo.substring(6);
	}
	@Override
	public String generateCollectDetaSerialNo() {
		String seqNo = formateSequence(BATCHCOLLECTDETA);
		return seqNo.substring(0, 6) + "BEPS380D" + seqNo.substring(6);
	}
	@Override
	public String generateRealTimeCollectSerialNo() {
		String seqNo = formateSequence(REALTIMECOLLECT);
		return seqNo.substring(0, 6) + "BEPS384" + seqNo.substring(6);
	}

	@Override
	public String generateRealTimeCollectBatchNo() {
		String seqNo = formateSequence(REALTIMECOLLECTBATCH);
		return seqNo.substring(0, 6) + "BEPS384B" + seqNo.substring(6);
	}

	@Override
	public String generateMsgId() {
		String seqNo = formateSequence(MSGID);
		return seqNo.substring(0, 6) + "CNAPS" + seqNo.substring(6);
	}
	
}
