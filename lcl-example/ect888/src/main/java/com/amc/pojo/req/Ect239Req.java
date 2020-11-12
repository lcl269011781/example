package com.amc.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 209实名认证入参
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ect239Req {
    /**
     * 姓名
     * 必填
     */
    private String usernm;
    /**
     * 证件号码
     * 签名的时候身份证号要利用会话密钥进行AES加密
     * post传参数时的身份证号要进行以下处理：
     * 步骤为：[a]，用会话密钥加密(AES加密方法);
     * [b].URLEncoder.encode[a]中加密串;
     * [c],base64[b]中字符串
     *
     * 必填
     */
    private String certseq;
    /**
     * 手机号
     * 必填
     */
    private String phoneno;
}
