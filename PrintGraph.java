import java.io.*;
import java.util.*;

// Class PrintGraph does the work for deliverable PrintGraph of the Main

public class PrintGraph {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public PrintGraph( File in, Graph gr ) {
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
		printGraphInfo(g, output);
	}

	public void printGraphInfo(Graph g, PrintWriter output){
		for(int i = 0; i < g.edgeList.size(); i++){
		System.out.println("Edge from " + g.edgeList.get(i).tail.name +  " to " +  g.edgeList.get(i).head.name + " labeled " + g.edgeList.get(i).label);
		output.println("Edge from " + g.edgeList.get(i).tail.name +  " to " +  g.edgeList.get(i).head.name + " labeled " + g.edgeList.get(i).label);
		output.flush();
		}
	}

}