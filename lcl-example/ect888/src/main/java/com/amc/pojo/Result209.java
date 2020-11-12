package com.amc.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {
 * "cerkey":"",
 * "respinfo":"认证一致(通过)",
 * "status":"00",
 * "mpssim":"0.0",
 * "biztyp":"A001",
 * "sysSeqNb":"19103520190308802006580001",
 * "respcd":"5000",
 * "certseq":"341227198912173710",
 * "accountName":"",
 * "name":"姓名",
 * "ptyAcct":"ect888_public_demo",
 * "ptycd":"ect888_public",
 * "localsim":"",
 * "telephone":""
 * }
 *
 * @author Chunliang.Li
 */
@Data
@ApiModel(value = "Result209", description = "209result返回值说明")
public class Result209 {

    @ApiModelProperty(value = "订单流水号")
    private String sysSeqNb;

    @ApiModelProperty(value = "业务应答码")
    private String respcd;

    @ApiModelProperty(value = "业务应答信息")
    private String respinfo;

    @ApiModelProperty(value = "保留字段")
    private String status;

    @ApiModelProperty(value = "比对分值")
    private String mpssim;

    @ApiModelProperty(value = "机构代码")
    private String ptycd;

    @ApiModelProperty(value = "机构账号")
    private String ptyAcct;

    @ApiModelProperty(value = "业务类型")
    private String biztyp;

    @ApiModelProperty(value = "预留字段")
    private String certseq;

    @ApiModelProperty(value = "预留字段")
    private String accountName;

    @ApiModelProperty(value = "预留字段")
    private String telephone;

    @ApiModelProperty(value = "预留字段")
    private String name;

    @ApiModelProperty(value = "预留字段")
    private String cerkey;

    @ApiModelProperty(value = "预留字段")
    private String localsim;

}
