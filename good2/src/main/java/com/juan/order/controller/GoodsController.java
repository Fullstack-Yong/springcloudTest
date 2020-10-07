package com.juan.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    @RequestMapping("/getGoods")
    public String getGoods(){
        return "我是goods2";
    }
}
