package com.steveq.config;

import com.steveq.model.Mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Adam on 2017-05-15.
 */
@Configuration
public class BeanConfiguration {
    @Bean
    public Mock mockCreation(){
        return new Mock();
    }
}
