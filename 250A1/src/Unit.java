
public abstract class Unit {

	private Tile POS;
	private double HP;
	private int Range;
	private String Faction;


	public Unit(Tile POS, double HP, int Range, String Faction) {
		this.POS = POS;
		this.HP = HP;
		this.Range = Range;
		this.Faction = Faction;
		if(this.POS.addUnit(this))
		{	
		
		}else {throw new IndexOutOfBoundsException("IllegalArgumentException");}
	}



	public final Tile getPosition() {
		return this.POS;
	}



	public final double getHP() {
		return this.HP;
	}



	public final int getRange() {
		return this.Range;
	}



	public final String getFaction() {
		return this.Faction;	
	}



	public boolean moveTo(Tile Position) {
		

		if (this.Range+1>Position.getDistance(this.POS,Position)) {

			if(Position.addUnit(this)) {
				
				this.getPosition().removeUnit(this);
				this.POS = Position;
				return true;
			}	
		}
		return false;
	}



	public void receiveDamage(double damage) {
		if(this.POS.isCity()) {
			this.HP= this.HP-damage*0.9;
		}else {
			this.HP= this.HP-damage;
		}
		if(this.getHP()<=0) this.POS.removeUnit(this);
	}
	
	
	public abstract void takeAction(Tile Position);
	
	
	
	public boolean equals(Object obj) {
		if(obj instanceof Unit) {
			if((this.getPosition().equals(((Unit)obj).getPosition())
					&& (this.getHP()==((Unit)obj).getHP()))&&(this.getFaction().equals(((Unit)obj).getFaction()))) {	
				return true;	
			}
		}
		return false;
	} 

	
	
	
	
	
}


	

