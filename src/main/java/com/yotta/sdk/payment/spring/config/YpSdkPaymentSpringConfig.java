package com.yotta.sdk.payment.spring.config;

import com.yotta.sdk.payment.YpSdkPayment;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import com.yotta.sdk.payment.spring.properties.YpSdkPaymentSpringProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(YpSdkPaymentSpringProperties.class)
public class YpSdkPaymentSpringConfig {
    @Bean
    public YpSdkPaymentConfiguration getYottaSdkPaymentConfiguration(YpSdkPaymentSpringProperties properties) {
        YpSdkPaymentConfiguration config = YpSdkPaymentConfiguration.createDefault();
        configureRootProperties(config, properties);
        configureEndpointsProperties(config, properties.getEndpoints());
        return config;
    }

    protected void configureRootProperties(YpSdkPaymentConfiguration configuration,
                                           YpSdkPaymentSpringProperties properties) {
        configuration.setServerBaseUrl(properties.getBaseUrl());
        configuration.setMerchantIdentity(properties.getMerchantIdentity());
        configuration.setSecret(properties.getSecret());
    }

    protected void configureEndpointsProperties(YpSdkPaymentConfiguration configuration,
                                                YpSdkPaymentSpringProperties.Endpoints endpointsProperties) {
        configuration.setCreatePaymentEndpoint(endpointsProperties.getCreatePayment());
    }

    @Bean
    public YpSdkPayment getYottaSdkPayment(YpSdkPaymentConfiguration configuration) {
        return YpSdkPayment.createFromConfiguration(configuration);
    }
}
