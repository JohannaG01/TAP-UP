package com.johannag.tapup.users.application.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserSystemConfig {

    @Value("${user.system.admin.id}")
    private Long adminUserId;
}
