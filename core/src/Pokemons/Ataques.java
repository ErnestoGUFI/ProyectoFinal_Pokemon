package Pokemons;

public class Ataques {
	public String nombre;
	public int damage;
	
	public Ataques(String nombre, int damage) {
		this.nombre = nombre;
		this.damage = damage;
	}
	
	public float atacar(Pokemon pokemon) {
		int vida = pokemon.vida - this.damage;
		
		float porcentajeVida = (vida * 100) / pokemon.vida;
		
		return porcentajeVida;
		
	}
}
