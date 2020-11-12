package com.ect888.config;

import lombok.Data;

/**
 * 应用配置
 * <p>
 * 账户参数证通只下发如下：
 * 1 机构号(ptycd)
 * 2 机构账号(ptyacct)
 * 3 访问地址url
 * 4 公钥(publicKey)
 * 另外您提到的两个是由客户端进行账户初始化设置的，包括：
 * 1 账户密码 ptypwd
 * 2 会话秘钥 ptyKey
 * 若账户密码 ptypwd遗失，可申请重置。重置后密码就为空字符串。您可以重新进行账户初始化。
 * <p>
 * 账户密码并不参与业务交易，只用于修改会话秘钥;
 * 发起交易只需要会话密钥。会话密钥设置后不更改则长久有效;
 * 公钥只在200002和200006接口有用。发起业务交易不需要。
 *   *注：调用200002步骤中生成的会话密钥是没有时效性的，一次调用产生会话密钥可以永久使用的 。 
 * 调用200002和2000006两步一般只需要调用一次，后续有修改密码以及修改会话秘钥需要时可再次调用。
 *   *
 *   *会话密钥和账户密码都需要客户端维护和保管。账户密码若遗失可申请证通进行重置。
 *   *重置后初始密码为空，需要设置为非空值。
 * 即第一次设置密码时，ptypwd为空，但是newptypwd不能为空（生成规则机构自定义，超过16位的字母数字组合）。
 *
 * @author fanyj
 */
@Data
public class Config {

    private Config() {

    }

    private static class Holder {
        public static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 访问地址
     */
    private String url = "https://112.65.144.19:9085/servlet/json";

    /**
     * 机构账号
     */
    private String ptyacct = "hxfundacct01";
    /**
     * 机构号
     */
    private String ptycd = "0103400008";
    /**
     * 会话密钥
     */
    private String ptyKey = "bjsyhxjjkfxthxods2020";

    /**
     * 账户密码 旧值
     * <p>
     * 账户密码需要客户端维护和保管。账户密码若遗失可申请证通进行重置。
     * <p>
     * 重置后初始密码为空，需要设置为非空值。即第一次设置密码时，ptypwd为空，但是newptypwd不能为空（超过8位的字母数字组合）
     */
    private String ptyPwd = "";

    /**
     * 账户密码 新值
     * <p>
     * 账户密码需要客户端维护和保管。账户密码若遗失可申请证通进行重置。
     * <p>
     * 重置后初始密码为空，需要设置为非空值。即第一次设置密码时，ptypwd为空，但是newptypwd不能为空（超过16位的字母数字组合）
     */
    private String newPtyPwd = "hxods2020";
    /**
     * 公钥
     * <p>
     * 公钥只在200002和200006接口有用。发起业务交易不需要。
     * <p>
     * 证通下发
     * 生产环境和测试环境不一样
     */
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCIjEFl2tn3W9GssK0iDju2ILU5UlLwinOEy7CiZAF9Q/gYXPoAr8AWx3gQ8UVAszAEw1X25Qa2TPq63/boiLip6299pbzpCAToFFHzNhZT6SFm0HDvOQWJxs8k/kUDDMRF+TUbf9NJekhtg87bbMAB68fi3/jreeYJ7OYC5ZKSwIDAQAB";


}
