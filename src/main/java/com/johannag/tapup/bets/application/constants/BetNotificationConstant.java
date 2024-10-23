package com.johannag.tapup.bets.application.constants;

public class BetNotificationConstant {
    public static String WINNERS_MSG = "Horse Race has finished; congrats, you have won the bet. Total amount: %s :)";
    public static String LOSERS_MSG = "Horse Race has finished; unfortunately, you didn't won the bet. You have loss " +
            "%s :c";
    public static String SUCCESSFUL_PAYMENTS_MSG = "Bet payments process for horse race uuid %s has ended SUCCESSFULLY. " +
            "Results: %s";
    public static String FAILED_PAYMENTS_MSG = "Bet payments process for horse race uuid %s has FAILED. Error: %s. " +
            "Results: %s";
    public static String REFUND_MSG = "Unfortunately, horse race has been CANCELLED. You have been refund %s";
    public static String SUCCESSFUL_REFUNDS_MSG = "Bet refunds process for horse race uuid %s has ended SUCCESSFULLY. " +
            "Results: %s";
    public static String FAILED_REFUNDS_MSG = "Bet refunds process for horse race uuid %s has FAILED. Error: %s. " +
            "Results: %s";
}
