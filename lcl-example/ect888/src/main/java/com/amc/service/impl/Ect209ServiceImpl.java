package com.amc.service.impl;

import com.alibaba.fastjson.JSON;
import com.amc.pojo.Json209;
import com.amc.pojo.Result209;
import com.amc.pojo.req.Ect209Req;
import com.amc.service.Ect209Service;
import com.amc.utils.Constant;
import com.amc.utils.DateUtil;
import com.ect888.bus.FunctionCommon;
import com.ect888.bus.impl.FunctionCommonImpl;
import com.ect888.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 接口实现类
 * Author: Chunliang.Li
 * Date: 2020/11/12 9:25
 **/
@Service
public class Ect209ServiceImpl<T extends Ect209Req> implements Ect209Service<T> {

    private static final Logger log = LoggerFactory.getLogger(Ect209ServiceImpl.class);

    private static final String FUNC_NO = "2000209";

    /**
     * 来源渠道，填固定值“0”
     * <p>
     * 参与签名
     */
    private static final String SOURCECHNL = "0";
    /**
     * 业务发生地
     * 符合入参长度即可，不做技术限制
     * <p>
     * 参与签名
     */
    private static final String PLACEID = "00";
    /**
     * 服务类型
     * 符合入参长度即可，不做技术限制
     * <p>
     * 参与签名
     */
    private static final String BIZTYP = "A001";
    /**
     * 服务描述
     * <p>
     * 符合入参长度即可，不做技术限制
     * <p>
     * 参与签名
     */
    private static final String BIZTYPDESC = "对比服务";
    /**
     * 时间戳
     * <p>
     * 参与签名
     */
    private static final String TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private static final String LOG_209 = "209实名认证接口";

    private final Config config = Config.getInstance();

    private final FunctionCommonImpl funcCommon = FunctionCommonImpl.getInstance();

    @Override
    public Json209 get209Result(Ect209Req ect209Req) {
        //获取调用结果
        String result = this.doWork(ect209Req);
        Json209 json = JSON.parseObject(result, Json209.class);
        //系统级调用成功
        if (Constant.ECT_SUCCESS_RESULT.equals(json.getError_no())) {
            //异常，系统级调用成功，却无结果，健壮性考虑，留此分支,联系服务端
            if (json.getResults().isEmpty() || null == json.getResults().get(0)) {
                throw new IllegalStateException("209实名认证接口异常，系统级调用成功，却无结果，健壮性考虑，留此分支,联系服务端");
            }

            Result209 re = json.getResults().get(0);
            log.info("{} 业务应答码respcd:【{}】", LOG_209, re.getRespcd());
            log.info("{} 业务应答信息respinfo:【{}】", LOG_209, re.getRespinfo());
            return json;
        } else {//系统级调用失败，异常，查看入参或者联系服务端
            throw new IllegalStateException("209实名认证接口系统级调用失败，异常，查看入参或者联系服务端");
        }
    }

    /**
     * 将入参，按照http post上送和签名规则，放入map内
     * <p>
     * <p>
     * 调用2000209接口时的签名过程：
     * 上送参数（biztyp,biztypdesc,certseq，placeid,ptyacct,ptycd,sourcechnl,timestamp,key(会话密钥)），其中key前面的是按照字母排序的，key则是要最后附加上去。其中在签名的时候身份证号需要利用会话密钥进行AES加密。
     * 生成的防篡改签名sign在接口调用时和业务参数一起上传。"
     * <p>
     * 调用2000209实名认证接口：上送参数（biztyp,biztypdesc,certseq,placeid,ptyacct,ptycd,sourcechnl,timestamp, videopic, usernm,funcNo,sign(签名)）
     * ，传上述参数时的身份证号要进行以下处理，步骤为：[a]，用会话密钥加密(AES加密方法);[b].URLEncoder.encode（[a]中加密串）;[c],base64（[b]中字符串）  ,身份证正面照需用Base64编码，传上述参数的时候没有顺序要求的。
     *
     * @return 将入参，按照http post上送和签名规则，放入map内
     */
    @Override
    public Map<String, String> buildParams(Ect209Req ectReq) {
        Map<String, String> params = new HashMap<>(16);

        params.put(FunctionCommon.TO_AES_TO_URL_TO_BASE64_HEAD + "certseq", ectReq.getCertseq());

        params.put(FunctionCommon.TO_SIGN_HEAD + "timestamp", DateUtil.getTimestamp());
        params.put(FunctionCommon.TO_SIGN_HEAD + "biztypdesc", BIZTYPDESC);
        params.put(FunctionCommon.TO_SIGN_HEAD + "biztyp", BIZTYP);
        params.put(FunctionCommon.TO_SIGN_HEAD + "placeid", PLACEID);
        params.put(FunctionCommon.TO_SIGN_HEAD + "sourcechnl", SOURCECHNL);

        params.put(FunctionCommon.TO_SIGN_HEAD + "ptyacct", config.getPtyacct());
        params.put(FunctionCommon.TO_SIGN_HEAD + "ptycd", config.getPtycd());

        params.put("usernm", ectReq.getUsernm());
        params.put("funcNo", FUNC_NO);
        log.info("{} 参数构建成功", LOG_209);
        return params;
    }

    @Override
    public String doWork(Ect209Req ectReq) {
        Map<String, String> params = this.buildParams(ectReq);
        //加密加签,发起post请求，UrlEncodedFormEntity方式，选择相信服务端ssl证书，忽略证书认证
        String result = funcCommon.invoke(params);
        log.info("{} 请求ect接口成功，返回内容:【{}】", LOG_209, result);
        return result;
    }
}
