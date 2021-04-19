package com.kenzan.rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

@Configuration
@Primary
public class BeanLoader {

    @Value("${datasource.ssl.enabled:false}")
    private String sslConnection;
    @Value("${datasource.ssl.trust-store:}")
    private String sslTrustStoreLocation;
    @Value("${datasource.ssl.trust-store-password:}")
    private String sslTrustStorePassword;
    @Value("${datasource.url:}")
    private String url;
    @Value("${datasource.username:}")
    private String username;
    @Value("${datasource.password:}")
    private String password;
    @Value("${datasource.driver-class-name:}")
    private String driver;

    @ConfigurationProperties(prefix = "datasource")
    @ConditionalOnProperty("datasource.enabled")
    @Bean
    public DataSource dataSource() {
        return DataSource.getDataSource(sslConnection, sslTrustStoreLocation, sslTrustStorePassword, url, username,
                password, driver);
    }

}
