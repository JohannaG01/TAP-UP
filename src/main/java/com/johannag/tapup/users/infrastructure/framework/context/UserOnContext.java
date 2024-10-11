package com.johannag.tapup.users.infrastructure.framework.context;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class UserOnContext {
    UUID uuid;
    String email;
    String name;
    String lastName;

    public String getContext() {
        String uuidAsString = uuid.toString();
        return String.format("%s %s - #%s", name, lastName, uuidAsString.substring(uuidAsString.length() - 4));
    }
}
