package de.cenglisch.sample.employees.core;

import de.cenglisch.sample.common.adapter.port.secondary.event.EventPublisherAdapter;
import de.cenglisch.sample.employees.core.adapter.port.secondary.database.EmployeePersistenceAdapter;
import de.cenglisch.sample.employees.core.application.EmployeePrimaryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmployeeConfig {

    private final EmployeePersistenceAdapter employeePersistenceAdapter;
    private final EventPublisherAdapter eventPublisherAdapter;

    @Bean
    EmployeePrimaryPort employeePrimaryPort() {
        return new EmployeePrimaryPort(
            employeePersistenceAdapter,
            eventPublisherAdapter
        );
    }
}
