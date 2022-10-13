
public class Archer extends MilitaryUnit {
	
	private int arrows;
	
	public Archer (Tile POS, double HP,  String Faction) {
		super(POS, HP, 2, Faction, 15.0, 2, 0);
		this.arrows = 5;
	}	
	
	
	
	public void takeAction(Tile input) {
			if(arrows!=0) {
					super.takeAction(input);
				arrows--;
			}else {
				arrows = 5;
			}			
	}
		
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Archer) {
			if(this.arrows==((Archer)obj).arrows) {
			return super.equals(obj);
			}
		}
		return false;
	}
}
