package com.lcl.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: IkIndex
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/24 16:39
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
public class IkIndex {
    private Long id;
    private String title;
    private String desc;
    private String category;

}
