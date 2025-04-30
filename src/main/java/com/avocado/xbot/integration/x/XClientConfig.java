package com.avocado.xbot.integration.x;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class XClientConfig implements RequestInterceptor {

    final XProperties xOAuthService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String url = requestTemplate.url();
        String method = requestTemplate.method();
        Map<String, Collection<String>> queryParams = requestTemplate.queries();

        String params = queryParams.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(value -> String.format("%s=%s", entry.getKey(), value))
                )
                .collect(Collectors.joining("&"));

        String oauthSignature = xOAuthService.generateOAuthSignature(url, method, params);

        String authorizationHeader = String.format(
                "OAuth oauth_consumer_key=\"%s\", oauth_token=\"%s\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"%s\", oauth_nonce=\"%s\", oauth_version=\"1.0\", oauth_signature=\"%s\"",
                xOAuthService.getConsumerKey(),
                xOAuthService.getAccessToken(),
                System.currentTimeMillis() / 1000,
                java.util.UUID.randomUUID(),
                oauthSignature
        );

        requestTemplate.header("Authorization", authorizationHeader);
    }
}
