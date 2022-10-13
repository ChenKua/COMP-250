
public class ListOfUnits {

	private Unit[] units;
	private int SIZE;


	public ListOfUnits(){
		this.units = new Unit[10]; 
		this.SIZE = 0;
	}

	public int size() {
		/*int i =0;
		while(true) {
			if(this.units[i] != null) {
				i++;

			}else {
				break;
			}			
		}
		this.SIZE=i+1;*/

		return this.SIZE;
		
	}

	public Unit[] getUnits() {
		Unit[] arr = new Unit[SIZE];
		for(int i =0; i<SIZE;i++) {
			arr[i]=this.units[i];
		}
		return arr;
	}



	public Unit get(int a) {
		if(a>=0 && a<SIZE) {
			return this.units[a];

		}else {
			throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
		}
	}


	public void add(Unit newunits) {

		if(this.SIZE >= this.units.length) {
			Unit[] tmp = new Unit[this.units.length+this.units.length/2+1];
			for(int i=0; i<this.units.length;i++) {
				tmp[i]=units[i]; 	
			}

			this.units = tmp;
			this.units[SIZE] = newunits;
			
			SIZE++; 
		}else {
			this.units[this.SIZE]=newunits;
			SIZE++;
		}
	}



	public int indexOf(Unit inputUnit) {
		int i;
		for(i=0;i<this.SIZE;i++) {
			if(this.units[i].equals(inputUnit)) {
				return i;
			}
		}
		return -1;
	} 



	//could input be null?
	public boolean remove(Unit newinput) {

		boolean exist = false;
		for(int i=0; i<this.SIZE;i++)
		{
			if(this.units[i].equals(newinput)) 
			{
				exist = true;

				for(int j =i; j<this.units.length-1;j++)
				{
					this.units[j]=this.units[j+1];
				}
				SIZE--;
				break;
			}		
		}
		return exist;
	}



	public	MilitaryUnit[] getArmy() {
//		int NumOfArmy = 0;
//		int s = 0;
//
//		for(int i =0; i<this.units.length;i++) {
//			if(this.units[i] instanceof MilitaryUnit) {
//				NumOfArmy++;
//			}	
//		}
//
//		MilitaryUnit[] Army = new MilitaryUnit[NumOfArmy];
//		for(int i =0; i<this.units.length;i++) {
//			if(this.units[i] instanceof MilitaryUnit) {
//				Army[s]=(MilitaryUnit)units[i];
//				s++;
//			}
//		}
//		return Army;
				MilitaryUnit[] tmparr = new MilitaryUnit[this.units.length];
				int NumOfMilitary = 0;
				for(int i =0; i<this.units.length;i++) {
					if(this.units[i] instanceof MilitaryUnit) {	
						tmparr[NumOfMilitary]= (MilitaryUnit) this.units[i];
						NumOfMilitary++;
					}	
				}
				// to keep the size consistency
				MilitaryUnit[] arr = new MilitaryUnit[NumOfMilitary];
				for(int a =0; a<NumOfMilitary;a++) {
					arr[a] = tmparr[a];
				}
				return arr;
	}









}	

