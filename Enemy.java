
/**
 * @(#)Enemy.java
 *
 *
 * @author
 * @version 1.00 2018/5/12
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.Timer;
public class Enemy {
	private int health,timer,directionX = 2,directionL = 2,directionR = -2,fire;
	private Random rng = new Random();
	private String attackType;
	private Rectangle baseRect,topRect,leftRect,rightRect;
	public Rectangle attackRect,leftattackRect,rightattackRect;
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> web1 = new ArrayList<Rectangle>();
	public boolean displaying = false;
	private boolean displayed = false;
    public Enemy(int h, Rectangle r) {
    	health = h;
    	baseRect = r;
    }
    public int getHealth(){
    	return health;
    }
    public void damage(int d){
    	health -= d;
    }
    public void attack1(Graphics g){
    	attackType = "attack1";
    	if(!displayed){
    		rects.clear();
			for(int i = 600; i <= 1800; i+=40){ //1. starting pos for rects 2. amount of rects 3. space between rects
				int leftY = rng.nextInt((int) baseRect.getHeight());
				while (leftY < 20 || leftY > 120){ //change bounds accordingly
					leftY = rng.nextInt((int) baseRect.getHeight());
				}
				Rectangle topRect = new Rectangle(i,(int) baseRect.getY(),10,leftY);
				Rectangle bottomRect = new Rectangle(i,(int) baseRect.getY()+40+leftY,10,((int) baseRect.getHeight()-40)-leftY); //y value - baseRectY - leftY = space in between rects. BaseRectWidth - space - leftY
				rects.add(topRect);
				rects.add(bottomRect);
			}
			displayed = true;
    	}
    	for(int i = rects.size()-1; i>=0; i--){

    		Rectangle r = rects.get(i);
    		if(r.getX() < (int) baseRect.getX() + (int) baseRect.getWidth()){
    			displayRectangleBL(r,g);
    		}
    		r.translate(-1,0);
    		if(r.getX() < (int) baseRect.getX()){
				rects.remove(r);
				//r.setLocation(760,(int)r.getY()); //i at finish - (i at start - x) + 40
				//rects.add(r);
			}
    	}

    }



    public void attack2(Graphics g){
    	if(!displayed){
    		fire = rng.nextInt(100) + 300; //change timer after testing
	    	int newX = rng.nextInt((int) baseRect.getWidth());
	    	int leftY = rng.nextInt((int) baseRect.getHeight());
	    	int rightY = rng.nextInt((int) baseRect.getHeight());
	    	while(Math.abs(leftY - rightY) < 20){ //player height + 10
	    		rightY = rng.nextInt((int) baseRect.getHeight());
	    	}
	    	topRect = new Rectangle ((int) baseRect.getX() + newX, (int) baseRect.getY() - 60 ,30,30);
	    	attackRect = new Rectangle((int) topRect.getX() + 5,(int) topRect.getY() + (int) topRect.getHeight(),(int) topRect.getWidth() - 10,600);
	    	leftRect = new Rectangle ((int) baseRect.getX() - 60, (int) baseRect.getY() + leftY ,30,30);
	    	leftattackRect = new Rectangle((int) leftRect.getX() + (int) leftRect.getWidth(),(int) leftRect.getY() + 5,800,(int) leftRect.getHeight() - 10);
	    	rightRect = new Rectangle ((int) baseRect.getX() + (int) baseRect.getWidth() + 30, (int) baseRect.getY() + rightY ,30,30);
	    	rightattackRect = new Rectangle(0,(int) rightRect.getY() + 5,800 - (800 - (int) rightRect.getX()),(int) rightRect.getHeight() - 10);
	    	displayed = true;
    	}
		displayRectangle(topRect,g);
		displayRectangle(leftRect,g);
		displayRectangle(rightRect,g);
		if((int) attackRect.getX() + 10 > (int) baseRect.getX() + (int) baseRect.getWidth() - (int) attackRect.getWidth()){
			directionX = -2; //rate which its moving
		}
		else if((int) attackRect.getX() - 10 < (int) baseRect.getX()){
			directionX = 2;
		}
		if((int) leftattackRect.getY() + 10 > (int) baseRect.getY() + (int) baseRect.getHeight() - (int) leftattackRect.getHeight()){
			directionL = -2; //rate which its moving
		}
		else if((int) leftattackRect.getY() - 10 < (int) baseRect.getY()){
			directionL = 2;
		}
		if((int) rightattackRect.getY() + 10 > (int) baseRect.getY() + (int) baseRect.getHeight() - (int) rightattackRect.getHeight()){
			directionR = -2; //rate which its moving
		}
		else if((int) rightattackRect.getY() - 10 < (int) baseRect.getY()){
			directionR = 2;
		}
		if(Math.abs((int) leftRect.getY() + (int) leftRect.getHeight() - (int) rightRect.getY()) > 70 &&
			(timer >= fire && timer < fire + 150 || timer >= fire + 300 && timer < fire + 450 || timer >= fire + 800 && timer < fire + 950 || timer >= fire + 950 && timer < fire + 1100)){ //fire + how many seconds its firing
			displayRectangleBL(attackRect,g);
			displayRectangleBL(leftattackRect,g);
			displayRectangleBL(rightattackRect,g);
			displaying = true;
		}
		else{
			leftRect.translate(0,directionL);
			leftattackRect.translate(0,directionL);
			rightRect.translate(0,directionR);
			rightattackRect.translate(0,directionR);
			displaying = false;
		}
		topRect.translate(directionX,0);
		attackRect.translate(directionX,0);
		timer++;

    }
    /*
    public void attack3(Graphics g){
    	if(!displayed){
    		rects.clear();
    		timer = 0;
    		int web = rng.nextInt(20) + 30;
    		int webTimer = rng.nextInt(50) + 100;
    		/*
    		int web2 = rng.nextInt(20) + 30;
    		int web3 = rng.nextInt(20) + 30;
    		int web4 = rng.nextInt(20) + 30;
    		int web5 = rng.nextInt(20) + 30;
    		
    			
	    	for(int i = ((int) baseRect.getWidth() - 5*5)/6 + 1; i <= (int) baseRect.getWidth(); i += ((int) baseRect.getWidth() - 5*5)/6 + 6){
	    		Rectangle spiderWeb = new Rectangle(i  + (int) baseRect.getX(),(int) baseRect.getY(),5,(int) baseRect.getHeight());
	    		rects.add(spiderWeb);
	    	}
	    	while(web1.size() < web){
	    		
	    	}
	    	Rectangle up = ((int) r.getX()-3, (int) r.getY(),11,11);
	    	displayed = false;
    	}
    	for(int i = rects.size()-1; i>=0; i--){
			Rectangle r = rects.get(i);
			displayRectangleBL(r,g);	
    	}
    	up.translate
    	timer++;
    	
    }
    */
    public void displayRectangle(Rectangle r, Graphics g){
		g.setColor(Color.white);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));

    }
    public void displayRectangleBL(Rectangle r, Graphics g){
		g.setColor(Color.black);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));
    }
}

    /*
     *
     
    	    	rects.clear();
	    	enemyNumber = rng.nextInt(3) + 2;
	    	for(int i = 0; i < enemyNumber; i++){
	    		Rectangle enemyRect = new Rectangle (160,300+40*i,10,10);
	    		rects.add(enemyRect);
	    	}
	
			for(int i = rects.size()-1; i>=0; i--){
			Rectangle r = rects.get(i);
			if((int)r.getX() > (int) playerR.getX()){
				r.translate(-1,0);
			}
			if((int)r.getX() < (int) playerR.getX()){
				r.translate(1,0);
			}
			if((int)r.getY() > (int) playerR.getY()){
				r.translate(0,-1);
			}
			if((int)r.getY() < (int) playerR.getY()){
				r.translate(0,1);
			}
			displayRectangleBL(r,g);
		}
	*/