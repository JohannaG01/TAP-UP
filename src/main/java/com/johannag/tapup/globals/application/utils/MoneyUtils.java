package com.johannag.tapup.globals.application.utils;

import com.johannag.tapup.bets.application.config.MoneyConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Component
@AllArgsConstructor
public class MoneyUtils {

    private final MoneyConfig moneyConfig;

    public BigDecimal ToBigDecimal(double value) {
        return BigDecimal
                .valueOf(value)
                .setScale(moneyConfig.getScale(), RoundingMode.HALF_UP);
    }

    public String toString(BigDecimal value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00", symbols);
        return decimalFormat.format(value);
    }
}
