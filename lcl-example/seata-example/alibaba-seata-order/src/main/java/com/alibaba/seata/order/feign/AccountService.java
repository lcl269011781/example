package com.alibaba.seata.order.feign;

import com.alibaba.seata.order.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "alibaba-seata-account")
public interface AccountService {

    @PostMapping("/seata/account/decrease")
    R decrease(@RequestParam Long userId, @RequestParam BigDecimal money);
}
