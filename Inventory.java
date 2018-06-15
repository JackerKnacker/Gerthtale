//Inventory.java
//Jakir Ansari/ Alex Shi/ Jason Wong
//Inventory.java is used to keep track of the items the user has throughout the game

public class Inventory {
	private int pot, largePot, gerthPot, ironPot, wrathPot, gold;

	public Inventory(int pot, int largePot, int gerthPot, int ironPot, int wrathPot, int gold) { //Constructor method
		this.pot = pot;
		this.largePot = largePot;
		this.gerthPot = gerthPot;
		this.ironPot = ironPot;
		this.wrathPot = wrathPot;
		this.gold = gold;
	}

	public void addItem(String item) { //when a user buys an item from the shop, it will be added to here
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

	public void addGold(int amount) { //when a battle is won, gold will be added to here
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
	
	public static void main(String[] args) {}
}
