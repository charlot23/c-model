package com.cy.cblog.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class EsConfig {

    @Bean
    RestHighLevelClient restHighLevelClient(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9300")
                .build();
        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        return client;
    }

}
