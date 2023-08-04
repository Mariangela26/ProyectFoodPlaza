package com.pragma.powerup.domain.spi.bearertoken;

public interface IToken {
    String getBearerToken();

    String getEmail(String token);

    Long getUserAutenticateId(String token);
}
