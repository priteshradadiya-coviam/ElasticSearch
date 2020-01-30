package com.example.demo;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;


import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.demo.repository")
@ComponentScan(basePackages = {"com.example.demo"})
class EsConfig {
    @Value("${elasticsearch.home:/Users/priteshradadiya/Downloads/elasticsearch-7.5.2}")
    private String elasticsearchHome;

    @Value("${elasticsearch.cluster.name:elasticsearch}")
    private String clusterName;

    @Bean
    public Client client() throws UnknownHostException {
        Settings elasticsearchSettings = Settings.builder()
                .put("path.home", elasticsearchHome)
                .put("cluster.name", clusterName)
                .build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        return client;
    }
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}
