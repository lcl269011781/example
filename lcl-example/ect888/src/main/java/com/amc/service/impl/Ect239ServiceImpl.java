package com.amc.service.impl;

import com.alibaba.fastjson.JSON;
import com.amc.pojo.Json239;
import com.amc.pojo.Result239;
import com.amc.pojo.req.Ect239Req;
import com.amc.service.Ect239Service;
import com.amc.utils.Constant;
import com.amc.utils.DateUtil;
import com.ect888.bus.FunctionCommon;
import com.ect888.bus.impl.FunctionCommonImpl;
import com.ect888.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author: Chunliang.Li
 * Date: 2020/11/12 11:22
 **/
@Service
public class Ect239ServiceImpl<T extends Ect239Req> implements Ect239Service<T> {

    private static final Logger log = LoggerFactory.getLogger(Ect239ServiceImpl.class);

    private static final String FUNC_NO = "2000239";
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
    private final String timestamp = DateUtil.getTimestamp();

    private static final String LOG_239 = "239实名认证接口";
    private final Config config = Config.getInstance();

    private final FunctionCommonImpl funcCommon = FunctionCommonImpl.getInstance();

    @Override
    public Json239 get239Result(Ect239Req ect239Req) {
        //获取调用结果
        String result = this.doWork(ect239Req);
        Json239 json = JSON.parseObject(result, Json239.class);
        //系统级调用成功
        if (Constant.ECT_SUCCESS_RESULT.equals(json.getError_no())) {
            //异常，系统级调用成功，却无结果，健壮性考虑，留此分支,联系服务端
            if (json.getResults().isEmpty() || null == json.getResults().get(0)) {
                throw new IllegalStateException("209实名认证接口异常，系统级调用成功，却无结果，健壮性考虑，留此分支,联系服务端");
            }

            Result239 re = json.getResults().get(0);
            log.info("{} 业务应答码respcd:【{}】", LOG_239, re.getRespcd());
            log.info("{} 业务应答信息respinfo:【{}】", LOG_239, re.getRespinfo());
            return json;
        } else {//系统级调用失败，异常，查看入参或者联系服务端
            throw new IllegalStateException("239实名认证接口系统级调用失败，异常，查看入参或者联系服务端");
        }
    }

    @Override
    public Map<String, String> buildParams(Ect239Req ectReq) {
        Map<String, String> params = new HashMap<>(16);

        params.put(FunctionCommon.TO_AES_TO_URL_TO_BASE64_HEAD + "certseq", ectReq.getCertseq());

        params.put(FunctionCommon.TO_SIGN_HEAD + "phoneno", ectReq.getPhoneno());
        params.put(FunctionCommon.TO_SIGN_HEAD + "timestamp", DateUtil.getTimestamp());
        params.put(FunctionCommon.TO_SIGN_HEAD + "biztypdesc", BIZTYPDESC);
        params.put(FunctionCommon.TO_SIGN_HEAD + "biztyp", BIZTYP);
        params.put(FunctionCommon.TO_SIGN_HEAD + "placeid", PLACEID);
        params.put(FunctionCommon.TO_SIGN_HEAD + "sourcechnl", SOURCECHNL);

        params.put(FunctionCommon.TO_SIGN_HEAD + "ptyacct", config.getPtyacct());
        params.put(FunctionCommon.TO_SIGN_HEAD + "ptycd", config.getPtycd());

        params.put("usernm", ectReq.getUsernm());
        params.put("funcNo", FUNC_NO);

        return params;
    }

    @Override
    public String doWork(Ect239Req ectReq) {
        Map<String, String> params = this.buildParams(ectReq);
        //加密加签,发起post请求，UrlEncodedFormEntity方式，选择相信服务端ssl证书，忽略证书认证
        String result = funcCommon.invoke(params);
        log.info("{} 请求ect接口成功，返回内容:【{}】", LOG_239, result);
        return result;
    }
}
