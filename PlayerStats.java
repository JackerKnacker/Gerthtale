//PlayerStats.java
//Jakir Ansari/ Alex Shi/ Jason Wong
//PlayerStats.java is used to keep track of the player and their stats in the game

class PlayerStats { //class used to keep track of a player's stats
	private int hp, maxHp, atk, lvl, exp, charNum;
	private String name;
	private boolean atkBoost, defBoost;

	public PlayerStats(int hp, int maxHp, int atk, int lvl, int exp, String name, int charNum) { //Constructor class
		this.maxHp = maxHp;
		this.hp = hp;
		this.atk = atk;
		this.lvl = lvl;
		this.exp = exp;
		this.name = name;
		this.charNum = charNum;
		this.atkBoost = false;
		this.defBoost = false;
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

	public boolean getAtkBoost() {
		return atkBoost;
	}

	public boolean getDefBoost() {
		return defBoost;
	}

	//Set the hp after taking dmg
	public void setHp(int dmg) {
		hp = hp - dmg;
	}

	public void addExp(int xp) {
		exp += xp;
	}

	//Set the atk boost if wrath potion is used
	public void setAtkBoost(boolean setter) {
		atkBoost = setter;
	}

	//sets the def boost if iron potion is used
	public void setDefBoost(boolean setter) {
		defBoost = setter;
	}

	//Using an item in inventory
	public void useItem(String item) {
		if (item == "Health Potion") { //heals the user 5 HP or to the max
			hp += 5;
			if (hp > maxHp) {
				hp = maxHp;
			}
		}
		if (item == "Large Health Potion") { //heals the user 10 HP or to the max
			hp += 10;
			if (hp > maxHp) {
				hp = maxHp;
			}
		}
		if (item == "Gerthy Health Potion") { //heals the user to max
			hp = maxHp;
		}

		if (item == "Wrath Potion") { //sets the atk boost
			atkBoost = true;
		}

		if (item == "Iron Potion") { //sets the def boost
			defBoost = true;
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

	public static void main(String[] args) {}
}