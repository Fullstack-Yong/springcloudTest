package com.juan.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "client-goods",fallback = GoodsFeignFallback.class)
public interface GoodsFeignService {

    @RequestMapping("/getGoods")
    String getGoods();
}
