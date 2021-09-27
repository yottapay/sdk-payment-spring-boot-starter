package com.yotta.sdk.payment.spring;

import com.yotta.sdk.core.exception.YpRequiredPropertyException;
import com.yotta.sdk.payment.YpSdkPayment;
import com.yotta.sdk.payment.config.YpSdkPaymentConfiguration;
import com.yotta.sdk.payment.spring.config.YpSdkPaymentSpringConfig;
import com.yotta.sdk.payment.spring.properties.YpSdkPaymentSpringProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("yp-sdk-payment-test")
@EnableAutoConfiguration
public class YpSdkPaymentSpringConfigTest {

    @Autowired
    private YpSdkPaymentSpringProperties properties;

    @Test
    public void test_propertiesLoadedToContext() {
        assertNotNull("Properties are not loaded to context", properties);
    }

    @Test
    public void test_ConfiguredPropertiesRoot() {
        assertEquals("localhost:8080", properties.getBaseUrl());
        assertEquals("merchant-identity", properties.getMerchantIdentity());
        assertEquals("secret", properties.getSecret());
    }

    @Test
    public void test_ConfiguredEndpoints() {
        YpSdkPaymentSpringProperties.Endpoints endpoints = properties.getEndpoints();

        assertEquals("/create-payment", endpoints.getCreatePayment());
    }

    @Test
    public void test_configurationWithRequiredProperties() {
        YpSdkPaymentSpringConfig config = new YpSdkPaymentSpringConfig();

        YpSdkPaymentSpringProperties properties = new YpSdkPaymentSpringProperties();
        properties.setMerchantIdentity("merchant-identity");
        properties.setSecret("secret");

        YpSdkPaymentConfiguration sdkConfig = config.getYottaSdkPaymentConfiguration(properties);
        YpSdkPayment sdk = config.getYottaSdkPayment(sdkConfig);
        assertNotNull(sdk);
    }
}
