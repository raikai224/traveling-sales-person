import java.io.*;
import java.util.*; 

// Class DFS does the work for deliverable DFS of the Main

public class DFS {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DFS( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		depthFirstSearch(g);
		printTimes(g, output);
		printEdges(g, output);
		
	}
	//https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	public static boolean isNumeric(String str) { 
		return str.matches("-?\\d+(\\.\\d+)?");  
	}

	public void dfs(Stack<Node> stack, int timeCounter){
		if(stack.isEmpty()){
			return;
		}
		Node currentNode = stack.peek();
		List<Edge> edges = new ArrayList<Edge>();
		boolean allInts = true;
		for(int i = 0; i < currentNode.getOutgoingEdges().size(); i++){
			if(!currentNode.getOutgoingEdges().get(i).head.visited){
				Edge currentEdge = currentNode.getOutgoingEdges().get(i);
				edges.add(currentEdge);
				if(!isNumeric(currentEdge.label)){
					allInts = false;
				}
			}
		}
		if(edges.size() == 0){
			Node doneNode = stack.pop();
			doneNode.setEndTime(timeCounter);
			setEdgeTypes(doneNode);
			timeCounter++;
			dfs(stack, timeCounter);
		}else if(edges.size() == 1){
			visitNode(edges.get(0), stack, timeCounter);
			timeCounter++;
			dfs(stack, timeCounter);
		}else{
			if(allInts == true){
				int nextNodeIndex = 0;
				for(int i = 1; i < edges.size(); i++){
					int compareEdges = edges.get(nextNodeIndex).label.compareTo(edges.get(i).label);
					if(compareEdges > 0){
						nextNodeIndex = i;
					}else if(compareEdges == 0){
						int compareNodes = edges.get(nextNodeIndex).head.name.compareTo(edges.get(i).head.name);
						if(compareNodes > 0){
							nextNodeIndex = i;
						}
					}
				}
				visitNode(edges.get(nextNodeIndex), stack, timeCounter);
				timeCounter++;
				dfs(stack, timeCounter);
			}else{
				int nextNodeIndex = 0;
				for(int i = 1; i < edges.size(); i++){
					int compareNodes = edges.get(nextNodeIndex).head.name.compareTo(edges.get(i).head.name);
					if(compareNodes > 0){
						nextNodeIndex = i;
					}
				}
				visitNode(edges.get(nextNodeIndex), stack, timeCounter);
				timeCounter++;
				dfs(stack, timeCounter);
			}
		}
	}
	public void depthFirstSearch(Graph g){
		//find where to start in graph/find node with s value
		int startingNodeIndex = 0;
		int timeCounter = 1;
		for(int i = 0; i < g.nodeList.size(); i++){
			if(g.nodeList.get(i).getVal().equals("s")){
				startingNodeIndex = i;
			}
		}
		//add node onto stack when initially visit
		//check outgoing edges and add them to stack if not visited yet
		//if no outgoing edges are unvisited, pop off stack

		Stack<Node> stack = new Stack<Node>();
		Node startingNode = g.nodeList.get(startingNodeIndex);
		startingNode.setStartTime(timeCounter);
		timeCounter++;
		stack.push(startingNode);
		startingNode.setVisited();
		dfs(stack, timeCounter);
	}

	public void printTimes(Graph g, PrintWriter output){
		System.out.println("Node	Start Time	End Time");
		output.println("Node	Start Time	End Time");
		for(int i = 0; i < g.nodeList.size(); i++){
			String s = String.format("%-13s %-13d %d", g.nodeList.get(i).name, g.nodeList.get(i).startTime, 
			g.nodeList.get(i).endTime);//https://stackoverflow.com/questions/4418308/java-output-formatting-for-strings
			System.out.println(s);
			output.println(s);
		  }
		  System.out.println();
		  output.println();
		  output.flush();
	}

	public void printEdges(Graph g, PrintWriter output){
		System.out.println("Edge	 Type");
		output.println("Edge	 Type");
		for(int i = 0; i < g.edgeList.size(); i++){
			String edges = String.format("%s-%s", g.edgeList.get(i).tail.abbrev, g.edgeList.get(i).head.abbrev);
			String print = String.format("%-11s%s", edges, g.edgeList.get(i).type);
			System.out.println(print);
			output.println(print);
			output.flush();
		}
	}

	public void visitNode(Edge e, Stack<Node> stack, int timeCounter){ //all the setter methods and updates to data structures before recusrive call
		Node nextNode = e.head;
		e.setType("T");
		nextNode.addAncestor(e.tail);
		stack.push(nextNode);
		nextNode.setStartTime(timeCounter);
		nextNode.setVisited();
	}

	public void setEdgeTypes(Node n){
		for(int i = 0; i < n.outgoingEdges.size(); i++){
			if(n.outgoingEdges.get(i).head.visited == true && (n.outgoingEdges.get(i).type == null)){
				if(n.outgoingEdges.get(i).head.startTime > n.outgoingEdges.get(i).tail.startTime){
					if(n.outgoingEdges.get(i).head.ancestors.contains(n.outgoingEdges.get(i).tail)){
						n.outgoingEdges.get(i).setType("F");
					}else{
						n.outgoingEdges.get(i).setType("C");
					}
				}else{
					if(n.outgoingEdges.get(i).tail.ancestors.contains(n.outgoingEdges.get(i).head)
					|| (n.outgoingEdges.get(i).tail == n.outgoingEdges.get(i).head)){
						n.outgoingEdges.get(i).setType("B");
					}else{
						n.outgoingEdges.get(i).setType("C");
					}
				}
			}
		}
	}
}

