package com.lcl.swaggerexapmle.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: Test
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/1 16:57
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@ApiModel(value = "Test",description = "测试实体类swagger")
public class Test {

    @ApiModelProperty(value = "内容")
    private String content;

    public Test(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
