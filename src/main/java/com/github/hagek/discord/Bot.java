package com.github.hagek.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.security.auth.login.LoginException;

public class Bot {
    private static final String TOKEN = "Nzk3MDU1OTIxNjY2NTIzMTM2.X_g6LA.oEvF1LStxauI0fWaIB81KJJOhzU";
    private static JDA jda;

    public static void login() throws LoginException {
        jda = JDABuilder.createDefault(TOKEN)
                .addEventListeners(new BotListener())
                .build();
    }

    public static void sendMessage(MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public static void sendEmbed(MessageChannel channel, MessageEmbed embed) {
        channel.sendMessage(embed).queue();
    }
}
