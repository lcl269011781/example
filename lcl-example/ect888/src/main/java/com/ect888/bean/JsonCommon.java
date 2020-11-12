package com.ect888.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * 
 * 一般无业务字段的通用查询结果
 * 对应json
 * 
 * {"error_info":"","dsName":["results"],"results":[],"error_no":"0"}
或
{"error_info":"机构账号或者密码不对","error_no":"-1004"}
 * 
 * 
 * @author fanyj
 *
 */
@Data
@ApiModel(value = "JsonCommon",description = "最外层返回值")
public class JsonCommon {
	
	/**
	 * 
	 * 最外层，系统级调用返回码
	 * 
	 * 等于0成功，否则代表失败
	 */
	@ApiModelProperty(value = "系统级调用返回码",notes = "等于0成功，否则代表失败")
	private String error_no;
	
	/**
	 * 失败说明
	 */
	@ApiModelProperty(value = "失败说明")
	private String error_info;
	
	/**
	 * 预留字段
	 */
	@ApiModelProperty(value = "预留字段")
	private ArrayList<String> dsName;


}
