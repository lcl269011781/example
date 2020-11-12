package com.amc.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {
 * "respinfo":"认证一致(通过)",
 * "phoneProvCity":"",
 * "status":"00",
 * "phoneOperator":"移动",
 * "biztyp":"A001",
 * "respcd":"10000",
 * "certseq":"341227198912173710",
 * "resultdata":"0",
 * "sysseqnb":"19103520190309801921280001",
 * "name":"姓名",
 * "ptyacct":"ect888_public_demo",
 * "ptycd":"ect888_public",
 * "phoneno":"15298386506"
 * }
 *
 * @author Chunliang.Li
 */
@Data
@ApiModel(value = "Result239", description = "239result返回值说明")
public class Result239 {

    /**
     * 订单流水号
     */
    @ApiModelProperty(value = "订单流水号")
    private String sysseqnb;

    @ApiModelProperty(value = "业务应答码")
    private String respcd;

    @ApiModelProperty(value = "业务应答信息")
    private String respinfo;

    @ApiModelProperty(value = "保留字段")
    private String status;

    @ApiModelProperty(value = "返回结果")
    private String resultdata;

    @ApiModelProperty(value = "机构代码")
    private String ptycd;

    @ApiModelProperty(value = "机构账号")
    private String ptyAcct;

    @ApiModelProperty(value = "业务类型")
    private String biztyp;

    @ApiModelProperty(value = "手机号")
    private String phoneno;

    @ApiModelProperty(value = "身份证号")
    private String certseq;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号所属地")
    private String phoneProvCity;

    @ApiModelProperty(value = "手机运营商")
    private String phoneOperator;

}
