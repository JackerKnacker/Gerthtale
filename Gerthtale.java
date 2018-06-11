//Gerthtale.java
//Alex S, Jakir A, Jason W
///To be announced...
package Gerthtale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.event.*;
import java.lang.Math;

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
	JButton openSaveConfirm;

	JLabel charDisplay1;
	JLabel charDisplay2;
	JLabel charDisplay3;
	JLabel noSave;
	JLabel charFace1;
	JLabel charFace2;
	JLabel charFace3;
	JLabel nameStat;
	JLabel hpStat;
	JLabel lvlStat;
	JLabel expStat;
	JLabel atkStat;
	JTextField nameBox;

	PlayerStats user;
	Sound menuTheme;

	//The two icons will be used interchangeably to display whether or not the music is muted or not
    ImageIcon soundIcon1 = new ImageIcon("Pictures/sound.png"); //soundIcon1 holds the unmuted version of the sound image
    ImageIcon soundIcon2 = new ImageIcon("Pictures/mute.png");  //soundIcon2 holds the muted version of the sound image

	String saveSlotSelect = "none"; //keeps track which save slot is selected
	String charName;
	int charSelect = 0;
	ArrayList<String> savedStats;

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
        
        this.charFace1 = new JLabel(new ImageIcon("Pictures/charPortrait1.png"));
        this.charFace1.setSize(150,150);
        this.charFace1.setLocation(100,150);
        
        this.charFace2 = new JLabel(new ImageIcon("Pictures/charPortrait2.png"));
        this.charFace2.setSize(150,150);
        this.charFace2.setLocation(100,150);
        
        this.charFace3 = new JLabel(new ImageIcon("Pictures/charPortrait3.png"));
        this.charFace3.setSize(150,150);
        this.charFace3.setLocation(100,150);
        
        this.openSaveConfirm = new JButton(new ImageIcon("Pictures/confirm.png"));
        this.openSaveConfirm.addActionListener(this);
        this.openSaveConfirm.setSize(50,50);
        this.openSaveConfirm.setLocation(700,500);
        this.openSaveConfirm.setContentAreaFilled(false);
        this.openSaveConfirm.setFocusPainted(false);
        this.openSaveConfirm.setBorderPainted(false);
        this.openSaveConfirm.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

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

        	if(charSelect == 1) {
        		this.user = new PlayerStats(20, 10, 1, 0, charName, charSelect); //HP, ATK, LVL, EXP, NAME, CHAR-NUM
        	}

        	if(charSelect == 2) {
        		this.user = new PlayerStats(15, 15, 1, 0, charName, charSelect);
        	}

        	if(charSelect == 3) {
        		this.user = new PlayerStats(10, 20, 1, 0, charName, charSelect);
        	}
			
			Inventory bag = new Inventory(1,1,1,1,1, 500);
        	this.pPage.remove(nameBox);
        	menuTheme.stop();

			if(this.user != null) {
				game = new GameScreen(user, bag, menuTheme.getIsPlaying(), -908, -320, "map1");
			}

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
        	
        	this.noSave = new JLabel("No Save File Found");
			this.noSave.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
			this.noSave.setSize(200,150);
			this.noSave.setLocation(300,100);

            this.cLayout.show(this.cards, "saveSelect");

            //This try/catch statement reads from the save text file
	        try { //This reads the text file
	            BufferedReader in = new BufferedReader(new FileReader("saveFile.txt"));
	            String line;
	            this.savedStats = new ArrayList<String>();

	            while((line = in.readLine()) != null){ //reads a line if it is not nothing
	            	savedStats.add(line);
	            }
	            System.out.println(savedStats);
	            
	            if(this.noSave.getParent() == this.sPage) {
	            	sPage.remove(noSave);
	            }
	            
	            if(this.openSaveConfirm.getParent() != sPage) {
	            	this.sPage.add(openSaveConfirm);
	            }
	            
	            if(Integer.parseInt(savedStats.get(6)) == 1) {
	            	this.sPage.add(charFace1,2);
	            }
	            
	            else if(Integer.parseInt(savedStats.get(6)) == 2) {
	            	this.sPage.add(charFace2,2);
	            }
	            
	            else if(Integer.parseInt(savedStats.get(6)) == 3) {
	            	this.sPage.add(charFace3,2);
	            }
	            
	            this.nameStat = new JLabel(savedStats.get(0));
				this.nameStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.nameStat.setSize(800,50);
				this.nameStat.setLocation(300,150);
				this.sPage.add(nameStat,3);
				
	            this.lvlStat = new JLabel("LVL: " + savedStats.get(1));
				this.lvlStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
				this.lvlStat.setSize(400,50);
				this.lvlStat.setLocation(300,200);
				this.sPage.add(lvlStat,3);
				
				this.expStat = new JLabel("EXP: " + savedStats.get(3));
				this.expStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
				this.expStat.setSize(400,50);
				this.expStat.setLocation(300,250);
				this.sPage.add(expStat,3);
				
	            this.hpStat = new JLabel("HP: " + savedStats.get(3) + "/" + savedStats.get(4));
				this.hpStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
				this.hpStat.setSize(400,50);
				this.hpStat.setLocation(300,300);
				this.sPage.add(hpStat,3);
				
				this.atkStat = new JLabel("ATK: " + savedStats.get(5));
				this.atkStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
				this.atkStat.setSize(400,50);
				this.atkStat.setLocation(300,350);
				this.sPage.add(atkStat,3);
	        }

	        catch (IOException e){ //If there no save text file, the slot will be empty
	        	if(this.openSaveConfirm.getParent() == this.sPage) {
	        		this.sPage.remove(openSaveConfirm);
	        	}
	        	
				this.sPage.add(this.noSave,1);
	        }
        }
        
        else if(source == this.openSaveConfirm) {
       		this.user = new PlayerStats(Integer.parseInt(savedStats.get(3)), Integer.parseInt(savedStats.get(5)), Integer.parseInt(savedStats.get(1)), Integer.parseInt(savedStats.get(2)), savedStats.get(0), Integer.parseInt(savedStats.get(6))); //HP, ATK, LVL, EXP, NAME, CHAR-NUM
       		Inventory bag = new Inventory(Integer.parseInt(savedStats.get(17)), Integer.parseInt(savedStats.get(18)), Integer.parseInt(savedStats.get(19)), Integer.parseInt(savedStats.get(20)), Integer.parseInt(savedStats.get(21)), 500);
       		
       		if(this.nameStat.getParent() == this.sPage) {
       			System.out.println("A");
       			this.sPage.remove(nameStat);
       		}
       		
       		if(this.lvlStat.getParent() == this.sPage) {
       			this.sPage.remove(lvlStat);
       		}
       		
       		if(this.expStat.getParent() == this.sPage) {
       			this.sPage.remove(expStat);
       		}
       		
       		if(this.hpStat.getParent() == this.sPage) {
       			this.sPage.remove(hpStat);
       		}
       		
       		if(this.atkStat.getParent() == this.sPage) {
       			this.sPage.remove(atkStat);
       		}
       		
       		this.game = new GameScreen(user, bag, menuTheme.getIsPlaying(), Integer.parseInt(savedStats.get(7)), Integer.parseInt(savedStats.get(8)), savedStats.get(9));
       		this.cards.add(game, "game");
        	this.game.loadRects();
            this.cLayout.show(this.cards,"game");
            timer.start();
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

        if(this.charSelect == 0) { //if the selected char is 0, it will remove all char avatars from char select screen
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

        else if(this.charSelect == 1) { //if the selected char is not 0, it will only remove the char avatars that do not correspond with the num
        	if(this.charDisplay2.getParent() == pPage) {
        		this.pPage.remove(charDisplay2);
        	}

        	if(this.charDisplay3.getParent() == pPage) {
        		this.pPage.remove(charDisplay3);
        	}
        	this.pPage.add(charDisplay1,2);
        }

        else if(this.charSelect == 2) { //if the selected char is not 0, it will only remove the char avatars that do not correspond with the num
        	if(this.charDisplay1.getParent() == pPage) {
        		this.pPage.remove(charDisplay1);
        	}

        	if(this.charDisplay3.getParent() == pPage) {
        		this.pPage.remove(charDisplay3);
        	}
        	this.pPage.add(charDisplay2,2);
        }

        else if(this.charSelect == 3) { //if the selected char is not 0, it will only remove the char avatars that do not correspond with the num
        	if(this.charDisplay1.getParent() == pPage) {
        		this.pPage.remove(charDisplay1);
        	}

        	if(this.charDisplay2.getParent() == pPage) {
        		this.pPage.remove(charDisplay2);
        	}
        	this.pPage.add(charDisplay3,2);
        }

        if (game != null) { //while the gameScreen exists
        	if(game.getBack()) { //checks if the user has chose to return to menu from in-game

        		if(game.getSongOn() == true) { //re-checks if the user has muted before entering game
        			this.soundBut.setIcon(soundIcon1);
        			menuTheme.setPosition();
		    		menuTheme.loop();
		    	}

		    	else {
		    		this.soundBut.setIcon(soundIcon2);
		    	}

		    	this.cLayout.show(this.cards, "menu");
		    	game.setBack();
		    	this.cards.remove(game);
		    	this.charSelect = 0; //resets char selection to 0 (nothing)
        	}

        	game.toggleMenu();

        	if(game.getMenuPause() == false) {
        		game.reloadStats();
				game.move();
				game.shopControls();
				game.dialogueControls();
				game.inventoryControls();
				game.battleControls();
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
	private JButton saveYesBut;
	private JButton saveNoBut;
	private JButton profCloseBut;
	private JButton songBut;

	private JLabel menuLabel1;
	private JLabel menuLabel2;
	private JLabel menuLabel3;
	private JLabel menuLabel4;
	private JLabel nameLabel;
	private JLabel saveLabel;
	private JLabel charProf1;
	private JLabel charProf2;
	private JLabel charProf3;
	private JLabel hpStat;
	private JLabel atkStat;
	private JLabel lvlStat;
	private JLabel expStat;
	private JLabel expLeftStat;

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image clickCursor = toolkit.getImage("Pictures/clickCursor.png");

	private ImageIcon songIcon1 = new ImageIcon("Pictures/sound.png"); //unmuted version of the song image
    private ImageIcon songIcon2 = new ImageIcon("Pictures/mute.png");  //muted version of the song image

    //--------|Shop Related Stuff|--------//
	Image shopBack = new ImageIcon("Pictures/shopBack.jpg").getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
	Image panel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205, Image.SCALE_DEFAULT);
	Image panel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550, Image.SCALE_DEFAULT);
	Image longPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(765, 205, Image.SCALE_DEFAULT);
	Image iconPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
	Image arrow = new ImageIcon("Pictures/arrow.png").getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT);
	Image healthPot = new ImageIcon("Pictures/pot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image largePot = new ImageIcon("Pictures/largepot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image gerthPot = new ImageIcon("Pictures/gerthpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image wrathPot = new ImageIcon("Pictures/wrathpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image ironPot = new ImageIcon("Pictures/ironpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image shopExit = new ImageIcon("Pictures/shopExit.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);

	Font shopFont = new Font("Comic Sans MS", Font.BOLD, 20);
	Font bigShopFont = new Font("Comic Sans MS", Font.BOLD, 28);
	String[] shopItems = {"Health Potion [50g]", "Large Health Potion [100g]", "Gerthy Health Potion [200g]", "Wrath Potion [150g]", "Iron Potion [150g]", "Exit Shop"};
	int[] goldCosts = {50, 100, 200, 150, 150};
	boolean shop = false, itemSelect = false, choice = false, notEnoughGold = false;
	int itemPos = 0, testGold = 500;
	ArrayList<String> testInv = new ArrayList<String>();
	//------------------------------------//

	//---------|Dialogue Stuff|---------//
	Image dialoguePanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(755, 200, Image.SCALE_DEFAULT);
	Image npc1 = new ImageIcon("Pictures/npc1.png").getImage().getScaledInstance(36, 48, Image.SCALE_DEFAULT);
	Image npc1head = new ImageIcon("Pictures/npc1head.png").getImage().getScaledInstance(102, 81, Image.SCALE_DEFAULT);
	Font dialogueFont = new Font("Comic Sans MS", Font.BOLD, 24);
	boolean firstPanel = true, secondPanel = false;
	int npc = 1;
	//----------------------------------//
	
	//-------|Inventory Stuff|-------//
	Image scrollPanel1 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205, Image.SCALE_DEFAULT);
	Image scrollPanel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550, Image.SCALE_DEFAULT);
	Image scrollPanel3 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
	Image bagPic = new ImageIcon("Pictures/bag.png").getImage().getScaledInstance(275, 325, Image.SCALE_DEFAULT);
	ArrayList<String> userItems = new ArrayList<String>();
	ArrayList<Integer> itemNums = new ArrayList<Integer>();
	int invPos = 0;
	boolean invSelect = false, invChoice = false;
	//-------------------------------//
	
	//--------|Battle Stuff|--------//
	private int timer = 0,health,runChance,attackTimer = 0,directionX = 2,directionL = 2,directionR = -2,fire;
	private String battleScreen = "options", attackType;
	private boolean immune= false,displaying = false,displayed = false;
	private GameStuff mainFrame;
	private Rectangle baseRect = new Rectangle(150,360,480,170),topRect,leftRect,rightRect,attackRect,leftattackRect,rightattackRect;
	private Color dgreen = new Color(0, 153, 10);
	//private Player battleChar = new Player(50);
	private Random rng = new Random();
	private Enemy slime = new Enemy(50, 1, "slime"), goblin = new Enemy(75, 3, "goblin"), boss = new Enemy(75, 3, "boss");
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> web1 = new ArrayList<Rectangle>();
	
	private int linex = 130,damage,option;
	public boolean reverse = false, stop = false, finished = false;
	private ArrayList<String> items = new ArrayList<String>();
	public Rectangle pRect = new Rectangle(300,410,20,20);
	
	Image battleBack = new ImageIcon("Pictures/map.jpg").getImage().getScaledInstance(800,650,Image.SCALE_SMOOTH);
	Image consumer = new ImageIcon("Pictures/player.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	Image backRect = new ImageIcon("Pictures/Back Rect.png").getImage().getScaledInstance(530,210,Image.SCALE_SMOOTH);
	Image damaged = new ImageIcon("Pictures/damaged.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	Image immune1 = new ImageIcon("Pictures/immune_0.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	Image redRect = new ImageIcon("Pictures/Red Border.png").getImage().getScaledInstance(180,80,Image.SCALE_SMOOTH);
	//-------------------------------//
	
	//-------|Enemy Attacks|-------//
    public void displayRectangle(Rectangle r, Graphics g){
		g.setColor(Color.white);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));

    }
    public void displayRectangleBL(Rectangle r, Graphics g){
		g.setColor(Color.black);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));
    }
    public void pipeAttack(Graphics g){
    	//attackType = "attack1";
    	if(!displayed){
    		rects.clear();
			for(int i = 600; i <= 1800; i+=60){ //1. starting pos for rects 2. amount of rects 3. space between rects
				int leftY = rng.nextInt((int) baseRect.getHeight());
				while (leftY < 30 || leftY > 120){ //change bounds accordingly
					leftY = rng.nextInt((int) baseRect.getHeight());
				}
				Rectangle topRect = new Rectangle(i,(int) baseRect.getY(),10,leftY);
				Rectangle bottomRect = new Rectangle(i,(int) baseRect.getY()+50+leftY,10,((int) baseRect.getHeight()-50)-leftY); //y value - baseRectY - leftY = space in between rects. BaseRectWidth (height?) - space - leftY
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
   	public void laserAttack(Graphics g){
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
			(attackTimer >= fire && attackTimer < fire + 150 || 
			 attackTimer >= fire + 300 && attackTimer < fire + 450 || 
			 attackTimer >= fire + 600 && attackTimer < fire + 850 || 
			 attackTimer >= fire + 900 && attackTimer < fire + 1050 ||
			 attackTimer >= fire + 1200 && attackTimer < fire + 1350 ||
			 attackTimer >= fire + 1500 && attackTimer < fire +  1650 ||
			 attackTimer >= fire + 1800 && attackTimer < fire +  1950)){
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
		attackTimer++;
    }
    public void options(Graphics g){
    	if(!displayed){
    		option = 0;
    		displayed = true;
    	}
    	for(int i = 40; i <= 622; i += 190){
			g.setColor(Color.black);
			g.fillRect(i-15,445,180,80);
			g.setColor(Color.white);
			g.fillRect(i-5,453,160,64);
		}
		g.drawImage(redRect,(40+option*190)-15,445,this);
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
	public int attack(Graphics g, Enemy goon){
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
			if(linex < 240){
				damage = ((linex/10)/24) * (goon.getMaxHealth()/2);
			}
			else if(linex > 240){
				damage = (((480-linex)/10)/24) * (goon.getMaxHealth()/2);
			}
			else if(linex == 240){
				damage = goon.getMaxHealth()/2;
			}
			finished = true;
		}
		System.out.println(damage);
		return damage;
	}
	//-----------------------------//

	private boolean[] keys;
	private int mapx, mapy, boxy = 55, x1 = -908, y1 = -320, x2 = -716, y2 = -768, x3 = -80, y3 = -460, steps = 0;
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

	private int character;
	private PlayerStats user;
	private Inventory bag;
	//private ShowBag bagScreen;

	//Player sprites
	Image[] playerDown, playerUp, playerLeft, playerRight;

	private boolean back = false;
	private boolean menuPause = false;
	private boolean saveScreen = false;
	private boolean profScreen = false;
	private boolean songOn;
	private boolean keypress;

	private Sound gameTheme;

	public GameScreen(PlayerStats user, Inventory bag, boolean songOn, int mapx, int mapy, String current) {
		this.user = user;
		this.songOn = songOn;
		this.bag = bag;

		gameTheme = new Sound("Music/BOTW OST - Cave.wav"); //This loads the background music

		if(this.songOn) {
			gameTheme.loop(); //This will play game music if music has not been muted in menu
			this.songBut = new JButton(songIcon1);
		}

		else {
			this.songBut = new JButton(songIcon2);
		}

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

        //song button (mute & unmute)
        this.songBut.addActionListener(this);
        this.songBut.setSize(50,50);
        this.songBut.setLocation(25,30);
        this.songBut.setContentAreaFilled(false);
        this.songBut.setFocusPainted(false);
        this.songBut.setBorderPainted(false);
        this.songBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

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

		//Other side buttons/displays
		this.saveYesBut = new JButton(new ImageIcon("Pictures/confirm.png"));
        this.saveYesBut.addActionListener(this);
        this.saveYesBut.setSize(50,50);
        this.saveYesBut.setLocation(250,210);
        this.saveYesBut.setContentAreaFilled(false);
        this.saveYesBut.setFocusPainted(false);
        this.saveYesBut.setBorderPainted(false);
        this.saveYesBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.saveNoBut = new JButton(new ImageIcon("Pictures/cancel.png"));
        this.saveNoBut.addActionListener(this);
        this.saveNoBut.setSize(50,50);
        this.saveNoBut.setLocation(500,210);
        this.saveNoBut.setContentAreaFilled(false);
        this.saveNoBut.setFocusPainted(false);
        this.saveNoBut.setBorderPainted(false);
        this.saveNoBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.profCloseBut = new JButton(new ImageIcon("Pictures/cancel.png"));
        this.profCloseBut.addActionListener(this);
        this.profCloseBut.setSize(50,50);
        this.profCloseBut.setLocation(700,185);
        this.profCloseBut.setContentAreaFilled(false);
        
        this.profCloseBut.setFocusPainted(false);
        this.profCloseBut.setBorderPainted(false);
        this.profCloseBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.saveLabel = new JLabel("Would you like to save the game?");
		this.saveLabel.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.saveLabel.setSize(600,100);
		this.saveLabel.setLocation(175,100);

        this.nameLabel = new JLabel(user.getName());
		this.nameLabel.setFont(new Font("Comic Sans ms", Font.PLAIN, 25));
		this.nameLabel.setSize(800,100);
		this.nameLabel.setLocation(225,100);

		//Character profile
		this.charProf1 = new JLabel(new ImageIcon("Pictures/charPortrait1.png"));
		this.charProf1.setSize(150,150);
		this.charProf1.setLocation(50,135);

		this.charProf2 = new JLabel(new ImageIcon("Pictures/charPortrait2.png"));
		this.charProf2.setSize(150,150);
		this.charProf2.setLocation(50,135);

		this.charProf3 = new JLabel(new ImageIcon("Pictures/charPortrait3.png"));
		this.charProf3.setSize(150,150);
		this.charProf3.setLocation(50,135);

        this.character = user.getCharNum();

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
		this.mapx = mapx;
		this.mapy = mapy;
		
		if(current.equals("map1")) {
			this.currentMap = map1;
			this.currentRects = map1Rects;
		}
		
		else if(current.equals("map2")) {
			this.currentMap = map2;
			this.currentRects = map2Rects;
		}
		
		else if(current.equals("map3")) {
			this.currentMap = map3;
			this.currentRects = map3Rects;
		}
		
		playerFrame = 0;

		setSize(800,600);
		addKeyListener(this);

	}


	//KEYBOARD METHODS - Moving Code
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keys[e.getKeyCode()] = true;
		keypress = true;
		if (screen == "battle") {
	        if(key == KeyEvent.VK_SPACE){
	        	stop = true;
	        }
	        if(key == KeyEvent.VK_RIGHT){
				if(option < 3){
					option ++;
				}
			}
			if(key == KeyEvent.VK_LEFT){
				if(option > 0){
					option --;
				}
			}
			if(battleScreen.equals("options") && key == KeyEvent.VK_ENTER){
				displayed = false;
				//change screen
				if(option == 0){
					battleScreen = "player attack";
				}
				else if(option == 1){
					battleScreen = "talk";
				}
				else if(option == 2){
					battleScreen = "items";
				}
				else if(option == 3){
					battleScreen = "run";
				}
			}     	
        }

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
		map2Rects.add(new Rectangle(1408+x2, 736+y2, 36, 48)); //NPC
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
					if (currentMap.equals(map1) || currentMap.equals(map3))
						steps++;
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
					if (currentMap.equals(map1) || currentMap.equals(map3))
						steps++;
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
					if (currentMap.equals(map1) || currentMap.equals(map3))
						steps++;
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
					if (currentMap.equals(map1) || currentMap.equals(map3))
						steps++;
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

				mapx = -716+(wallDist); //Putting the player at the entrance in map 2
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
			
			//Engaging in Battle
			if (steps == 100) {
				if (currentMap.equals(map1)) {
					steps = 0;
					screen = "battle";
				}
				else if (currentMap.equals(map3)) {
					steps = 0;
					screen = "battle";
				}
			}

			//Accessing the Shop
			if (currentMap.equals(map2) && mapx == 96 && mapy == -268) {
				for (Rectangle r : map2Rects) {
					r.translate(0, -64);
				}
				screen = "shop";
			}
			
			//Engaging in Dialogue
			if (currentMap.equals(map2) && keys[KeyEvent.VK_ENTER] && keypress) {
				if ((mapy == -508 && mapx <= -1020 && mapx >= -1036) || (mapy == -412 && mapx <= -1020 && mapx >= -1036) ||
					 (mapx == -1064 && mapy >= -472 && mapy <= -448) || (mapx == -988 && mapy >= -472 && mapy <= -448)) {
					npc = 1;
					screen = "dialogue";
				}
			}
			
			keypress = false;
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
							if (bag.getGold() >= goldCosts[itemPos]) {
								if (itemPos == 0) {
									bag.addItem("pot");
								}
								else if (itemPos == 1) {
									bag.addItem("largePot");
								}
								else if (itemPos == 2) {
									bag.addItem("gerthPot");
								}
								else if (itemPos == 3) {
									bag.addItem("wrathPot");
								}
								else if (itemPos == 4) {
									bag.addItem("ironPot");
								}
								bag.removeGold(goldCosts[itemPos]);
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
		}
	}
	
	public void battleControls() {
		requestFocus();
		if(keys[KeyEvent.VK_RIGHT] ){
			pRect.translate(1,0);
		}
		if(keys[KeyEvent.VK_LEFT] ){
			pRect.translate(-1,0);
		}
		if(keys[KeyEvent.VK_UP] ){
			pRect.translate(0,-1);
		}
		if(keys[KeyEvent.VK_DOWN] ){
			pRect.translate(0,1);
		}
	}

	public void displayPlayer(Graphics g) {
		requestFocus();
		if (screen == "moving") {
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
		if (screen == "dialogue") { //Just display the character (without animations)
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
		
		//Drawing NPC
		if (currentMap.equals(map2)) {
			g.drawImage(npc1, 1408+mapx, 736+mapy, this);
		}

		//Drawing collision rectangles (for experimental purposes only)
		/*
		for (Rectangle r : currentRects) {
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
		}
		*/
	}

	public void drawShop(Graphics g) {
		if (shop == false) {
			g.drawImage(shopBack, 0, 0, this);
			g.drawImage(longPanel, 15, 355, this);
			g.setFont(bigShopFont);
			g.drawString("Welcome to the shop!", 30, 400);
			g.drawString("Here, you can purchase potions to aid you in battles.", 30, 470);
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
			if (itemSelect) {
				if (itemPos == 0) {
					g.drawString("A potion that restores 5 HP.", 30, 400);
				}
				else if (itemPos == 1) {
					g.drawString("A potion that restores 10 HP.", 30, 400);
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
			g.drawString("Gold: "+bag.getGold(), 15, 340);
		}
	}
	
	public void dialogueControls() {
		if (screen == "dialogue") {
			requestFocus();
			if (keys[KeyEvent.VK_ENTER] && keypress && firstPanel) {
				firstPanel = false;
				secondPanel = true;
			}
			else if (keys[KeyEvent.VK_ENTER] && keypress && secondPanel) {
				firstPanel = true;
				secondPanel = false;
				screen = "moving";
			}
			
			keypress = false;
		}
	}

	public void drawDialogue(Graphics g) {
		if (screen == "dialogue") {
			g.drawImage(dialoguePanel, 20, 350, this);
			g.setFont(dialogueFont);
			g.setColor(Color.BLACK);
			
			if (npc == 1) {
				g.drawImage(npc1head, 45, 400, this);
				if (firstPanel) {
					g.drawString("Villager: Folks around here have been tensed up", 170, 410);
					g.drawString("cause of all the disappearances. I can't even let", 170, 450);
					g.drawString("my children out anymore without worrying sick", 170, 490);
				}
				else if (secondPanel) {
					g.drawString("about what'll happen to them. It's gotta have", 170, 410);
					g.drawString("something to do with those hooded men that come", 170, 450);
					g.drawString("here every so often, I'm sure it is!", 170, 490);
				}
//				else if (thirdPanel) {
//
//				}
			}
//			if (npc == 2) {
//
//			}
		}
	}
	public void initItems() {
		userItems.clear();
		itemNums.clear();
		
		if (bag.getPot() != 0) {
			userItems.add("Health Potion");
			itemNums.add(bag.getPot());
		}
		if (bag.getLargePot() != 0) {
			userItems.add("Large Health Potion");
			itemNums.add(bag.getLargePot());
		}
		if (bag.getGerthPot() != 0) {
			userItems.add("Gerthy Health Potion");
			itemNums.add(bag.getGerthPot());
		}
		if (bag.getWrathPot() != 0) {
			userItems.add("Wrath Potion");
			itemNums.add(bag.getWrathPot());
		}
		if (bag.getIronPot() != 0) {
			userItems.add("Iron Potion");
			itemNums.add(bag.getIronPot());
		}
	}
	public void inventoryControls() {
		if (screen == "inventory") {
			requestFocus();
			if (keys[KeyEvent.VK_ENTER] && keypress) {
				if (invPos == userItems.size()) {
					screen = "moving";
					this.menuPause = true;
					return;
				}
				else if (invSelect == false){
					invSelect = true;
				}
				else if (invSelect) {
					if (invChoice == false) {
						invSelect = false;
					}
					else if (invChoice) { //user uses an item
						user.useItem(userItems.get(invPos));
						if (userItems.get(invPos) == "Health Potion") {
							bag.removePot();
						}
						if (userItems.get(invPos) == "Large Health Potion") {
							bag.removeLargePot();
						}
						if (userItems.get(invPos) == "Gerthy Health Potion") {
							bag.removeGerthPot();
						}
						initItems();
						invSelect = false;
					}
				}
			}
			else if (keys[KeyEvent.VK_UP] && keypress) {
				if (invPos == 0) {
					invPos = userItems.size();
				}
				else {
					invPos--;
				}
			}
			else if (keys[KeyEvent.VK_DOWN] && keypress) {
				if (invPos == userItems.size()) {
					invPos = 0;
				}
				else {
					invPos++;
				}
			}
			else if (keys[KeyEvent.VK_LEFT] && keypress && invSelect) {
				if (invChoice == false)
					invChoice = true;
			}
			else if (keys[KeyEvent.VK_RIGHT] && keypress && invSelect) {
				if (invChoice)
					invChoice = false;
			}
		}
		keypress = false;
	}
	
	public void drawInventory(Graphics g) {
		if (screen == "inventory") {
			g.setColor(new Color(255,243,199));
			g.fillRect(0,0,800,600);
			g.drawImage(scrollPanel1, 15, 355, this);
			g.drawImage(scrollPanel2, 480, 10, this);
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(bagPic, 75, 10, this);
			
			g.setColor(Color.BLACK);
			g.setFont(shopFont);
			for (int i = 0; i < userItems.size()+1; i++) {
				if (i != userItems.size()) {
					g.drawString(userItems.get(i), 500, (i+1)*80);
					g.drawString("x"+itemNums.get(i), 740, (i+1)*80);
				}
				else {
					g.drawString("Exit Inventory", 500, (i+1)*80);
				}
				
			}
			if (invPos < userItems.size()) {
				if(userItems.get(invPos) == "Health Potion") {
					g.drawImage(scrollPanel3, 370, 245, this);
					g.drawImage(healthPot, 380, 250, this);
				}
				
				if(userItems.get(invPos) == "Large Health Potion") {
					g.drawImage(scrollPanel3, 370, 245, this);
					g.drawImage(largePot, 380, 250, this);
				}
				
				if(userItems.get(invPos) == "Gerthy Health Potion") {
					g.drawImage(scrollPanel3, 370, 245, this);
					g.drawImage(gerthPot, 380, 250, this);
				}
				
				if(userItems.get(invPos) == "Iron Potion") {
					g.drawImage(scrollPanel3, 370, 245, this);
					g.drawImage(ironPot, 380, 250, this);
				}
				
				if(userItems.get(invPos) == "Wrath Potion") {
					g.drawImage(scrollPanel3, 370, 245, this);
					g.drawImage(wrathPot, 380, 250, this);
				}		
			}
			if (invSelect == false) {
				g.drawString("Which item would you like to use?", 30, 400);
			}
			if (invSelect) {
				if (userItems.get(invPos) == "Health Potion") {
					g.drawString("A potion that restores 5 HP.", 30, 400);
				}
				else if (userItems.get(invPos) == "Large Health Potion") {
					g.drawString("A potion that restores 10 HP.", 30, 400);
				}
				else if (userItems.get(invPos) == "Gerthy Health Potion") {
					g.drawString("Made of the rarest herbs from across", 30, 400);
					g.drawString("all of Gerthland, this potion restores", 30, 420);
					g.drawString("your HP to full capacity.", 30, 440);
				}
				else if (userItems.get(invPos) == "Wrath Potion") {
					g.drawString("A potion that temporarily increases your", 30, 400);
					g.drawString("damage dealt to enemies.", 30, 420);
				}
				else if (userItems.get(invPos) == "Iron Potion") {
					g.drawString("A potion that temporarily decreases", 30, 400);
					g.drawString("damage dealt to you by enemies.", 30, 420);
				}
				g.drawString("Would you like to use this item?", 30, 475);
				g.drawString("YES", 150, 525);
				g.drawString("NO", 290, 525);
				if (invChoice)
					g.drawImage(arrow, 60, 498, this);
				if (invChoice == false)
					g.drawImage(arrow, 200, 498, this);

			}
			g.setColor(Color.RED);
			g.drawRect(495, (invPos*80)+45, 275, 50);
		}
	}
	
	public void drawBattle(Graphics g, Enemy goon) {
		if (screen == "battle") {
	    	g.drawImage(battleBack,0,0,this);
	    	//Enemy Battle Stuff
	    	//pRect.setLocation(300,410);
	 		if(battleScreen.equals("pipe attack") || battleScreen.equals("laser attack")){
		    	g.setColor(dgreen);
		    	g.fillRect(135,347,510,196);
		    	g.drawImage(backRect,125,340,this);
		    	g.setColor(Color.white);
		    	g.fillRect(150,360,480,170);
				if(user.getHp()<= 0){
					battleScreen = "lose";
				}
				else{
					
					if(user.getHp() < user.getMaxHp()/2){
						g.drawImage(damaged,(int)pRect.getX(),(int)pRect.getY(),this);
					}
					else{
						g.drawImage(consumer,(int)pRect.getX(),(int)pRect.getY(),this);
					}
					if(immune){
						g.drawImage(immune1,(int)pRect.getX(),(int)pRect.getY(),this);
						timer++;
					}	 			
	 			}
	 			if(timer > 100){
					immune = false;
				}
			}
			inside();
			if (battleScreen.equals("options")) {
				options(g);
			}
			else if(battleScreen.equals("player attack")){
				goon.damage(attack(g, goon));
				//System.out.println(goon.getHealth());
				if (goon.getHealth() <= 0) {
					goon.resetHealth();
					screen = "moving";
				}
				if(finished){

					if(goon.getType().equals("slime")){
						battleScreen = "pipe attack";				
					}
					else if(goon.getType().equals("goblin")){
						battleScreen = "laser attack";		
					}
					else if(goon.getType().equals("gerth")){
						System.exit(0);			
					}
					reverse = false;
					stop = false;
					finished = false;
				}
			}
			else if(battleScreen.equals("talk")){
				if(goon.getType().equals("slime")){
					battleScreen = "pipe attack";			
				}
			}
			else if(battleScreen.equals("items")){
				System.exit(0);
			}
			else if(battleScreen.equals("run")){
				if(goon.run()){
					//screen == "game";
				}
				else{
					if(goon.getType().equals("slime")){
						battleScreen = "pipe attack";				
					}
					else if(goon.getType().equals("goblin")){
						System.exit(0);			
					}
					else if(goon.getType().equals("gerth")){
						System.exit(0);			
					}
				}
			}
			else if(battleScreen.equals("pipe attack")){
				pipeAttack(g);
				if(collision(rects) && !immune){
					user.setHp(1);;
					immune = true;
					timer = 0;
				}
				if(rects.size() == 0){
					battleScreen = "options";
					displayed = false;
				}
			}
			else if(battleScreen.equals("laser attack")){
				laserAttack(g);
				if((pRect.intersects(attackRect) && !immune && displaying) ||
					(pRect.intersects(leftattackRect) && !immune && displaying) ||
					(pRect.intersects(rightattackRect) && !immune && displaying)){

					user.setHp(4);
					immune = true;
					timer = 0;
				}
				if (attackTimer > 1960) {
					battleScreen = "options";
					displayed = false;
				}
			}
		}

	}

	public boolean getBack() {
		return back;
	}

	public void setBack() {
		back = false;
	}

	public boolean getSongOn() {
		return this.songOn;
	}

	public void toggleMenu() { //toggles if the in game menu shows or not (depending on key pressed)
		if (keys[KeyEvent.VK_C]) {
			this.menuPause = true;
		}

		if (keys[KeyEvent.VK_X]) {
			removeMenu();
			this.menuPause = false;
		}
	}

	public boolean getMenuPause() { //accessor method
		return menuPause;
	}

	public void displayMenu(Graphics g) { //dislays contents of the in game menu
		g.setColor(Color.BLACK);
		g.drawLine(0,100,800,100);
		g.drawLine(0,324,800,324);
		g.setColor(new Color(255,255,255,100));
		g.fillRect(0,325,800,275);
		g.fillRect(0,0,800,100);
		this.add(menuBut);
		this.add(bagBut);
		this.add(saveBut);
		this.add(profBut);
		this.add(songBut);
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
		this.remove(songBut);
		this.remove(menuLabel1);
		this.remove(menuLabel2);
		this.remove(menuLabel3);
		this.remove(menuLabel4);
	}

	public void displaySaveScreen(Graphics g) {
		g.setColor(new Color(255,243,199));
		g.fillRect(0,100,800,225);
		g.setColor(Color.BLACK);
		g.drawRect(1,100,799,224);

		this.add(saveLabel);
        this.add(saveYesBut);
        this.add(saveNoBut);
	}

	public void displayProfile(Graphics g) {
		g.setColor(new Color(255,243,199));
		g.fillRect(0,100,800,225);
		g.setColor(Color.BLACK);
		g.drawRect(1,100,799,224);
		g.drawRect(49,133,152,152);

		this.add(nameLabel);
		this.add(profCloseBut);

		if(this.character == 1) {
			this.add(charProf1);
		}

		else if(this.character == 2) {
			this.add(charProf2);
		}

		else if(this.character == 3) {
			this.add(charProf3);
		}

		//Display stats of the current character
		this.add(lvlStat);
		this.add(hpStat);
		this.add(atkStat);
		this.add(expStat);
		this.add(expLeftStat);
	}

	public void reloadStats() { //constantly updating the stats displayed in the profile
		this.lvlStat = new JLabel("LVL: " + user.getLvl());
		this.lvlStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		this.lvlStat.setSize(100,50);
		this.lvlStat.setLocation(225,170);

		this.hpStat = new JLabel("HP: " + user.getHp() + "/" + user.getMaxHp());
		this.hpStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		this.hpStat.setSize(100,50);
		this.hpStat.setLocation(225,195);

		this.atkStat = new JLabel("ATK: " + user.getAtk());
		this.atkStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		this.atkStat.setSize(100,50);
		this.atkStat.setLocation(225,220);

		this.expStat = new JLabel("EXP: " + user.getExp());
		this.expStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		this.expStat.setSize(100,50);
		this.expStat.setLocation(225,245);

		this.expLeftStat = new JLabel("Exp Needed: " + (int)Math.pow(user.getLvl()/0.1,2));
		this.expLeftStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		this.expLeftStat.setSize(200,50);
		this.expLeftStat.setLocation(400,245);
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if(source == menuBut && saveScreen == false && profScreen == false) { //if the save screen or profile is displayed, other buttons cannot be pressed
			int paneResult = JOptionPane.showConfirmDialog(null,"Any unsaved progress will be lost!","Return to menu?",JOptionPane.YES_NO_OPTION);
			if (paneResult == JOptionPane.YES_OPTION) { //opens a warning msg toward the user to make they have saved before returning to menu
				gameTheme.stop();
          		back = true;
			}

          	else {}
		}

		else if(source == bagBut && saveScreen == false && profScreen == false) {
			removeMenu();
			menuPause = false;
			screen = "inventory";
/*			this.bagScreen = new ShowBag(this.bag);
			this.add(bagScreen);*/
		}

		else if(source == saveBut && profScreen == false) {
			this.saveScreen = true;
		}

		else if(source == profBut && saveScreen == false) {
			this.profScreen = true;
		}

		else if(source == saveYesBut) {
			int paneResult = JOptionPane.showConfirmDialog(null,"This will replace any pre-existing data!","Save Current Game?",JOptionPane.YES_NO_OPTION);
			if (paneResult == JOptionPane.YES_OPTION) { //opens a warning msg toward the user to inform them that any pre saved data will be replaced
				ArrayList<String> recordStats = new ArrayList<String>();
				
				recordStats.add(user.getName());
				recordStats.add(Integer.toString(user.getLvl()));
				recordStats.add(Integer.toString(user.getExp()));
				recordStats.add(Integer.toString(user.getHp()));
				recordStats.add(Integer.toString(user.getMaxHp()));
				recordStats.add(Integer.toString(user.getAtk()));
				recordStats.add(Integer.toString(user.getCharNum()));
				recordStats.add(Integer.toString(mapx));
				recordStats.add(Integer.toString(mapy));
				
				if(currentMap.equals(map1)) {
					recordStats.add("map1");
					recordStats.add("currentRect1");
				}
				
				if(currentMap.equals(map2)) {
					recordStats.add("map2");
					recordStats.add("currentRect2");
				}
				
				if(currentMap.equals(map3)) {
					recordStats.add("map3");
					recordStats.add("currentRect3");
				}
				
				recordStats.add(Integer.toString(x1));
				recordStats.add(Integer.toString(y1));
				recordStats.add(Integer.toString(x2));
				recordStats.add(Integer.toString(y2));
				recordStats.add(Integer.toString(x3));
				recordStats.add(Integer.toString(y3));
				recordStats.add(Integer.toString(bag.getPot()));
				recordStats.add(Integer.toString(bag.getLargePot()));
				recordStats.add(Integer.toString(bag.getGerthPot()));
				recordStats.add(Integer.toString(bag.getIronPot()));
				recordStats.add(Integer.toString(bag.getWrathPot()));

				//This updates the text
            	try {
                	BufferedWriter writer = new BufferedWriter(new FileWriter("saveFile.txt"));
                	for(int i=0; i<recordStats.size(); i++) { //writing in the txt file, replacing it each time
	                    writer.write(recordStats.get(i));
	                    if(i != recordStats.size()) { //does not create a new line if it is the last recorded stat
	                    	writer.newLine();
	                    }
                	}
                	
                writer.close();
                JOptionPane.showMessageDialog(this, "Game has been saved");

	            }

	            catch(IOException e){
	                System.out.println("Game cannot be saved during this time");
	            }
			}

			else{}
		}

		else if(source == saveNoBut) {
			this.saveScreen = false;
			this.remove(saveYesBut);
			this.remove(saveNoBut);
			this.remove(saveLabel);
		}

		else if(source == profCloseBut) {
			this.profScreen = false;
			this.remove(nameLabel);
			this.remove(profCloseBut);

			if(this.character == 1) {
				this.remove(charProf1);
			}

			else if(this.character == 2) {
				this.remove(charProf2);
			}

			else if(this.character == 3) {
				this.remove(charProf3);
			}

			this.remove(lvlStat);
			this.remove(hpStat);
			this.remove(atkStat);
			this.remove(expStat);
			this.remove(expLeftStat);
		}

		else if(source == this.songBut && saveScreen == false && profScreen == false){
            this.gameTheme.playPause();
            //Displays a certain icon depending on if the music is playing
            if(gameTheme.getIsPlaying()) {
                this.songBut.setIcon(songIcon1);
                this.songOn = true;
            }
            else {
                this.songBut.setIcon(songIcon2);
                this.songOn = false;
            }
        }
        
/*        if(this.bagScreen != null) {
        	this.bagScreen.bagControls();
        }*/
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (screen == "moving") {
			drawMap(g);
			displayPlayer(g);
		}
		else if (screen == "dialogue") {
			drawMap(g);
			displayPlayer(g);
			drawDialogue(g);
		}
		else if (screen == "shop") {
			drawShop(g);
		}
		
		else if (screen == "inventory") {
			drawInventory(g);
		}
		else if (screen == "battle") {
			if (currentMap.equals(map1))
				drawBattle(g, goblin);
			if (currentMap.equals(map3))
				drawBattle(g, boss);
		}
		
		//System.out.println(secondPanel);
		//System.out.println(mapx + ", " + mapy);

		if(this.menuPause) {
			displayMenu(g);
		}

		if(this.saveScreen) {
			displaySaveScreen(g);
		}

		if(this.profScreen) {
			displayProfile(g);
		}
	}
}

class Inventory {
	private int pot, largePot, gerthPot, ironPot, wrathPot, gold;

	public Inventory(int pot, int largePot, int gerthPot, int ironPot, int wrathPot, int gold) {
		this.pot = pot;
		this.largePot = largePot;
		this.gerthPot = gerthPot;
		this.ironPot = ironPot;
		this.wrathPot = wrathPot;
		this.gold = gold;
	}

	public void addItem(String item) {
		if(item == "pot") {
			pot ++;
		}
		
		if(item == "largePot") {
			largePot ++;
		}
		
		if(item == "gerthPot") {
			gerthPot ++;
		}

		else if(item == "ironPot") {
			ironPot ++;
		}

		else if(item == "wrathPot") {
			wrathPot ++;
		}
	}
	
	public void addGold(int amount) {
		gold += amount;
	}
	
	//Accessor methods
	public int getPot() {
		return pot;
	}
	
	public int getLargePot() {
		return largePot;
	}
	
	public int getGerthPot() {
		return gerthPot;
	}

	public int getIronPot() {
		return wrathPot;
	}

	public int getWrathPot() {
		return ironPot;
	}
	public int getGold() {
		return gold;
	}
	
	//Removing items if used
	public void removePot() {
		pot --;
	}
	
	public void removeLargePot() {
		largePot --;
	}
	
	public void removeGerthPot() {
		gerthPot --;
	}
	
	public void removeIronPot() {
		ironPot --;
	}
	
	public void removeWrathPot() {
		wrathPot --;
	}
	public void removeGold(int amount) {
		gold -= amount;
	}
}

/*class ShowBag extends JPanel implements ActionListener, KeyListener {
	private int pot, largePot, gerthPot, ironPot, wrathPot, itemPos;
	private ArrayList<String> bag;
	private String currentItem;
	
	Image scrollPanel1 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205, Image.SCALE_DEFAULT);
	Image scrollPanel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550, Image.SCALE_DEFAULT);
	Image scrollPanel3 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
	Image bagPic = new ImageIcon("Pictures/bag.png").getImage().getScaledInstance(275, 325, Image.SCALE_DEFAULT);
	Image arrow = new ImageIcon("Pictures/arrow.png").getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT);
	Image healthPotPic = new ImageIcon("Pictures/pot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image largePotPic = new ImageIcon("Pictures/largepot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image gerthPotPic = new ImageIcon("Pictures/gerthpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image wrathPotPic = new ImageIcon("Pictures/wrathpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	Image ironPotPic = new ImageIcon("Pictures/ironpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	
	private boolean[] keys;
	private boolean itemSelect;
	private boolean keypress;

	public ShowBag(Inventory items) {
		this.pot = items.getPot();
		this.largePot = items.getLargePot();
		this.gerthPot = items.getGerthPot();
		this.ironPot = items.getIronPot();
		this.wrathPot = items.getWrathPot();
		
		this.bag = new ArrayList<String>();
		
		if(pot != 0) {
			this.bag.add("Health Potion");
		}
		
		if(largePot != 0) {
			this.bag.add("Large Health Potion");
		}
		
		if(gerthPot != 0) {
			this.bag.add("Gerthy Health Potion");
		}
		
		if(ironPot != 0) {
			this.bag.add("Iron Potion");
		}
		
		if(wrathPot != 0) {
			this.bag.add("Wrath Potion");
		}
		
		this.itemPos = 0;
		this.itemSelect = false;
		this.keypress = false;
		this.bag.add("Close Bag");
		this.setSize(800,600);
		keys = new boolean[KeyEvent.KEY_LAST + 1];
	}
	
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		keypress = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	public void bagControls() {
		requestFocus();
		if (keys[KeyEvent.VK_UP] && keypress && itemSelect == false) {
			this.itemPos --;
		}
		if (keys[KeyEvent.VK_DOWN] && keypress && itemSelect == false) {
			this.itemPos ++;
		}
		keypress = false;
	}

	public void actionPerformed(ActionEvent evt) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255,243,199));
		g.fillRect(0,0,800,600);
		g.drawImage(scrollPanel1, 15, 355, this);
		g.drawImage(scrollPanel2, 480, 10, this);
		g.drawImage(scrollPanel3, 370, 245, this);
		g.drawImage(bagPic, 75, 10, this);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Comic Sans ms", Font.BOLD, 20));
		
		for(int i=0; i<this.bag.size(); i++) {
			g.drawString(this.bag.get(i), 500, (i+1)*80);
		}
		
		g.setColor(Color.RED);
		g.drawRect(495, (this.itemPos +1)*45, 275, 50);
		
		if(this.bag.get(itemPos) == "Health Potion") {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(healthPotPic, 375, 250, this);
		}
		
		if(this.bag.get(itemPos) == "Large Health Potion") {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(largePotPic, 375, 250, this);
		}
		
		if(this.bag.get(itemPos) == "Gerthy Health Potion") {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(gerthPotPic, 375, 250, this);
		}
		
		if(this.bag.get(itemPos) == "Iron Potion") {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(ironPotPic, 375, 250, this);
		}
		
		if(this.bag.get(itemPos) == "Wrath Potion") {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(wrathPotPic, 375, 250, this);
		}
	}
}*/

class PlayerStats { //class used to keep track of a player's stats (FIXING)
	private int hp, maxHp, atk, lvl, exp, charNum;
	private String name;

	public PlayerStats(int hp, int atk, int lvl, int exp, String name, int charNum) {
		this.maxHp = hp;
		this.hp = maxHp-5;
		this.atk = atk;
		this.lvl = lvl;
		this.exp = exp;
		this.name = name;
		this.charNum = charNum;
	}

	//Accessor methods
	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAtk() {
		return atk;
	}

	public int getLvl() {
		return lvl;
	}

	public int getExp() {
		return exp;
	}

	public int getCharNum() {
		return charNum;
	}

	//Set the hp after taking dmg
	public void setHp(int dmg) {
		hp = hp - dmg;
	}

	//Using an item in inventory
	public void useItem(String item) {
		if (item == "Health Potion") {
			hp += 5;
			if (hp > maxHp) {
				hp = maxHp;
			}
		}
		if (item == "Large Health Potion") {
			hp += 10;
			if (hp > maxHp) {
				hp = maxHp;
			}
		}
		if (item == "Gerthy Health Potion") {
			hp = maxHp;
		}
	}

	//Sets the current lvl
	public void setLvl() {
		lvl = 1 + (int)(0.1*Math.sqrt(exp));
	}

	//Increases hp based on player lvl
	public void hpUp() {
		maxHp = maxHp + 2;
	}
}