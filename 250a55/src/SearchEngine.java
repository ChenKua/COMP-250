import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
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
		
		internet.addVertex(url);
		internet.setVisited(url, true);
		
		
		ArrayList<String> tmp =  parser.getContent(url);
		ArrayList<String> content = new ArrayList<>();
		
		for(int i=0; i<tmp.size();i++) {
			content.add(tmp.get(i).toLowerCase());
		}
		
		for(int i=0; i<content.size();i++) {
			ArrayList<String> links = new ArrayList<>();
			links.add(url);
			
			if(wordIndex.containsKey(content.get(i))) {
				links = ifAbsent(wordIndex.get(content.get(i)),url);
				wordIndex.put(content.get(i), links);
			}else {
				wordIndex.put(content.get(i),links);
			}
			
		}

		ArrayList<String> Neighbours = parser.getLinks(url);
		for(String vertices: Neighbours) {
			internet.addVertex(vertices);
			internet.addEdge(url, vertices);
			if (!internet.getVisited(vertices)) {
				crawlAndIndex(vertices);
			}
		}
	}
	
	//help method to compute the case to avoid duplicates of url
	private ArrayList<String> ifAbsent(ArrayList<String> links,String url){
		if(links.contains(url)) {
			return links;
		}else {
			links.add(url);
		}
		return links;	
	} 






	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here

		
		ArrayList<Double> oldrank = new ArrayList<>();
		
		for(int i=0; i<internet.getVertices().size();i++) {
				oldrank.add(1.0);
				internet.setPageRank(internet.getVertices().get(i),1.0);
				
		}
		
		ArrayList<Double> newrank =computeRanks(internet.getVertices());
		
		for(int i=0; i<internet.getVertices().size();i++) {
				internet.setPageRank(internet.getVertices().get(i),newrank.get(i));
		}
		while (!moreiteration(oldrank,newrank,epsilon)) {
			oldrank = newrank;
			newrank = computeRanks(internet.getVertices());
			for(int j=0; j<internet.getVertices().size();j++) {
				internet.setPageRank(internet.getVertices().get(j),newrank.get(j));
			}
		}
		
/*
		while(NeedMoreIteration) {
			int counter = 0;
			ArrayList<Double> ranks = computeRanks(internet.getVertices());
			//int iteration =1;
			double difference; 

			for(int g=0; g<internet.getVertices().size();g++) {
				difference = Math.abs(ranks.get(g)-internet.getPageRank(internet.getVertices().get(g)));
				if(difference<epsilon) counter++;
				//iteration++;
			}
			
			for(int k=0; k<internet.getVertices().size();k++) {
				internet.setPageRank(internet.getVertices().get(k), ranks.get(k));
			}
		
			if(counter==internet.getVertices().size()) NeedMoreIteration = false;
		}
*/
	}

	
	private static boolean moreiteration (ArrayList<Double> old, ArrayList<Double> current, double epsilon) {
		for(int i=0; i < current.size();i++) {
			if (Math.abs(old.get(i)-current.get(i))>=epsilon) return false;
		}
		return true;	
	}
	
	
	
	
	
	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here

		ArrayList<Double> Ranks = new ArrayList<>();
	

		for(int i=0; i<vertices.size(); i++) {
			double tmp =0.5;
			ArrayList<String> VerticesIntoThis = internet.getEdgesInto(vertices.get(i));
			for(int y=0; y<VerticesIntoThis.size();y++) { 
				double DegreeOut = internet.getOutDegree(VerticesIntoThis.get(y));
				tmp = tmp + 0.5*(internet.getPageRank(VerticesIntoThis.get(y))/DegreeOut);
			}
			Ranks.add(tmp);	
		}
		return Ranks;
	}
	/*		}
		int size =vertices.size();
		ArrayList<Double> InitialRanks = null;
		ArrayList<Double> FinalRanks = null;
		double d=0.5;

		for(int i=0; i<size; i++) {
			InitialRanks.add(1.0);
		}

		for(int i=0; i<size; i++) {
			double tmp =0.5;
			ArrayList<String> VerticesIntoThis = internet.getEdgesInto(vertices.get(i));
			for(int j=0; j<VerticesIntoThis.size();j++) { 
				int index = vertices.indexOf(VerticesIntoThis.get(j));
				double DegreeOut = internet.getOutDegree(VerticesIntoThis.get(i));
				tmp = tmp + d*(InitialRanks.get(index)/DegreeOut);
			}
			FinalRanks.add(tmp);
		}

		return FinalRanks;
	 */




	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		query = query.toLowerCase();
		HashMap<String,Double> listofURL = new HashMap<String,Double>();
		

		ArrayList<String> emptylist = new ArrayList<>();
		if(!wordIndex.containsKey(query)) {
			return emptylist;
		}
		
		for(int c=0; c<wordIndex.get(query).size();c++) {
			listofURL.put(wordIndex.get(query).get(c),internet.getPageRank(wordIndex.get(query).get(c)));
		}
				
		return Sorting.fastSort(listofURL);
	}
}
