package com.johannag.tapup.globals.constants;

public class Regex {
    public static final String EMAIL_RGX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+(;[A-Za-z0-9" +
            "._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+)*$";
    public static final String PASSWORD_RGX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\\\":{}|<>])" +
            "[A-Za-z0-9!@#$%^&*(),.?\\\":{}|<>]{8,}$";
}
