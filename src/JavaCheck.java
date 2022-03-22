import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaCheck {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Graph> graphs = readSplitGraphs(args[0]);
		Set<Graph> computed = readComputedGraphs(args[1]);
		//
		int hits = 0;
		for(int i=0;i!=graphs.size();++i) {
			Graph g = graphs.get(i);
			if(computed.contains(g)) {
				hits++;
			} else {
				//System.out.println("Missing graph " + (i+1) + " : " + g);
				System.out.println(g);
			}
		}
		System.out.println("Missing " + (graphs.size() - hits) + " graphs overall");
	}

	public static ArrayList<Graph> readSplitGraphs(String filename) throws FileNotFoundException, IOException {
		ArrayList<Graph> graphs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Graph g = Graph.fromString(line);
		    	graphs.add(g.compact());
		    }
		}
		return graphs;
	}

	public static Set<Graph> readComputedGraphs(String filename) throws FileNotFoundException, IOException {
		HashSet<Graph> graphs = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if(line.startsWith("G[")) {
		    		String tp = br.readLine();
		    		if(tp == null || !tp.startsWith("TP[")) {
		    			System.out.println("*** SKIPPING GRAPH");
		    			continue;
		    		} else {
		    			line = line.substring("G[1] := {".length(),line.length()-1);
				    	graphs.add(Graph.fromString(line));
		    		}
		    	}
		    }
		}
		return graphs;
	}
}
