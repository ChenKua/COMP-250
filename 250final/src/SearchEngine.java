import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;
	private int counter = 0;
	//	private int count = 0;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>(); 	// word and list of urls/ have the contents of the website
		this.internet = new MyWebGraph();							//Hashmap in MyWebGraph is the vertices
		this.parser = new XmlParser(filename);
	}

	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		ArrayList<String> linkArray = new ArrayList<String>();
		ArrayList<String> wordArray = new ArrayList<String>();	//keys




		internet.addVertex(url);

		linkArray = parser.getLinks(url);
		ArrayList<String> temp = parser.getContent(url);
		for(int i = 0; i < temp.size(); i++) {
			wordArray.add(temp.get(i).toLowerCase());
		}
		//		wordArray = parser.getContent(url);



		//		wordIndex.put(url, wordArray);

		for (int i = 0; i < wordArray.size(); i++) {
			if(wordIndex.containsKey(wordArray.get(i).toLowerCase()) == false) {
				ArrayList<String> urls = new ArrayList<String>();
				urls.add(url);
				wordIndex.put(wordArray.get(i), urls);
			}else {
				ArrayList<String> tempArrayList = new ArrayList<String>();
				//				count++;
				tempArrayList.addAll(wordIndex.get(wordArray.get(i)));									//add the urls to a new ArrayList
				tempArrayList = checkDuplicateUrl (tempArrayList, url);
				wordIndex.put(wordArray.get(i), tempArrayList);

			}

		}


		internet.setVisited(url, true);
		for(String node : linkArray) {
			internet.addVertex(node);
			if((internet.getVisited(node)) == false) {
				internet.addEdge(url, node);
				crawlAndIndex(node);
			}else {
				internet.addEdge(url, node);

			}
		}
	}

	private ArrayList<String> checkDuplicateUrl (ArrayList<String> tempArray, String url){
		if(tempArray.contains(url)) {
			return tempArray;
		}else {
			tempArray.add(url);
			return tempArray;
		}
	}

	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		ArrayList<Double> compareRanks = new ArrayList<Double>();


		if(counter == 0) {

			for(int i = 0; i < internet.getVertices().size(); i++) {
				internet.setPageRank(internet.getVertices().get(i), 1);						//set page rank to 1
				//				compareRanks.add(internet.getPageRank(internet.getVertices().get(i)));
			}
			counter ++;
		}

		while(true) {
			int size = 0;
			compareRanks = computeRanks(internet.getVertices());
			for(int i = 0; i < internet.getVertices().size() ; i++) {
				if(Math.abs(internet.getPageRank(internet.getVertices().get(i))- compareRanks.get(i)) < epsilon) {
					size++;																								//check if all the ranks < epsilon
				}
			}
			if(size == internet.getVertices().size()) {
				for(int j = 0; j < internet.getVertices().size(); j++) {
					internet.setPageRank(internet.getVertices().get(j), compareRanks.get(j));
				}
				return;
			}else {
				for(int j = 0; j < internet.getVertices().size(); j++) {
					internet.setPageRank(internet.getVertices().get(j), compareRanks.get(j));
				}
			}
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		//		ArrayList<Double> realRankArray = new ArrayList<Double>();
		ArrayList<Double> nodesRank = new ArrayList<Double>();


		for (int i = 0; i < vertices.size(); i++) {								//compute the bracketed part
			double nodeValue = 0;
			for(String neighbor : internet.getEdgesInto(vertices.get(i))) {
				double realRank = internet.getPageRank(neighbor);  				//parser.getPageRank(neighbor);
				double outDegree = internet.getOutDegree(neighbor);
				if(outDegree == 0) {
					continue;
				}
				nodeValue = nodeValue + realRank/outDegree;
			}
			nodesRank.add(0.5 + 0.5*nodeValue);
		}

		//		for(int i = 0; i < nodesRank.size(); i++) {								//compute the rank
		//			realRankArray.add(0.5 + 0.5 *(nodesRank.get(i)));					//add the rank to the arraylist
		//			internet.setPageRank(vertices.get(i), realRankArray.get(i));		//set the rank
		//		}
		return nodesRank;
	}


	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here


		ArrayList<String> results = new ArrayList<String>();
		//		ArrayList<String> keyList = new ArrayList<String>();

		//		keyList.addAll(wordIndex.keySet());
		//		Set<String> keyList = wordIndex.keySet();
		//
		//		for(String key: keyList) {
		//			ArrayList<String> listOfQueryUrl = wordIndex.get(key);
		//			for(int j = 0; j < listOfQueryUrl.size(); j++ ) {
		//				if(listOfQueryUrl.get(j).equals(query)) {
		//					results.add(key);
		//					break;
		//				}
		//			}
		//		}

		if(wordIndex.containsKey(query.toLowerCase()) == false) {
			return results;
		}else {
			//			for(int i = 0; i < (wordIndex.get(query)).size(); i++) {
			//				results.add((wordIndex.get(query)).get(i));
			results.addAll(wordIndex.get(query.toLowerCase()));
			//			}

		}

		HashMap<String, Double> sortedHashMap = new HashMap<String, Double>();

		for(int i = 0; i < results.size(); i++) {
			sortedHashMap.put(results.get(i), internet.getPageRank(results.get(i)));
		}

		results = Sorting.fastSort(sortedHashMap);
		return results ;
	}
}
