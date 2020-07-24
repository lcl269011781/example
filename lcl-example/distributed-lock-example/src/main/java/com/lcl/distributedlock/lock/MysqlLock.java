package com.lcl.distributedlock.lock;

import com.lcl.distributedlock.dao.MysqlLockDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: MysqlLock
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/23 15:20
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Component
@Slf4j
public class MysqlLock {
    @Resource
    private MysqlLockDao mysqlLockDao;

    public boolean tryLock(String lockName, String val) {
        try {
            mysqlLockDao.insert(lockName, val);
            log.info("===>>> 抢锁{}成功", lockName);
        } catch (Exception e) {
            log.info("===>>> 抢锁{}失败", lockName);
            return false;
        }
        return true;
    }

    public boolean releaseLock(String lockName, String val) {
        try {
            int i = mysqlLockDao.delete(lockName, val);
            if (i == 0) {
                return false;
            }
            log.info("===>>> 锁{}释放成功", lockName);
        } catch (Exception e) {
            log.error("===>>> 锁{}释放失败", lockName);
            return false;
        }
        return true;
    }


}
