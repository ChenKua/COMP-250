import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {
	//	private static int counter;

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

		ArrayList<K> list = new ArrayList<K>();
		//		ArrayList<K> mergedList = new ArrayList<K>();
		//		ArrayList<ArrayList<K>> listArray = new ArrayList<ArrayList<K>>();
		list.addAll(results.keySet());
		return partition(list, results);

	}
	private  static <K, V extends Comparable> ArrayList<K> partition(ArrayList<K> list, HashMap<K,V> hashList) {

		ArrayList<K> list1 = new ArrayList<K>();
		ArrayList<K> list2 = new ArrayList<K>();
		if(list.size() <= 1) {
			return list;
		}else {
			int mid = list.size()/2;
			for (int i = 0; i < mid; i++) {
				list1.add(list.get(i));

			}
			for (int i = mid; i < list.size(); i++) {
				list2.add(list.get(i));
			}

			partition(list1, hashList);
			partition(list2, hashList);

			merge(list1, list2, list, hashList);

			return list;
		}
	}

	private  static <K, V extends Comparable> ArrayList<K> merge (ArrayList<K> list1, ArrayList<K> list2, ArrayList<K> mergedList, HashMap<K, V> hashList) {

		int left = 0;
		int right = 0;
		int sorted = 0;

		while(left < list1.size() && right < list2.size()) {
			if(hashList.get(list1.get(left)).compareTo(hashList.get(list2.get(right))) > 0) {
				mergedList.set(sorted, list1.get(left));
				left++;
				sorted++;
			}else {
				mergedList.set(sorted, list2.get(right));
				right++;
				sorted++;
			}
		}

		int remainderIndex;
		if(right >= list1.size()) {
			remainderIndex = left;
			for(int i = remainderIndex; i < list1.size(); i++) {
				mergedList.set(sorted, list1.get(i));
				sorted++;
			}
		}else {
			remainderIndex = right;
			for(int i = right; i < list2.size(); i++){
				mergedList.set(sorted, list2.get(i));
				sorted++;
			}
		}

		return mergedList;
	}
}

