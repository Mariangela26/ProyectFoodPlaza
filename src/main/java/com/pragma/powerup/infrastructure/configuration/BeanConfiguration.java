package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.spi.persistence.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.usecase.RestEmployeeUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestEmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestEmployeeRepository objectRepository;
    private final IRestEmployeeEntityMapper objectEntityMapper;

    @Bean
    public IRestEmployeePersistencePort objectPersistencePort() {
        return new RestEmployeeJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IRestEmployeeServicePort objectServicePort() {
        return new RestEmployeeUseCase(objectPersistencePort());
    }
}