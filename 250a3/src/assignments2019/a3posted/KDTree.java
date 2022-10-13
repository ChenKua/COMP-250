package assignments2019.a3posted;
import java.util.ArrayList;
import java.util.Iterator;


public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k;   
	int			numLeaves;

	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}

		//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}


	//   KDTree methods

	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}


	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}

	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}






	class KDNode { 

		boolean leaf;				//if this is a leaf node
		Datum leafDatum;           //  only stores Datum if this is a leaf

		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  

		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		//  You may think of them as "left" and "right" instead of "low" and "high", respectively

		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE			

			if(datalist.length==0)	throw new Exception("Empty datalist");
			
			
			
			if( datalist.length == 1) {
				leaf = true;
				lowChild = null;
				highChild = null;
				this.leafDatum = datalist[0];
				numLeaves++;
			}else {
				
				leaf=false;
				
				int MaxRange = 0;
				int Max=0;
				int Min=0;
				int realmax=0;
				int realmin=0;

				// is k a valid answer?		
				//why we go up tp k?  k is the length, should we go to k-1?
				for(int i=0; i<k; i++){
					Max =datalist[0].x[i]; 
					Min =datalist[0].x[i]; 
					for(int j=0;j<datalist.length;j++) {
						

						if (datalist[j].x[i]> Max)  Max = datalist[j].x[i];
						if (datalist[j].x[i]< Min)  Min = datalist[j].x[i];							
					}

					int range = Max - Min;			
					if(range>MaxRange) {
						MaxRange = range;
						splitDim = i;
						realmax = Max;
						realmin = Min;	
					}
					
				}
//dupliute
				if(MaxRange==0) {
					leaf = true;
					lowChild = null;
					highChild = null;
					this.leafDatum = datalist[0];
					numLeaves++;
					return;
				}else {
					splitValue = (int)Math.floor((MaxRange)/2)+realmin;
				}
					//splitvalue =	(int)Math.floor((realmin+realmax)/2)  will not work
				
			/*	
				if(MaxRange == 1 && realmax<0) {
					splitValue = realmax--;
					
				}else {
					
				//do somthing to prevent stackoverflow
					splitValue = (realmax+realmin)/2;
				}
				*/
				
				//arraylist for datum
				ArrayList<Datum>  lownode = new ArrayList<Datum>();
				ArrayList<Datum>  highnode = new ArrayList<Datum>();

				for(int a=0;a<datalist.length;a++) {

					if(datalist[a].x[splitDim] <= splitValue) {
						lownode.add(datalist[a]);
					}else {
						highnode.add(datalist[a]);
					}
				}
				
				Datum[] Lnode = new Datum[ lownode.size() ];
				Datum[] Hnode = new Datum[ highnode.size() ];

				for(int b=0; b<lownode.size();b++) {
					Lnode[b] = lownode.get(b);	

				}

				for(int c=0; c<highnode.size();c++) {
					Hnode[c] = highnode.get(c);	

				}
				
					this.lowChild = new KDNode(Lnode);
					this.highChild = new KDNode(Hnode);
				
			}
			
		





		//   ADD YOUR CODE ABOVE HERE

	}






	public Datum nearestPointInNode(Datum queryPoint) {
		Datum nearestPoint, nearestPoint_otherSide ;
		
		//   ADD YOUR CODE BELOW HERE
		
		if (this.leaf) {
			return this.leafDatum;
		}else {
			
			if(queryPoint.x[this.splitDim] <= this.splitValue) {
				nearestPoint = this.lowChild.nearestPointInNode(queryPoint);
				
				if ( Math.pow((queryPoint.x[this.splitDim] - this.splitValue), 2) < distSquared(queryPoint, nearestPoint)  ) {
					nearestPoint_otherSide = this.highChild.nearestPointInNode(queryPoint);
					
					if( distSquared(queryPoint,nearestPoint) > distSquared(queryPoint,nearestPoint_otherSide)) {
						nearestPoint = nearestPoint_otherSide;
					}
				}
				
			}else {
				nearestPoint = this.highChild.nearestPointInNode(queryPoint);
				
				if (Math.pow((queryPoint.x[this.splitDim] - this.splitValue), 2) < distSquared(queryPoint, nearestPoint ) ) {
					nearestPoint_otherSide = this.lowChild.nearestPointInNode(queryPoint);
					
					if(distSquared(queryPoint,nearestPoint) > distSquared(queryPoint,nearestPoint_otherSide)) {
						nearestPoint = nearestPoint_otherSide;
					}
				}
			}
		}
		return nearestPoint;
		
		/*
		
		if(this.leaf==true) {
			return this.leafDatum;
		}else {
			if(queryPoint.x[this.splitDim] <= this.splitValue) {			
				nearestPoint = this.lowChild.nearestPointInNode(queryPoint);
				if((this.splitValue - nearestPoint.x[this.splitDim])*(this.splitValue - nearestPoint.x[this.splitDim]) <
						distSquared(queryPoint, nearestPoint))
				{
					nearestPoint_otherSide = this.highChild.nearestPointInNode(queryPoint);
					
					if( distSquared(queryPoint, nearestPoint) > distSquared(queryPoint, nearestPoint_otherSide) ) {
						nearestPoint = nearestPoint_otherSide;
					}
				}
				
				
				
			}else {
				nearestPoint = this.highChild.nearestPointInNode(queryPoint);
				
				if((this.splitValue - nearestPoint.x[this.splitDim])*(this.splitValue - nearestPoint.x[this.splitDim]) <
						distSquared(queryPoint, nearestPoint))
				{
					nearestPoint_otherSide = this.lowChild.nearestPointInNode(queryPoint);
					
					if(distSquared(queryPoint, nearestPoint) > distSquared(queryPoint, nearestPoint_otherSide) ) {
						nearestPoint = nearestPoint_otherSide;
					}
				}
			}
			
		}
		
		return nearestPoint;
		



		*/


		//   ADD YOUR CODE ABOVE HERE

	}

	// -----------------  KDNode helper methods (might be useful for debugging) -------------------

	public int height() {
		if (this.leaf) 	
			return 0;
		else {
			return 1 + Math.max( this.lowChild.height(), this.highChild.height());
		}
	}

	public int countNodes() {
		if (this.leaf)
			return 1;
		else
			return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
	}

	/*  
	 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
	 * of the subtree rooted at this KDNode.   The second element is the number of leaves
	 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
	 * to element 0 and size refers to element 1.
	 */

	public int[] sumDepths_numLeaves(){
		int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
		int[] return_sumDepths_numLeaves = new int[2];

		/*     
		 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
		 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
		 *  is one greater than the depth of each leaf in the subtree.
		 */

		if (this.leaf) {  // base case
			return_sumDepths_numLeaves[0] = 0;
			return_sumDepths_numLeaves[1] = 1;
		}
		else {
			sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
			sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
			return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
		}	
		return return_sumDepths_numLeaves;
	}

}

public Iterator<Datum> iterator() {
	return new KDTreeIterator();
}







private class KDTreeIterator implements Iterator<Datum> {

	//   ADD YOUR CODE BELOW HERE
	ArrayList<Datum> sorted;
	
	int count = 0;
	
	KDNode root = rootNode;
	
	
	KDTreeIterator(){
		this.sorted = new ArrayList<Datum>();

		this.count = -1;

		this.traverse_inorder(root);
	}
	

	public boolean hasNext() {
		if(this.count + 1 < this.sorted.size()) {
			return true;
		}
		return false;
	}
	

	public Datum next() {
		return this.sorted.get(++this.count);
	}


	
	
	
	private void traverse_inorder(KDNode node) {
		if (node.lowChild != null)
			traverse_inorder(node.lowChild);
		
		if(node.leaf) {
			sorted.add(node.leafDatum);
		}
		
		
		if (node.highChild != null)
			traverse_inorder(node.highChild);
	}
	
	
	
	
	
	
	
	//   ADD YOUR CODE ABOVE HERE

}

}

