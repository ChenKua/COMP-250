
public class Worker extends Unit{

	private int NumOfJobs;


	public Worker(Tile POS, double HP, String Faction) {
		super(POS, HP, 2, Faction);
		this.NumOfJobs=0;
	}
	
	
	public void takeAction(Tile input) {
		if((this.getPosition().equals(input))&&(input.isImproved()==false)) {
			input.buildImprovement();
			this.NumOfJobs++;
		}
		if (this.NumOfJobs==10) input.removeUnit(this);	
	
	}
	
	
	
	public boolean equals(Object obj) {
		if((obj instanceof Worker)){
			if(this.NumOfJobs==((Worker)obj).NumOfJobs) {
			return (super.equals(obj));
			}
		}
		return false;
	}
	
	
	
	
	
	
}
