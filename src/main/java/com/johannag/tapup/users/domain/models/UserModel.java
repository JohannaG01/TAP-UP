package com.johannag.tapup.users.domain.models;

import com.johannag.tapup.bets.domain.models.BetModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
@ToString(exclude = "hashedPassword")
public class UserModel {
    private UUID uuid;
    private List<BetModel> bets;
    private String email;
    private String name;
    private String lastName;
    private Boolean isAdmin;
    private BigDecimal balance;
    private String hashedPassword;

    public boolean hasEnoughBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }
}
