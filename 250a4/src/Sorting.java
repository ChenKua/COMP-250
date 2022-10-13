

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort


//merge sort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
	public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
		ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

		int N = sortedUrls.size();
		for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
		}
		return sortedUrls;                    
	}


	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
	public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
		// ADD YOUR CODE HERE

		ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());
		
		return mergeSort(sortedUrls,results);
	}


	private static <K,V extends Comparable> ArrayList<K>  mergeSort(ArrayList<K> sortedUrls,HashMap<K, V> results){

		ArrayList<K> first = new ArrayList<K>();
		ArrayList<K> second = new ArrayList<K>();
		
		
		if(sortedUrls.size() < 2){
			return sortedUrls;
		}else {
			int mid = sortedUrls.size()/2;

			for(int i=0; i<mid;i++){
				first.add(sortedUrls.get(i));
			}
			for(int i=mid;i<sortedUrls.size();i++){
				second.add(sortedUrls.get(i));
			}

			//sort first and second halves
			mergeSort(first,results);
			mergeSort(second,results);

			//merge sorted halves
			merge(first,second,sortedUrls,results);
			
			return sortedUrls;
			
		}


	}

	private static <K ,V extends Comparable> ArrayList<K>  merge(ArrayList<K> first, ArrayList<K> second,ArrayList<K> sortedUrls, HashMap<K,V> resluts){

		int i=0;
		int j=0;
		int sortedIndex =0;
		
		while((i<first.size()) && (j<second.size())) {
			if(	(resluts.get(first.get(i))).compareTo(resluts.get(second.get(j))) > 0) {
				sortedUrls.set(sortedIndex,first.get(i));
				i++;
			}else {
				sortedUrls.set(sortedIndex,second.get(j));
				j++;
			}
			sortedIndex++;
		}
		
		
		
		while(i<first.size()) {
			sortedUrls.set(sortedIndex, first.get(i));
			i++;
			sortedIndex++;
		}
		while(j<second.size()) {
			sortedUrls.set(sortedIndex, second.get(j));
			j++;
			sortedIndex++;
		}
		return sortedUrls;
	}





}


