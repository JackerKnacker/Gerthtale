package Gerthtale;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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
		game.loadRects();

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
	private String lastDirection = "down";
	private Rectangle playerHitbox = new Rectangle(382,276,36,48);
	private ArrayList<Rectangle> map1Rects = new ArrayList<Rectangle>();
	Image map = new ImageIcon("map1.png").getImage();
	//Image mask = new ImageIcon("src\\Gerthtale\\map1mask.png").getImage();
	//Image duzzi = new ImageIcon("profile.jpg").getImage().getScaledInstance(96,96,Image.SCALE_DEFAULT);

	//Player sprites
	Image[] playerDown, playerUp, playerLeft, playerRight;

	public GamePanel() {
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		playerDown = new Image[4];
		playerUp = new Image[4];
		playerLeft = new Image[4];
		playerRight = new Image[4];
		for (int i = 0; i < 4; i++) {
			playerDown[i] = new ImageIcon("char1sprites/char1-" + (i+1) + ".png").getImage().getScaledInstance(36, 48,
					Image.SCALE_DEFAULT);
			playerLeft[i] = new ImageIcon("char1sprites/char1-" + (i+5) + ".png").getImage().getScaledInstance(36, 48,
					Image.SCALE_DEFAULT);
			playerRight[i] = new ImageIcon("char1sprites/char1-" + (i+9) + ".png").getImage().getScaledInstance(36, 48,
					Image.SCALE_DEFAULT);
			playerUp[i] = new ImageIcon("char1sprites/char1-" + (i+13) + ".png").getImage().getScaledInstance(36, 48,
					Image.SCALE_DEFAULT);
		}
		mapx = 256;
		mapy = 256;
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
		playerFrame = 0; //Restarts frame if user stops pressing key and starts again
	}

	public void loadRects() {
		map1Rects.add(new Rectangle(32+mapx,32+mapy,96,96));
		map1Rects.add(new Rectangle(0+mapx, 192+mapy, 128, 128));
		map1Rects.add(new Rectangle(256+mapx, 96+mapy, 64, 96));
	}

	public boolean checkCollision(String dir, ArrayList<Rectangle> rectList) {
		boolean flag = false;
		for (Rectangle r : rectList) {
			if (dir == "l")
				r.translate(4,0);
			else if (dir == "r")
				r.translate(-4,0);
			else if (dir == "u")
				r.translate(0,4);
			else if (dir == "d")
				r.translate(0,-4);
			if (playerHitbox.intersects(r)) {
				for (Rectangle s : rectList) {
					if (dir == "l")
						s.translate(-4,0);
					else if (dir == "r")
						s.translate(4,0);
					else if (dir == "u")
						s.translate(0,-4);
					else if (dir == "d")
						s.translate(0,4);
				}
				flag = true;
			}
		}
		return flag;
	}
	public void move() {
		requestFocus();
		if (keys[KeyEvent.VK_LEFT]) {
			lastDirection = "left";
			if (mapx < 382) {
				if (checkCollision("l",map1Rects) == false) {
						playerFrame += 0.2;
						if (playerFrame > 3.8)
							playerFrame = 0;
						mapx += 4;
				}
			}

		}
		else if (keys[KeyEvent.VK_RIGHT]) {
			lastDirection = "right";
			if (mapx+1024 > 382+36) { //if mapx + map width > 382 + player sprite width
				if (checkCollision("r",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8)
						playerFrame = 0;
					mapx -= 4;
				}
			}
		}
		else if (keys[KeyEvent.VK_DOWN]) {
			lastDirection = "down";
			if (mapy+768 > 276+48){ //if mapy + map length > 276 + player sprite length
				if (checkCollision("d",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8)
						playerFrame = 0;
					mapy -= 4;
				}
			}
		}
		else if (keys[KeyEvent.VK_UP]) {
			lastDirection = "up";
			if (mapy < 276) {
				if (checkCollision("u",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8)
						playerFrame = 0;
					mapy += 4;
				}
			}
		}
	}

	public void displayPlayer(Graphics g) {
		requestFocus();
		if (keys[KeyEvent.VK_LEFT]) {
			g.drawImage(playerLeft[(int) Math.floor(playerFrame)], 382, 276, this);
		}
		else if (keys[KeyEvent.VK_RIGHT]) {
			g.drawImage(playerRight[(int) Math.floor(playerFrame)], 382, 276, this);
		}
		else if (keys[KeyEvent.VK_UP]) {
			g.drawImage(playerUp[(int) Math.floor(playerFrame)], 382, 276, this);
		}
		else if (keys[KeyEvent.VK_DOWN]) {
			g.drawImage(playerDown[(int) Math.floor(playerFrame)], 382, 276, this);
		}
		else { //When player is not moving
			if (lastDirection.equals("down"))
				g.drawImage(playerDown[0], 382, 276, this);
			else if (lastDirection.equals("up"))
				g.drawImage(playerUp[0], 382, 276, this);
			else if (lastDirection.equals("left"))
				g.drawImage(playerLeft[0], 382, 276, this);
			else if (lastDirection.equals("right"))
				g.drawImage(playerRight[0], 382, 276, this);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawImage(mask, mapx, mapy, this);
		g.drawImage(map, mapx, mapy, this);
		g.setColor(Color.RED);
		for (Rectangle r : map1Rects) {
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
			//g.drawImage(duzzi, (int)r.getX(), (int)r.getY(), this);
		}
		displayPlayer(g);
	}
}
