package com.ego.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;



public class PersonalUndirectedNetwork extends UndirectedNetwork{

	private int ego;
	private int k;
	private int year;
	
	// Constructor
	public PersonalUndirectedNetwork(int ego, int k, int year) {
		super();
		this.ego=ego;
		this.k=k;
		this.year=year;
	}
	
	public PersonalUndirectedNetwork(int ego, int year) {
		super();
		this.ego=ego;
		this.year=year;
		
	}
	
	public int getEgo()
	{
		return this.ego;
	}
	
	public int getK()
	{
		return this.k;
	}
	
	public int getYear()
	{
		return this.year;
	}
	
	public void setEgo(int ego)
	{
		this.ego=ego;
	}
	
	public void setK(int k)
	{
		this.k=k;
	}
	
	public void setYear(int year)
	{
		this.year=year;
	}
	
	public int egoDegree()
	{
		return this.ugraph.degreeOf(this.ego);
	}
	
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> retrievePersonalUndirectedNetworkEgoKYear(MySQLAccess msqla) throws Exception
	{
			List<Integer> authorsList=new ArrayList<Integer>();
			authorsList.add(ego);
			RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
			retrieveNetwork.retrieveUndirectedCoAuthorsEgoNetwork(msqla, authorsList, k, year);
			this.ugraph=retrieveNetwork.ugraph;
			return this.ugraph;
	}
	
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> retrieveMaxPersonalUndirectedNetworkEgoYear(MySQLAccess msqla) throws Exception{
		List<Integer> authorsList=new ArrayList<Integer>();
		authorsList.add(ego);
		RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
		k=retrieveNetwork.retrieveMaxCoAuthorsEgoNetwork(msqla, authorsList, year);
		this.ugraph=retrieveNetwork.ugraph;
		return this.ugraph;
	}
	
	public double computeMaxK(){
		List<Double> findMaxKList=new ArrayList<>();
		for(int vertex:this.ugraph.vertexSet())
		{
			DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraAlgo=new DijkstraShortestPath<Integer, DefaultWeightedEdge>(this.ugraph, ego, vertex);
			findMaxKList.add(dijkstraAlgo.getPathLength());
			
		}
		if (!findMaxKList.isEmpty()) return	Collections.max(findMaxKList);
		else return 0;
	
	}
	
	
	public void viewNetwork() {
		
		Graph g=new SingleGraph("Co-authors Ego-network");
			
			for(int s:ugraph.vertexSet())
			{
				if(s==this.ego){
					g.addNode("EGO");
					g.getNode("EGO").setAttribute("ui.label", "EGO");
			}
			else{
				g.addNode(""+s);
				g.getNode(""+s).setAttribute("ui.label", ""+s);
			}
					
			
				
			}
			
			for(DefaultWeightedEdge s:ugraph.edgeSet())
			{
				
				
				
				if(!ugraph.getEdgeSource(s).toString().equals(""+this.ego))
				{
					if(!g.getNodeSet().toString().contains(ugraph.getEdgeSource(s).toString()))
					{
						g.addNode(ugraph.getEdgeSource(s).toString());
						g.getNode(ugraph.getEdgeSource(s).toString()).setAttribute("ui.label", ugraph.getEdgeSource(s).toString());

					}
				}
					
				if(!ugraph.getEdgeTarget(s).toString().equals(""+this.ego))
				{
					if(!g.getNodeSet().toString().contains(ugraph.getEdgeTarget(s).toString()))
					{
						g.addNode(ugraph.getEdgeTarget(s).toString());
						g.getNode(ugraph.getEdgeTarget(s).toString()).setAttribute("ui.label", ugraph.getEdgeTarget(s).toString());		
					}
				}
				


			}
			
			for(DefaultWeightedEdge e:ugraph.edgeSet())
			{
				if(ugraph.getEdgeSource(e).equals(this.ego)){
					g.addEdge("(EGO, "+ugraph.getEdgeTarget(e).toString()+")", "EGO", ugraph.getEdgeTarget(e).toString());

				}
				else
				if(ugraph.getEdgeTarget(e).equals(this.ego)){
					g.addEdge("("+ugraph.getEdgeSource(e).toString()+",EGO)", ugraph.getEdgeSource(e).toString(), "EGO");

				}
				else 	g.addEdge("("+ugraph.getEdgeSource(e).toString()+","+ugraph.getEdgeTarget(e).toString()+")", ugraph.getEdgeSource(e).toString(), ugraph.getEdgeTarget(e).toString());

				//Edge edge=g.addEdge("("+ugraph.getEdgeSource(e).toString()+","+ugraph.getEdgeTarget(e).toString()+")", ugraph.getEdgeSource(e).toString(), ugraph.getEdgeTarget(e).toString());
				//= g.addEdge("("+ugraph.getEdgeSource(e)+","+ugraph.getEdgeTarget(e)+")", g.getNode(ugraph.getEdgeSource(e).toString()) , g.getNode(ugraph.getEdgeTarget(e).toString()), true);
				//edge.setAttribute("ui.label", ugraph.getEdgeWeight(e));
			}
		
			
			//display
			g.display();
		
			SpriteManager sm =new SpriteManager(g);                              

			Sprite title=sm.addSprite("title");
			title.setAttribute("ui.label", String.format("Personal network of ego %d ", this.getEgo())+", k= "+this.k+", year= "+this.year);
			title.setPosition(Units.PX, 20, 20, 0);
			
			Sprite nbNodes=sm.addSprite("nbNodes");  
			nbNodes.setAttribute("ui.label", String.format("Number of nodes = %d ", this.nodesNumber()));
			nbNodes.setPosition(Units.PX, 600, 20, 0);

			Sprite nbEdges=sm.addSprite("nbEdges");  
			nbEdges.setAttribute("ui.label", String.format("Number of edges = %d ", this.edgesNumber()));
			nbEdges.setPosition(Units.PX, 600, 40, 0);
			 
		
			g.getNode("EGO").setAttribute("ui.label", this.ego); //remove if I want t have "EGO" as label of the ego node
			
			
			
			g.addAttribute("ui.quality");
		    g.addAttribute("ui.antialias");
		    g.setAttribute("ui.stylesheet", "" +
		                            "edge {" +
		                            "   size: 1px;" +
		                            "   fill-color: orange;" +
		                            "}" +
		                            " node {size: 10px; fill-color: steelblue;}"+
		                            " sprite#nbNodes {size: 0.5px; text-color: steelblue; text-size: 16; }"+
		                            " sprite#nbEdges {size: 0.5px; text-color: orange; text-size: 16; }"+
		                            " sprite#title {size: 0.5px; text-color: YELLOWGREEN; text-size: 22; }"+
		    						" node#EGO {size: 20px; fill-color: YELLOWGREEN;}"  ); 

		    						

		    
			
			
		}
	
	/**
	 * TODO to revisit
	 * @param predictedEgoNetwork the ego network predicted by the model
	 * @param ego id of the ego
	 * @param k 
	 * @return apply Dijkstra algorithm to check if the predicted ego network moved to the next level (k+1)
	 */
	public void checkMoveToNextLevel(SimpleWeightedGraph<Integer, DefaultWeightedEdge> predictedEgoNetwork, double k) {
		
			//Boolean moveToNextLevel=false;
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> intermediateEgoNetwork=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
			intermediateEgoNetwork=predictedEgoNetwork;

			List<Double> findMaxKList=new ArrayList<>();
			findMaxKList.add(k);
			// Set all edge weights to 1
			for(DefaultWeightedEdge e:intermediateEgoNetwork.edgeSet())
			{
				intermediateEgoNetwork.setEdgeWeight(e, 1);
			}
			
			for(int vertex:intermediateEgoNetwork.vertexSet())
			{
				DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraAlgo=new DijkstraShortestPath<Integer, DefaultWeightedEdge>(intermediateEgoNetwork, this.ego, vertex);
				if(dijkstraAlgo.getPathLength()>k)
				{
					findMaxKList.add(dijkstraAlgo.getPathLength());
					//System.out.println("Distance between "+vertex+" and "+ ego +"= " + dijkstraAlgo.getPathLength());
					System.out.println("Edge("+vertex+" , "+ this.ego +") moves to the next level k= " +dijkstraAlgo.getPathLength());
					//moveToNextLevel=true;
				}
			}
			System.out.println("Level of the ego network, k= "+ Collections.max(findMaxKList));

	}
	
	/**
	 * TODO to revisit
	 * @param predictedEgoNetwork
	 * @param ego
	 * @param k
	 * @return the list of nodes existing in the ego network with k-1 given an ego network with k
	 */
	public List<Integer> getNodesOfLowerLevels(SimpleWeightedGraph<Integer, DefaultWeightedEdge> predictedEgoNetwork, double k)
	{
		List<Integer> nodesOfLowerLevels=new ArrayList<>();
		SimpleWeightedGraph<Integer, DefaultWeightedEdge> intermediateEgoNetwork=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		intermediateEgoNetwork=predictedEgoNetwork;
		
		for(DefaultWeightedEdge e:intermediateEgoNetwork.edgeSet())
		{
			intermediateEgoNetwork.setEdgeWeight(e, 1);
		}
		for(int vertex:intermediateEgoNetwork.vertexSet())
		{
			DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraAlgo=new DijkstraShortestPath<Integer, DefaultWeightedEdge>(intermediateEgoNetwork, this.ego, vertex);
			if(dijkstraAlgo.getPathLength()<k)
			{
				if (vertex!=this.ego) nodesOfLowerLevels.add(vertex);
				//System.out.println("Distance between "+vertex+" and "+ ego +"= " + dijkstraAlgo.getPathLength());
			}
		}
		return nodesOfLowerLevels;
	}
			
	/**
	 * 
	 * @param ego
	 * @param egoNetwork
	 * @return the betweenness of the ego inside the egoNetwork
	 */
	/*
	public double getEgoBetwenness(){
		FloydWarshallShortestPaths<Integer, DefaultWeightedEdge> floydWarshallAlgo = new FloydWarshallShortestPaths<Integer, DefaultWeightedEdge>(this.ugraph);
		

		
		
		Collection<GraphPath<Integer, DefaultWeightedEdge>> allShortestPaths= floydWarshallAlgo.getShortestPaths();
				
		for(final GraphPath<Integer, DefaultWeightedEdge> shortestpath: allShortestPaths){
			if(shortestpath.getStartVertex()!=this.ego)
				if (shortestpath.getEndVertex()!=this.ego)
				{
			        for(final DefaultWeightedEdge e: shortestpath.getEdgeList()){
			            	if(this.ugraph.getEdgeSource(e)==this.ego) betweenness++;
			            	if (this.ugraph.getEdgeTarget(e)==this.ego) betweenness++;
						
			         }		
				}					
		}
				
		return betweenness/2;		
	}
*/
	/**
	 * TODO to check
	 * @param nbLinksBetweenFirstLevelALters
	 * @return the effective size where Redundancy= nbLinksBetweenALters()level1*2 / egoDegree
	 * and EffectiveSize= egoDegree - Redundancy
	 */
	public double getEffectiveSize(){
		int nbLinksBetweenFirstLevelALters=this.edgesNumber()-this.egoDegree();
		double redundancy=(nbLinksBetweenFirstLevelALters*2);
		redundancy=redundancy/this.egoDegree();
		return (this.egoDegree() - redundancy);
	}

	/**
	 * TODO to check
	 * @return the embeddedness of the ego inside the first level ego network (k=1)
	 */
	public double getEmbeddedness(){
		/* * 
		 * 	Zego = 1/n Ʃj O*ego,j 
		 * 	Zalters=2/n(n-1) Ʃ  N(i)intersect N(j)-1/N(i)U N(j)-1
		 * */
		//Zego
		double embeddedness=0;
		List<Integer> alterNeighbors=new ArrayList<>();
	    for(int vertex:this.getNeighbors(this.ego)){
	    	alterNeighbors=Graphs.neighborListOf(this.ugraph, vertex);
	    
	    	for(int neighbor: alterNeighbors){
	    		if(this.getNeighbors(this.ego).contains(neighbor)) embeddedness ++;
	    	}
	    }
	    embeddedness=embeddedness/2;
		return embeddedness/this.nodesNumber();
	}
	
	
}
