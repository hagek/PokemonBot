package com.github.hagek.pokemon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PokemonRegistry {
    private static final Map<Integer, Pokemon> REGISTERED_POKEMONS = new HashMap<>();

    public static void put(int number, Pokemon pokemon) {
        REGISTERED_POKEMONS.put(number, pokemon);
    }

    public static Map<Integer, Pokemon> getRegisteredPokemons() {
        return Collections.unmodifiableMap(REGISTERED_POKEMONS);
    }
}
