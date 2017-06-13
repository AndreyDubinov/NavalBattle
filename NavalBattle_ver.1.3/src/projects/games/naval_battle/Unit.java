package projects.games.naval_battle;

import java.util.ArrayList;

/**
 * This is class to create ships in the fleet. There are 4 different type of the
 * ship, each type based on the attribute hitsToSunk. This attribute connect with attribute locationCells.
 * Ship with 3 hitsToSunk need 3 location cells on the map.
 * 
 * @author Andrew
 *
 */

public class Unit {	
	private String name;
	private int hitsToSunk;
	private Type typeOfShip;
	private ArrayList<Integer> locationCells;

	public Unit(String name, int hits) {
		this.name = name;
		hitsToSunk = hits;
		typeOfShip = Type.getType(hits);
		locationCells = new ArrayList<Integer>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHitsToSunk() {
		return hitsToSunk;
	}

	public void setHitsToSunk(int hitsToSunk) {
		this.hitsToSunk = hitsToSunk;
	}

	public Type getTypeOfShip() {
		return typeOfShip;
	}

	public void setTypeOfShip(Type typeOfShip) {
		this.typeOfShip = typeOfShip;
	}

	public ArrayList<Integer> getLocationCells() {
		return locationCells;
	}

	public void setLocationCells(ArrayList<Integer> locationCells) {
		this.locationCells = locationCells;
	}

	@Override
	public String toString() {
		return "Unite [name= " + name + ", hitsToSunk= " + hitsToSunk
				+ ", typeOfShip= " + typeOfShip + ", locationCells= "
				+ locationCells + "]";
	}

}
