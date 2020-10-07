package com.juan.user.service;

import org.springframework.stereotype.Component;

@Component
public class GoodsFeignFallback implements GoodsFeignService{
    @Override
    public String getGoods() {
        return "GoodsFeignFallback服务器正在维护,请稍后重试";
    }
}
