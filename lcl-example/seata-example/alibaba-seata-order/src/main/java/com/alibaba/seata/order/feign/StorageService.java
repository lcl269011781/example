package com.alibaba.seata.order.feign;

import com.alibaba.seata.order.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alibaba-seata-storage")
public interface StorageService {

    @PostMapping("/seata/storage/decrease")
    R decrease(@RequestParam Long productId,@RequestParam Long count);

}
