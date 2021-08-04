import java.util.*;

// Tour of nodes for TSP
public class Tour{

    ArrayList<Edge> edges = new ArrayList<Edge>();  
    ArrayList<Node> nodes = new ArrayList<Node>();
    int distance;

    public Tour() {
        edges = new ArrayList<Edge>();
        nodes = new ArrayList<Node>();
        distance = 0;
	}

    public void addEdge(Edge e){
        edges.add( e );
    }
    
    public void addNode(Node n){
        nodes.add( n );
    }

    public int getDistance(){
        return distance;
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public void setDistance(int d){
        distance = d;
    }

    public void setNodes(ArrayList<Node> n){
        nodes = n;
    }

    public void setEdges(ArrayList<Edge> e){
        edges = e;
    }

    public void calculateDistance(){
        for(int i = 0; i < edges.size(); i++){
            distance += edges.get(i).dist;
        }
    }

}