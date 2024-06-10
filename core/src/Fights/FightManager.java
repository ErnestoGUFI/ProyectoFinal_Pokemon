package Fights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.badlogic.gdx.graphics.Texture;
import com.pokemon.game.MyPokemonGame;

import Pokemons.Pokemon;
import Audio.Sonido;

public class FightManager {
    private MyPokemonGame game;
    private Fight peleaScreen;
    private Timer tiempo;
    private int seg = 0;
    private String playerName;
    private int score;
    private boolean pelea = false;
    private boolean turno = true;
    private boolean paused = false;
    private float pauseTimer = 0f;
    private final float PAUSE_DURATION = 2f;
    private int pokemonRandom = -1;
    private Sonido sonido; // Instancia para manejar los sonidos

    private Pokemon[] arregloPokemon = {
    		new Pokemon("Bulbasaur",100,new Texture("Pokemons/bulbasaurSprite.png"),"Placaje",20,"Latigo cepa",15,"Arañazo",12,"Embestida",20),
    		new Pokemon("Ivysaur", 100, new Texture("Pokemons/ivysaurSprite.png"), "Hoja Afilada", 20, "Látigo Cepa", 15, "Polvo Veneno", 12, "Bomba Germen", 20),
    		new Pokemon("Venusaur", 100, new Texture("Pokemons/venusaurSprite.png"), "Hoja Afilada", 20, "Látigo Cepa", 15, "Polvo Veneno", 12, "Bomba Germen", 20),
    		new Pokemon("Charmander", 100, new Texture("Pokemons/charmanderSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Charmeleon", 100, new Texture("Pokemons/charmeleonSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Charizard", 100, new Texture("Pokemons/charizardSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Squirtle", 100, new Texture("Pokemons/squirtleSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Wartortle", 100, new Texture("Pokemons/wartottleSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Blastoise", 100, new Texture("Pokemons/blastoiseSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Pikachu", 100, new Texture("Pokemons/pikachuSprite.png"), "Impactrueno", 20, "Rayo", 15, "Chispazo", 12, "Voltio Cruel", 20),
    		new Pokemon("Pidgey", 100, new Texture("Pokemons/pidgeySprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Pidgeotto", 100, new Texture("Pokemons/pidgeottoSprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Pidgeot", 100, new Texture("Pokemons/pidgeotSprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Abra", 100, new Texture("Pokemons/abraSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Kadabra", 100, new Texture("Pokemons/kadabraSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Alakazam", 100, new Texture("Pokemons/alakazamSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Vaporeon", 100, new Texture("Pokemons/vaporeonSprite.png"), "Aqua Jet", 20, "Hidrobomba", 15, "Rayo Burbuja", 12, "Cola Surf", 20),
    		new Pokemon("Jolteon", 100, new Texture("Pokemons/jolteonSprite.png"), "Chispa", 20, "Trueno", 15, "Voltio Cruel", 12, "Bola Voltio", 20),
    		new Pokemon("Flareon", 100, new Texture("Pokemons/flareonSprite.png"), "Ascuas", 20, "Llama", 15, "Sofoco", 12, "Puño Fuego", 20),
    		new Pokemon("Nidoran hembra", 100, new Texture("Pokemons/nidoranhembraSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidorina", 100, new Texture("Pokemons/nidorinaSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidoqueen", 100, new Texture("Pokemons/nidoqueenSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Ratatta", 100, new Texture("Pokemons/ratattaSprite.png"), "Ataque Rápido", 20, "Mordisco", 15, "Hipercolmillo", 12, "Tajo Umbrío", 20),
    		new Pokemon("Raticate", 100, new Texture("Pokemons/raticateSprite.png"), "Ataque Rápido", 20, "Mordisco", 15, "Hipercolmillo", 12, "Tajo Umbrío", 20),
    		new Pokemon("Nidoran macho", 100, new Texture("Pokemons/nidoranmachoSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidorino", 100, new Texture("Pokemons/nidorinoSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidoking", 100, new Texture("Pokemons/nidokingSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20)
    };

    private Pokemon[] listaPokemon = {
        arregloPokemon[0],
        arregloPokemon[5],
        arregloPokemon[6],
        arregloPokemon[9],
        arregloPokemon[12],
    };

    public FightManager(MyPokemonGame game, String playerName) {
        this.game = game;
        this.playerName = playerName;
        peleaScreen = new Fight(game, listaPokemon);
        sonido = new Sonido(); // Inicializa la instancia de sonidos

        tiempo = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seg++;
                System.out.println(seg);
            }
        });

        score = 0;
    }

    public void startBattle() {
        if (!tiempo.isRunning() && pokemonRandom == -1) {
            tiempo.start();
            Random rand = new Random();
            pokemonRandom = rand.nextInt(arregloPokemon.length);
            peleaScreen.setPokemonEnemigo(arregloPokemon[pokemonRandom]);
        }
    }

    public void renderBattle(float delta) {
        peleaScreen.cameraFight.update();
        game.batch.setProjectionMatrix(peleaScreen.cameraFight.combined);

        if (paused) {
            peleaScreen.batallaScreen();
            pauseTimer += delta;
            if (pauseTimer >= PAUSE_DURATION) {
                playSonidoAtaque(); // Reproducir sonido de ataque enemigo
                peleaScreen.turnoEnemigo();
                pauseTimer = 0f;
                paused = false;
            }
            return;
            
        }
        peleaScreen.cameraFight.update();
        game.batch.setProjectionMatrix(peleaScreen.cameraFight.combined);
        peleaScreen.sr.setProjectionMatrix(peleaScreen.cameraFight.combined);

        if (seg < 5) {
            peleaScreen.introBatalla();
        } else if (seg >= 5) {
            if (tiempo.isRunning()) tiempo.stop();
            if (turno) {
                peleaScreen.batallaScreen();
                if (peleaScreen.seleccion()) {
                    
                    turno = false;
                }
            } else {
                paused = true;
                turno = true;
            }
        }

        if (peleaScreen.pokemonEnemigo.vida <= 0) {
            score += 10;
            resetBattle();
            System.out.println(score);
        }
    }

    public void resetBattle() {
        pelea = false;
        seg = 0;
        arregloPokemon[pokemonRandom].vida = 100;
        pokemonRandom = -1;
        peleaScreen.ySpriteJugador = 0;
        peleaScreen.ySpriteEnemigo = 520;
        peleaScreen.porcentajeEnemigo = 1f;
        peleaScreen.porcentajeJugador = 1f;
    }

    public boolean isPelea() {
        return pelea;
    }

    public void setPelea(boolean pelea) {
        this.pelea = pelea;
    }

    private void playSonidoAtaque() {
        sonido.playAtaqueSonido();
    }
    
    public int getScore() {
        return score;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}
