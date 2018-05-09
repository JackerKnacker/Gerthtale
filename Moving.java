package Gerthtale;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Moving extends JFrame implements ActionListener {
	
	GamePanel game;
	Timer myTimer;
	
	public Moving() {
		super("Gerthtale: Moving");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allowing for the program to PROPERLY close
		setSize(800,600); //Window Size
		
		setVisible(true);
		game = new GamePanel();
		add(game);
		
		myTimer = new Timer(20, this);
		myTimer.start();
	}
	public void actionPerformed(ActionEvent evt) {
		if (game != null) {
			game.repaint();
			game.move();
		}
	}
	
	public static void main(String[] args) {
		Moving game = new Moving();
	}
}

class GamePanel extends JPanel implements KeyListener {
	private boolean[] keys;
	private int mapx, mapy;
	private double playerFrame;
	Image map = new ImageIcon("src\\Gerthtale\\map1.png").getImage();
	
	//Player sprites
	Image[] player;
	
	public GamePanel() {
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		player = new Image[8];
		for (int i = 0; i < 8; i++) {
			player[i] = new ImageIcon("src\\Gerthtale\\sprDown" + i + ".png").getImage().getScaledInstance(50, 50, 
					Image.SCALE_DEFAULT);
		}
		
		mapx = 250;
		mapy = 250;
		playerFrame = 0;
		
		setSize(800,600);
		addKeyListener(this);
		
	}
	
	//KEYBOARD METHODS - Moving Code
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		playerFrame = 0;
	}
	public void move() {
		requestFocus();
		if (keys[KeyEvent.VK_LEFT]) {
			//if (mapx <= 375)
			mapx += 5;
		}
		if (keys[KeyEvent.VK_RIGHT]) {
			mapx -= 5;
		}
		if (keys[KeyEvent.VK_DOWN]) {
			playerFrame += 0.15;
			if (playerFrame >= 7.75) {
				playerFrame = 0;
			}
			mapy -= 5;
		}
		if (keys[KeyEvent.VK_UP]) {
			mapy += 5;
		}
	}
	
	public void displayPlayer(Graphics g) {
		requestFocus();
		g.drawImage(player[(int) Math.floor(playerFrame)], 375, 275, this); //375, 275 = IN THE MIDDLE OF SCREEN

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(map, mapx, mapy, this);
		displayPlayer(g);
	}
}
