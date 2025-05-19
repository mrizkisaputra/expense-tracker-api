package com.expense.bootstrap;

import com.expense.services.UserSeederService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component @Order(2)
@Slf4j
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserSeederService userSeederService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("AdminSeeder started");
        this.userSeederService.createSuperAdministrator();
    }
}
