package com.kgr.framework.dict.config;

import com.kgr.framework.dict.core.service.DictDataFrameworkService;
import com.kgr.framework.dict.core.util.DictFrameworkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataFrameworkService service) {
        DictFrameworkUtils.init(service);
        return new DictFrameworkUtils();
    }

}
