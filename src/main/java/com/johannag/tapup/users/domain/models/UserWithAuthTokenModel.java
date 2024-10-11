package com.johannag.tapup.users.domain.models;

import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class UserWithAuthTokenModel {
    private AuthTokenModel token;
    private UserModel user;
}
