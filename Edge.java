//import java.util.*;

// Edge between two nodes
public class Edge {
	
	String label;
	int dist;
	Node tail;
	Node head;
	String type; //store what type of edge this is, also a setter and getter method for type
	
	public Edge( Node tailNode, Node headNode, String theLabel ) {
		setLabel( theLabel );
		setTail( tailNode );
		setHead( headNode );
	}
	
	public String getLabel() {
		return label;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public Node getHead() {
		return head;
	}
	
	public int getDist() {
		return dist;
	}

	public String getType() {
		return type;
	}
	
	public void setLabel( String s ) {
		label = s;
	}
	
	public void setTail( Node n ) {
		tail = n;
	}
	
	public void setHead( Node n ) {
		head = n;
	}
	
	public void setDist( String s ) {
		try {
			dist = Integer.parseInt( s );
		}
		catch ( NumberFormatException nfe ) {
			dist = Integer.MAX_VALUE;
		}
	}

	public void setType( String t ) {
		type = t;
	}
}
