package com.github.hagek.discord;

import com.github.hagek.APICaller;
import com.github.hagek.pokemon.Pokemon;
import com.github.hagek.pokemon.PokemonRegistry;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            if (!event.getAuthor().isBot()) {
                String[] args = event.getMessage().getContentRaw().split(" ");
                if (event.getMessage().getContentDisplay().startsWith("/get")) {
                    if (args.length == 2) {
                        int number = Integer.parseInt(args[1]);
                        if (!PokemonRegistry.getRegisteredPokemons().containsKey(number)) {
                            try {
                                PokemonRegistry.put(number, new Pokemon(APICaller.call(new URL(String.format("https://pokeapi.co/api/v2/pokemon/%d", number)))));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Bot.sendEmbed(event.getChannel(), BotUtil.getPokemonEmbed(PokemonRegistry.getRegisteredPokemons().get(number)));
                    }
                } else if (event.getMessage().getContentDisplay().startsWith("/printall")) {
                    Bot.sendMessage(event.getChannel(), PokemonRegistry.getRegisteredPokemons().values().stream().map(Pokemon::getName).collect(Collectors.toList()).toString());
                }
            }
        } else {
            event.getChannel().sendMessage(BotUtil.error("ギルドからのメッセージのみ許可されています"));
        }
    }
}
