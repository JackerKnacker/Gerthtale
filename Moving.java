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
	Image map = new ImageIcon("map1.png").getImage();

	//Player sprites
	Image[] player;

	public GamePanel() {
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		player = new Image[8];
		for (int i = 0; i < 8; i++) {
			player[i] = new ImageIcon("sprDown" + i + ".png").getImage().getScaledInstance(64, 64,
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
		playerFrame = 0;
	}
	public void move() {
		requestFocus();
		if (keys[KeyEvent.VK_LEFT]) {
			if (mapx < 368)
				mapx += 4;
		}
		if (keys[KeyEvent.VK_RIGHT]) {
			if (mapx+1024 > 368+64) //if mapx + map width > 368 + player sprite width
				mapx -= 4;
		}
		if (keys[KeyEvent.VK_DOWN]) {
			if (mapy+768 > 268+64){ //if mapy + map length > 268 + player sprite length
				playerFrame += 0.15;
				if (playerFrame >= 7.75) {
					playerFrame = 0;
				}
				mapy -= 4;
			}
			//System.out.println(mapx + " " + mapy);
		}
		if (keys[KeyEvent.VK_UP]) {
			if (mapy < 268)
				mapy += 4;
		}
	}

	public void displayPlayer(Graphics g) {
		requestFocus();
		g.drawImage(player[(int) Math.floor(playerFrame)], 368, 268, this); //368, 268 = IN THE MIDDLE OF SCREEN
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(map, mapx, mapy, this);
		displayPlayer(g);
	}
}
