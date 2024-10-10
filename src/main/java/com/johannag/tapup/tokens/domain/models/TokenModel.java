package com.johannag.tapup.tokens.domain.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderClassName = "Builder")
public class TokenModel {
    private String value;
    private String type;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
