package Gerthtale;

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

	public void loadRects(int mapNum) {

	}
	public void move() {
		requestFocus();
		if (keys[KeyEvent.VK_LEFT]) {
			lastDirection = "left";
			playerFrame += 0.2;
			if (playerFrame > 3.8)
				playerFrame = 0;
			if (mapx < 382)
				mapx += 4;
		}
		else if (keys[KeyEvent.VK_RIGHT]) {
			lastDirection = "right";
			playerFrame += 0.2;
			if (playerFrame > 3.8)
				playerFrame = 0;
			if (mapx+1024 > 382+36) //if mapx + map width > 382 + player sprite width
				mapx -= 4;
		}
		else if (keys[KeyEvent.VK_DOWN]) {
			lastDirection = "down";
			playerFrame += 0.2;
			if (playerFrame > 3.8)
				playerFrame = 0;
			if (mapy+768 > 276+48){ //if mapy + map length > 276 + player sprite length
				mapy -= 4;
			}
		}
		else if (keys[KeyEvent.VK_UP]) {
			lastDirection = "up";
			playerFrame += 0.2;
			if (playerFrame > 3.8)
				playerFrame = 0;
			if (mapy < 276)
				mapy += 4;
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
		displayPlayer(g);
	}
}
