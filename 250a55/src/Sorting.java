import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable.
	 * It returns an ArrayList containing all the keys from the map, ordered
	 * in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number
	 * of pairs in the map.
	 */
	public static <K, V extends Comparable> ArrayList<K> slowSort(HashMap<K, V> results) {
		ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());    //Start with unsorted list of urls

		int N = sortedUrls.size();
		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < N - i - 1; j++) {
				if (results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j + 1))) < 0) {
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j + 1));
					sortedUrls.set(j + 1, temp);
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
		ArrayList<K> sortedUrls = new ArrayList<K>();//the unsorted arrylist
		sortedUrls.addAll(results.keySet());
		quickSort(results,sortedUrls,0,sortedUrls.size()-1);
		return sortedUrls;
	}


	//a helper method that help with quickSort
	private static <K, V extends Comparable> void quickSort(HashMap<K, V> results, ArrayList<K> sortedUrls,int left,int right){
		if (left >= right) {
			return;
		} else {
			
			int i = PlaceDivide(results, sortedUrls, left, right);
			
			quickSort(results, sortedUrls, left, i - 1);
			
			quickSort(results, sortedUrls, i + 1, right);

		}
		return;
	}


	//a helper method that place and divide
	private static <K, V extends Comparable> int PlaceDivide(HashMap<K, V> results,ArrayList<K> sortedUrls, int left,int right){
		int pivotpoint = right;
		int MID = (left+right)/2;

		//chosing the medium of the first, middle and last element to avoid worst case in PAD
		if(results.get(sortedUrls.get(left)).compareTo(results.get(sortedUrls.get(MID)))>0){
			K key = sortedUrls.get(left);
			sortedUrls.set(left,sortedUrls.get(MID));
			sortedUrls.set(MID,key);
		}
		if(results.get(sortedUrls.get(left)).compareTo(results.get(sortedUrls.get(right)))>0){

			K key = sortedUrls.get(left);
			sortedUrls.set(left,sortedUrls.get(right));
			sortedUrls.set(right,key);
		}
		if(results.get(sortedUrls.get(right)).compareTo(results.get(sortedUrls.get(MID)))>0){
			K key = sortedUrls.get(right);
			sortedUrls.set(right,sortedUrls.get(MID));
			sortedUrls.set(MID,key);
		}


		K pivot = sortedUrls.get(pivotpoint);
		int end = left;
		for(int i = left;i<right;i++){
			if(results.get(sortedUrls.get(i)).compareTo(results.get(pivot))>0){
				K key = sortedUrls.get(i);
				sortedUrls.set(i,sortedUrls.get(end));
				sortedUrls.set(end,key);
				end++;
			}
		}
		sortedUrls.set(pivotpoint,sortedUrls.get(end));
		sortedUrls.set(end,pivot);
    	return end;

	}




}