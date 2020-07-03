package com.alibaba.seata.account.controller;

import com.alibaba.seata.account.domain.R;
import com.alibaba.seata.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @ClassName: AccountController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 17:44
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/decrease")
    public R decrease(@RequestParam Long userId, @RequestParam BigDecimal money){
        accountService.decrease(userId,money);
        return new R(200,"扣钱成功");
    }

}
