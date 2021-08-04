import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.*;

// Class ShortestPathFinder does the work for deliverable ShortestPathFinder of the Main

public class ShortestPathFinder {
	public static int steps = 0;

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public ShortestPathFinder( File in, Graph gr ) {
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
		for (Edge e : g.edgeList) 
    		e.setDist(e.label); 
		Tour shortestTour = localSearch(g, output);
		output.print("The Shortest path found was ");
		System.out.print("The Shortest path found was ");
		for(int i = 0; i < shortestTour.nodes.size(); i++){
			output.print(shortestTour.nodes.get(i).val);
			System.out.print(shortestTour.nodes.get(i).val);
		}
		output.print(" with distance of " + shortestTour.getDistance() + " after " + ShortestPathFinder.steps + " steps.");
		System.out.print(" with distance of " + shortestTour.getDistance() + " after " + ShortestPathFinder.steps + " steps.");
		output.flush();
	}
	//STEP1- make initial first tour (just some random city, then fill in rest of tour based of proximity)
	public Tour initFirstTour(Graph graph){
		int graphSize = graph.nodeList.size();
		Random rand = new Random();
		int startingPoint = rand.nextInt(graphSize);
		return generateTour(graph.nodeList, graph.nodeList.get(startingPoint));
	}

	public Tour localSearch(Graph graph, PrintWriter output){
		Tour currentTour = initFirstTour(graph);
		verbosePrint(currentTour, output, 0);
		summaryPrint(currentTour);
		int counter = 0;
		Tour shortestTour = currentTour;
		for(int i = 0; i < 40; i++){
			while(counter < 40){
				Tour newTour = twoEdgeSwap(currentTour);
				verbosePrint(newTour, output, 1);
				if(newTour.getDistance() < currentTour.getDistance()){
					currentTour = newTour;
					counter = 0;
					if(shortestTour.getDistance() > currentTour.getDistance()){
						summaryPrint(newTour);
					}
					ShortestPathFinder.steps++;
				}else{
					counter++;
					ShortestPathFinder.steps++;
				}
			}
			if(shortestTour.getDistance() > currentTour.getDistance()){
				shortestTour = currentTour;
			}
			if(i != 9){
				currentTour = initFirstTour(graph);
				verbosePrint(currentTour, output, 0);
				if(currentTour.distance < shortestTour.distance){
					summaryPrint(currentTour);
				}
				counter = 0;
				ShortestPathFinder.steps++;
			}

		}

		return shortestTour;
		
	}

	public void verbosePrint(Tour tour, PrintWriter output, int walkOrRestart){
		//1 for walk 0 for restart
		for(int i = 0; i < tour.nodes.size(); i++){
			output.print(tour.nodes.get(i).val);
		}
		output.print(" ");
		output.print(" ");
		output.print(tour.getDistance());
		output.print(" ");
		output.print(" ");
		if(walkOrRestart == 1){
			output.println("(swap)");
		}else{
			output.println("(restart)");
		}
		output.flush();
	}

	public void summaryPrint(Tour tour){
		for(int i = 0; i < tour.nodes.size(); i++){
			System.out.print(tour.nodes.get(i).val);
		}
		System.out.print(" ");
		System.out.print(" ");
		System.out.println(tour.getDistance());
	}

	public Tour twoEdgeSwap(Tour tour){
		//need to make sure first number is smaller than second
		//numbers should not be the same or within 1 of each other
		int firstEdgeSwap; 
		int secondEdgeSwap;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.addAll(tour.getNodes()); 
		ArrayList<Edge> edges = new ArrayList<Edge>(); 
		edges.addAll(tour.getEdges());
		Random rand = new Random();
		int min = 1;
		int max = nodes.size() - 2;
		int firstRandom = rand.nextInt((max - min) + 1) + min;
		int secondRandom= rand.nextInt((max - min) + 1) + min;
		while(Math.abs(secondRandom - firstRandom) <= 1){
			secondRandom = rand.nextInt((max - min) + 1) + min;
		}
		if(firstRandom < secondRandom){
			firstEdgeSwap = firstRandom;
			secondEdgeSwap = secondRandom;
		}else{
			firstEdgeSwap = secondRandom;
			secondEdgeSwap = firstRandom;
		}
		int swapCounter = secondEdgeSwap - firstEdgeSwap;
		if(swapCounter % 2 != 0){
			swapCounter++;
		}
		
		//swap 2 edges by reversing order of set range of node list
		for(int i = 0; i < swapCounter / 2; i++){
			Node tempNode = nodes.get(i + firstEdgeSwap);
			nodes.set(i + firstEdgeSwap, nodes.get(secondEdgeSwap - i));
			nodes.set(secondEdgeSwap - i, tempNode);
		}

		//swap edges
		for(int i = 0; i < nodes.get(firstEdgeSwap - 1).outgoingEdges.size(); i++){
			if(nodes.get(firstEdgeSwap - 1).outgoingEdges.get(i).getHead() == nodes.get(firstEdgeSwap)){
				edges.set(edges.indexOf(edges.get(firstEdgeSwap)), nodes.get(firstEdgeSwap).outgoingEdges.get(i));
				break;
			}
		}
		for(int i = 0; i < nodes.get(secondEdgeSwap).outgoingEdges.size(); i++){
			if(nodes.get(secondEdgeSwap).outgoingEdges.get(i).getHead() == nodes.get(secondEdgeSwap + 1)){
				edges.set(edges.indexOf(edges.get(secondEdgeSwap)), nodes.get(secondEdgeSwap).outgoingEdges.get(i));
				break;
			}
		}
		Tour newTour = new Tour();
		newTour.setEdges(edges);
		newTour.setNodes(nodes);
		newTour.calculateDistance();

		return newTour;
	}

	public Tour generateTour(List<Node> nodes, Node startingNode){
		Tour tour = new Tour();
		tour.addNode(startingNode);
		Node currentNode = startingNode;
		int totalDistance = 0;
		//int distance = Integer.MAX_VALUE;
		Node closestNode = nodes.get(0);
		HashSet<Node> alreadyInTour = new HashSet<Node>();
		alreadyInTour.add(startingNode);
		Edge currentEdge = nodes.get(0).outgoingEdges.get(0);
		for(int i = 0; i < nodes.size() - 1; i++){
			int distance = Integer.MAX_VALUE;
			for(int j = 0; j < currentNode.outgoingEdges.size(); j++){
				if(currentNode.outgoingEdges.get(j).getDist() < distance && !alreadyInTour.contains(currentNode.outgoingEdges.get(j).head)){
					currentEdge = currentNode.outgoingEdges.get(j);
					distance = currentNode.outgoingEdges.get(j).getDist();
					//System.out.println(currentNode.outgoingEdges.get(j).getDist() + " this shouold not be 0");
					closestNode = currentNode.outgoingEdges.get(j).head;
				}
			}
			totalDistance += distance;
			//System.out.println(totalDistance);
			tour.addNode(closestNode);
			tour.addEdge(currentEdge);
			currentNode = closestNode;
			alreadyInTour.add(closestNode);
			if(i == nodes.size() - 2){
				//Edge lastEdge = currentNode.outgoingEdges.get(j);
				for(int x = 0; x < currentNode.outgoingEdges.size(); x++){
					if(currentNode.outgoingEdges.get(x).head == startingNode){
						Edge lastEdge = currentNode.outgoingEdges.get(x);
						tour.addEdge(lastEdge);
						tour.addNode(startingNode);
						totalDistance += lastEdge.getDist();
					}
				}
			}
		}

		tour.setDistance(totalDistance);
		return tour;
	}


}

