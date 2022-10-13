
public class Tile {

	private int Xco;
	private int Yco;
	private boolean CityOn;
	private boolean Improvements;
	private ListOfUnits UnitsInTile = new ListOfUnits();



	public Tile(int Xco, int Yco) {
		this.Xco = Xco;
		this.Yco = Yco;
		this.CityOn = false;
		this.Improvements = false;	

	}	

	public int getX() {
		return this.Xco;
	}



	public int getY() {
		return this.Yco;		
	}



	public boolean isCity() {
		return this.CityOn;

	}



	public boolean isImproved() {
		return this.Improvements;

	}




	public void foundCity() {	
		if(this. CityOn== false) {
			this.CityOn = true;
		}
	}



	public void buildImprovement() {
		if(this.Improvements == false) {
			this.Improvements = true;
		}

	}



	public boolean addUnit(Unit inputunit) {

		if(inputunit instanceof MilitaryUnit) {
			if(UnitsInTile.getArmy().length==0) {
				UnitsInTile.add(inputunit);
				return true;
			}else {
				for(int i=0; i<this.UnitsInTile.getArmy().length; i++) {
					if(UnitsInTile.getArmy()[0].getFaction().equals(inputunit.getFaction())==false) {
						return false;
					}else {
						UnitsInTile.add(inputunit);
						return true;
					}
				}
			}			
		}else {
			UnitsInTile.add(inputunit);
			return true;	
		}
		return false;

	}



	public boolean removeUnit(Unit inputunit) {
		boolean remove = false;
		if(inputunit ==null ) return remove;

		if(this.UnitsInTile.remove(inputunit)) {
			remove = true;
		}
		return remove;
	}

	

	
	public Unit selectWeakEnemy(String faction) {
		Unit tmp=null;
		int b=0;
		
		
		if(this.UnitsInTile.size()==0) return null;
		
		for(int i =0; i<this.UnitsInTile.size();i++) {
			if(!(faction.equals(UnitsInTile.get(i).getFaction()))){ 
				b++;
			}
			
		}
		if(b==0) {
			return null;	
		}
		
		
		
		int a =0;
		Unit[] arm = new Unit[b];
		for(int i =0; i<this.UnitsInTile.size();i++) {
			if(!(faction.equals(UnitsInTile.get(i).getFaction()))) {
			arm[a]=this.UnitsInTile.get(i);
			a++;
			}
		}
		
		Unit low = arm[0];
		for(int i =0; i<arm.length;i++) {
			if(arm[i].getHP()<low.getHP()) {
				low=arm[i];
			}
			
		}
		
		return low;
	}
		
		
		
//	
		
//	public Unit selectWeakEnemy(String faction) {
//		Unit tmp=null;
//		
//		if(this.UnitsInTile.size()==0) return null;
//		
//		for(int i =0; i<this.UnitsInTile.size();i++) {
//			if(!(faction.equals(UnitsInTile.get(i).getFaction()))) {
//				tmp = UnitsInTile.get(i);	
//				
//				if(tmp.getHP()>UnitsInTile.get(i).getHP()) {
//					tmp = UnitsInTile.get(i);
//				}
//			}
//		}
//		return tmp;
//	}



	public static double getDistance(Tile one, Tile two) {
		double dis = Math.sqrt((one.getX()-two.getX())*(one.getX()-two.getX())+(one.getY()-two.getY())*(one.getY()-two.getY()));
		return dis;
	}

//	private Unit lowestHp(Unit[] lowest) {
//		Unit low= lowest[0];
//		for(int i =0; i<lowest.length;i++) {
//			if(lowest[i].getHP()<low.getHP()) {
//				low=lowest[i];
//			}
//			
//		}
//		return low;
//	}





}
