package com.example.demo.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "paitool.jwt")
@Data
public class JwtProperties {

    private String SecretKey;
    private long Ttl;
    private String TokenName;

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public long getTtl() {
        return Ttl;
    }

    public void setTtl(long ttl) {
        Ttl = ttl;
    }

    public String getTokenName() {
        return TokenName;
    }

    public void setTokenName(String tokenName) {
        TokenName = tokenName;
    }
}