package com.juan.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "client-order")
public interface OrderFeignService {
    @RequestMapping("/getOrder")
    Object getGoods();
}
