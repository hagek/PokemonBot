package com.github.hagek.discord;

import com.github.hagek.pokemon.Pokemon;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Optional;

public class BotUtil {

    public static MessageEmbed error(String reason) {
        return new EmbedBuilder()
                .setTitle("エラー")
                .setDescription(reason)
                .setColor(0xff0000)
                .build();
    }

    public static MessageEmbed getPokemonEmbed(Pokemon pokemon) {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle(pokemon.getName())
                .setDescription(String.format("%s\n%s\n", pokemon.getGenus(), pokemon.getFlavorText()))
                .setThumbnail(pokemon.getImageUrl())
                .addField("タイプ", pokemon.getTypes().getKey().getEmojiId() + " " + (pokemon.getTypes().getValue() == null ? "" : pokemon.getTypes().getValue().getEmojiId()), false);
        pokemon.getValues().forEach((type, value) -> eb.addField(type.getJapanese(), value.toString(), true));
        return eb.build();
    }
}
