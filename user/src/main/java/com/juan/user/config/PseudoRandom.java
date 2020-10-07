package com.juan.user.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 伪随机
 * 当一个下标(伪服务)连接被调用两次
 * 第三次如果还是它,就让再随机一次
 */
public class PseudoRandom extends AbstractLoadBalancerRule {

    private int currentIndex = -1;
    private int lastIndex = -1;
    private int skipIndex = -1;

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            int index = chooseRandomInt(serverCount);

            //判断只有一个节点的情况
            if (serverCount != 1){
                index = pseudoRandom(index, serverCount);
            }

            server = upList.get(index);

            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub

    }

    /**
     * 伪随机算法
     * @param index
     * @param serverCount
     * @return
     */
    private int pseudoRandom(int index,int serverCount){
        if (index == skipIndex){
            System.out.println("重新随机一次");
            index = chooseRandomInt(serverCount);
            if (index == skipIndex){
                pseudoRandom(index,serverCount);
            }
            skipIndex = -1;
        }
        currentIndex = index;
        if (currentIndex == lastIndex){
            skipIndex = currentIndex;
            System.out.println("这次下标跟上次一样");
        }
        lastIndex = currentIndex;

        return index;
    }
}
