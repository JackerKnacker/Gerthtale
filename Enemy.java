package Gerthtale;
/**
 * @(#)Enemy.java
 *
 *
 * @author
 * @version 1.00 2018/5/12
 */
//Enemy.java
//Jakir Ansari/ Alex Shi/ Jason Wong
//Enemy.java is used for accessing variables that are used in the enemy class. Theres also a method to determine if the player could run away or not
import java.util.*;
public class Enemy {
	private int health,runChance,maxHealth;
	private String type;
	private Random rng = new Random();
    public Enemy(int h, int rc,String t) { //delcares the health, max health, run chance and type of enemy
    	this.health = h;
    	this.maxHealth = h;
    	this.runChance = rc;
    	this.type = t;
    }

    public int getHealth(){ //gets health
    	return health;
    }
    public int getMaxHealth(){ //gets maxhealth
    	return maxHealth;
    }
    public String getType(){ //gets type of enemy
    	return type;
    }
    public void damage(int d){ //deals damage
    	health -= d;
    }
    public boolean run(){ //calculate if the player can run away by generating a random number
    	if (runChance == 0) {
    		return false;
    	}
    	if(rng.nextInt(runChance) == runChance-1){
    		return true;
    	}
		return false;
    }
    public void resetHealth() { //resets the enemy health
    	health = maxHealth;
    }
}
