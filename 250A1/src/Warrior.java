
public class Warrior extends MilitaryUnit{

	
	public Warrior(Tile POS, double HP, String Faction) {
		super(POS,HP,1, Faction, 20.0, 1, 25);
	}

	
	
	public boolean equals(Object obj) {
		if(obj instanceof Warrior) {
		return super.equals(obj);
		}
		return false;
	}	
		
	
	
	
	
	
	
	
	
	
	
	
	
}
