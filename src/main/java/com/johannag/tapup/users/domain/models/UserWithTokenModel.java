package com.johannag.tapup.users.domain.models;

import com.johannag.tapup.tokens.domain.models.TokenModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class UserWithTokenModel {
    private TokenModel token;
    private UserModel user;
}
