package com.github.hagek;

import com.github.hagek.discord.Bot;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        try {
            Bot.login();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
