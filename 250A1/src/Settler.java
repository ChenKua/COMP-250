
public class Settler extends Unit {

	public Settler(Tile POS, double HP, String Faction) {
		super(POS, HP,2, Faction);
		}


	
	public void takeAction(Tile input) {
		if((input.isCity()==false)&&(this.getPosition().equals(input))) {
			input.foundCity();
			input.removeUnit(this);
		}
	}

	
	
	public boolean equals(Object obj) {
		if(obj instanceof Settler) {
			return super.equals(obj);
		}
		return false;
		}
	
	
	
	
	


}
