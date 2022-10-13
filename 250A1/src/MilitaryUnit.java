
public abstract class MilitaryUnit extends Unit{

	private double AttackDamage;
	private int AttackRange;
	private int Armor;



	public MilitaryUnit(Tile POS, double HP, int Range, String Faction, double AtkDamage, int AtkRange, int ArmoR) {
		super(POS, HP, Range, Faction);
		this.AttackDamage = AtkDamage;
		this.AttackRange = AtkRange;
		this.Armor = ArmoR;
	}



	public void takeAction(Tile input) {
		if(input.getDistance(input, this.getPosition())<this.AttackRange+1) {
			if(input.selectWeakEnemy(this.getFaction()) !=null){
				if(this.getPosition().isImproved()) {
					input.selectWeakEnemy(this.getFaction()).receiveDamage(this.AttackDamage*1.05);	
				}	
				else {
					input.selectWeakEnemy(this.getFaction()).receiveDamage(this.AttackDamage);	
				}
			}			
		}	
	}



	public void receiveDamage(double input) {
		if(this instanceof MilitaryUnit) {
			input = input*100.0/(100.0+this.Armor);
		}

		super.receiveDamage(input);
	}







}
