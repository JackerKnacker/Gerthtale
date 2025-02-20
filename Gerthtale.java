//Gerthtale.java
//By Alex Shi, Jakir Ansari, Jason Wong
//A Turn-Based Rhythmic RPG inspired by Undertale & Final Fantasy

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

    // LayeredPane allows me to control what shows on top
    JLayeredPane mPage = new JLayeredPane(); //menu page
    JLayeredPane pPage = new JLayeredPane(); //player creation page
    JLayeredPane sPage = new JLayeredPane(); //save-select page
    JLayeredPane iPage = new JLayeredPane(); //options page
    JLayeredPane cPage = new JLayeredPane(); //credits page

    JButton newGameBut, saveSelectBut, instructBut, creditBut, continueBut, soundBut, backBut, confirmBut, pBut1, pBut2, pBut3, openSaveConfirm;

	JLabel charDisplay1, charDisplay2, charDisplay3, noSave, charFace1, charFace2, charFace3, nameStat, hpStat, lvlStat, expStat, atkStat, charPanel;

	JTextField nameBox;

	PlayerStats user; //keeps track of the player's stats
	Sound menuTheme; //main menu song

	//The two icons will be used interchangeably to display whether or not the music is muted or not
    ImageIcon soundIcon1 = new ImageIcon("Pictures/sound.png"); //soundIcon1 holds the unmuted version of the sound image
    ImageIcon soundIcon2 = new ImageIcon("Pictures/mute.png");  //soundIcon2 holds the muted version of the sound image
    Image scrollSavePanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(400, 450, Image.SCALE_DEFAULT);

	String saveSlotSelect = "none"; //keeps track which save slot is selected
	String charName;
	int charSelect = 0;
	ArrayList<String> savedStats; //arrayList to keep track of all the recorded stats from txt file

	GameScreen game; //game screen
	
	Toolkit toolkit = Toolkit.getDefaultToolkit(); //used to change the mouse cursor pic
	Image clickCursor = toolkit.getImage("Pictures/clickCursor.png"); //pic for when mouse is normal
	Image gloveCursor = toolkit.getImage("Pictures/gloveCursor.png"); //pic for when mouse hovers over a button

    public Gerthtale() {
        super("Gerthtale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        setCursor(toolkit.createCustomCursor(gloveCursor, new Point(0,0), "")); //sets the mouse cursor pic

        setLocationRelativeTo(null); //makes the window appear in the center

        this.timer = new javax.swing.Timer(25, this);

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
		
		//pictures to display the character avatars
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
		
		//char portraits
        this.charFace1 = new JLabel(new ImageIcon("Pictures/charPortrait1.png"));
        this.charFace1.setSize(200,200);
        this.charFace1.setLocation(300,350);

        this.charFace2 = new JLabel(new ImageIcon("Pictures/charPortrait2.png"));
        this.charFace2.setSize(200,200);
        this.charFace2.setLocation(300,350);

        this.charFace3 = new JLabel(new ImageIcon("Pictures/charPortrait3.png"));
        this.charFace3.setSize(200,200);
        this.charFace3.setLocation(300,350);

        this.charPanel = new JLabel(new ImageIcon(scrollSavePanel));
        this.charPanel.setSize(400,450);
        this.charPanel.setLocation(200,115);
        
        //confirm button to play the game with save file
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

        JLabel instructPic = new JLabel(new ImageIcon("Pictures/instruct1.png"));
        instructPic.setSize(785,325);
        instructPic.setLocation(5,110);
        this.iPage.add(instructPic,1);

        JLabel instructText = new JLabel("'c' to open in game menu");
        instructText.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		instructText.setSize(400,50);
		instructText.setLocation(275,425);
		this.iPage.add(instructText,2);

		JLabel instruct2Text = new JLabel("'x' to close in game menu");
        instruct2Text.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		instruct2Text.setSize(400,50);
		instruct2Text.setLocation(275,455);
		this.iPage.add(instruct2Text,2);

		JLabel instruct3Text = new JLabel("'enter' to confirm");
        instruct3Text.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
		instruct3Text.setSize(400,50);
		instruct3Text.setLocation(275,485);
		this.iPage.add(instruct3Text,2);

        //\/ --- components for cPage --- \/
		JLabel subTitle4 = new JLabel(new ImageIcon("Pictures/subtitle4.png"));
        subTitle4.setSize(300,50);
        subTitle4.setLocation(250,50);
        this.cPage.add(subTitle4,1);

        JLabel creditText = new JLabel(new ImageIcon("Pictures/creditText.png"));
        creditText.setSize(450,370);
        creditText.setLocation(175,100);
        this.cPage.add(creditText,1);

        requestFocus();
        setResizable(false);
        setVisible(true);
    }

    public void keyPressed(KeyEvent k){}
	public void keyReleased(KeyEvent k){}

	public void keyTyped(KeyEvent k){ //making sure the name length does not go over 20 characters
		if(this.nameBox.getText().length() == 20) {
			k.consume();
		}
	}

    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();

		if(source == this.newGameBut) { //opens the character creation 
			if(this.backBut.getParent() != pPage) { //only adds back button if not already in the page
        		this.pPage.add(backBut,1);
        	}
        	
			//Text Box
	        this.nameBox = new JTextField("Type Name Here!"); //text field used to recieve the player's name
	        this.nameBox.addKeyListener(this);
			this.nameBox.setSize(200,25);
			this.nameBox.setLocation(200,475);
        	this.pPage.add(nameBox,1);
        	this.cLayout.show(this.cards, "playerCreation");
		}

        else if(source == this.confirmBut && charSelect != 0){ //confirming character and sets up game
        	this.confirmBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), "")); //once confirm button can be selected, than the cursor will change pic
        	charName = nameBox.getText();  //This gets the name from text box typed by the player

        	if(charSelect == 1) { //different characters will have different base stats (creates the player class to keep track of stats)
        		this.user = new PlayerStats(20, 20, 10, 1, 0, charName, charSelect); //HP, MAXHP, ATK, LVL, EXP, NAME, CHAR-NUM
        	}

        	if(charSelect == 2) {
        		this.user = new PlayerStats(15, 15, 15, 1, 0, charName, charSelect);
        	}

        	if(charSelect == 3) {
        		this.user = new PlayerStats(10, 10, 20, 1, 0, charName, charSelect);
        	}

			Inventory bag = new Inventory(3,1,0,0,0, 100); //creates the inventory of the player
        	this.pPage.remove(nameBox);
        	menuTheme.stop(); //stops the menu music so it doesnt overlap with game music

			if(this.user != null) { //creates the game screen when character has been selected
				game = new GameScreen(user, bag, menuTheme.getIsPlaying(), -908, -320, "map1", true, false);
			}

        	this.cards.add(game, "game");
            this.cLayout.show(this.cards,"game");
            timer.start();
        }
		
		//changes the current character selected
        else if(source == this.pBut1) {
        	this.charSelect = 1;
        }

        else if(source == this.pBut2) {
        	this.charSelect = 2;
        }

        else if(source == this.pBut3) {
        	this.charSelect = 3;
        }

        else if(source == this.saveSelectBut){ //opens save select page
        	if(this.backBut.getParent() != sPage) { //only adds back button if not already in the page
        		this.sPage.add(backBut,1);
        	}

        	this.noSave = new JLabel("No Save File Found"); //label used if there is no save file
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
	            
	            //System.out.println(savedStats); //checking the save file list (experimental purposes)

	            if(this.noSave.getParent() == this.sPage) { //if the no save label exists, it will be removed since there will be a save file
	            	sPage.remove(noSave);
	            }

	            if(this.openSaveConfirm.getParent() != sPage) { //adds the confirm button if not on the page
	            	this.sPage.add(openSaveConfirm);
	            }
				
				//displays which character is in the save
	            if(Integer.parseInt(savedStats.get(6)) == 1) {
	            	this.sPage.add(charFace1,2);
	            }

	            else if(Integer.parseInt(savedStats.get(6)) == 2) {
	            	this.sPage.add(charFace2,2);
	            }

	            else if(Integer.parseInt(savedStats.get(6)) == 3) {
	            	this.sPage.add(charFace3,2);
	            }

	            this.sPage.add(this.charPanel,3);
				
				//displaying the stats of the save file character
	            this.nameStat = new JLabel(savedStats.get(0));
				this.nameStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.nameStat.setSize(800,50);
				this.nameStat.setLocation(250,125);
				this.sPage.add(nameStat,3);

	            this.lvlStat = new JLabel("LVL: " + savedStats.get(1));
				this.lvlStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.lvlStat.setSize(400,50);
				this.lvlStat.setLocation(250,165);
				this.sPage.add(lvlStat,3);

				this.expStat = new JLabel("EXP: " + savedStats.get(2));
				this.expStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.expStat.setSize(400,50);
				this.expStat.setLocation(250,205);
				this.sPage.add(expStat,3);

	            this.hpStat = new JLabel("HP: " + savedStats.get(3) + "/" + savedStats.get(4));
				this.hpStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.hpStat.setSize(400,50);
				this.hpStat.setLocation(250,245);
				this.sPage.add(hpStat,3);

				this.atkStat = new JLabel("ATK: " + savedStats.get(5));
				this.atkStat.setFont(new Font("Comic Sans ms", Font.PLAIN, 20));
				this.atkStat.setSize(400,50);
				this.atkStat.setLocation(250,285);
				this.sPage.add(atkStat,3);
	        }

	        catch (IOException e){ //If there no save text file, the slot will be empty
	        	if(this.openSaveConfirm.getParent() == this.sPage) {
	        		this.openSaveConfirm.setVisible(false); //will remove the confirm button if it exists on the page
	        		this.sPage.remove(openSaveConfirm);
	        	}

	        	this.sPage.add(this.charPanel,3);
				this.sPage.add(this.noSave,1);
	        }
        }

        else if(source == this.openSaveConfirm) { //sets up the game with the save file information
       		this.user = new PlayerStats(Integer.parseInt(savedStats.get(3)), Integer.parseInt(savedStats.get(4)), Integer.parseInt(savedStats.get(5)), Integer.parseInt(savedStats.get(1)), Integer.parseInt(savedStats.get(2)), savedStats.get(0), Integer.parseInt(savedStats.get(6))); //HP, ATK, LVL, EXP, NAME, CHAR-NUM
       		Inventory bag = new Inventory(Integer.parseInt(savedStats.get(10)), Integer.parseInt(savedStats.get(11)), Integer.parseInt(savedStats.get(12)), Integer.parseInt(savedStats.get(13)), Integer.parseInt(savedStats.get(14)), Integer.parseInt(savedStats.get(15)));

       		this.game = new GameScreen(user, bag, menuTheme.getIsPlaying(), Integer.parseInt(savedStats.get(7)), Integer.parseInt(savedStats.get(8)), savedStats.get(9), false, Boolean.valueOf(savedStats.get(16)));
       		this.cards.add(game, "game");
            this.cLayout.show(this.cards,"game");
            menuTheme.stop();
            timer.start();
       	}
		
        else if(source == this.instructBut){ //opens instructions page
        	if(this.backBut.getParent() != iPage) {
        		this.iPage.add(backBut,1);
        	}

        	this.cLayout.show(this.cards, "instructions");
        }

        else if(source == this.creditBut){ //opens credits page
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
        			menuTheme.setPosition(); //restarts the song if music isnt muted
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

        	game.toggleMenu(); //checks if in game menu is toggled on

        	if(game.getMenuPause() == false) { //if the menu isnt toggled on, the controls can be used
        		game.storyControls();
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

class GameScreen extends JPanel implements ActionListener, KeyListener { //Screen that displays the game
	private JButton menuBut, bagBut, saveBut, profBut, saveYesBut, saveNoBut, profCloseBut, songBut, gameOverBut;

	private JLabel menuTitle, menuLabel1, menuLabel2, menuLabel3, menuLabel4, saveLabel, charProf1, charProf2, charProf3;

	private Toolkit toolkit = Toolkit.getDefaultToolkit(); //sets cursor image when hovering over a button
	private Image clickCursor = toolkit.getImage("Pictures/clickCursor.png");

	private ImageIcon songIcon1 = new ImageIcon("Pictures/sound.png"); //unmuted version of the song image
    private ImageIcon songIcon2 = new ImageIcon("Pictures/mute.png");  //muted version of the song image

    //--------|Story Related Stuff| ------//
    Image storyPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);

    //--------|Shop Related Stuff|--------//
	private Image shopBack = new ImageIcon("Pictures/shopBack.jpg").getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
	private Image panel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205, Image.SCALE_DEFAULT);
	private Image panel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550, Image.SCALE_DEFAULT);
	private Image longPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(765, 205, Image.SCALE_DEFAULT);
	private Image iconPanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
	private Image arrow = new ImageIcon("Pictures/arrow.png").getImage().getScaledInstance(80, 40, Image.SCALE_DEFAULT);
	private Image healthPot = new ImageIcon("Pictures/pot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	private Image largePot = new ImageIcon("Pictures/largepot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	private Image gerthPot = new ImageIcon("Pictures/gerthpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	private Image wrathPot = new ImageIcon("Pictures/wrathpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	private Image ironPot = new ImageIcon("Pictures/ironpot.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	private Image shopExit = new ImageIcon("Pictures/shopExit.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);

	private Font shopFont = new Font("Comic Sans MS", Font.BOLD, 20);
	private Font bigShopFont = new Font("Comic Sans MS", Font.BOLD, 28);
	//Lists for displaying items
	private String[] shopItems = {"Health Potion [50g]", "Large Health Potion [100g]", "Gerthy Health Potion [200g]", "Wrath Potion [150g]", "Iron Potion [150g]", "Exit Shop"};
	private int[] goldCosts = {50, 100, 200, 150, 150};
	//Booleans for displaying certain panels
	private boolean shop = false, itemSelect = false, choice = false, notEnoughGold = false;
	private int itemPos = 0; //item that user is hovering over
	//------------------------------------//

	//---------|Dialogue Stuff|---------//
	private Image dialoguePanel = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(755, 200, Image.SCALE_DEFAULT);
	private Image npc1 = new ImageIcon("Pictures/npc1.png").getImage().getScaledInstance(36, 48, Image.SCALE_DEFAULT);
	private Image npc1head = new ImageIcon("Pictures/npc1head.png").getImage().getScaledInstance(102, 81, Image.SCALE_DEFAULT);
	private Image npc2 = new ImageIcon("Pictures/npc2.png").getImage().getScaledInstance(36, 48, Image.SCALE_DEFAULT);
	private Image npc2head = new ImageIcon("Pictures/npc2head.png").getImage().getScaledInstance(102, 81, Image.SCALE_DEFAULT);
	private Image npc3 = new ImageIcon("Pictures/npc3.png").getImage().getScaledInstance(36, 48, Image.SCALE_DEFAULT);
	private Image npc3head = new ImageIcon("Pictures/npc3head.png").getImage().getScaledInstance(102, 81, Image.SCALE_DEFAULT);
	private Font dialogueFont = new Font("Comic Sans MS", Font.BOLD, 24);
	private boolean firstPanel = true, secondPanel = false; //panel that is currently displayed
	private int npc = 1; //the NPC that user is talking to (1 = villager, 2 = mayor, 3 = final boss)
	//----------------------------------//

	//-------|Inventory Stuff|-------//
	private Image scrollPanel1 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(455, 205, Image.SCALE_DEFAULT);
	private Image scrollPanel2 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(300, 550, Image.SCALE_DEFAULT);
	private Image scrollPanel3 = new ImageIcon("Pictures/panelbox.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
	private Image bagPic = new ImageIcon("Pictures/bag.png").getImage().getScaledInstance(275, 325, Image.SCALE_DEFAULT);
	private ArrayList<String> userItems = new ArrayList<String>();
	private ArrayList<Integer> itemNums = new ArrayList<Integer>();
	private int invPos = 0;
	private boolean invSelect = false, invChoice = false, cantUse = false;
	//-------------------------------//

	//--------|Battle Stuff|--------//
	private int timer = 0, attackTimer = 0,directionX = 2,directionL = 2,directionR = -2,fire; //variables used in the various attacks throughout our game
	private String battleScreen = "options"; //changes the screen of the battle
	private boolean immune= false,displaying = false,displayed = false, bossBattle = false, gameFinish;
	private Rectangle baseRect = new Rectangle(150,360,480,170),topRect,leftRect,rightRect,attackRect,leftattackRect,rightattackRect;
	private Random rng = new Random();
	private Enemy slime = new Enemy(30, 2, "Slime"), goblin = new Enemy(50, 4, "Goblin"), boss = new Enemy(80, 0, "Boss");
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

	private int linex = 170,damage,option,delayTimer = 0, atkModifier = 0, defModifier = 0;
	public boolean reverse = false, stop = false;
	public Rectangle pRect = new Rectangle(300,410,20,20);
	private Color dgreen = new Color(0,153,2);
	
	//Variables for 'items' option in battle
	private int bPos = 0;
	private boolean bSelect = false, bChoice = false;

	private Image battleBack = new ImageIcon("Pictures/map.jpg").getImage().getScaledInstance(800,650,Image.SCALE_SMOOTH);
	private Image consumer = new ImageIcon("Pictures/player.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	private Image backRect = new ImageIcon("Pictures/Back Rect.png").getImage().getScaledInstance(530,210,Image.SCALE_SMOOTH);
	private Image damaged = new ImageIcon("Pictures/damaged.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	private Image immune1 = new ImageIcon("Pictures/immune_0.png").getImage().getScaledInstance((int)pRect.getWidth(),(int)pRect.getHeight(),Image.SCALE_SMOOTH);
	private Image redRect = new ImageIcon("Pictures/Red Border.png").getImage().getScaledInstance(200,80,Image.SCALE_SMOOTH);
	private Image attackBackground = new ImageIcon("Pictures/Attack Background.png").getImage().getScaledInstance(480,170,Image.SCALE_SMOOTH);
	private Image slider = new ImageIcon("Pictures/slider.png").getImage().getScaledInstance(15,170,Image.SCALE_SMOOTH);
	private Image slimePic = new ImageIcon("Pictures/slime.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
	private Image goblinPic = new ImageIcon("Pictures/goblin.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
	private Image bossPic = new ImageIcon("Pictures/knight.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
	private Image char1Pic = new ImageIcon("Pictures/charDisplay1.png").getImage().getScaledInstance(111, 150, Image.SCALE_DEFAULT);
	private Image char2Pic = new ImageIcon("Pictures/bCharDisplay.png").getImage().getScaledInstance(111, 150, Image.SCALE_DEFAULT);
	private Image char3Pic = new ImageIcon("Pictures/charDisplay3.png").getImage().getScaledInstance(111, 150, Image.SCALE_DEFAULT);
	private Image sword = new ImageIcon("Pictures/sword.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
	private Image run = new ImageIcon("Pictures/running man.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
	private Image itemBag = new ImageIcon("Pictures/items.png").getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
	//-------------------------------//

	private boolean[] keys;
	private int mapx, mapy, boxy = 55, steps = 0;
	private double playerFrame;
	private String screen, lastDirection = "down";
	private Rectangle playerHitbox = new Rectangle(382,276,36,48);
	private ArrayList<Rectangle> map1Rects = new ArrayList<Rectangle>(); //Collision rectangles for each map
	private ArrayList<Rectangle> map2Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> map3Rects = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> currentRects; //Current array of rectangles on screen
	private Image map1 = new ImageIcon("Maps/map1.png").getImage();
	private Image map2 = new ImageIcon("Maps/map2.png").getImage();
	private Image map3 = new ImageIcon("Maps/map3.png").getImage();
	private Image currentMap; //Current map displayed

	private int character; //keep track of which character is being used
	private PlayerStats user; //the player's stats
	private Inventory bag; //the player's inventory

	//Player sprites
	private Image[] playerDown, playerUp, playerLeft, playerRight;

	private boolean back = false;
	private boolean menuPause = false;
	private boolean saveScreen = false;
	private boolean profScreen = false;
	private boolean songOn;
	private boolean keypress;

	private Sound gameTheme; //game music

	public GameScreen(PlayerStats user, Inventory bag, boolean songOn, int mapx, int mapy, String current, boolean storyStart, boolean gameFinish) { //constructor for GameScreen
		this.user = user;
		this.songOn = songOn;
		this.bag = bag;
		this.gameFinish = gameFinish; //checks if the game has been beaten before (when save file is selected)

		gameTheme = new Sound("Music/BOTW OST - Cave.wav"); //This loads the background music

		if(this.songOn) {
			gameTheme.loop(); //This will play game music if music has not been muted in menu
			this.songBut = new JButton(songIcon1);
		}

		else {
			this.songBut = new JButton(songIcon2);
		}

		if(storyStart) { //boolean checks if the game is a brand new game
			screen = "story"; //if it is it set the screen to display story
		}

		else { //if not (loading from save) it will not display story
			screen = "moving";
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
		this.menuTitle = new JLabel("Game Paused");
		this.menuTitle.setFont(new Font("Comic Sans ms", Font.PLAIN, 50));
		this.menuTitle.setSize(400,50);
		this.menuTitle.setLocation(250,25);

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
		
		//text to prompt the user if they would like to save the game
        this.saveLabel = new JLabel("Would you like to save the game?");
		this.saveLabel.setFont(new Font("Comic Sans ms", Font.PLAIN, 30));
		this.saveLabel.setSize(600,100);
		this.saveLabel.setLocation(175,100);

		//Character profile
		this.charProf1 = new JLabel(new ImageIcon("Pictures/charPortrait1.png")); //loading images of all characters, since any of the three could be used
		this.charProf1.setSize(150,150);
		this.charProf1.setLocation(50,135);

		this.charProf2 = new JLabel(new ImageIcon("Pictures/charPortrait2.png"));
		this.charProf2.setSize(150,150);
		this.charProf2.setLocation(50,135);

		this.charProf3 = new JLabel(new ImageIcon("Pictures/charPortrait3.png"));
		this.charProf3.setSize(150,150);
		this.charProf3.setLocation(50,135);
		
		//Button to return to main menu after dying in battle
		this.gameOverBut = new JButton(new ImageIcon("Pictures/gameOverText.png"));
		this.gameOverBut.addActionListener(this);
        this.gameOverBut.setSize(400,100);
        this.gameOverBut.setLocation(200,250);
        this.gameOverBut.setContentAreaFilled(false);
        this.gameOverBut.setFocusPainted(false);
        this.gameOverBut.setBorderPainted(false);
        this.gameOverBut.setCursor(toolkit.createCustomCursor(clickCursor, new Point(0,0), ""));

        this.character = user.getCharNum(); //gets the num from the parameter

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

		this.mapx = mapx;
		this.mapy = mapy;

		if(current.equals("map1")) { //loads correct map and corresponding rects based on parameter
			this.currentMap = map1;
			this.currentRects = map1Rects;
			loadRects(1);
		}

		else if(current.equals("map2")) {
			this.currentMap = map2;
			this.currentRects = map2Rects;
			loadRects(2);
		}

		else if(current.equals("map3")) {
			this.currentMap = map3;
			this.currentRects = map3Rects;
			loadRects(3);
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
	        if(key == KeyEvent.VK_SPACE && battleScreen == "player attack"){ //used to stop the slider
	        	stop = true;
	        }
	        else if(key == KeyEvent.VK_RIGHT && battleScreen == "options"){ //moves the option selected
				if(option < 2){
					option ++;
				}

				else {
					option = 0;
				}
			}

			else if(key == KeyEvent.VK_LEFT && battleScreen == "options"){
				if(option > 0){
					option --;
				}

				else {
					option = 2;
				}
			}

			else if (battleScreen.equals("items")) {
				//If user is selecting an item, UP and DOWN keys are used to navigate through items
				if (keys[KeyEvent.VK_UP] && bSelect == false) { 
					if (bPos == 0) {
						bPos = userItems.size();
					}
					else {
						bPos--;
					}
				}

				else if (keys[KeyEvent.VK_DOWN] && bSelect == false) {
					if (bPos == userItems.size()) {
						bPos = 0;
					}

					else {
						bPos++;
					}
				}
				
				//Once an item has been selected, LEFT and RIGHT keys are used to switch between
				//if the player's confirmation choice (if they're sure they want to use the item)
				else if (keys[KeyEvent.VK_LEFT] && bSelect) {
					if (bChoice == false)
						bChoice = true;
				}

				else if (keys[KeyEvent.VK_RIGHT] && bSelect) {
					if (bChoice)
						bChoice = false;
				}

				else if (keys[KeyEvent.VK_ENTER]) { //ENTER is used for multiple things
					if (bPos == userItems.size()) { //exiting items
						bPos = 0;
						battleScreen = "options";
					}

					else if (bSelect == false){ //selecting an item
						bSelect = true;
					}

					else if (bSelect) { //checking if player is sure they want to use item
						if (bChoice == false) {
							bSelect = false;
						}

						else if (bChoice) { //user uses an item
							user.useItem(userItems.get(bPos));
							if (userItems.get(bPos) == "Health Potion") { //removing item from bag
								bag.removePot();
							}
							if (userItems.get(bPos) == "Large Health Potion") {
								bag.removeLargePot();
							}
							if (userItems.get(bPos) == "Gerthy Health Potion") {
								bag.removeGerthPot();
							}
							if (userItems.get(bPos) == "Wrath Potion") {
								bag.removeWrathPot();
							}
							if (userItems.get(bPos) == "Iron Potion") {
								bag.removeIronPot();
							}

							initItems();
							//resetting all variables; preparing for enemy to attack
							bSelect = false;
							bPos = 0;
							reverse = false;
							stop = false;
							delayTimer = 0;
							linex = 170;
							//switching to enemy attack
							if(currentMap.equals(map1)){
								battleScreen = "pipe attack";
							}
							else if(currentMap.equals(map3)){
								if (bossBattle) {
									battleScreen = "laser attack";
								}
								else {
									battleScreen = "laser attack";
								}
							}
						}
					}
				}
			}

			else if(battleScreen.equals("options") && key == KeyEvent.VK_ENTER){ //choosing an option
 				displayed = false;

 				if (option == 0){
 					battleScreen = "player attack";
 				}

 				else if(option == 1){
 					battleScreen = "items";
 				}

 				else if(option == 2){
 					battleScreen = "run";
 				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		playerFrame = 0; //Restarts frame if user stops pressing key and starts again
	}

	public void loadRects(int rectsNum) { //A method using for initializing all collision rectangles
										  //for a certain map
		//-------MAP 1 RECTS-------//
		if (rectsNum == 1) {
			map1Rects.clear();

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
		}


		//-------MAP 2 RECTS-------//

		else if (rectsNum == 2) {
			map2Rects.clear();

			map2Rects.add(new Rectangle(1152+mapx, 992+mapy, 320, 416));
			map2Rects.add(new Rectangle(1136+mapx, 992+mapy, 32, 416));
			map2Rects.add(new Rectangle(992+mapx, 992+mapy, 16, 416));
			map2Rects.add(new Rectangle(1168+mapx, 976+mapy, 672, 32));
			map2Rects.add(new Rectangle(1808+mapx, 496+mapy, 32, 512));
			map2Rects.add(new Rectangle(1824+mapx, 480+mapy, 672, 32));
			map2Rects.add(new Rectangle(1856+mapx, 464+mapy, 672, 32));
			map2Rects.add(new Rectangle(1824+mapx, 256+mapy, 672, 32));
			map2Rects.add(new Rectangle(1792+mapx, 176+mapy, 32, 64));
			map2Rects.add(new Rectangle(1760+mapx, 192+mapy, 32, 32));
			map2Rects.add(new Rectangle(192+mapx, 192+mapy, 1568, 32));
			map2Rects.add(new Rectangle(160+mapx, 224+mapy, 16, 736));
			map2Rects.add(new Rectangle(160+mapx, 960+mapy, 32, 32));
			map2Rects.add(new Rectangle(192+mapx, 976+mapy, 800, 16));

			map2Rects.add(new Rectangle(800+mapx, 864+mapy, 96, 128));
			map2Rects.add(new Rectangle(608+mapx, 864+mapy, 96, 128));
			map2Rects.add(new Rectangle(416+mapx, 800+mapy, 96, 128));
			map2Rects.add(new Rectangle(224+mapx, 800+mapy, 96, 128));
			map2Rects.add(new Rectangle(160+mapx, 608+mapy, 96, 32));
			map2Rects.add(new Rectangle(160+mapx, 384+mapy, 96, 32));
			map2Rects.add(new Rectangle(256+mapx, 352+mapy, 28, 256));
			map2Rects.add(new Rectangle(288+mapx, 352+mapy, 32, 192));
			map2Rects.add(new Rectangle(324+mapx, 352+mapy, 28, 256));
			map2Rects.add(new Rectangle(1118+mapx, 500+mapy, 36, 48)); //Mayor NPC
			map2Rects.add(new Rectangle(352+mapx, 352+mapy, 160, 256));
			map2Rects.add(new Rectangle(512+mapx, 448+mapy, 32, 192));
			map2Rects.add(new Rectangle(672+mapx, 480+mapy, 96, 128));
			map2Rects.add(new Rectangle(992+mapx, 576+mapy, 32, 64));
			map2Rects.add(new Rectangle(1248+mapx, 576+mapy, 32, 64));
			map2Rects.add(new Rectangle(1408+mapx, 736+mapy, 36, 48)); //NPC
			map2Rects.add(new Rectangle(864+mapx, 320+mapy, 544, 128));
			map2Rects.add(new Rectangle(864+mapx, 448+mapy, 224, 64));
			map2Rects.add(new Rectangle(896+mapx, 512+mapy, 160, 32));
			map2Rects.add(new Rectangle(1088+mapx, 448+mapy, 24, 96));
			map2Rects.add(new Rectangle(1160+mapx, 448+mapy, 24, 96));
			map2Rects.add(new Rectangle(1184+mapx, 448+mapy, 224, 64));
			map2Rects.add(new Rectangle(1216+mapx, 512+mapy, 160, 32));
			map2Rects.add(new Rectangle(1696+mapx, 544+mapy, 96, 128));
			map2Rects.add(new Rectangle(1568+mapx, 800+mapy, 64, 64));
		}

		//-------MAP 3 RECTS-------//
		else if (rectsNum == 3) {
			map3Rects.clear();

			map3Rects.add(new Rectangle(mapx, 576+mapy, 704, 32));
			map3Rects.add(new Rectangle(704+mapx, 320+mapy, 16, 288));
			map3Rects.add(new Rectangle(704+mapx, 288+mapy, 2240, 32));
			
			map3Rects.add(new Rectangle(2928+mapx, 320+mapy, 16, 576 ));
			map3Rects.add(new Rectangle(2912+mapx, 896+mapy, 32, 32));
			map3Rects.add(new Rectangle(2032+mapx, 912+mapy, 896, 16));
			map3Rects.add(new Rectangle(2016+mapx, 928+mapy, 32, 32));
			map3Rects.add(new Rectangle(2000+mapx, 944+mapy, 16, 608));
			map3Rects.add(new Rectangle(1984+mapx, 1536+mapy, 32, 32));
			map3Rects.add(new Rectangle(1472+mapx, 1552+mapy, 512, 16));
			map3Rects.add(new Rectangle(1712+mapx, 1500+mapy, 36, 48)); //Boss NPC
			map3Rects.add(new Rectangle(1440+mapx, 1536+mapy, 32, 32));
			map3Rects.add(new Rectangle(1440+mapx, 560+mapy, 16, 992));
			map3Rects.add(new Rectangle(1440+mapx, 560+mapy, 1088, 16));
			map3Rects.add(new Rectangle(2496+mapx, 576+mapy, 32, 32));
			map3Rects.add(new Rectangle(2528+mapx, 592+mapy, 16, 96));
			map3Rects.add(new Rectangle(1440+mapx, 704+mapy, 1088, 32));
			map3Rects.add(new Rectangle(1232+mapx, 560+mapy, 16, 608));
			map3Rects.add(new Rectangle(1232+mapx, 560+mapy, 224, 16));
			map3Rects.add(new Rectangle(704+mapx, 1120+mapy, 32, 32));
			map3Rects.add(new Rectangle(736+mapx, 1136+mapy, 512, 16));
			map3Rects.add(new Rectangle(672+mapx, 800+mapy, 32, 32));
			map3Rects.add(new Rectangle(704+mapx, 816+mapy, 16, 320));
			map3Rects.add(new Rectangle(mapx, 784+mapy, 704, 16));

			//Trees
			map3Rects.add(new Rectangle(992+mapx, 736+mapy, 96, 128));
			map3Rects.add(new Rectangle(1024+mapx, 384+mapy, 96, 128));
			map3Rects.add(new Rectangle(1472+mapx, 864+mapy, 96, 128));

			//Rocks
			map3Rects.add(new Rectangle(1472+mapx, 1184+mapy, 32, 64));
			map3Rects.add(new Rectangle(1504+mapx, 1216+mapy, 32, 64));
			map3Rects.add(new Rectangle(1568+mapx, 1248+mapy, 32, 64));
			map3Rects.add(new Rectangle(1664+mapx, 1184+mapy, 32, 64));
			map3Rects.add(new Rectangle(1728+mapx, 1248+mapy, 32, 64));
			map3Rects.add(new Rectangle(1856+mapx, 1184+mapy, 32, 64));
			map3Rects.add(new Rectangle(1920+mapx, 1248+mapy, 32, 64));
			map3Rects.add(new Rectangle(1952+mapx, 1184+mapy, 32, 64));
		}

	}

	public boolean checkCollision(String dir, ArrayList<Rectangle> rectList) {
		boolean flag = false;
		for (Rectangle r : rectList) { //When user moves (map moves), rectangles have to move with the map
			if (dir == "l") //Moving the rectangles depending on direction that player wants to go
				r.translate(4,0);
			else if (dir == "r")
				r.translate(-4,0);
			else if (dir == "u")
				r.translate(0,4);
			else if (dir == "d")
				r.translate(0,-4);
			//Once the rectangles are moved, if the player is intersecting with where the rectangle's
			//new position is, then move the rectangles back (since the map doesn't move either)
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

	public void move() { //responsible for player moving
		if (screen == "moving") {
			requestFocus();
			if (keys[KeyEvent.VK_LEFT]) {
				lastDirection = "left";
				if (checkCollision("l",currentRects) == false) { //if player doesn't collide with a rect
					playerFrame += 0.2; //switching animations (slowly)
					if (playerFrame > 3.8) 
						playerFrame = 0;
					mapx += 4; //move the map (moving the character)
					if (currentMap.equals(map1) || currentMap.equals(map3)) //if the player is in an area
																			//where there are enemies
						steps++; //every 150 steps, user initiates a battle
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

				int wallDist = 2028+mapx; //The distance from the player and the right wall; used for properly placing the player in the next map

				//Switching the map image and rectangles

				mapx = -716+(wallDist); //Putting the player at the entrance in map 2
				mapy = -768; //The player spawns on the next map 64 units above the entrance back to map 2 to avoid infinitely looping in maps

				loadRects(2); //Loading (or re-loading) the collision rectangles in the next map
				currentRects = map2Rects;
				currentMap = map2;
			}

			//MAP 2 TO 1
			if (currentMap.equals(map2) && mapx <= -628 && mapx >= -716 && mapy == -832) {
				int wallDist = 716+mapx;
				mapx = -2028+wallDist;
				mapy = -64;
				loadRects(1);
				currentRects = map1Rects;
				currentMap = map1;

			}

			//MAP 2 TO 3
			if (currentMap.equals(map2) && mapx == -1692 && mapy <= -12 && mapy >= -140) {
				int wallDist = 140+mapy;
				mapx = -84;
				mapy = -460+wallDist;
				loadRects(3);
				currentRects = map3Rects;
				currentMap = map3;

			}

			//MAP 3 TO 2
			if (currentMap.equals(map3) && mapx == -20 && mapy <= -332 && mapy >= -460) {
				int wallDist = 460+mapy;
				mapx = -1628;
				mapy = -140+wallDist;
				loadRects(2);
				currentRects = map2Rects;
				currentMap = map2;

			}

			//Engaging in Battle
			if (steps >= 150) {
				if (currentMap.equals(map1)) {
					steps = 0; //resetting steps
					screen = "battle";
				}
				else if (currentMap.equals(map3)) {
					steps = 0;
					screen = "battle";
				}
			}

			//Accessing the Shop
			if (currentMap.equals(map2) && mapx == 96 && mapy == -268) {
/*				for (Rectangle r : map2Rects) {
					r.translate(0, -64);
				}*/
				screen = "shop";
			}

			//Engaging in Dialogue
			if (currentMap.equals(map2) && keys[KeyEvent.VK_ENTER] && keypress) {
				if ((mapy == -508 && mapx <= -1020 && mapx >= -1036) || (mapy == -412 && mapx <= -1020 && mapx >= -1036) ||
					 (mapx == -1064 && mapy >= -472 && mapy <= -448) || (mapx == -988 && mapy >= -472 && mapy <= -448)) { //villager
					npc = 1; //setting which NPC to talk to
					screen = "dialogue";
				}
				else if ((mapy == -272 && mapx >= -744 && mapx <= -732)) { //mayor
					npc = 2;
					screen = "dialogue";
				}
			}
			if (currentMap.equals(map3) && keys[KeyEvent.VK_ENTER] && keypress) { //talking with final boss
				if ((mapy == -1176 && mapx <= -1316 && mapx >= -1344) || (mapx == -1368 && mapy <= -1208 && mapy >= -1228) ||
						(mapx == -1292 && mapy <= -1208 && mapy <= -1228)) {
					npc = 3;
					
					if(gameFinish != true) { //boss battle will not activate if it has been beaten before
						bossBattle = true; 
					}
					screen = "dialogue";
				}
			}

			keypress = false; //Always resetting keypress so that when user presses key, it only
							  //iterates once.
		}
	}

	public void shopControls() { //keyboard controls for shop
		if (screen == "shop") {
			requestFocus();
			if (keys[KeyEvent.VK_UP] && keypress && itemSelect == false && shop) { //Navigating through shop
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
			else if (keys[KeyEvent.VK_LEFT] && keypress && itemSelect && shop) { //Confirming if player wants to buy
				if (choice == false)
					choice = true;
			}
			else if (keys[KeyEvent.VK_RIGHT] && keypress && itemSelect && shop) {
				if (choice)
					choice = false;
			}
			else if (keys[KeyEvent.VK_ENTER] && keypress) {
				if (shop) { //if the player is shopping (no intro screen)
					if (itemPos == 5) { //exiting shop
						mapx = 96;
						mapy = -332;
						screen = "moving";
						return;
					}
					else if (itemSelect) {
						if (notEnoughGold) { //the panel that tells the user if they can't buy the item
							notEnoughGold = false; //when they press enter, they can shop again
							itemSelect = false;
						}
						else if (choice) {
							if (bag.getGold() >= goldCosts[itemPos]) { //If the user has enough gold
								if (itemPos == 0) { //Adding items to player's bag
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
								bag.removeGold(goldCosts[itemPos]); //Taking gold out
								itemSelect = false;
							}
							else { //User doesn't have enough gold? Barricade them from buying item.
								notEnoughGold = true;
							}
						}
						else if (choice == false) { //Player says they don't want to buy item
							itemSelect = false;
						}
					}
					else if (itemSelect == false) { //Player selecting an item
						itemSelect = true;
					}
				}
				if (shop == false) { //if its the intro screen
					shop = true; //switch it to the shop
				}
			}
			keypress = false;
		}
	}
	
	//-------|Stuff Related To Displaying Battle|-------//
    public void displayRectangle(Rectangle r, Graphics g){ //takes rectangle paremeter and draws the rectangle according to the colour
		g.setColor(Color.white);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));

    }
    public void displayRectangleBL(Rectangle r, Graphics g){ //black version of displayRectangle
		g.setColor(Color.black);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));
    }

    public void displayRectangleR(Rectangle r, Graphics g){ //red version of displayRectangle
		g.setColor(Color.red);
		g.fillRect((int)(r.getX()),(int)(r.getY()),(int)(r.getWidth()),(int)(r.getHeight()));
    }

    public void endScreen(Graphics g){ //end screen when player health <= 0
    	g.setColor(Color.black);
    	g.fillRect(0,0,800,600);
    	g.setColor(Color.white);
    	g.setFont(new Font("TimesRoman",Font.PLAIN,64));
    	g.drawString("YOU LOSE",230,150); //blits the text you lose to the screen
    	this.add(gameOverBut);
    }

	public void displayHealth(Graphics g, Enemy goon){ //displays the health bars of both the enemy and player
		//Displaying enemy sprite
		if (goon.getType().equals("Slime")) {
			g.drawImage(slimePic, 515, 140, this);
		}
		else if (goon.getType().equals("Goblin")) {
			g.drawImage(goblinPic, 515, 140, this);
		}
		else if (goon.getType().equals("Boss")) {
			g.drawImage(bossPic, 515, 140, this);
		}

		//Displaying player sprite
		if (user.getCharNum() == 1) {
			g.drawImage(char1Pic, 155, 120, this);
		}
		else if (user.getCharNum() == 2) {
			g.drawImage(char2Pic, 155, 120, this);
		}
		else if (user.getCharNum() == 3) {
			g.drawImage(char3Pic, 155, 120, this);
		}
		
		g.setColor(Color.BLACK); //outside border of the health bars
		g.fillRect(155,275,110,40);
		g.fillRect(535,275,110,40);
		
		g.setColor(Color.GREEN); //display of the health they have remaining
		g.fillRect(160,280,100,30);
		g.fillRect(540,280,100,30);
		
		g.setColor(Color.BLACK); //display the damage that the user has taken using a rect that gets bigger from right to left
		g.fillRect(260-Math.round(100-(100*(float)user.getHp()/user.getMaxHp())),280,Math.round(100-(100*(float)user.getHp()/user.getMaxHp())),30);
		g.fillRect(640-Math.round(100-(100*(float)goon.getHealth()/goon.getMaxHealth())),280,Math.round(100-(100*(float)goon.getHealth()/goon.getMaxHealth())),30);
		g.setFont(new Font("Comic Sans ms",Font.BOLD,25)); //displays the hp of the player and enemy through text
		g.drawString(user.getName(), 125,75);
		g.drawString(goon.getType(), 520,75);
		String playerMaxHealth = Integer.toString(user.getMaxHp());
		String playerHealth = Integer.toString(user.getHp());
		String enemyMaxHealth = Integer.toString(goon.getMaxHealth());
		String enemyHealth = Integer.toString(goon.getHealth());
		g.drawString("HP: " + playerHealth+"/"+playerMaxHealth, 125,110);
		g.drawString("HP: " + enemyHealth+"/"+enemyMaxHealth, 520,110);
	}

    public void pipeAttack(Graphics g){
    	if(!displayed){ //ensure these varibles are declared once
    		rects.clear(); //clears the array just in case
    		pRect.setLocation(300, 410); //resets the location of the player
			for(int i = 600; i <= 1800; i+=60){ //1. starting pos for rects 2. amount of rects created 3. space between rects
				int leftY = rng.nextInt((int) baseRect.getHeight()); // randomly determine where the space of the pipe will be
				while (leftY < 30 || leftY > 120){ // bound of how low/high the pipe is, if its too small/tall, reset the value
					leftY = rng.nextInt((int) baseRect.getHeight());
				}
				Rectangle topRect = new Rectangle(i,(int) baseRect.getY(),10,leftY);
				Rectangle bottomRect = new Rectangle(i,(int) baseRect.getY()+50+leftY,10,((int) baseRect.getHeight()-50)-leftY); //declares the top and bottom rects with a space between them
				rects.add(topRect);
				rects.add(bottomRect);
			}
			displayed = true;
    	}
    	
    	for(int i = rects.size()-1; i>=0; i--){ //loops through the rects list and displays them

    		Rectangle r = rects.get(i);
    		if(r.getX() < (int) baseRect.getX() + (int) baseRect.getWidth()){ //if the x value of the rect is inside the base rectangle
    			displayRectangleBL(r,g);
    		}
    		r.translate(-2,0);
    		if(r.getX() < (int) baseRect.getX()){ //if its out of the rectangle, delete it
				rects.remove(r);
			}
    	}
    }

    public void goblinAttack(Graphics g){ // same concept as the pipe attack, except with tighter spaces and less space between pipes 
    	if(!displayed){ 
    		rects.clear();
    		pRect.setLocation(300,410);
			for(int i = 600; i <= 1800; i+=50){
				int leftY = rng.nextInt((int) baseRect.getHeight());
				while (leftY < 30 || leftY > 120){
					leftY = rng.nextInt((int) baseRect.getHeight());
				}

				Rectangle topRect = new Rectangle(i,(int) baseRect.getY(),10,leftY);
				Rectangle bottomRect = new Rectangle(i,(int) baseRect.getY()+30+leftY,10,((int) baseRect.getHeight()-30)-leftY);
				rects.add(topRect);
				rects.add(bottomRect);
			}
			displayed = true;
    	}

    	for(int i = rects.size()-1; i>=0; i--){

    		Rectangle r = rects.get(i);
    		if(r.getX() < (int) baseRect.getX() + (int) baseRect.getWidth()){
    			displayRectangleR(r,g);
    		}
    		r.translate(-2,0);
    		if(r.getX() < (int) baseRect.getX()){
				rects.remove(r);
			}
    	}
    }

   	public void laserAttack(Graphics g){
    	if(!displayed){ // making sure these variables are only declared once
    		pRect.setLocation(300, 410); //reset location of player
    		fire = rng.nextInt(100) + 300; //time before the the lasers fire
	    	int newX = rng.nextInt((int) baseRect.getWidth()); //randomly place the rects that fire the lasers
	    	int leftY = rng.nextInt((int) baseRect.getHeight()); 
	    	int rightY = rng.nextInt((int) baseRect.getHeight());
	    	while(Math.abs(leftY - rightY) < 70){ //if the rects arent at least 70 pikels apart
	    		rightY = rng.nextInt((int) baseRect.getHeight());
	    	}
	    	topRect = new Rectangle ((int) baseRect.getX() + newX, (int) baseRect.getY() - 60 ,30,30); // declare all the rects that are used in the attack
	    	attackRect = new Rectangle((int) topRect.getX() + 5,(int) topRect.getY() + (int) topRect.getHeight(),(int) topRect.getWidth() - 10,600);
	    	leftRect = new Rectangle ((int) baseRect.getX() - 60, (int) baseRect.getY() + leftY ,30,30);
	    	leftattackRect = new Rectangle((int) leftRect.getX() + (int) leftRect.getWidth(),(int) leftRect.getY() + 5,800,(int) leftRect.getHeight() - 10);
	    	rightRect = new Rectangle ((int) baseRect.getX() + (int) baseRect.getWidth() + 30, (int) baseRect.getY() + rightY ,30,30);
	    	rightattackRect = new Rectangle(0,(int) rightRect.getY() + 5,800 - (800 - (int) rightRect.getX()),(int) rightRect.getHeight() - 10);
	    	displayed = true;
    	}
		displayRectangle(topRect,g); //always display the moving rects
		displayRectangle(leftRect,g);
		displayRectangle(rightRect,g);
		
		if((int) attackRect.getX() + 10 > (int) baseRect.getX() + (int) baseRect.getWidth() - (int) attackRect.getWidth()){
			directionX = -2; //rate which its moving which changes direction depending on what part of the base rect it hit
		}
		
		else if((int) attackRect.getX() - 10 < (int) baseRect.getX()){
			directionX = 2;
		}
		
		if((int) leftattackRect.getY() + 10 > (int) baseRect.getY() + (int) baseRect.getHeight() - (int) leftattackRect.getHeight()){
			directionL = -2;
		}
		
		else if((int) leftattackRect.getY() - 10 < (int) baseRect.getY()){
			directionL = 2;
		}
		
		if((int) rightattackRect.getY() + 10 > (int) baseRect.getY() + (int) baseRect.getHeight() - (int) rightattackRect.getHeight()){
			directionR = -2; 
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
			 attackTimer >= fire + 1800 && attackTimer < fire +  1950)){ //if statement where checks when the actual laser should fire
			displayRectangleBL(attackRect,g);
			displayRectangleBL(leftattackRect,g);
			displayRectangleBL(rightattackRect,g);
			displaying = true;
		}

		else{ //while its not firing, moves the rects
			leftRect.translate(0,directionL);
			leftattackRect.translate(0,directionL);
			rightRect.translate(0,directionR);
			rightattackRect.translate(0,directionR);
			displaying = false;
		}

		topRect.translate(directionX,0); //keeps the top box moving when the attack commences
		attackRect.translate(directionX,0);
		attackTimer++;
    }

    public void options(Graphics g){
    	if(!displayed){ //resets the choice when its first declared
    		option = 0;
    		displayed = true;
    	}

    	for(int i = 50; i <= 550; i += 250){ //draws the option rects
			g.setColor(Color.black); //draws the border rect
			g.fillRect(i-15,445,200,80);
			g.setColor(Color.white);
			g.fillRect(i-5,453,180,64);
		}

		g.setColor(Color.black);
		g.drawImage(redRect,(50+option*250)-15,445,this); //draws selecter
		g.setFont(new Font("TimesRoman",Font.PLAIN,28)); //writes the words for the options and the pictures
		g.drawString("Attack", 50,485);
		g.drawString("Items",300,485);
		g.drawString("Run",550,485);
		g.drawImage(sword,150,465,this);
		g.drawImage(itemBag,400,465,this);
		g.drawImage(run,640,460,this);
    }

    public boolean collision(ArrayList<Rectangle> ar){ //checking if the player collides with anyone of the rects in the pipe/goblin attack
    	for (Rectangle newR: ar){
    		if(pRect.intersects(newR)){
    			if (pRect.getX() + pRect.getWidth() < newR.getX() + newR.getWidth() / 2) { //if it hits the rectangle on the left side
    				pRect.setLocation((int)newR.getX()-(int)pRect.getWidth(),(int)pRect.getY()); //reset location of the player to one piksel ago
    			}
    			else if (newR.getY() == 360 && pRect.getY() > newR.getY()) { //if it hits the rectangle on top
    				pRect.setLocation((int)pRect.getX(),(int)newR.getY() + (int)newR.getHeight());
    			}
    			else if (pRect.getY() < newR.getY()) { //if it hits the rectangle on bottom
    				pRect.setLocation((int)pRect.getX(),(int)newR.getY()-(int)pRect.getHeight());
    			}

    			return true;
    		}
    	}
    	return false;
    }
	public void inside(){ //ensures that the player cant go outside the base rectangle
		if((int)baseRect.getX() >= (int)pRect.getX()){ //checks all sides to keep him inside the rect
			pRect.setLocation((int)baseRect.getX(),(int)pRect.getY()); //if he goes out, reset loction to where it was one piksel ago
		}
		else if((int)pRect.getX() >= (int)baseRect.getX() + (int)baseRect.getWidth() - (int)pRect.getWidth()){
			pRect.setLocation((int)baseRect.getX() + (int)baseRect.getWidth() - (int)pRect.getWidth(),(int)pRect.getY());
		}
		if((int)baseRect.getY() >= (int)pRect.getY()){
			pRect.setLocation((int)pRect.getX(),(int)baseRect.getY());
		}
		else if((int)pRect.getY() >= (int)baseRect.getY() + (int)baseRect.getHeight() - (int)pRect.getHeight()){
			pRect.setLocation((int)pRect.getX(),(int)baseRect.getY() + (int)baseRect.getHeight() - (int)pRect.getHeight());
		}
	}

	public int attack(Graphics g){ //returns that damage that the player deals
		damage = 0;
    	g.drawImage(attackBackground,160,360,this); 
		if(linex > 625){ //changes direction of the slider
			reverse = true;
		}

		else if(linex < 161){
			reverse = false;
		}

		if(reverse && !stop){ //if the slider hasnt stopped yet
			linex -= 15;
		}

		else if(!reverse && !stop){
			linex += 15;
		}

		else if(stop){ //calculate damage relitive to where the slider is on the background
			damage = Math.round((float) (400 - Math.abs(400 - linex))/400 * user.getAtk());
			delayTimer++; //delay so the player can see where he landed on the background
		}

		g.drawImage(slider,linex,360,this);
		return damage + atkModifier; //return the damage + attack boost if you used the item
	}

	public void battleControls() { //moving the player left and right && up and down (in battle)
		requestFocus();
		if(keys[KeyEvent.VK_RIGHT] ){
			pRect.translate(2,0);
		}
		if(keys[KeyEvent.VK_LEFT] ){
			pRect.translate(-2,0);
		}
		if(keys[KeyEvent.VK_UP] ){
			pRect.translate(0,-2);
		}
		if(keys[KeyEvent.VK_DOWN] ){
			pRect.translate(0,2);
		}
	}

	public void displayPlayer(Graphics g) { //Switching player animations and displaying player
		requestFocus();
		if (screen == "moving") {
			if (keys[KeyEvent.VK_LEFT]) {
				g.drawImage(playerLeft[(int) Math.floor(playerFrame)], 382, 276, this);
				//playerFrame is constantly increasing (and resetting) as user moves
				//so it flips through frames in the image lists
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
				if (lastDirection.equals("down")) //lastDirection = direction that player faced last
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
		//System.out.println(mapx + ", " + mapy); //checking the max, mapy of game (experimental purposes)
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(currentMap, mapx, mapy, this);
		g.setColor(Color.RED);

		//Drawing NPCs
		if (currentMap.equals(map2)) {
			g.drawImage(npc1, 1408+mapx, 736+mapy, this);
			g.drawImage(npc2, 1118+mapx, 500+mapy, this);
		}
		if (currentMap.equals(map3)) {
			g.drawImage(npc3, 1712+mapx, 1500+mapy, this);
		}
		//Drawing collision rectangles (for experimental purposes only)
		
/*		for (Rectangle r : currentRects) {
			g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
		}*/
		
	}

	public void drawShop(Graphics g) { //Displaying graphics for shop
		if (shop == false) { //intro screen
			g.drawImage(shopBack, 0, 0, this);
			g.drawImage(longPanel, 15, 355, this);
			g.setFont(bigShopFont);
			g.drawString("Welcome to the shop!", 30, 400);
			g.drawString("Here, you can purchase potions to aid you in battles.", 30, 470);
		}
		if (shop == true) { //player navigating through items
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

			if (itemSelect == false) {
				g.drawString("What would you like to purchase?", 30, 400);
			}
			if (itemSelect) { //Once player has selected an item, the item description is shown
							  //and player is asked to confirm their purchase
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
			if (notEnoughGold) { //Player doesn't have enough gold
				g.drawImage(panel, 15, 355, this);
				g.drawString("Sorry, you don't have enough gold to buy", 30, 400);
				g.drawString("that.", 30, 430);
			}
			g.setColor(Color.RED);
			g.drawRect(495, boxy, 280, 40); //drawing the navigating box
			g.setColor(Color.YELLOW);
			g.drawString("Gold: "+bag.getGold(), 15, 340); //showing player's gold
		}
	}

	public void storyControls() { //commands for the story
		if (screen == "story") {
			requestFocus();
			if (keys[KeyEvent.VK_ENTER] && keypress) { //return the screen to moving
				screen = "moving";
			}
			keypress = false;
		}
	}

	public void storyDialogue(Graphics g) { //displays dialogue of the story (at the beginning)
		if (screen == "story") {
			g.drawImage(storyPanel, 0, 0, this);
			g.setFont(dialogueFont);
			g.setColor(Color.BLACK);
			g.drawString("Dear "+user.getName()+", ", 50, 100);
			g.drawString("The town is currently in a state of utter chaos.", 100, 150);
			g.drawString("The hooded men have plagued our peaceful town and", 50, 200);
			g.drawString("have already caused several disappearances. I'm afraid", 50, 250);
			g.drawString("there is nothing we can possibly do at this point in", 50, 300);
			g.drawString("time. Please help us!", 50, 350);
			g.drawString("- The Mayor", 50, 400);
			g.drawString("(Click Enter to Continue)", 50, 500);
		}
	}

	public void dialogueControls() { //keyboard controls for dialogue (just pressing ENTER)
		if (screen == "dialogue") {
			requestFocus();
			if (keys[KeyEvent.VK_ENTER] && keypress && firstPanel) { //switching to second panel
				firstPanel = false;
				secondPanel = true;
			}
			else if (keys[KeyEvent.VK_ENTER] && keypress && secondPanel) {
				firstPanel = true;
				secondPanel = false;
				if (bossBattle && gameFinish != true) { //if they were talking to the boss 
					screen = "battle"; //battling the boss (will not allow you to battle boss if player beaten him before)
				}
				else { //if they were talking to the friendly NPCs
					screen = "moving";
				}
			}
			keypress = false;
		}
	}

	public void drawDialogue(Graphics g) { //Graphics for dialogue
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

			}
			if (npc == 2) {
				g.drawImage(npc2head, 45, 400, this);
				if (firstPanel) {
					g.drawString("Mayor: Oh thank goodness you're here! All of", 170, 410);
					g.drawString("these disappearances are uncontrollable and", 170, 450);
					g.drawString("we've needed someone to finally stop them.", 170, 490);
				}
				if (secondPanel) {
					g.drawString("My wife told me she saw the hooded figures", 170, 410);
					g.drawString("leave through the east exit into the forest.", 170, 450);
					g.drawString("I'll be forever in your debt if you help us!", 170, 490);
				}
			}
			if (npc == 3) {
				g.drawImage(npc3head, 45, 400, this);
				if(gameFinish) { //different text if player has beaten final boss
					if (firstPanel) {
						g.drawString("Boss Griffin: ............", 170, 410);
					}
					if (secondPanel) {
						g.drawString("(A hollow shell remains...)", 170, 410);
					}
				}	
				else {
					if (firstPanel) {
					g.drawString("Boss Griffin: You eliminated quite a bit of", 170, 410);
					g.drawString("my goons I see. Whatever, you'll be no match", 170, 450);
					g.drawString("for me. My fellow goons have acquired enough", 170, 490);
					}
					if (secondPanel) {
					g.drawString("blood from all of those villagers to make me", 170, 410);
					g.drawString("the strongest human ever. Now face the", 170, 450);
					g.drawString("consequences of not minding your own business!", 170, 490);
					}
				}
			}
		}
	}

	public void initItems() { //This method is used to reset displayed items in inventory / items (battle)
		userItems.clear(); //userItems only gets one instance of an item that a player has, so that
						   //items are only blitted once
		itemNums.clear();  //itemNums are used for blitting how much of each item the player has

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
	public void inventoryControls() { //keyboard controls for inventory
		if (screen == "inventory") {
			requestFocus();
			if (keys[KeyEvent.VK_ENTER] && keypress) {
				if (invPos == userItems.size()) { //return to game
					screen = "moving";
					this.menuPause = true;
					return;
				}
				else if (invSelect == false){
					if (cantUse == false && (userItems.get(invPos) == "Iron Potion" || userItems.get(invPos) == "Wrath Potion")) {
						cantUse = true; //Players cant use Iron Potion / Wrath Potion outside of battle
					}
					else if (cantUse) { //removing "cant use item" panel
						cantUse = false;
					}
					else {
						invSelect = true; //user selects an item
					}

				}
				else if (invSelect) {
					if (invChoice == false) { //user decides they dont want to use the item
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
			else if (keys[KeyEvent.VK_UP] && keypress && cantUse == false) { //navigating through items
				if (invPos == 0) {
					invPos = userItems.size();
				}
				else {
					invPos--;
				}
			}
			else if (keys[KeyEvent.VK_DOWN] && keypress && cantUse == false) {
				if (invPos == userItems.size()) {
					invPos = 0;
				}
				else {
					invPos++;
				}
			}
			else if (keys[KeyEvent.VK_LEFT] && keypress && invSelect) { //confirming if user wants to use item
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

	public void drawInventory(Graphics g) { //Graphics for inventory
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

			else {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(shopExit, 380, 250, this);
			}

			if (invSelect == false) {
				if (cantUse) {
					g.drawString("It's not a wise idea to use that.", 30, 400);
				}
				else {
					g.drawString("Which item would you like to use?", 30, 400);
				}
			}
			else if (invSelect) {
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
			g.setColor(Color.BLACK);
			g.drawString("Gold: "+bag.getGold(), 15, 340);
		}
	}

	public void drawBattleInv(Graphics g) { //Graphics for items (in battle); very similar to Inventory
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
		if (bPos < userItems.size()) {
			if(userItems.get(bPos) == "Health Potion") {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(healthPot, 380, 250, this);
			}

			if(userItems.get(bPos) == "Large Health Potion") {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(largePot, 380, 250, this);
			}

			if(userItems.get(bPos) == "Gerthy Health Potion") {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(gerthPot, 380, 250, this);
			}

			if(userItems.get(bPos) == "Iron Potion") {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(ironPot, 380, 250, this);
			}

			if(userItems.get(bPos) == "Wrath Potion") {
				g.drawImage(scrollPanel3, 370, 245, this);
				g.drawImage(wrathPot, 380, 250, this);
			}
		}
		else {
			g.drawImage(scrollPanel3, 370, 245, this);
			g.drawImage(shopExit, 380, 250, this);
		}
		if (bSelect == false) {
			g.drawString("Which item would you like to use?", 30, 400);
		}
		else if (bSelect) {
			if (userItems.get(bPos) == "Health Potion") {
				g.drawString("A potion that restores 5 HP.", 30, 400);
			}
			else if (userItems.get(bPos) == "Large Health Potion") {
				g.drawString("A potion that restores 10 HP.", 30, 400);
			}
			else if (userItems.get(bPos) == "Gerthy Health Potion") {
				g.drawString("Made of the rarest herbs from across", 30, 400);
				g.drawString("all of Gerthland, this potion restores", 30, 420);
				g.drawString("your HP to full capacity.", 30, 440);
			}
			else if (userItems.get(bPos) == "Wrath Potion") {
				g.drawString("A potion that temporarily increases your", 30, 400);
				g.drawString("damage dealt to enemies.", 30, 420);
			}
			else if (userItems.get(bPos) == "Iron Potion") {
				g.drawString("A potion that temporarily decreases", 30, 400);
				g.drawString("damage dealt to you by enemies.", 30, 420);
			}
			g.drawString("Would you like to use this item?", 30, 475);
			g.drawString("YES", 150, 525);
			g.drawString("NO", 290, 525);
			if (bChoice)
				g.drawImage(arrow, 60, 498, this);
			if (bChoice == false)
				g.drawImage(arrow, 200, 498, this);
		}
		g.setColor(Color.RED);
		g.drawRect(495, (bPos*80)+45, 275, 50);
	}

	public void battleComponent(Graphics g, Enemy goon) {
		if (screen == "battle") { //if you are battling
			if (user.getAtkBoost()) { //if you have an attackboost
				atkModifier = 5;
			}
			if (user.getDefBoost()) {
				defModifier = 1;
			}
	    	g.drawImage(battleBack,0,0,this);

	    	//Enemy Battle Stuff
	 		if(battleScreen.equals("pipe attack") || battleScreen.equals("laser attack") || battleScreen.equals("goblin attack")){ //if you are on an enemy attack
		    	g.setColor(dgreen); 
		    	g.fillRect(135,347,510,196);
		    	g.drawImage(backRect,125,340,this);
		    	g.setColor(Color.white);
		    	g.fillRect(150,360,480,170);
				if(user.getHp()<= 0){ //if you die
					battleScreen = "end screen";
				}
				else{
					if(user.getHp() < user.getMaxHp()/2){ //if you are below half health, change sprite
						g.drawImage(damaged,(int)pRect.getX(),(int)pRect.getY(),this);
					}
					else{
						g.drawImage(consumer,(int)pRect.getX(),(int)pRect.getY(),this);
					}
					if(immune){ //if the player gets hit and becomes immune
						g.drawImage(immune1,(int)pRect.getX(),(int)pRect.getY(),this);
						timer++;
					}
	 			}
	 			if(timer > 100){ //after 1 sec of being immune, you are no longer immune
					immune = false;
				}
			}
			inside(); //calls the method to keep the player inside
			displayHealth(g, goon); //draws the health bars

			if (battleScreen.equals("end screen")) { //if you lost
				endScreen(g);
			}

			else if (battleScreen.equals("options")) { //if you finished an attack or started a battle, you can choose what move to do
				options(g);
			}
			else if(battleScreen.equals("player attack")){ //if its your attack
				if (delayTimer == 0) { //deal damage for only one tick
					goon.damage(attack(g));
				}

				else { //display the attack, but dont deal damage
					attack(g);
				}

				if (goon.getHealth() <= 0) { //When enemy dies
					goon.resetHealth();
					
					if(goon.getType() == "Boss") {
						gameFinish = true; //if the boss is defeated, it will set this boolean to true
					}

					bag.addGold(15); //add gold and exp
					user.addExp(20);

					//resetting battle variables
					reverse = false;
					stop = false;
					delayTimer = 0;
					linex = 170;
					battleScreen = "options";
					
					//resetting boosts
					user.setAtkBoost(false);
					user.setDefBoost(false);
					atkModifier = 0;
					defModifier = 0;
					//Calculating if player leveled up or not
					int prevLvl = user.getLvl();
					user.setLvl();
					if (user.getLvl() != prevLvl) { //If the player levels up
						user.hpUp();
					}
					screen = "moving"; //bringing back the screen to where you were 
				}

				if(delayTimer > 50){ //after the delay timer is done
					if(goon.getType().equals("Slime")){ //attacks for each enemy
						battleScreen = "pipe attack";
					}
					else if(goon.getType().equals("Goblin")){
						battleScreen = "goblin attack";
					}

					else if(goon.getType().equals("Boss")){
						battleScreen = "laser attack";
					}

					reverse = false; //reset variables
					stop = false;
					delayTimer = 0;
					linex = 170;
				}
			}

			else if(battleScreen.equals("items")){ //if itmes are chosen
				initItems();
				drawBattleInv(g);
			}

			else if(battleScreen.equals("run")){ //if they try to run away
				if(goon.run()){
					goon.resetHealth();
					battleScreen = "options";
					user.setAtkBoost(false);
					user.setDefBoost(false);
					atkModifier = 0;
					defModifier = 0;
					screen = "moving";
				}

				else{
					if(goon.getType().equals("Slime")){
						battleScreen = "pipe attack";
					}
					else if(goon.getType().equals("Goblin")){
						battleScreen = "goblin attack";
					}
					else if(goon.getType().equals("Boss")){
						battleScreen = "laser attack";
					}
				}
			}

			else if(battleScreen.equals("pipe attack")){ //draws the pipes and checks if the player hits the rects
				pipeAttack(g);
				if(collision(rects) && !immune){
					user.setHp(2-defModifier);
					immune = true;
					timer = 0;
				}

				if(rects.size() == 0){ //when the attack is done
					battleScreen = "options";
					displayed = false;
				}
			}

			else if(battleScreen.equals("goblin attack")){ //same code ^ (more damage)
				goblinAttack(g);

				if(collision(rects) && !immune){
					user.setHp(4-defModifier);;
					immune = true;
					timer = 0;
				}

				if(rects.size() == 0){
					battleScreen = "options";
					displayed = false;
				}
			}

			else if(battleScreen.equals("laser attack")){ //declares the attack and checking if the player hits the attack rects to deal damage
				laserAttack(g);
				if((pRect.intersects(attackRect) && !immune && displaying) ||
					(pRect.intersects(leftattackRect) && !immune && displaying) ||
					(pRect.intersects(rightattackRect) && !immune && displaying)){

					user.setHp(4-defModifier);
					immune = true;
					timer = 0;
				}

				if (attackTimer > 2000) { //if the attack is done
					battleScreen = "options";
					displayed = false;
				}
			}
		}

	}

	public boolean getBack() { //gets the back boolean that checks if user is returning to main menu
		return back;
	}

	public void setBack() { //sets the back boolean to false
		back = false;
	}

	public boolean getSongOn() { //gets the boolean that checks if the music is muted or not
		return this.songOn;
	}

	public void toggleMenu() { //toggles if the in game menu shows or not (depending on key pressed)
		if (keys[KeyEvent.VK_C] && screen != "battle" && screen != "inventory" && screen != "dialogue" && screen != "story") {
			this.menuPause = true;
		}

		if (keys[KeyEvent.VK_X]) { //removes menu when toggled off
			removeMenu();
			this.menuPause = false;
		}
	}

	public boolean getMenuPause() { //accessor method to check if game is paused (in-game menu on)
		return menuPause;
	}

	public void displayMenu(Graphics g) { //dislays contents of the in-game menu
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
		this.add(menuTitle);
		this.add(menuLabel1);
		this.add(menuLabel2);
		this.add(menuLabel3);
		this.add(menuLabel4);
	}

	public void removeMenu() { //removes contents of the in-game menu
		this.remove(menuBut);
		this.remove(bagBut);
		this.remove(saveBut);
		this.remove(profBut);
		this.remove(songBut);
		this.remove(menuTitle);
		this.remove(menuLabel1);
		this.remove(menuLabel2);
		this.remove(menuLabel3);
		this.remove(menuLabel4);
	}

	public void displaySaveScreen(Graphics g) { //displays the save screen in in-game menu
		g.setColor(new Color(255,243,199));
		g.fillRect(0,100,800,225);
		g.setColor(Color.BLACK);
		g.drawRect(1,100,799,224);

		this.add(saveLabel);
        this.add(saveYesBut);
        this.add(saveNoBut);
	}

	public void displayProfile(Graphics g) { //displays the profile in in-game menu
		g.setColor(new Color(255,243,199));
		g.fillRect(0,100,800,225);
		g.setColor(Color.BLACK);
		g.drawRect(1,100,799,224);
		g.drawRect(49,133,152,152);

		this.add(profCloseBut);

		if(this.character == 1) { //displays the specific character portrait
			this.add(charProf1);
		}

		else if(this.character == 2) {
			this.add(charProf2);
		}

		else if(this.character == 3) {
			this.add(charProf3);
		}

		//Display stats of the current character
		g.setFont(shopFont);
		g.drawString(user.getName(), 225, 160);
		g.drawString("LVL: " + user.getLvl(), 225, 185);
		g.drawString("HP: " + user.getHp() + "/" + user.getMaxHp(), 225, 210);
		g.drawString("ATK: " + user.getAtk(), 225, 235);
		g.drawString("EXP: " + user.getExp(), 225, 260);
		g.drawString("Exp Required: " + (int)Math.pow(user.getLvl()/0.1,2), 400, 260);
		g.drawString("Exp Left Till Lvl Up: " + ((int)Math.pow(user.getLvl()/0.1,2) - user.getExp()), 400, 285);
		g.drawString("Gold: " + bag.getGold(), 225, 285);
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if(source == menuBut && saveScreen == false && profScreen == false) { //if the save screen or profile is displayed, other buttons cannot be pressed
			int paneResult = JOptionPane.showConfirmDialog(null,"Any unsaved progress will be lost!","Return to menu?",JOptionPane.YES_NO_OPTION);
			if (paneResult == JOptionPane.YES_OPTION) { //opens a warning msg toward the user to make they have saved before returning to menu
				this.gameTheme.stop();
          		this.back = true;
			}

          	else {}
		}

		else if(source == bagBut && saveScreen == false && profScreen == false) { //when the bag button is pressed when the other options aren't opened
			removeMenu();
			menuPause = false;
			initItems();
			screen = "inventory"; //it will set the screen to inventory
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
				ArrayList<String> recordStats = new ArrayList<String>(); //will record stats if player selects "yes"
				
				//Recording stats of the player and writing them into a txt file
				recordStats.add(user.getName());
				recordStats.add(Integer.toString(user.getLvl()));
				recordStats.add(Integer.toString(user.getExp()));
				recordStats.add(Integer.toString(user.getHp()));
				recordStats.add(Integer.toString(user.getMaxHp()));
				recordStats.add(Integer.toString(user.getAtk()));
				recordStats.add(Integer.toString(user.getCharNum()));
				recordStats.add(Integer.toString(mapx));
				recordStats.add(Integer.toString(mapy));

				if(currentMap.equals(map1)) { //records the current map the user is in
					recordStats.add("map1");
				}

				if(currentMap.equals(map2)) {
					recordStats.add("map2");
				}

				if(currentMap.equals(map3)) {
					recordStats.add("map3");
				}

				recordStats.add(Integer.toString(bag.getPot()));
				recordStats.add(Integer.toString(bag.getLargePot()));
				recordStats.add(Integer.toString(bag.getGerthPot()));
				recordStats.add(Integer.toString(bag.getIronPot()));
				recordStats.add(Integer.toString(bag.getWrathPot()));
				recordStats.add(Integer.toString(bag.getGold()));
				recordStats.add(String.valueOf(gameFinish));

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
                JOptionPane.showMessageDialog(this, "Game has been saved"); //displays a quick message if successful

	            }

	            catch(IOException e){
	                System.out.println("Game cannot be saved during this time");
	            }
			}

			else{}
		}

		else if(source == saveNoBut) { //closes save screen
			this.saveScreen = false;
			this.remove(saveYesBut);
			this.remove(saveNoBut);
			this.remove(saveLabel);
		}

		else if(source == profCloseBut) { //closes the profile
			this.profScreen = false;
			this.remove(profCloseBut);

			if(this.character == 1) { //removes the correct char portrait
				this.remove(charProf1);
			}

			else if(this.character == 2) {
				this.remove(charProf2);
			}

			else if(this.character == 3) {
				this.remove(charProf3);
			}
		}

		else if(source == this.songBut && saveScreen == false && profScreen == false){ //if other options are not displayed, user is allowed to mute/unmute song
            this.gameTheme.playPause();
            //Displays a certain icon depending on if the music is playing
            if(gameTheme.getIsPlaying()) {
                this.songBut.setIcon(songIcon1);
                this.songOn = true; //sets the song boolean if it is playing or not
            }
            else {
                this.songBut.setIcon(songIcon2);
                this.songOn = false;
            }
        }
        
        else if(source == this.gameOverBut) { //button returns the user to the main menu after losing
        	this.gameTheme.stop(); //stops game music so it doesnt overlap with menu music
        	this.back = true;
        }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Switching between what 'mode' the game is in
		if (screen == "moving") { //Player moving
			drawMap(g);
			displayPlayer(g);
		}

		else if (screen == "story") { //Story at the beginning
			storyDialogue(g);
		}

		else if (screen == "dialogue") { //Player is talking to NPC
			drawMap(g);
			displayPlayer(g);
			drawDialogue(g);
		}

		else if (screen == "shop") { //Player is shopping
			drawShop(g);
		}

		else if (screen == "inventory") { //Player inventory
			drawInventory(g);
		}
		else if (screen == "battle") { //Player battling
			if (currentMap.equals(map1)) {
				battleComponent(g, slime);
			}

			if (currentMap.equals(map3)) {
				if (bossBattle) {
					battleComponent(g, boss);
				}
				else {
					battleComponent(g, goblin);
				}
			}
		}

		if(this.menuPause) { //in game menu
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