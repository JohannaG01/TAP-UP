package com.johannag.tapup.users.domain.models;

import com.johannag.tapup.auth.domain.models.AuthTokenModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UserWithAuthTokenModel {
    private AuthTokenModel token;
    private UserModel user;
}
