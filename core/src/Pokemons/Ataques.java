package Pokemons;

public class Ataques {
	public String nombre;
	public int damage;
	
	public Ataques(String nombre, int damage) {
		this.nombre = nombre;
		this.damage = damage;
	}
	
	public float atacar(Pokemon pokemon) {
		float auxVida = 100;
		float vida = pokemon.vida - this.damage;
		
		pokemon.vida = (int)vida;
		
		float porcentajeVida = (float)(vida / auxVida);
		
		return porcentajeVida;
		
	}
}
