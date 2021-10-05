### Yotta Digital LTD SDK Payment

# Introduction

This is a repo for Yotta® Digital Software Development Kit - Payments

### What is this

Yotta Pay® is the UK’s first ethical payment processor based on Open Banking technology.

Yotta Pay® offers a three-click payment/authorisation via the client’s mobile banking app for your website or application.

Yotta Pay® ethical payments™ are low cost, fast, secure, and require no plastic cards.

[More info](https://yottapay.co.uk)

## Our public SDKs:

### Client only SDK:

Just Java libs to make integration on system on your choice

[Yotta Payment SDK](https://github.com/yottapay/sdk-payment)

[Yotta Auth SDK](https://github.com/yottapay/sdk-auth)

### Spring-starter SDK:

Client autoconfiguration, preferred for Spring Boot based applications

Yotta Payment SDK Spring Boot Starter (You are here)

[Yotta Auth SDK Spring Boot Starter](https://github.com/yottapay/sdk-payment-spring-boot-starter)

# Quick start

## Adding dependency

We use [JitPack](https://jitpack.io) for binaries distribution

### Gradle

1. Add to `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency
```groovy
//...
implementation 'com.github.yottapay:sdk-payment:v1.0.0'
```

### Maven

1. Add to the `pom.xml` repository
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

2. Add the dependency
```xml
<dependency>
    <groupId>com.github.yottapay</groupId>
    <artifactId>sdk-payment</artifactId>
    <version>v1.0.0</version>
    <scope>compile</scope>
</dependency>
```

## Usage

### Configuration

This intended to be used with Spring Boot autoconfiguration.

Add to your `application.properties` following config:
```properties
yotta-pay.sdk.payment.merchant-identity='your_id'
yotta-pay.sdk.payment.secret='your_secret'
```

Now you may use `YpSdkPayment` bean and inject it into your services.

### Creating orders for processing
```
String myTransactionId = "123"; // your custom transaction id

YpPaymentCreation payment = YpPaymentCreation.builder()
 .merchantTransactionId(myTransactionId)
 .customerId("customerId") // your customer ID
 .amount("12.45") // order amount
 .currency("GBP") // always GBP
 .notificationId("") // always set to empty string and not null
 .urlPaymentResult("https://api.mysite.org/" + myTransactionId) // webhook address
 .urlPaymentSuccess("https://mysite.org/success/" + myTransactionId) // user will be redirected here after payment completed successfully
 .urlPaymentCancel("https://mysite.org/failed/" + myTransactionId) // user will be redirected here after payment failed
 .build();
YpPaymentCreationResult result = sdkPayment.createPayment(payment);
        // redirect user to result.getUrlProcessPaymentIntent()
```

Returned object (`YpPaymentCreationResult`) consists of two fields:
`urlProcessPaymentIntent` - is URL for making payment. Redirect user to this URL.
`yottaTransactionId` - Our transaction ID for this payment.

Please notice that `currency` is restricted to `GBP`. `notificationId` is reserved and should be set to empty string and be not null.

### Handling webhooks

Webhooks used to inform you that payment status has been changed.

We will send you a `POST` request to URL you specified on `urlPaymentResult` during order creation.
Callback body:
```json
{
  "yottapay_transaction_identifier": "YPR-123",
  "shop_transaction_identifier": "123",
  "merchant_identifier": "your_id",
  "customer_identifier": "customer_id",
  "amount": "12.45",
  "currency": "GBP",
  "response_code": "0",
  "signature": "string",
  "type": "statuschange"
}
```

The following JSON body can be deserialized to `YpPaymentExecutionStatusChangedRequest` POJO.

You **MUST** validate request's signature using `YpSdkPayment#checkSignature` method. It will return `false` if signature failed.

`response_code` states for payment processing result. If not `"0"` payment should be treated as `FAILED`


## Contact us
If you have any questions, please contact us

E-mail: [info@yottapay.co.uk](mailto:info@yottapay.co.uk)
