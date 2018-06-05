  /**
 * @(#)Player.java
 *
 *
 * @author
 * @version 1.00 2018/5/21
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.Timer;
public class Player {
	private int health,linex = 100,damage;
	private String charType;
	public boolean reverse,stop;
	private ArrayList<String> items = new ArrayList<String>();
	public Rectangle pRect = new Rectangle(300,410,10,10), baseRect;
    public Player(int h, Rectangle r) {
    	health = h;
    	baseRect = r;
    }
    public int getHealth(){
    	return health;
    }
    public void damage(int d){
    	health -= d;
    }
    public void displayRectangleG(Rectangle r, Graphics g){
		g.setColor(Color.green);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));

    }
    public void displayRectangleBL(Rectangle r, Graphics g){
		g.setColor(Color.black);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));
    }
    public boolean collision(ArrayList<Rectangle> ar){
    	for(Rectangle newR: ar){
    		if(pRect.intersects(newR)){
    			pRect.setLocation((int)newR.getX()-(int)pRect.getWidth(),(int)pRect.getY());
    			return true;
    		}
    	}
    	return false;
    }
	public void inside(){
		if((int)baseRect.getX() >= (int)pRect.getX()){
			pRect.setLocation((int)baseRect.getX(),(int)pRect.getY());
		}
		else if((int)pRect.getX() >= (int)baseRect.getX() + (int)baseRect.getWidth() - (int)pRect.getWidth()){ //rect x + width - player width
			pRect.setLocation((int)baseRect.getX() + (int)baseRect.getWidth() - (int)pRect.getWidth(),(int)pRect.getY());
		}
		if((int)baseRect.getY() >= (int)pRect.getY()){
			pRect.setLocation((int)pRect.getX(),(int)baseRect.getY());
		}
		else if((int)pRect.getY() >= (int)baseRect.getY() + (int)baseRect.getHeight() - (int)pRect.getHeight()){
			pRect.setLocation((int)pRect.getX(),(int)baseRect.getY() + (int)baseRect.getHeight() - (int)pRect.getHeight());
		}
	}
	public int attack(Graphics g){
		damage = 0;
    	g.setColor(Color.red);
    	g.fillRect(120,400,480,170);
		g.setColor(Color.white);
		g.fillRect(360,400,7,170);
		g.setColor(Color.blue);
		if(linex > 590){
			reverse = true;
		}
		else if(linex < 121){
			reverse = false;
		}
		if(reverse && !stop){
			linex -= 10;
			g.fillRect(linex,400,7,170);
		}
		else if(!reverse && !stop){
			linex += 10;
			g.fillRect(linex,400,7,170);
		}
		else if(stop){
			g.fillRect(linex,400,7,170);
			if(linex < 360){
				damage = linex/10;
			}
			else if(linex > 360){
				damage = (620-linex)/10;
			}
			else if(linex == 360){
				damage = 36;
			}
		}
		return damage;
	}

}