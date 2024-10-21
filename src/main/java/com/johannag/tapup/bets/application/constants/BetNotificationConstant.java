package com.johannag.tapup.bets.application.constants;

public class BetNotificationConstant {
    public static String WINNERS_MSG = "Horse Race has finished; congrats, you have won the bet. Total amount: %s :)";
    public static String LOSERS_MSG = "Horse Race has finished; unfortunately, you didn't won the bet. You have loss " +
            "%s :c";
    public static String SUCCESSFUL_PAYMENTS = "Bet payments process for horse race uuid %s has ended SUCCESSFULLY. Results: %s";
    public static String FAILED_PAYMENTS = "Bet payments process for horse race uuid %s has FAILED. Error: %s. Results: %s";
}
