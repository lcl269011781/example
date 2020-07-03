package com.alibaba.seata.account.service.impl;

import com.alibaba.seata.account.dao.AccountDao;
import com.alibaba.seata.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName: AccountServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 17:42
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;


    @Override
    public void decrease(Long userId, BigDecimal money) {
        accountDao.update(userId,money);
    }
}
