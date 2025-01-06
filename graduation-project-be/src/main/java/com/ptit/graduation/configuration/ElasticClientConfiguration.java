package com.ptit.graduation.configuration;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticClientConfiguration {
  @Value("${spring.elasticsearch.rest.uris}")
  private String elasticsearchUri;

  @Bean
  public RestHighLevelClient client() {
    return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticsearchUri)));
  }

  @Bean
  public ElasticsearchClient elasticsearchClient() {
    RestClient restClient = client().getLowLevelClient();
    RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
    return new ElasticsearchClient(transport);
  }
}
