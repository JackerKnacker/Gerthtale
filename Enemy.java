package Gerthtale;
/**
 * @(#)Enemy.java
 *
 *
 * @author
 * @version 1.00 2018/5/12
 */

import java.util.*;
public class Enemy {
	private int health,runChance,maxHealth;
	private String type;
	private Random rng = new Random();
    public Enemy(int h, int rc, String t) {
    	health = h;
    	maxHealth = h;
    	runChance = rc;
    	type = t;
    }
    public int getMaxHealth() {
    	return maxHealth;
    }
    public int getHealth(){
    	return health;
    }
    public String getType(){
    	return type;
    }
    public void damage(int d){
    	health -= d;
    }
    public void resetHealth() {
    	health = maxHealth;
    }
    public boolean run(){
    	if(rng.nextInt(runChance) == runChance){
    		return true;
    	}
		return false;
    }
}
