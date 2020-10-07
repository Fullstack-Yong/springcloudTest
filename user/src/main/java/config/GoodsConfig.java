package config;

import com.juan.user.config.PseudoRandom;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoodsConfig {

    @Bean
    public IRule iRule(){
        return new PseudoRandom();
    }
}
