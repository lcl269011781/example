package com.lcl.kafka.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lcl.kafka.pojo.KafkaJson;
import com.lcl.kafka.pojo.req.KafkaProduce;
import com.lcl.kafka.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: KafkaTestController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/22 10:07
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 简单发送
     *
     * @param kafkaProduce
     * @param kafkaJson
     * @return
     */
    @GetMapping("/sendTopic1")
    public ApiResult<String> sendTopic1(KafkaProduce kafkaProduce, KafkaJson kafkaJson) {
        kafkaTemplate.send(kafkaProduce.getTopic(), kafkaProduce.getPartition(), kafkaJson.getId(), JSONObject.toJSONString(kafkaJson));
        return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
    }

    /**
     * 带回调的发送
     *
     * @param kafkaProduce
     * @param kafkaJson
     * @return
     */
    @GetMapping("/sendTopic2")
    public ApiResult<String> sendTopic2(KafkaProduce kafkaProduce, KafkaJson kafkaJson) {
        kafkaTemplate.send(kafkaProduce.getTopic(), kafkaProduce.getPartition(), kafkaJson.getId(), JSONObject.toJSONString(kafkaJson))
                .addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        log.error("消息发送失败:{}", throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                        log.info("消息发送成功,消息头:{},主题:{},分区:{},内容:{}"
                                , stringObjectSendResult.getProducerRecord().headers()
                                , stringObjectSendResult.getProducerRecord().topic()
                                , stringObjectSendResult.getProducerRecord().partition()
                                , stringObjectSendResult.getProducerRecord().value());
                    }
                });
        return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
    }

    /**
     * 事务测试
     *
     * @param kafkaProduce
     * @param kafkaJson
     * @return
     */
    @GetMapping("/transaction")
    public ApiResult<String> transaction(KafkaProduce kafkaProduce, KafkaJson kafkaJson) {
        //事务方式提交，出异常不会发送消息
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(kafkaProduce.getTopic(), kafkaProduce.getPartition(), kafkaJson.getId(), JSONObject.toJSONString(kafkaJson));
            throw new RuntimeException("fail");
        });
        return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
    }

}
