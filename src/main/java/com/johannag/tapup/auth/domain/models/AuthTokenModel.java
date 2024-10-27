package com.johannag.tapup.auth.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderClassName = "Builder")
public class AuthTokenModel {
    private String value;
    private String type;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
