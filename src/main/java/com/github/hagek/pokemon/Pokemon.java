package com.github.hagek.pokemon;

import com.github.hagek.APICaller;
import com.google.gson.JsonObject;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Activity;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Getter
public class Pokemon {
    private final int number;
    private final String name;
    private final String imageUrl;
    private final String genus;
    private final String flavorText;
    private final Pair<PokemonType, PokemonType> types;
    @Getter(value = AccessLevel.NONE)
    private final EnumMap<StatusType, Number> value;

    public Map<StatusType, Number> getValues() {
        return Collections.unmodifiableMap(this.value);
    }

    public Pokemon(JsonObject json) throws IOException {
        this(
                json.getAsJsonPrimitive("id").getAsInt(),
                json.getAsJsonPrimitive("name").getAsString(),
                json.getAsJsonObject("sprites").getAsJsonObject("other").getAsJsonObject("official-artwork").getAsJsonPrimitive("front_default").getAsString(),
                APICaller.call(new URL(json.getAsJsonObject("species").getAsJsonPrimitive("url").getAsString())).getAsJsonArray("genera").get(0).getAsJsonObject().getAsJsonPrimitive("genus").getAsString(),
                StreamSupport.stream(APICaller.call(new URL(json.getAsJsonObject("species").getAsJsonPrimitive("url").getAsString())).getAsJsonArray("flavor_text_entries").spliterator(), false).filter(element -> element.getAsJsonObject().getAsJsonObject("language").getAsJsonPrimitive("name").getAsString().equals("ja")).collect(Collectors.toList()).get(0).getAsJsonObject().getAsJsonPrimitive("flavor_text").getAsString(),
                new Pair<>(PokemonType.valueOf(json.getAsJsonArray("types").get(0).getAsJsonObject().getAsJsonObject("type").getAsJsonPrimitive("name").getAsString().toUpperCase()), ((Supplier<PokemonType>) () -> json.getAsJsonArray("types").size() == 1 ? null : PokemonType.valueOf(json.getAsJsonArray("types").get(1).getAsJsonObject().getAsJsonObject("type").getAsJsonPrimitive("name").getAsString().toUpperCase())).get()),
                ((Supplier<EnumMap<StatusType, Number>>) () -> {
                    EnumMap<StatusType, Number> ret = new EnumMap<>(StatusType.class);
                    json.getAsJsonArray("stats").forEach(element -> ret.put(StatusType.valueOf(element.getAsJsonObject().getAsJsonObject("stat").getAsJsonPrimitive("name").getAsString().toUpperCase().replace('-', '_')), element.getAsJsonObject().getAsJsonPrimitive("base_stat").getAsInt()));
                    return ret;
                }).get()
        );
    }

    @AllArgsConstructor
    @Getter
    public enum StatusType {
        ATTACK("こうげき"),
        SPECIAL_ATTACK("とっこう"),
        DEFENSE("ぼうぎょ"),
        SPECIAL_DEFENSE("とくぼう"),
        SPEED("すばやさ"),
        HP("HP");

        private final String japanese;
    }

    @AllArgsConstructor
    public enum PokemonType {
        NORMAL("ノーマル"),
        FIRE("ほのお"),
        WATER("みず"),
        ELECTRIC("でんき"),
        GRASS("くさ"),
        ICE("こおり"),
        FIGHTING("かくとう"),
        POISON("どく"),
        GROUND("じめん"),
        FLYING("ひこう"),
        PSYCHIC("エスパー"),
        BUG("むし"),
        ROCK("いわ"),
        GHOST("ゴースト"),
        DRAGON("ドラゴン"),
        DARK("あく"),
        STEEL("はがね"),
        FAIRY("フェアリー");

        @Getter
        private final String japanese;

        public String getEmojiId() {
            return String.format(":_%s_:", this.name().toLowerCase());
        }
    }
}
