package com.pokemon.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("PokemonGDX");
        //config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode()); // Iniciar en pantalla completa
        config.setWindowedMode(1280,720);
        config.setResizable(true); // Permitir que la ventana sea redimensionable
        new Lwjgl3Application(new MyPokemonGame(), config);
    }
}