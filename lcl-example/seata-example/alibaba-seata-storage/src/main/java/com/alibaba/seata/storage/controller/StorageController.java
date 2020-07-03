package com.alibaba.seata.storage.controller;

import com.alibaba.seata.storage.domain.R;
import com.alibaba.seata.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: StorageController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 17:58
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/decrease")
    public R decrease(@RequestParam Long productId, @RequestParam Long count){
        storageService.update(productId, count);
        return new R(200,"扣库存成功");
    }

}
