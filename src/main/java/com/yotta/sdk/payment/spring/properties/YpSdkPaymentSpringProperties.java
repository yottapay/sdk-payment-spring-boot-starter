package com.yotta.sdk.payment.spring.properties;

import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration.PaymentProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yotta-pay.sdk.payment")
public class YpSdkPaymentSpringProperties {
    private String baseUrl = PaymentProperties.SERVER_BASE_URL.getDefaultValue();
    private String merchantIdentity = PaymentProperties.MERCHANT_IDENTITY.getDefaultValue();
    private String secret = PaymentProperties.SECRET.getDefaultValue();

    private final Endpoints endpoints = new Endpoints();

    @Data
    public static class Endpoints {
        private String createPayment = PaymentProperties.ENDPOINT_CREATE_PAYMENT.getDefaultValue();
    }
}
