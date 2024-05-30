package Pokemons;

import com.badlogic.gdx.graphics.Texture;

public class Pokemon {
	public String nombre;
	public int vida;
	public int cantPeleas;
	public Texture pokemonSprite;
	public Ataques[] atacks = {
			new Ataques("",0),
			new Ataques("",0),
			new Ataques("",0),
			new Ataques("",0),
	}; 
	
	public Pokemon(String nombre, int vida, Texture pokemonSprite, String atack1, int damage1, String atack2, int damage2, String atack3, int damage3, String atack4, int damage4) {
		this.nombre = nombre;
		this.vida = vida;
		this.cantPeleas = 0;
		this.pokemonSprite = pokemonSprite;
		
		atacks[0] = new Ataques(atack1,damage1);
		atacks[1] = new Ataques(atack2,damage2);
		atacks[2] = new Ataques(atack3,damage3);
		atacks[3] = new Ataques(atack4,damage4);
	}
	
	
	
	
	
}
