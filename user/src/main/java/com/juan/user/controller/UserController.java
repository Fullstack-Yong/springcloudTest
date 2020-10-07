package com.juan.user.controller;

import com.juan.user.service.GoodsFeignService;
import com.juan.user.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GoodsFeignService goodsFeignService;

    @RequestMapping("/getGoods")
    public ResponseResult getGoods(){
        System.out.println("user----getGoods");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return ResponseResult.success("执行成功",goodsFeignService.getGoods());
    }

    @RequestMapping("/getOrder")
    public Object getOrder(){
        return ResponseResult.success("执行成功",restTemplate.getForObject("http://client-order/getOrder", String.class));
    }
}
