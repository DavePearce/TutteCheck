import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
	private final Edge[] edges;

	public Graph(List<Edge> edges) {
		this.edges = edges.toArray(new Edge[edges.size()]);
	}

	public Graph(Edge... edges) {
		this.edges = edges;
	}

	public Graph compact() {
		int[] labels = new int[maxVertex()+1];
		int counter = 0;
		for(int v : vertices()) {
			labels[v] = counter++;
		}
		return permute(labels);
	}

	public Graph permute(int[] permutation) {
		Edge[] nedges = new Edge[edges.length];
		for(int i=0;i!=nedges.length;++i) {
			nedges[i] = edges[i].permute(permutation);
		}
		return new Graph(nedges);
	}

	public List<Edge> edges() {
		return Arrays.asList(edges);
	}

	public Set<Integer> vertices() {
		HashSet<Integer> vs = new HashSet<>();
		for(Edge e : edges) {
			vs.add(e.from);
			vs.add(e.to);
		}
		return vs;
	}

	public int maxVertex() {
		int max = 0;
		for(Edge e : edges) {
			max = Math.max(e.from, max);
			max = Math.max(e.to, max);
		}
		return max;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Graph) {
			Graph g = (Graph) o;
			return Arrays.equals(edges,g.edges);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(edges);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i!=edges.length;++i) {
			if(i != 0) {
				sb.append(",");
			}
			sb.append(edges[i].toString());
		}
		return sb.toString();
	}

	public static class Edge {
		private int from;
		private int to;

		public Edge(int from, int to) {
			this.from = from;
			this.to = to;
		}

		public Edge permute(int[] permutation) {
			return new Edge(permutation[from],permutation[to]);
		}

		@Override
		public boolean equals(Object o) {
			if(o instanceof Edge) {
				Edge e = (Edge) o;
				return from == e.from && to == e.to;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return from ^ to;
		}

		@Override
		public String toString() {
			return Integer.toString(from) + "--" + Integer.toString(to);
		}

		public static Edge fromString(String str) {
			String[] split = str.split("--");
			if(split.length != 2) {
				throw new IllegalArgumentException();
			}
			int from = Integer.parseInt(split[0]);
			int to = Integer.parseInt(split[1]);
			return new Edge(from,to);
		}
	}

	public static Graph fromString(String str) {
		ArrayList<Edge> edges = new ArrayList<>();
		for(String e : str.split(",")) {
			edges.add(Edge.fromString(e));
		}
		return new Graph(edges);
	}
}
