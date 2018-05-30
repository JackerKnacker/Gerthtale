//Gerthtale.java
//Alex S, Jakir A, Jason W
///To be announced...

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.event.*;

public class Gerthtale extends JFrame implements ActionListener, KeyListener {
    javax.swing.Timer timer;

    JPanel cards;
    CardLayout cLayout = new CardLayout();

    // LayeredPane allows my to control what shows on top
    JLayeredPane mPage = new JLayeredPane(); //menu page
    JLayeredPane pPage = new JLayeredPane(); //player creation page
    JLayeredPane sPage = new JLayeredPane(); //save-select page
    JLayeredPane iPage = new JLayeredPane(); //options page
    JLayeredPane cPage = new JLayeredPane(); //credits page

    JButton newGameBut;
    JButton saveSelectBut;
    JButton instructBut;
    JButton creditBut;
    JButton continueBut;
    JButton soundBut;
	JButton backBut;
	JButton confirmBut;
	JButton pBut1;
	JButton pBut2;
	JButton pBut3;

	JLabel charDisplay1;
	JLabel charDisplay2;
	JLabel charDisplay3;
	JTextField nameBox;

	Sound menuTheme;

	//The two icons will be used interchangeably to display whether or not the music is muted or not
    ImageIcon soundIcon1 = new ImageIcon("Pictures/sound.png"); //soundIcon1 holds the unmuted version of the sound image
    ImageIcon soundIcon2 = new ImageIcon("Pictures/mute.png");  //soundIcon2 holds the muted version of the sound image

	String saveSlotSelect = "none"; //keeps track which save slot is selected
	String charName;
	int charSelect = 0;

	GameScreen game;

    public Gerthtale() {
        super("Gerthtale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        setLocationRelativeTo(null); //makes the window appear in the center

        this.timer = new javax.swing.Timer(30, this);

        menuTheme = new Sound("Music/FINAL FANTASY XV - Somnus (Instrumental Version).wav"); //This loads the background music
        menuTheme.loop(); //This loops the background music

        //This sets the icon image for the game
        setIconImage(new ImageIcon("Pictures/logo.png").getImage());

        //\/ --- Menu Displays --- \/
        this.mPage.setLayout(null); //null layout
        this.cards = new JPanel(cLayout);

        this.cards.add(mPage, "menu");
        this.cards.add(pPage, "playerCreation");
		this.cards.add(sPage, "saveSelect");
		this.cards.add(iPage, "instructions");
		this.cards.add(cPage, "credits");
		this.add(cards);
		cards.setBackground(Color.WHITE);

		//\/ --- components for mPage --- \/
		JLabel title1 = new JLabel(new ImageIcon("Pictures/title1.png"));
        title1.setSize(400,100);
        title1.setLocation(200,50);
        this.mPage.add(title1,1);

        JLabel title2 = new JLabel(new ImageIcon("Pictures/title2.png"));
        title2.setSize(150,200);
        title2.setLocation(125,0);
        this.mPage.add(title2,2);

        JLabel title3 = new JLabel(new ImageIcon("Pictures/title3.png"));
        title3.setSize(600,400);
        title3.setLocation(150,100);
        this.mPage.add(title3,2);

		//newGame Button
		this.newGameBut = new JButton(new ImageIcon("Pictures/newGame.png"));
        this.newGameBut.addActionListener(this);
        this.newGameBut.setSize(250,50);
        this.newGameBut.setLocation(50,200);
        this.newGameBut.setContentAreaFilled(false);
        this.newGameBut.setFocusPainted(false);
        this.newGameBut.setBorderPainted(false);
        this.mPage.add(newGameBut,1);

        //saveSelectBut Button
		this.saveSelectBut = new JButton(new ImageIcon("Pictures/saveSelect.png"));
        this.saveSelectBut.addActionListener(this);
        this.saveSelectBut.setSize(250,50);
        this.saveSelectBut.setLocation(50,275);
        this.saveSelectBut.setContentAreaFilled(false);
        this.saveSelectBut.setFocusPainted(false);
        this.saveSelectBut.setBorderPainted(false);
        this.mPage.add(saveSelectBut,1);

        //saveSelectBut Button
		this.instructBut = new JButton(new ImageIcon("Pictures/instructions.png"));
        this.instructBut.addActionListener(this);
        this.instructBut.setSize(250,50);
        this.instructBut.setLocation(50,350);
        this.instructBut.setContentAreaFilled(false);
        this.instructBut.setFocusPainted(false);
        this.instructBut.setBorderPainted(false);
        this.mPage.add(instructBut,1);

        //credit Button
		this.creditBut = new JButton(new ImageIcon("Pictures/credits.png"));
        this.creditBut.addActionListener(this);
        this.creditBut.setSize(250,50);
        this.creditBut.setLocation(50,425);
        this.creditBut.setContentAreaFilled(false);
        this.creditBut.setFocusPainted(false);
        this.creditBut.setBorderPainted(false);
        this.mPage.add(creditBut,1);

        //back Button (used to return to previous card pages)
		this.backBut = new JButton(new ImageIcon("Pictures/back.png"));
        this.backBut.addActionListener(this);
        this.backBut.setSize(50,50);
        this.backBut.setLocation(50,500);
        this.backBut.setContentAreaFilled(false);
        this.backBut.setFocusPainted(false);
        this.backBut.setBorderPainted(false);

        //Add Sound Button (playing/pausing music)
        this.soundBut = new JButton(soundIcon1);
        this.soundBut.addActionListener(this);
        this.soundBut.setSize(50,50);
        this.soundBut.setLocation(700,500);
        this.soundBut.setContentAreaFilled(false);
        this.soundBut.setFocusPainted(false);
        this.soundBut.setBorderPainted(false);
        this.mPage.add(soundBut,2);

        //\/ --- components for pPage --- \/
        JLabel subTitle1 = new JLabel(new ImageIcon("Pictures/subtitle1.png"));
        subTitle1.setSize(450,60);
        subTitle1.setLocation(175,25);
        this.pPage.add(subTitle1,1);

        JLabel scrollPanel = new JLabel(new ImageIcon("Pictures/scroll.jpg"));
        scrollPanel.setSize(500,200);
        scrollPanel.setLocation(150,350);
        this.pPage.add(scrollPanel,3);

        JLabel namePrompt = new JLabel("Name:");
        namePrompt.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
        namePrompt.setSize(400,50);
        namePrompt.setLocation(200,415);
        this.pPage.add(namePrompt,1);

		//Confirm button
		this.confirmBut = new JButton(new ImageIcon("Pictures/confirm.png"));
        this.confirmBut.addActionListener(this);
        this.confirmBut.setSize(50,50);
        this.confirmBut.setLocation(700,500);
        this.confirmBut.setContentAreaFilled(false);
        this.confirmBut.setFocusPainted(false);
        this.confirmBut.setBorderPainted(false);
        this.pPage.add(confirmBut,3);

		//Buttons for selecting characters
		this.pBut1 = new JButton(new ImageIcon("Pictures/charPortrait1.png"));
        this.pBut1.addActionListener(this);
        this.pBut1.setSize(200,200);
        this.pBut1.setLocation(50,100);
        this.pBut1.setContentAreaFilled(false);
        this.pBut1.setFocusPainted(false);
        this.pBut1.setBorderPainted(true);
        this.pPage.add(pBut1,1);

        this.pBut2 = new JButton(new ImageIcon("Pictures/charPortrait2.png"));
        this.pBut2.addActionListener(this);
        this.pBut2.setSize(200,200);
        this.pBut2.setLocation(300,100);
        this.pBut2.setContentAreaFilled(false);
        this.pBut2.setFocusPainted(false);
        this.pBut2.setBorderPainted(true);
        this.pPage.add(pBut2,1);

        this.pBut3 = new JButton(new ImageIcon("Pictures/charPortrait3.png"));
        this.pBut3.addActionListener(this);
        this.pBut3.setSize(200,200);
        this.pBut3.setLocation(550,100);
        this.pBut3.setContentAreaFilled(false);
        this.pBut3.setFocusPainted(false);
        this.pBut3.setBorderPainted(true);
        this.pPage.add(pBut3,1);

        //text for selecting character
        JLabel pName1 = new JLabel("Warrior");
		pName1.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		pName1.setSize(400,100);
		pName1.setLocation(100,275);
		this.pPage.add(pName1,2);

		JLabel pName2 = new JLabel("Mage");
		pName2.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		pName2.setSize(400,100);
		pName2.setLocation(375,275);
		this.pPage.add(pName2,2);

		JLabel pName3 = new JLabel("Archer");
		pName3.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		pName3.setSize(400,100);
		pName3.setLocation(600,275);
		this.pPage.add(pName3,2);

		this.charDisplay1 = new JLabel(new ImageIcon("Pictures/charDisplay1.png"));
        this.charDisplay1.setSize(100,150);
        this.charDisplay1.setLocation(450,370);

        this.charDisplay2 = new JLabel(new ImageIcon("Pictures/charDisplay2.png"));
        this.charDisplay2.setSize(100,150);
        this.charDisplay2.setLocation(450,370);

        this.charDisplay3 = new JLabel(new ImageIcon("Pictures/charDisplay3.png"));
        this.charDisplay3.setSize(100,150);
        this.charDisplay3.setLocation(450,370);

		//\/ --- components for sPage --- \/
		JLabel subTitle2 = new JLabel(new ImageIcon("Pictures/subtitle2.png"));
        subTitle2.setSize(400,50);
        subTitle2.setLocation(200,50);
        this.sPage.add(subTitle2,1);

        //\/ --- components for iPage --- \/
		JLabel subTitle3 = new JLabel(new ImageIcon("Pictures/subtitle3.png"));
        subTitle3.setSize(350,50);
        subTitle3.setLocation(225,50);
        this.iPage.add(subTitle3,1);

        //\/ --- components for cPage --- \/
		JLabel subTitle4 = new JLabel(new ImageIcon("Pictures/subtitle4.png"));
        subTitle4.setSize(300,50);
        subTitle4.setLocation(250,50);
        this.cPage.add(subTitle4,1);

        requestFocus();
        setResizable(false);
        setVisible(true);
    }

    public void keyPressed(KeyEvent k){}
	public void keyReleased(KeyEvent k){}

	public void keyTyped(KeyEvent k){ //making sure the name length does not go over 25 characters
		if(this.nameBox.getText().length() == 25) {
			k.consume();
		}
	}

    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();

		if(source == this.newGameBut) {
			if(this.backBut.getParent() != pPage) { //only adds back button if not already in the page
        		this.pPage.add(backBut,1); //always looks to add back button cause it is removed from its current page every time it is added to a new one
        	}
			//Text Box
	        this.nameBox = new JTextField("Type Name Here!"); //text field used to recieve the player's name
	        this.nameBox.addKeyListener(this);
			this.nameBox.setSize(200,25);
			this.nameBox.setLocation(200,475);
        	this.pPage.add(nameBox,1);
        	this.cLayout.show(this.cards, "playerCreation");
		}

        else if(source == this.confirmBut && charSelect != 0){
        	charName = nameBox.getText();  //This gets the name from the player after they have died
        	this.pPage.remove(nameBox);
        	menuTheme.stop();

        	game = new GameScreen(charSelect, charName);
        	this.cards.add(game, "game");
        	game.loadRects();
            this.cLayout.show(this.cards,"game");
            timer.start();
        }

        else if(source == this.pBut1) {
        	this.charSelect = 1;
        }

        else if(source == this.pBut2) {
        	this.charSelect = 2;
        }

        else if(source == this.pBut3) {
        	this.charSelect = 3;
        }

        else if(source == this.saveSelectBut){
        	if(this.backBut.getParent() != sPage) {
        		this.sPage.add(backBut,1);
        	}

            this.cLayout.show(this.cards, "saveSelect");
        }

        else if(source == this.instructBut){
        	if(this.backBut.getParent() != iPage) {
        		this.iPage.add(backBut,1);
        	}

        	this.cLayout.show(this.cards, "instructions");
        }

        else if(source == this.creditBut){
        	if(this.backBut.getParent() != cPage) {
        		this.cPage.add(backBut,1);
        	}

        	this.cLayout.show(this.cards, "credits");
        }

        else if(source == this.backBut){
        	charSelect = 0; //resets the character currently selected
        	this.cLayout.show(this.cards, "menu");
        }

        //This toggles the menu music
        else if(source == this.soundBut){
            this.menuTheme.playPause();
            //Displays a certain icon depending on if the music is playing
            if(menuTheme.getIsPlaying()) {
                this.soundBut.setIcon(soundIcon1);
            }
            else {
                this.soundBut.setIcon(soundIcon2);
            }
        }
        if(this.charSelect == 0) {
        	if(this.charDisplay1.getParent() == pPage) {
        		this.pPage.remove(charDisplay1);
        	}

        	if(this.charDisplay2.getParent() == pPage) {
        		this.pPage.remove(charDisplay2);
        	}

        	if(this.charDisplay3.getParent() == pPage) {
        		this.pPage.remove(charDisplay3);
        	}
        }

        if(this.charSelect == 1) {
        	if(this.charDisplay2.getParent() == pPage) {
        		this.pPage.remove(charDisplay2);
        	}

        	if(this.charDisplay3.getParent() == pPage) {
        		this.pPage.remove(charDisplay3);
        	}
        	this.pPage.add(charDisplay1,2);
        }

        if(this.charSelect == 2) {
        	if(this.charDisplay1.getParent() == pPage) {
        		this.pPage.remove(charDisplay1);
        	}

        	if(this.charDisplay3.getParent() == pPage) {
        		this.pPage.remove(charDisplay3);
        	}
        	this.pPage.add(charDisplay2,2);
        }

        if(this.charSelect == 3) {
        	if(this.charDisplay1.getParent() == pPage) {
        		this.pPage.remove(charDisplay1);
        	}

        	if(this.charDisplay2.getParent() == pPage) {
        		this.pPage.remove(charDisplay2);
        	}
        	this.pPage.add(charDisplay3,2);
        }

        if (game != null) {
        	if(game.getBack()) {
		    	this.cLayout.show(this.cards, "menu");
		    	game.setBack();
		    	this.cards.remove(game);
		    	menuTheme.loop();
		    	this.charSelect = 0;
        	}

        	game.toggleMenu();
        	if(game.getMenuPause() == false) {
				game.move();
        	}
        	game.repaint();
		}
    }

    public static void main(String[] args){
        Gerthtale gameStart = new Gerthtale();
    }
}

class GameScreen extends JPanel implements ActionListener, KeyListener {
	private JButton menuBut;
	private JButton bagBut;
	private JButton saveBut;
	private JButton profBut;

	private JLabel menuLabel1;
	private JLabel menuLabel2;
	private JLabel menuLabel3;
	private JLabel menuLabel4;
	private JLabel nameLabel;

	private boolean[] keys;
	private int mapx, mapy;
	private double playerFrame;
	private String screen = "moving", lastDirection = "down";
	private Rectangle playerHitbox = new Rectangle(382,276,36,48);
	private ArrayList<Rectangle> map1Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> map2Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> map3Rects = new ArrayList<Rectangle>();
	Image map = new ImageIcon("Maps/map1.png").getImage();

	private int character;
	private String charName;

	//Player sprites
	Image[] playerDown, playerUp, playerLeft, playerRight;

	private boolean back = false;
	private boolean menuPause = false;

	private Sound gameTheme;

	public GameScreen(int character, String charName) {
		gameTheme = new Sound("Music/BOTW OST - Cave.wav"); //This loads the background music
        gameTheme.loop(); //This loops the background music

		//back to menu button
		this.menuBut = new JButton(new ImageIcon("Pictures/homeIcon.png"));
        this.menuBut.addActionListener(this);
        this.menuBut.setSize(150,150);
        this.menuBut.setLocation(25,355);
        this.menuBut.setContentAreaFilled(false);
        this.menuBut.setFocusPainted(false);
        this.menuBut.setBorderPainted(false);

        //inventory button
		this.bagBut = new JButton(new ImageIcon("Pictures/inventoryIcon.png"));
        this.bagBut.addActionListener(this);
        this.bagBut.setSize(150,150);
        this.bagBut.setLocation(225,355);
        this.bagBut.setContentAreaFilled(false);
        this.bagBut.setFocusPainted(false);
        this.bagBut.setBorderPainted(false);

        //save button
		this.saveBut = new JButton(new ImageIcon("Pictures/saveIcon.png"));
        this.saveBut.addActionListener(this);
        this.saveBut.setSize(150,150);
        this.saveBut.setLocation(425,355);
        this.saveBut.setContentAreaFilled(false);
        this.saveBut.setFocusPainted(false);
        this.saveBut.setBorderPainted(false);

        //prof button
		this.profBut = new JButton(new ImageIcon("Pictures/profileIcon.png"));
        this.profBut.addActionListener(this);
        this.profBut.setSize(150,150);
        this.profBut.setLocation(625,355);
        this.profBut.setContentAreaFilled(false);
        this.profBut.setFocusPainted(false);
        this.profBut.setBorderPainted(false);

		//label for in game menu buttons
        this.menuLabel1 = new JLabel("Menu");
		this.menuLabel1.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.menuLabel1.setSize(400,100);
		this.menuLabel1.setLocation(65,475);

		this.menuLabel2 = new JLabel("Inventory");
		this.menuLabel2.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.menuLabel2.setSize(400,100);
		this.menuLabel2.setLocation(230,475);

		this.menuLabel3 = new JLabel("Save Game");
		this.menuLabel3.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.menuLabel3.setSize(400,100);
		this.menuLabel3.setLocation(430,475);

		this.menuLabel4 = new JLabel("Profile");
		this.menuLabel4.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.menuLabel4.setSize(400,100);
		this.menuLabel4.setLocation(640,475);

        this.character = character;
        this.charName = charName;

		keys = new boolean[KeyEvent.KEY_LAST + 1];
		playerDown = new Image[4];
		playerUp = new Image[4];
		playerLeft = new Image[4];
		playerRight = new Image[4];
		for (int i = 0; i < 4; i++) {
			if (character == 1) {
				playerDown[i] = new ImageIcon("char1sprites/char1-" + (i+1) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerLeft[i] = new ImageIcon("char1sprites/char1-" + (i+5) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerRight[i] = new ImageIcon("char1sprites/char1-" + (i+9) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerUp[i] = new ImageIcon("char1sprites/char1-" + (i+13) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
			}
			if (character == 2) {
				playerDown[i] = new ImageIcon("char2sprites/char2-" + (i+1) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerLeft[i] = new ImageIcon("char2sprites/char2-" + (i+5) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerRight[i] = new ImageIcon("char2sprites/char2-" + (i+9) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerUp[i] = new ImageIcon("char2sprites/char2-" + (i+13) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
			}
			if (character == 3) {
				playerDown[i] = new ImageIcon("char3sprites/char3-" + (i+1) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerLeft[i] = new ImageIcon("char3sprites/char3-" + (i+5) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerRight[i] = new ImageIcon("char3sprites/char3-" + (i+9) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
				playerUp[i] = new ImageIcon("char3sprites/char3-" + (i+13) + ".png").getImage().getScaledInstance(36, 48,
						Image.SCALE_DEFAULT);
			}

		}
		mapx = -908;
		mapy = -320;
		playerFrame = 0;

		setSize(800,600);
		addKeyListener(this);

	}

	public boolean getBack() {
		return back;
	}

	public void setBack() {
		back = false;
	}

	//KEYBOARD METHODS - Moving Code
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		playerFrame = 0; //Restarts frame if user stops pressing key and starts again
	}

	public void loadRects() {
		map1Rects.add(new Rectangle(mapx,mapy,432,1728));
		map1Rects.add(new Rectangle(432+mapx, mapy, 1600, 456));
		map1Rects.add(new Rectangle(688+mapx, 440+mapy, 736, 48));
		map1Rects.add(new Rectangle(1388+mapx, 456+mapy, 420, 786));
		map1Rects.add(new Rectangle(692+mapx, 944+mapy, 696, 298));
		map1Rects.add(new Rectangle(416+mapx, 1390+mapy, 2720, 352));
		map1Rects.add(new Rectangle(2704+mapx, 480+mapy, 416, 928));
		map1Rects.add(new Rectangle(1776+mapx, mapy, 288, 1184));
		map1Rects.add(new Rectangle(2064+mapx, 688+mapy, 224, 512));

		//Trees
		map1Rects.add(new Rectangle(1088+mapx, 512+mapy, 96, 128));
		map1Rects.add(new Rectangle(1120+mapx, 756+mapy, 64, 104));
		map1Rects.add(new Rectangle(1312+mapx, 820+mapy, 64, 104));
		map1Rects.add(new Rectangle(896+mapx, 628+mapy, 64, 76));
		map1Rects.add(new Rectangle(576+mapx, 564+mapy, 64, 104));
		map1Rects.add(new Rectangle(512+mapx, 788+mapy, 64, 104));
		map1Rects.add(new Rectangle(2560+mapx, 1076+mapy, 64, 104));
		map1Rects.add(new Rectangle(2368+mapx, 800+mapy, 96, 128));
		map1Rects.add(new Rectangle(2400+mapx, 480+mapy, 96, 128));
		map1Rects.add(new Rectangle(2112+mapx, 536+mapy, 64, 104));

		//Bridge
		map1Rects.add(new Rectangle(1952+mapx, 1200+mapy, 352, 16));
		map1Rects.add(new Rectangle(1952+mapx, 1312+mapy, 352, 96));

		//Rocks
		map1Rects.add(new Rectangle(1984+mapx, mapy, 328, 432));
		map1Rects.add(new Rectangle(2016+mapx, 412+mapy, 288, 32));
		map1Rects.add(new Rectangle(2456+mapx, mapy, 352, 432));
		map1Rects.add(new Rectangle(2464+mapx, 412+mapy, 608, 32));
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
		if (screen == "moving") {
			requestFocus();
			if (keys[KeyEvent.VK_LEFT]) {
				lastDirection = "left";
				if (checkCollision("l",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8)
						playerFrame = 0;
					mapx += 4;
				}
			}
			
			else if (keys[KeyEvent.VK_RIGHT]) {
				lastDirection = "right";
				if (checkCollision("r",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
					mapx -= 4;
				}
			}
			else if (keys[KeyEvent.VK_DOWN]) {
				lastDirection = "down";
				if (checkCollision("d",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
					mapy -= 4;
				}
			}
			
			else if (keys[KeyEvent.VK_UP]) {
				lastDirection = "up";
				if (checkCollision("u",map1Rects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
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

	public void drawDialogue(Graphics g) {
		if (screen == "dialogue") {
			g.setColor(Color.WHITE);
			g.fillRect(600, 50, 700, 200);
		}
	}

	public void toggleMenu() { //toggles if the in game menu shows or not (depending on key pressed)
		if (keys[KeyEvent.VK_C]) {
				menuPause = true;
		}

		if (keys[KeyEvent.VK_X]) {
			removeMenu();
			menuPause = false;
		}
	}

	public boolean getMenuPause() { //accessor method
		return menuPause;
	}

	public void displayMenu(Graphics g) { //dislays contents of the in game menu
		g.setColor(new Color(255,255,255,100));
		g.fillRect(0,325,800,275);
		g.fillRect(0,0,800,100);
		this.add(menuBut);
		this.add(bagBut);
		this.add(saveBut);
		this.add(profBut);
		this.add(menuLabel1);
		this.add(menuLabel2);
		this.add(menuLabel3);
		this.add(menuLabel4);
	}

	public void removeMenu() { //removes contents of the in game menu
		this.remove(menuBut);
		this.remove(bagBut);
		this.remove(saveBut);
		this.remove(profBut);
		this.remove(menuLabel1);
		this.remove(menuLabel2);
		this.remove(menuLabel3);
		this.remove(menuLabel4);
	}

	public void displayProfile(Graphics g) {
		this.nameLabel = new JLabel(charName);
		this.nameLabel.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.nameLabel.setSize(400,100);
		this.nameLabel.setLocation(65,475);
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if(source == menuBut) {
			int paneResult = JOptionPane.showConfirmDialog(null,"Any unsaved progress will be lost!","Return to menu?",JOptionPane.YES_NO_OPTION);
			if (paneResult == JOptionPane.YES_OPTION) { //opens a warning msg toward the user to make they have saved before returning to menu
				gameTheme.stop();
          		back = true;
			}

          	else {}
		}

		if(source == bagBut) {
		}

		if(source == saveBut) {
		}

		if(source == profBut) {
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawImage(mask, mapx, mapy, this);
		g.drawImage(map, mapx, mapy, this);
		g.setColor(Color.RED);
		
		/*
		for (Rectangle r : map1Rects) {
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
		}
		*/
		
		displayPlayer(g);
		if(menuPause) {
			displayMenu(g);
		}
	}
}
