package com.zcbspay.platform.cnaps.message.beps.result;

import org.springframework.stereotype.Service;

import com.zcbspay.platform.cnaps.common.bean.ResultBean;
import com.zcbspay.platform.cnaps.fe.send.bean.SendResult;

/**
 * 
 * 代收业务结果处理
 *
 * @author guojia
 * @version
 * @date 2017年3月6日 上午10:16:15
 * @since
 */
@Service
public class PaymentMessageResult {

    public ResultBean batchPaymentResult(SendResult sendResult) {
        // TODO
        return null;
    }

    public ResultBean realTimePaymentResult(SendResult sendResult) {
        // TODO
        return null;
    }
}
