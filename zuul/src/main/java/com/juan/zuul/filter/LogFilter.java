package com.juan.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogFilter extends ZuulFilter {
    /*返回过滤器的类型*/
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; //前置过滤器
    }
    /*返回指定过滤器的执行顺序 越小越靠前*/
    @Override
    public int filterOrder() {
        //+1 在此过滤器之后执行
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }
    /*判断该过滤器是否要执行， true表示执行， false表示不执行。*/
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /*过滤器的具体逻辑*/
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("访问者IP："+remoteAddr+"访问地址:"+request.getRequestURI());
        //可以通过strip-prefix: true/false确定是否要移除前缀
        System.out.println("路由后的地址:"+ctx.get(FilterConstants.REQUEST_URI_KEY));
        return null;
    }
}
