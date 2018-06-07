//Gerthtale - A Turn-Based Rhythmic RPG inspired by Undertale & Final Fantasy
//By Alex Shi, Jakir Ansari, Jason Wong

package Gerthtale;

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
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image clickCursor = toolkit.getImage("Pictures/clickCursor.png");
	Image gloveCursor = toolkit.getImage("Pictures/gloveCursor.png");

    public Gerthtale() {
        super("Gerthtale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        setCursor(toolkit.createCustomCursor(gloveCursor, new Point(0,0), ""));

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
        this.newGameBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //saveSelectBut Button
		this.saveSelectBut = new JButton(new ImageIcon("Pictures/saveSelect.png"));
        this.saveSelectBut.addActionListener(this);
        this.saveSelectBut.setSize(250,50);
        this.saveSelectBut.setLocation(50,275);
        this.saveSelectBut.setContentAreaFilled(false);
        this.saveSelectBut.setFocusPainted(false);
        this.saveSelectBut.setBorderPainted(false);
        this.mPage.add(saveSelectBut,1);
        this.saveSelectBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //saveSelectBut Button
		this.instructBut = new JButton(new ImageIcon("Pictures/instructions.png"));
        this.instructBut.addActionListener(this);
        this.instructBut.setSize(250,50);
        this.instructBut.setLocation(50,350);
        this.instructBut.setContentAreaFilled(false);
        this.instructBut.setFocusPainted(false);
        this.instructBut.setBorderPainted(false);
        this.mPage.add(instructBut,1);
        this.instructBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //credit Button
		this.creditBut = new JButton(new ImageIcon("Pictures/credits.png"));
        this.creditBut.addActionListener(this);
        this.creditBut.setSize(250,50);
        this.creditBut.setLocation(50,425);
        this.creditBut.setContentAreaFilled(false);
        this.creditBut.setFocusPainted(false);
        this.creditBut.setBorderPainted(false);
        this.mPage.add(creditBut,1);
        this.creditBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //back Button (used to return to previous card pages)
		this.backBut = new JButton(new ImageIcon("Pictures/back.png"));
        this.backBut.addActionListener(this);
        this.backBut.setSize(50,50);
        this.backBut.setLocation(50,500);
        this.backBut.setContentAreaFilled(false);
        this.backBut.setFocusPainted(false);
        this.backBut.setBorderPainted(false);
        this.backBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //Add Sound Button (playing/pausing music)
        this.soundBut = new JButton(soundIcon1);
        this.soundBut.addActionListener(this);
        this.soundBut.setSize(50,50);
        this.soundBut.setLocation(700,500);
        this.soundBut.setContentAreaFilled(false);
        this.soundBut.setFocusPainted(false);
        this.soundBut.setBorderPainted(false);
        this.mPage.add(soundBut,2);
        this.soundBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

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
        this.pBut1.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.pBut2 = new JButton(new ImageIcon("Pictures/charPortrait2.png"));
        this.pBut2.addActionListener(this);
        this.pBut2.setSize(200,200);
        this.pBut2.setLocation(300,100);
        this.pBut2.setContentAreaFilled(false);
        this.pBut2.setFocusPainted(false);
        this.pBut2.setBorderPainted(true);
        this.pPage.add(pBut2,1);
        this.pBut2.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.pBut3 = new JButton(new ImageIcon("Pictures/charPortrait3.png"));
        this.pBut3.addActionListener(this);
        this.pBut3.setSize(200,200);
        this.pBut3.setLocation(550,100);
        this.pBut3.setContentAreaFilled(false);
        this.pBut3.setFocusPainted(false);
        this.pBut3.setBorderPainted(true);
        this.pPage.add(pBut3,1);
        this.pBut3.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

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
        	this.confirmBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));
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
				game.shopControls();
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

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image clickCursor = toolkit.getImage("Pictures/clickCursor.png");

	private boolean[] keys;
	private int mapx, mapy, boxy = 55;
	private double playerFrame;
	private String screen = "moving", lastDirection = "down";
	private Rectangle playerHitbox = new Rectangle(382,276,36,48);
	private ArrayList<Rectangle> map1Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> map2Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> map3Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> currentRects;
	Image map1 = new ImageIcon("Maps/map1.png").getImage();
	Image map2 = new ImageIcon("Maps/map2.png").getImage();
	Image map3 = new ImageIcon("Maps/map3.png").getImage();
	Image currentMap;

	//--------|Shop Related Stuff|--------//
	Image shopBack = new ImageIcon("Pictures/shopBack.jpg").getImage().getScaledInstance(800, 600,
			Image.SCALE_DEFAULT);
	Image panel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205,
			Image.SCALE_DEFAULT);
	Image panel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550,
			Image.SCALE_DEFAULT);
	Image longPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(765, 205,
			Image.SCALE_DEFAULT);
	Image iconPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(90, 90, 
			Image.SCALE_DEFAULT);
	Image arrow = new ImageIcon("Pictures/arrow.png").getImage().getScaledInstance(80, 40, 
			Image.SCALE_DEFAULT);
	Image healthPot = new ImageIcon("Pictures/pot.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Image largePot = new ImageIcon("Pictures/largepot.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Image gerthPot = new ImageIcon("Pictures/gerthpot.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Image wrathPot = new ImageIcon("Pictures/wrathpot.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Image ironPot = new ImageIcon("Pictures/ironpot.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Image shopExit = new ImageIcon("Pictures/shopExit.png").getImage().getScaledInstance(80, 80, 
			Image.SCALE_DEFAULT);
	Font shopFont = new Font("Comic Sans MS", Font.BOLD, 20);
	Font bigShopFont = new Font("Comic Sans MS", Font.BOLD, 28);
	String[] shopItems = {"Health Potion [50g]", "Large Health Potion [100g]", "Gerthy Health Potion [200g]", "Wrath Potion [150g]", "Iron Potion [150g]", "Exit Shop"};
	int[] goldCosts = {50, 100, 200, 150, 150};
	boolean shop = false, itemSelect = false, choice = false, notEnoughGold = false;
	int itemPos = 0, testGold = 500;
	ArrayList<String> testInv = new ArrayList<String>();
	//------------------------------------//

	private int character;
	private String charName;

	//Player sprites
	Image[] playerDown, playerUp, playerLeft, playerRight;

	private boolean back = false;
	private boolean menuPause = false;
	private boolean keypress;

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
        this.menuBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //inventory button
		this.bagBut = new JButton(new ImageIcon("Pictures/inventoryIcon.png"));
        this.bagBut.addActionListener(this);
        this.bagBut.setSize(150,150);
        this.bagBut.setLocation(225,355);
        this.bagBut.setContentAreaFilled(false);
        this.bagBut.setFocusPainted(false);
        this.bagBut.setBorderPainted(false);
        this.bagBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //save button
		this.saveBut = new JButton(new ImageIcon("Pictures/saveIcon.png"));
        this.saveBut.addActionListener(this);
        this.saveBut.setSize(150,150);
        this.saveBut.setLocation(425,355);
        this.saveBut.setContentAreaFilled(false);
        this.saveBut.setFocusPainted(false);
        this.saveBut.setBorderPainted(false);
        this.saveBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        //prof button
		this.profBut = new JButton(new ImageIcon("Pictures/profileIcon.png"));
        this.profBut.addActionListener(this);
        this.profBut.setSize(150,150);
        this.profBut.setLocation(625,355);
        this.profBut.setContentAreaFilled(false);
        this.profBut.setFocusPainted(false);
        this.profBut.setBorderPainted(false);
        this.profBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

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
		//OG COORDINATES: -908, -320 [any other coords used are for testing purposes only]
		//testing entrance [map 1 to 2] : -1984, -104
		//testing map 3 : -80, -404
		mapx = -716;
		mapy = -768;
		currentMap = map2;
		currentRects = map2Rects;
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
		keypress = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		playerFrame = 0; //Restarts frame if user stops pressing key and starts again
	}

	public void loadRects() {

		//-------MAP 1 RECTS-------//
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
		map1Rects.add(new Rectangle(1984+mapx, mapy, 336, 432));
		map1Rects.add(new Rectangle(2016+mapx, 412+mapy, 288, 32));
		map1Rects.add(new Rectangle(2448+mapx, mapy, 360, 432));
		map1Rects.add(new Rectangle(2464+mapx, 412+mapy, 608, 32));

		//-------MAP 2 RECTS-------//
		int x2 = -716;
		int y2 = -768;
		//Walls
		map2Rects.add(new Rectangle(1152+x2, 992+y2, 320, 416));
		map2Rects.add(new Rectangle(1136+x2, 992+y2, 32, 416));
		map2Rects.add(new Rectangle(992+x2, 992+y2, 16, 416));
		map2Rects.add(new Rectangle(1168+x2, 976+y2, 672, 32));
		map2Rects.add(new Rectangle(1808+x2, 496+y2, 32, 512));
		map2Rects.add(new Rectangle(1824+x2, 480+y2, 672, 32));
		map2Rects.add(new Rectangle(1856+x2, 464+y2, 672, 32));
		map2Rects.add(new Rectangle(1824+x2, 256+y2, 672, 32));
		map2Rects.add(new Rectangle(1792+x2, 176+y2, 32, 64));
		map2Rects.add(new Rectangle(1760+x2, 192+y2, 32, 32));
		map2Rects.add(new Rectangle(192+x2, 192+y2, 1568, 32));
		map2Rects.add(new Rectangle(160+x2, 224+y2, 16, 736));
		map2Rects.add(new Rectangle(160+x2, 960+y2, 32, 32));
		map2Rects.add(new Rectangle(192+x2, 976+y2, 800, 16));

		map2Rects.add(new Rectangle(800+x2, 864+y2, 96, 128));
		map2Rects.add(new Rectangle(608+x2, 864+y2, 96, 128));
		map2Rects.add(new Rectangle(416+x2, 800+y2, 96, 128));
		map2Rects.add(new Rectangle(224+x2, 800+y2, 96, 128));
		map2Rects.add(new Rectangle(160+x2, 608+y2, 96, 32));
		map2Rects.add(new Rectangle(160+x2, 384+y2, 96, 32));
		map2Rects.add(new Rectangle(256+x2, 352+y2, 28, 256));
		map2Rects.add(new Rectangle(288+x2, 352+y2, 32, 192));
		map2Rects.add(new Rectangle(324+x2, 352+y2, 28, 256));
		map2Rects.add(new Rectangle(352+x2, 352+y2, 160, 256));
		map2Rects.add(new Rectangle(512+x2, 448+y2, 32, 192));
		map2Rects.add(new Rectangle(672+x2, 480+y2, 96, 128));
		map2Rects.add(new Rectangle(992+x2, 576+y2, 32, 64));
		map2Rects.add(new Rectangle(1248+x2, 576+y2, 32, 64));
		map2Rects.add(new Rectangle(864+x2, 320+y2, 544, 128));
		map2Rects.add(new Rectangle(864+x2, 448+y2, 224, 64));
		map2Rects.add(new Rectangle(896+x2, 512+y2, 160, 32));
		map2Rects.add(new Rectangle(1088+x2, 448+y2, 24, 96));
		map2Rects.add(new Rectangle(1160+x2, 448+y2, 24, 96));
		map2Rects.add(new Rectangle(1184+x2, 448+y2, 224, 64));
		map2Rects.add(new Rectangle(1216+x2, 512+y2, 160, 32));
		map2Rects.add(new Rectangle(1696+x2, 544+y2, 96, 128));
		map2Rects.add(new Rectangle(1568+x2, 800+y2, 64, 64));

		//-------MAP 3 RECTS-------//
		int x3 = -80;
		int y3 = -460;
		map3Rects.add(new Rectangle(x3, 576+y3, 704, 32));
		map3Rects.add(new Rectangle(704+x3, 320+y3, 16, 288));
		map3Rects.add(new Rectangle(704+x3, 288+y3, 2240, 32));
		map3Rects.add(new Rectangle(2928+x3, 320+y3, 16, 576 ));
		map3Rects.add(new Rectangle(2912+x3, 896+y3, 32, 32));
		map3Rects.add(new Rectangle(2032+x3, 912+y3, 896, 16));
		map3Rects.add(new Rectangle(2016+x3, 928+y3, 32, 32));
		map3Rects.add(new Rectangle(2000+x3, 944+y3, 16, 608));
		map3Rects.add(new Rectangle(1984+x3, 1536+y3, 32, 32));
		map3Rects.add(new Rectangle(1472+x3, 1552+y3, 512, 16));
		map3Rects.add(new Rectangle(1440+x3, 1536+y3, 32, 32));
		map3Rects.add(new Rectangle(1440+x3, 560+y3, 16, 992));
		map3Rects.add(new Rectangle(1440+x3, 560+y3, 1088, 16));
		map3Rects.add(new Rectangle(2496+x3, 576+y3, 32, 32));
		map3Rects.add(new Rectangle(2528+x3, 592+y3, 16, 96));
		map3Rects.add(new Rectangle(1440+x3, 704+y3, 1088, 32));
		map3Rects.add(new Rectangle(1232+x3, 560+y3, 16, 608));
		map3Rects.add(new Rectangle(1232+x3, 560+y3, 224, 16));
		map3Rects.add(new Rectangle(704+x3, 1120+y3, 32, 32));
		map3Rects.add(new Rectangle(736+x3, 1136+y3, 512, 16));
		map3Rects.add(new Rectangle(672+x3, 800+y3, 32, 32));
		map3Rects.add(new Rectangle(704+x3, 816+y3, 16, 320));
		map3Rects.add(new Rectangle(x3, 784+y3, 704, 16));

		//Trees
		map3Rects.add(new Rectangle(992+x3, 736+y3, 96, 128));
		map3Rects.add(new Rectangle(1024+x3, 384+y3, 96, 128));
		map3Rects.add(new Rectangle(1472+x3, 864+y3, 96, 128));

		//Rocks
		map3Rects.add(new Rectangle(1472+x3, 1184+y3, 32, 64));
		map3Rects.add(new Rectangle(1504+x3, 1216+y3, 32, 64));
		map3Rects.add(new Rectangle(1568+x3, 1248+y3, 32, 64));
		map3Rects.add(new Rectangle(1664+x3, 1184+y3, 32, 64));
		map3Rects.add(new Rectangle(1728+x3, 1248+y3, 32, 64));
		map3Rects.add(new Rectangle(1856+x3, 1184+y3, 32, 64));
		map3Rects.add(new Rectangle(1920+x3, 1248+y3, 32, 64));
		map3Rects.add(new Rectangle(1952+x3, 1184+y3, 32, 64));
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
				if (checkCollision("l",currentRects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8)
						playerFrame = 0;
					mapx += 4;
				}
			}

			else if (keys[KeyEvent.VK_RIGHT]) {
				lastDirection = "right";
				if (checkCollision("r",currentRects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
					mapx -= 4;
				}
			}
			else if (keys[KeyEvent.VK_DOWN]) {
				lastDirection = "down";
				if (checkCollision("d",currentRects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
					mapy -= 4;
				}
			}

			else if (keys[KeyEvent.VK_UP]) {
				lastDirection = "up";
				if (checkCollision("u",currentRects) == false) {
					playerFrame += 0.2;
					if (playerFrame > 3.8) {
						playerFrame = 0;
					}
					mapy += 4;
				}
			}

			//Switching maps
			//MAP 1 TO 2
			if (currentMap.equals(map1) && mapx <= -1940 && mapx >= -2028 && mapy == 0) { //If the player is at the coordinates of the map
																						  //where the map will switch
				//When the player switches maps, they enter a path with walls on the left and right of them that remain constant
				//in the second map as well. The distance that the player is away from the right wall must also remain constant from
				//the first map to the second map.

				int wallDist = 2028+mapx; //The distance from the player and the right wall; used for properly translating the map and rects
										  //according to what x-coordinate the player enters from.
				for (Rectangle r : map1Rects) {
					r.translate(-wallDist, -64); //All rectangles in map 1 are moved up 64 units to make up for where the player spawns.
					    						 //The rectangles are also moved back to their original spots in the x-direction so that
												 //they can be properly translated when map 1 is entered again.
				}
				for (Rectangle r : map2Rects) {
					r.translate(wallDist, 0); //Moving the rectangles in map 2 to correspond with the walls in map 1
				}
				//Switching the map image and rectangles
				currentRects = map2Rects;
				currentMap = map2;

				mapx = -716+wallDist; //Putting the player at the entrance in map 2
				mapy = -768; //The player spawns on the next map 64 units above the entrance back to map 2 to avoid infinitely looping in maps

			}

			//MAP 2 TO 1
			if (currentMap.equals(map2) && mapx <= -628 && mapx >= -716 && mapy == -832) {
				int wallDist = 716+mapx;
				for (Rectangle r : map2Rects) {
					r.translate(-wallDist, 64);
				}
				for (Rectangle r : map1Rects) {
					r.translate(wallDist, 0);
				}
				currentRects = map1Rects;
				currentMap = map1;
				mapx = -2028+wallDist;
				mapy = -64;
			}

			//MAP 2 TO 3
			if (currentMap.equals(map2) && mapx == -1692 && mapy <= -12 && mapy >= -140) {
				int wallDist = 140+mapy;
				for (Rectangle r : map2Rects) {
					r.translate(64, -wallDist);
				}
				for (Rectangle r : map3Rects) {
					r.translate(0, wallDist);
				}
				currentRects = map3Rects;
				currentMap = map3;
				mapx = -84;
				mapy = -460+wallDist;
			}

			//MAP 3 TO 2
			if (currentMap.equals(map3) && mapx == -20 && mapy <= -332 && mapy >= -460) {
				int wallDist = 460+mapy;
				for (Rectangle r : map3Rects) {
					r.translate(-64, -wallDist);
				}
				for (Rectangle r : map2Rects) {
					r.translate(0, wallDist);
				}
				currentRects = map2Rects;
				currentMap = map2;
				mapx = -1628;
				mapy = -140+wallDist;
			}
			
			//Accessing the Shop
			if (currentMap.equals(map2) && mapx == 96 && mapy == -268) {
				for (Rectangle r : map2Rects) {
					r.translate(0, -64);
				}
				screen = "shop";
			}
		}
	}

	public void shopControls() { //starting value of boxy = 75
		if (screen == "shop") {
			requestFocus();
			if (keys[KeyEvent.VK_UP] && keypress && itemSelect == false && shop) {
				if (boxy == 55) {
					itemPos = 5;
					boxy = 455;
				}
				else {
					boxy -= 80;
					itemPos--;
				}
			}
			else if (keys[KeyEvent.VK_DOWN] && keypress && itemSelect == false && shop) {
				if (boxy == 455) {
					itemPos = 0;
					boxy = 55;
				}
				else {
					boxy += 80;
					itemPos++;
				}
			}
			else if (keys[KeyEvent.VK_LEFT] && keypress && itemSelect && shop) {
				if (choice == false)
					choice = true;
			}
			else if (keys[KeyEvent.VK_RIGHT] && keypress && itemSelect && shop) {
				if (choice)
					choice = false;
			}
			else if (keys[KeyEvent.VK_ENTER] && keypress) {
				if (shop) {
					if (itemPos == 5) {
						mapx = 96;
						mapy = -332;
						screen = "moving";
						return;
					}
					else if (itemSelect) {
						if (notEnoughGold) {
							notEnoughGold = false;
							itemSelect = false;
						}
						else if (choice) {
							if (testGold >= goldCosts[itemPos]) {
								testInv.add(shopItems[itemPos]);
								testGold -= goldCosts[itemPos];
								itemSelect = false;
							}
							else {
								notEnoughGold = true;
							}
						}
						else if (choice == false) {
							itemSelect = false;
						}
					}
					else if (itemSelect == false) {
						itemSelect = true;
					}
				}
				if (shop == false) {
					shop = true;
				}
			}
			keypress = false;
			//System.out.println(itemPos);
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

	public void drawMap(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(currentMap, mapx, mapy, this);
		g.setColor(Color.RED);

		//Drawing collision rectangles (for experimental purposes only)
		for (Rectangle r : currentRects) {
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
		}
	}

	public void drawShop(Graphics g) {
		if (shop == false) {
			g.drawImage(shopBack, 0, 0, this);
			g.drawImage(longPanel, 15, 355, this);
			g.setFont(bigShopFont);
			g.drawString("Welcome to the shop!", 30, 400);
			g.drawString("Here, you can purchase potions to aid you in battles.", 30, 470);
			//g.drawString("in battles.", 30, 480);
		}
		if (shop == true) {
			g.drawImage(shopBack, 0, 0, this);
			g.drawImage(panel, 15, 355, this);
			g.drawImage(panel2, 485, 10, this);
			g.setColor(Color.BLACK);
			g.setFont(shopFont);
			for (int i = 0; i < 6; i++) {
				g.drawString(shopItems[i], 500, (i+1)*80);
			}
			//Potion Icons
			if (itemPos == 0) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(healthPot, 385, 255, this);
			}
			else if (itemPos == 1) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(largePot, 385, 255, this);
			}
			else if (itemPos == 2) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(gerthPot, 385, 255, this);
			}
			else if (itemPos == 3) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(wrathPot, 385, 255, this);
			}
			else if (itemPos == 4) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(ironPot, 385, 255, this);
			}
			else if (itemPos == 5) {
				g.drawImage(iconPanel, 380, 250, this);
				g.drawImage(shopExit, 385, 252, this);
			}
			//////////////////
			if (itemSelect == false) {
				g.drawString("What would you like to purchase?", 30, 400);
			}
			if (itemSelect == true) {
				if (itemPos == 0) {
					g.drawString("A potion that restores x HP.", 30, 400);
				}
				else if (itemPos == 1) {
					g.drawString("A potion that restores x HP.", 30, 400);
				}
				else if (itemPos == 2) {
					g.drawString("Made of the rarest herbs from across", 30, 400);
					g.drawString("all of Gerthland, this potion restores", 30, 420);
					g.drawString("your HP to full capacity.", 30, 440);
				}
				else if (itemPos == 3) {
					g.drawString("A potion that temporarily increases your", 30, 400);
					g.drawString("damage dealt to enemies.", 30, 420);
				}
				else if (itemPos == 4) {
					g.drawString("A potion that temporarily decreases", 30, 400);
					g.drawString("damage dealt to you by enemies.", 30, 420);	
				}
				g.drawString("Would you like to purchase this item?", 30, 475);
				g.drawString("YES", 150, 525);
				g.drawString("NO", 290, 525);
				if (choice)
					g.drawImage(arrow, 60, 498, this);
				if (choice == false)
					g.drawImage(arrow, 200, 498, this);

			}
			if (notEnoughGold) {
				g.drawImage(panel, 15, 355, this);
				g.drawString("Sorry, you don't have enough gold to buy", 30, 400);
				g.drawString("that.", 30, 430);
			}
			g.setColor(Color.RED);
			g.drawRect(495, boxy, 280, 40);
			g.setColor(Color.YELLOW);
			g.drawString("Gold: "+testGold, 15, 340);
		}

	}

/*	public void drawDialogue(Graphics g) {
		if (screen == "dialogue") {
			g.setColor(Color.WHITE);
			g.fillRect(600, 50, 700, 200);
		}
	}*/

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
		if (screen == "moving") {
			drawMap(g);
			displayPlayer(g);
		}
		else if (screen == "shop") {
			drawShop(g);
		}
		if(menuPause) {
			displayMenu(g);
		}
		System.out.println(mapx + ", " + mapy);
	}
}
class Inventory {
	private ArrayList<String> inventory;

	public Inventory() {
		inventory = new ArrayList<String>();
	}

	public void addItem(String item) {
		inventory.add(item);
	}

	public ArrayList<String> getInventory() {
		return inventory;
	}
}

class showInventory extends JPanel implements ActionListener {
	private ArrayList<String> inventory;

	public showInventory(Inventory items) {
		inventory = items.getInventory();
		setSize(800,600);
	}

	public void actionPerformed(ActionEvent evt) {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
