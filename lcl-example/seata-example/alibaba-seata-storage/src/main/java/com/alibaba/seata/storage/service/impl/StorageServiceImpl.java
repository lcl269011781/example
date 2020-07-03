package com.alibaba.seata.storage.service.impl;

import com.alibaba.seata.storage.dao.StorageDao;
import com.alibaba.seata.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: StorageServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/4/6 17:57
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;

    @Override
    public void update(Long productId, Long count) {
        storageDao.update(productId,count);
    }
}
