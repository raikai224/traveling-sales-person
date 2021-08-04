import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node {

	String name;
	String val;  // The value of the Node
	String abbrev;  // The abbreviation for the Node
	ArrayList<Edge> outgoingEdges;  
	ArrayList<Edge> incomingEdges;
	ArrayList<Node> ancestors; //path of nodes that lead to this node, (get and set functions for all these vars below)
	int startTime; //start and end times for a node during dfs
	int endTime;
	boolean visited; //keep track of which nodes have already been visited
	
	public Node( String theAbbrev ) {
		setAbbrev( theAbbrev );
		val = null;
		name = null;
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
		ancestors = new ArrayList<Node>();
		visited = false;
	}
	
	public String getAbbrev() {
		return abbrev;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVal() {
		return val;
	}
	
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	public ArrayList<Node> getAncestors() {
		return ancestors;
	}
	
	public void setAbbrev( String theAbbrev ) {
		abbrev = theAbbrev;
	}
	
	public void setName( String theName ) {
		name = theName;
	}
	
	public void setVal( String theVal ) {
		val = theVal;
	}
	
	public void addOutgoingEdge( Edge e ) {
		outgoingEdges.add( e );
	}
	
	public void addIncomingEdge( Edge e ) {
		incomingEdges.add( e );
	}
	
	public void setStartTime( int start ) {
		startTime = start;
	}

	public void setEndTime( int end ) {
		endTime = end;
	}

	public void addAncestor( Node n ) {
		ancestors = n.ancestors;
		ancestors.add( n );
	}

	public void setVisited() {
		visited = true;
	}
	
}
