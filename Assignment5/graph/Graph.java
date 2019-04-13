import java.util.ArrayList;
import java.util.HashMap;

/**
 * File Name: Graph.java 
 * Graph 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2018
 */
/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java
 */

/*
 * ----------------  YOU CANNOT CHANGE ANYTHING IN THIS FILE ------------------------
 */
class Graph {
	
	/*
	 * class node(vertex)
	 */
	private class node {
		private int name ;
		private int temp ;
		ArrayList<edge> fanins ;
		ArrayList<edge> fanouts ;
		node(int name) {
			this.name = name ;
			this.temp = 0 ;
			fanins =  new ArrayList<edge>() ;
			fanouts = new ArrayList<edge>() ;
		}
		private void setTemp(int x) {
			temp = x ;
		}
		private int getTemp(){
			return temp ;
		}
	}
	
	/*
	 * class edge
	 */
	private class edge {
		private int other ;
		private double cost ;
		edge(int other, double cost) {
			this.other = other ;
			this.cost = cost ;
		}
	}
	
	private int numV; // Number of nodes
	private int numE; // Number of edges
	private GraphTest.GraphType type ; //Graph type
  private ArrayList<String> nodeNames ; //Node names given by the user
  private HashMap<String,Integer> hm; //String to Integer mapping
  private ArrayList<node> nodes ; //Array of all nodes
  public static final IntUtil u = new IntUtil();
  
  Graph() {
  	numV = 0 ;
  	numE = 0 ;
  	type = GraphTest.GraphType.NONE ;
  	nodeNames = new ArrayList<String>() ;
		hm = new HashMap<String,Integer>();
		nodes = new ArrayList<node>() ;
  }
  
  public int getnumV() {
  	u.myassert(numV == nodes.size()) ;
  	return numV;
  }
  
  public int getnumE() {
  	return numE;
  }

  private node getNode(int i) {
  	u.myassert(i >= 0 && i < getnumV());
  	node n = nodes.get(i) ;
  	return n ;
  }
  
  public int getNodeName(int i) {
  	node n = getNode(i) ;
  	return n.name ;
  }
  
  /* Get name from integer */
  public String getRealName(int i) {
  	u.myassert(i >= 0 && i < nodeNames.size() );
  	return nodeNames.get(i) ;
  }
  
  public String getNodeRealName(int i) {
  	int x = getNodeName(i) ;
  	return getRealName(x) ;
  }
  
  public int numFanout(int i) {
  	node n = getNode(i) ;
  	int s = n.fanouts.size();
  	return s ;
  }
  
  public int numFanin(int i) {
  	node n = getNode(i) ;
  	int s = n.fanins.size();
  	return s ;
  }
  
  public void setTemp(int i,int v){
  	node n = getNode(i) ;
  	n.setTemp(v) ;
  }
  
  public int getTemp(int i){
  	node n = getNode(i) ;
  	return n.getTemp() ;
  }
  
  private edge getNodeFanoutEdge(int n, int e) {
  	node no = getNode(n) ;
  	int size = numFanout(n) ;
  	u.myassert(e >= 0 && e < size);
  	return no.fanouts.get(e) ;
  }
  
  private edge getNodeFanInEdge(int n, int e) {
  	node no = getNode(n) ;
  	int size = numFanin(n) ;
  	u.myassert(e >= 0 && e < size);
  	return no.fanins.get(e) ;
  }
 
  public int getNodeFanout(int n, int e) {
  	edge ei = getNodeFanoutEdge(n,e) ;
  	return ei.other ;
  }
 
  public int getNodeFanin(int n, int e) {
  	edge ei = getNodeFanInEdge(n,e) ;
  	return ei.other ;
  }

  public GraphTest.GraphType getType() {
  	return type ;
  }

  public double getNodeFanoutEdgeWeight(int n, int e) {
  	edge ei = getNodeFanoutEdge(n,e) ;
  	return ei.cost ;
  }

  public int graphHasNode(String s) {
		boolean f = hm.containsKey(s) ;
		if (f) {
			return(hm.get(s));
		}
		return -1 ;
	}
	
  public int insertOrFind(String name, boolean mustbethere) {
		boolean f = hm.containsKey(name) ;
		if (f) {
			return(hm.get(name));
		}
    if (mustbethere) {
      u.myassert(false);
    } 	
  	//Not in the hash. Insert in hash
  	hm.put(name,numV++) ;
  	//Given an unique number you can get name
  	nodeNames.add(name) ;
  	u.myassert(nodeNames.size() == numV);
  	//add node
  	node n = new node(numV-1);
  	nodes.add(n) ;
    return numV-1; //We inserted at pos numV-1
  }
  
  public void createEdge(int from, int to, double w, boolean fanout) {
  	node n = nodes.get(from) ;
  	u.myassert(n.name == from) ;
  	edge e = new edge(to,w) ;
  	if (fanout == true) {
  		numE++ ;
  		n.fanouts.add(e) ;
  	}else {
  		n.fanins.add(e) ;
  	}
  }
	
	public void buildGraph(GraphTest.GraphType t, String[][] e) {
		type = t ;
		GraphBuilder b = new GraphBuilder(this,e);
	}
	
	public void writeDot(String s) {
		GraphDot b = new GraphDot(this,s);
	}
	
	public void dfs(String t, String start, boolean[] cycle, int [] work, ArrayList<String> ans) {
		GraphDfs b = new GraphDfs(t,this,start,cycle,work,ans);
	}
	
	public static void main(String[] args) {
    System.out.println("Graph.java starts");
    System.out.println("Use GraphTester.java to test");
    System.out.println("Graph.java Ends");
  }
}