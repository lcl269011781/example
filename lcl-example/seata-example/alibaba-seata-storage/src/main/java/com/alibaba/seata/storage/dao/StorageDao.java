package com.alibaba.seata.storage.dao;

import com.alibaba.seata.storage.domain.Storage;
import org.apache.ibatis.annotations.Param;

public interface StorageDao {

    int update(@Param("productId") Long productId, @Param("count") Long count);

}
