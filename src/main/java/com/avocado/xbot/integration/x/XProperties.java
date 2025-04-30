package com.avocado.xbot.integration.x;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "api.x")
@Getter
@Setter
public class XProperties {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    public String generateOAuthSignature(String url, String method, String params) {
        try {
            // Gera a base string da assinatura
            String signatureBaseString = generateSignatureBaseString(url, method, params);
            // Gera a chave de assinatura
            String signingKey = generateSigningKey();

            // Inicializa o HMAC-SHA1
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));

            // Calcula a assinatura
            byte[] signatureBytes = mac.doFinal(signatureBaseString.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(Base64.getEncoder().encodeToString(signatureBytes), StandardCharsets.UTF_8);  // Codifica a assinatura
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate OAuth signature", e);
        }
    }

    private String generateSignatureBaseString(String url, String method, String params) {
        // Ordena e codifica os parâmetros
        Map<String, String> sortedParams = new TreeMap<>();
        for (String param : params.split("&")) {
            String[] kv = param.split("=");
            sortedParams.put(kv[0], kv[1]);
        }

        String normalizedParams = sortedParams.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .reduce((a, b) -> a + "&" + b)
                .orElse("");

        // Gera a base string da assinatura
        return String.format("%s&%s&%s",
                URLEncoder.encode(method, StandardCharsets.UTF_8),
                URLEncoder.encode(url, StandardCharsets.UTF_8),
                URLEncoder.encode(normalizedParams, StandardCharsets.UTF_8));
    }

    private String generateSigningKey() {
        return String.format("%s&%s",
                URLEncoder.encode(getConsumerSecret(), StandardCharsets.UTF_8),
                URLEncoder.encode(getAccessTokenSecret(), StandardCharsets.UTF_8));
    }
}

