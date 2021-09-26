package com.kgr.framework.elsticsearch.config;

import com.kgr.framework.web.config.WebAutoConfiguration;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author kgr
 * @create 2021-07-01 15:51
 */
@Configuration
@AutoConfigureAfter(WebAutoConfiguration.class)
public class ElsticSearchAutoCofiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private String esHost;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;
    @Bean
    RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(esHost)
                .withBasicAuth(username, password)
                //.withConnectTimeout(Duration.ofSeconds(5))
                //.withSocketTimeout(Duration.ofSeconds(3))
                //.useSsl()
                //.withDefaultHeaders(defaultHeaders)
                //.withBasicAuth(username, password)
                // ... other options
                .build();
        return RestClients.create(configuration).rest();
    }
}
