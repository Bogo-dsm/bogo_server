package org.example.bogo.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.StringUtils;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.example.bogo.domain.template.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris:localhost:9200}") // yml 값을 읽고, 없으면 기본값 사용
    private String elasticsearchUri;
    @Value("${spring.elasticsearch.username:}")
    private String username;
    @Value("${spring.elasticsearch.password:}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        String normalizedUri = stripQuotes(elasticsearchUri);
        String normalizedUsername = stripQuotes(username);
        String normalizedPassword = stripQuotes(password);

        var builder = ClientConfiguration.builder()
                .connectedTo(normalizedUri);

        if (StringUtils.hasText(normalizedUsername) && StringUtils.hasText(normalizedPassword)) {
            builder = (ClientConfiguration.MaybeSecureClientConfigurationBuilder) builder.withBasicAuth(normalizedUsername, normalizedPassword);
        }

        return builder.build();
    }

    private String stripQuotes(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }

        String trimmed = value.trim();
        if ((trimmed.startsWith("'") && trimmed.endsWith("'"))
                || (trimmed.startsWith("\"") && trimmed.endsWith("\""))) {
            return trimmed.substring(1, trimmed.length() - 1);
        }

        return trimmed;
    }
}