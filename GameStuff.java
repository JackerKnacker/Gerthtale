import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.MouseInfo;

public class GameStuff extends JFrame implements ActionListener{
	Timer myTimer;
	GamePanel game;

    public GameStuff() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);

		myTimer = new Timer(10, this);	 // trigger every 10 ms


		game = new GamePanel(this);
		add(game);

		setResizable(false);
		setVisible(true);
    }

	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.move();
		game.repaint();
	}

    public static void main(String[] arguments) {
		GameStuff frame = new GameStuff();
    }
}

class GamePanel extends JPanel implements KeyListener{
	private int timer = 0;
	private boolean []keys;
	private boolean immune;
	private Image back;
	private GameStuff mainFrame;
	private Rectangle baseRect = new Rectangle(150,360,480,170);
	private Color dgreen = new Color(0, 153, 10);
	private Enemy goon = new Enemy(50,baseRect);
	private Player character = new Player(50,baseRect);


	public GamePanel(GameStuff m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("map.png").getImage();
		back = back.getScaledInstance(800,600,Image.SCALE_SMOOTH);
		mainFrame = m;
		setSize(800,600);
        addKeyListener(this);
	}

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

	public void move(){
		if(keys[KeyEvent.VK_RIGHT] ){
			character.pRect.translate(1,0);
		}
		if(keys[KeyEvent.VK_LEFT] ){
			character.pRect.translate(-1,0);
		}
		if(keys[KeyEvent.VK_UP] ){
			character.pRect.translate(0,-1);
		}
		if(keys[KeyEvent.VK_DOWN] ){
			character.pRect.translate(0,1);
		}
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		//System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
	}

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        character.stop = true;

    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	Random rng = new Random();
    	rng.nextInt(140);
    	g.drawImage(back,0,0,this);
    	//Enemy Battle Stuff
    	g.setColor(dgreen);
    	g.fillRect(140,350,500,190);
    	g.setColor(Color.white);
    	g.fillRect(150,360,480,170);
		if(character.getHealth() <= 0){
			System.out.println("Lose");
		}
		else{
			//Attack1
			/*
			goon.attack1(g);
			character.displayRectangle(character.pRect,g);
			if(character.collision(goon.rects) && !immune){
				character.damage(3);
			}
			*/

			//Attack2
			/*
			goon.attack2(g);
			if(!immune){
				character.displayRectangleG(character.pRect,g);
			}
			else{
				character.displayRectangleBL(character.pRect,g);
				timer++;
			}
			if((character.pRect.intersects(goon.attackRect) && !immune && goon.displaying) ||
				(character.pRect.intersects(goon.leftattackRect) && !immune && goon.displaying) ||
				(character.pRect.intersects(goon.rightattackRect) && !immune && goon.displaying)){

				character.damage(5);
				immune = true;
				timer = 0;
			}
			if(timer > 100){
				immune = false;
			}
			*/
			//Attack 3
			//goon.attack3(g);
			
		}
		character.inside();
		//character.attack(g);
		/*
		g.setColor(Color.red);
		for(int i = 40; i <= 622; i += 190){
			g.fillRect(i,460,150,40);
		}
		*/
    }
}