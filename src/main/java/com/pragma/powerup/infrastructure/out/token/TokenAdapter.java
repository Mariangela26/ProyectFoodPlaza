package com.pragma.powerup.infrastructure.out.token;

import com.pragma.powerup.domain.spi.bearertoken.IToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class TokenAdapter implements IToken {
    @Override
    public String getBearerToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

    @Override
    public String getEmail(String token) {
        return TokenUtils.getCorreo(token.replace("Bearer ",""));
    }

    @Override
    public Long getUserAutenticateId(String token) {
        return  TokenUtils.getUsuarioAutenticadoId(token.replace("Bearer ",""));
    }
}
