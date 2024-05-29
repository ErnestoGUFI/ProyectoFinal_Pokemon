package Pokemons;

import com.badlogic.gdx.graphics.Texture;

public class Pokemon {
	public String nombre;
	public int vida;
	public int cantPeleas;
	public Texture pokemonSprite;
	
	public Pokemon(String nombre, int vida, Texture pokemonSprite) {
		this.nombre = nombre;
		this.vida = vida;
		this.cantPeleas = 0;
		this.pokemonSprite = pokemonSprite;
	}
	
	
	
	
}
