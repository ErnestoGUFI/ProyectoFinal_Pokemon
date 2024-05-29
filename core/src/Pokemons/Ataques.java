package Pokemons;


public class Ataques {
	public String nombre;
	public int damage;
	
	public Ataques(String nombre, int damage) {
		this.nombre = nombre;
		this.damage = damage;
	}
	
	public float atacar(Pokemon pokemon, float porcentajeVida) {
		int vida = pokemon.vida - this.damage;
		
		porcentajeVida = (vida * 100) / pokemon.vida;
		
		return porcentajeVida;
		
	}
}
