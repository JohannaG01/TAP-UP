package com.johannag.tapup.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UserSystemConfig {

    @Value("${user.system.admin.id}")
    private Long adminUserId;
}
