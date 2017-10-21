package com.ego.test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import nl.peterbloem.powerlaws.Continuous;
import nl.peterbloem.powerlaws.Discrete;

/**
 * This class implement methods specific to undirected networks
 * @author sarah
 *
 */
public class UndirectedNetwork extends Network {

	
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph;//=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	public UndirectedNetwork(){
		ugraph=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	/**
	 * Sets a graph
	 * @param graph
	 */
	public void setNetwork(SimpleWeightedGraph<Integer, DefaultWeightedEdge> graph){
		this.ugraph=graph;
	}
	
	/**
	 * @return graph
	 */
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getNetwork(){
		return this.ugraph;
	}
	
	
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> retrieveWholeUndirectedNetwork(MySQLAccess msqla, int year) throws Exception
	{

			RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
			retrieveNetwork.retrieveWholeCoAuthorsNetwork(msqla, year);
			this.ugraph=retrieveNetwork.ugraph;
			return this.ugraph;
	}
	
	/**
	 * 
	 * @return the list of degrees of all the nodes in the graph
	 */
	public List<Integer> getDegreesList(){
		List<Integer> degreesList=new ArrayList<Integer>();
		for(int vertex:ugraph.vertexSet()){
			degreesList.add(ugraph.degreeOf(vertex));
		}
		return degreesList;
	}
	
	
	@Override
	/**
	 * returns the number of nodes
	 */
	public int nodesNumber() {
		
		return this.ugraph.vertexSet().size();
	}

	@Override
	/**
	 * returns the number of edges
	 */
	public int edgesNumber() {
		
		return this.ugraph.edgeSet().size();
	}

	@Override
	/**
	 * Allow to visualize the graph
	 */
	public void viewNetwork() {
		
	Graph g=new DefaultGraph("Co-authors Ego-network");
		
		for(int s:ugraph.vertexSet())
		{
			g.addNode(""+s);
			g.getNode(""+s).setAttribute("ui.label", ""+s);
		}
		
		for(DefaultWeightedEdge s:ugraph.edgeSet())
		{
			if(!g.getNodeSet().toString().contains(ugraph.getEdgeSource(s).toString()))
				g.addNode(ugraph.getEdgeSource(s).toString());
								
			if(!g.getNodeSet().toString().contains(ugraph.getEdgeTarget(s).toString()))
				g.addNode(ugraph.getEdgeTarget(s).toString());

			g.getNode(ugraph.getEdgeSource(s).toString()).setAttribute("ui.label", ugraph.getEdgeSource(s).toString());
			g.getNode(ugraph.getEdgeTarget(s).toString()).setAttribute("ui.label", ugraph.getEdgeTarget(s).toString());		
		}
		
		for(DefaultWeightedEdge e:ugraph.edgeSet())
		{
			Edge edge=g.addEdge("("+ugraph.getEdgeSource(e).toString()+","+ugraph.getEdgeTarget(e).toString()+")", ugraph.getEdgeSource(e).toString(), ugraph.getEdgeTarget(e).toString());
			//= g.addEdge("("+ugraph.getEdgeSource(e)+","+ugraph.getEdgeTarget(e)+")", g.getNode(ugraph.getEdgeSource(e).toString()) , g.getNode(ugraph.getEdgeTarget(e).toString()), true);
			edge.setAttribute("ui.label", ugraph.getEdgeWeight(e));
		}

		g.addAttribute("ui.quality");
		g.addAttribute("ui.antialias");
		g.addAttribute("ui.stylesheet",	"edge { fill-color: grey; }");
		
		//display
		g.display(true);
		
		//Compute betweenness using graphStream
		/*
		 BetweennessCentrality bcb=new BetweennessCentrality();
		 bcb.betweennessCentrality(g);
		 System.out.println("Betwenness using graphstream= "+bcb.centrality(g.getNode("1")));
		*/
		
		//Export graph
		//String fileName= "" + ego +"_"+ theYear ;
		
		/*	Export to DOT format using JGraphT
		 * 
		 *  DOTExporter<Integer, DefaultWeightedEdge> out=new DOTExporter<Integer, DefaultWeightedEdge>();
		 *  out.export(new FileWriter("C:\\Users\\sarah\\"+fileName+".dot"), en);
		 *  
		 */
 
		/*
		 * Export to GML using GraphStream
		 * 
		 * FileSinkGML exportGML=new FileSinkGML();
		 * exportGML.writeAll(g, "C:\\Users\\sarah\\"+fileName+"");
		 */
		
	}

	@Override
	public void printNetwork() {
		System.out.println("Number of nodes: "+this.ugraph.vertexSet().size());
		System.out.println("Number of edges: "+this.ugraph.edgeSet().size());
		
	}

	@Override
	/**
	 * returns the density of the graph
	 */
	public double density() {

		int n=this.nodesNumber();
		int t=this.edgesNumber();
		double density=(n-1)*n;
		density=t/density;
		return  density;
	}

	@Override
	/**
	 * returns the average degree inside the network
	 */
	public double averageDegree() {
		double meanDegree=0;
		for(int node: this.ugraph.vertexSet())
		{
			meanDegree+=this.ugraph.degreeOf(node);
		}
		meanDegree=meanDegree/(this.ugraph.vertexSet().size());
		return meanDegree;
	}

	@Override
	/**
	 * returns the neighbors list of a give node
	 */
	public List<Integer> getNeighbors(int node) {
		/*
		List<Integer> neighbors=new ArrayList<>();
		if(ugraph.containsVertex(node))
		for(DefaultWeightedEdge edge:ugraph.edgesOf(node))
			{
			 if(ugraph.getEdgeTarget(edge)==node) neighbors.add(ugraph.getEdgeSource(edge));
			 else neighbors.add(ugraph.getEdgeTarget(edge));
			}
		return neighbors;
		*/	
		return Graphs.neighborListOf(this.ugraph, node);
	}
	
	/**
	 * 
	 * @param source edge source
	 * @param target edge target
	 * @param transitiveEdges list of transitive edges
	 * @return yes or no as the the edge (source, target) belongs to a list or not
	 */
	
	public boolean edgeBelongsToList(int source, int target, List<DefaultWeightedEdge> edgesList){
		if(this.ugraph.containsEdge(source, target)) {
			if(!edgesList.contains(this.ugraph.getEdge(source, target)))
				edgesList.add(this.ugraph.getEdge(source, target));
			return true;
		}
		else return false;
	}
	
	/**
	 * 
	 * @param node1
	 * @param node2
	 * @return a print whether an edge exists between nodes source and target 
	 */
	public void checkEdgeExists(int source, int target){
		if(this.ugraph.containsEdge(source, target)) System.out.println("This edge exists");
		else System.out.println("This edge does not exist");
	}
	
	/**
	 * @param network predicted network at time t+1 
	 * @param newNode 
	 * @return the list of edges from/to a new node existing (edges) in this.ugraph(predicted) and in network(real): well predicted edges
	 */
	public List<DefaultWeightedEdge> checkEdgeExists(SimpleWeightedGraph<Integer, DefaultWeightedEdge> network, int newNode) {
		List<DefaultWeightedEdge> wellPredictedEdges=new ArrayList<>();
		for(DefaultWeightedEdge newNodeEdge:this.ugraph.edgesOf(newNode))
		{
			if(network.containsEdge(this.ugraph.getEdgeSource(newNodeEdge), this.ugraph.getEdgeTarget(newNodeEdge)))
			{
				if(!wellPredictedEdges.contains(newNodeEdge))
				wellPredictedEdges.add(newNodeEdge);
			}
				
		}
		return wellPredictedEdges;
	}
	
	/**
	 * @return the local clustering coefficient of the network
	 * C(v)=2Nv/Kv*(Kv-1) where Nv is the number of links between neighbors of v and Kv is the degree of node v
	 */
	public double getLocalClusteringCoefficientOf(int vertex){
		CentralityComputer<Integer, DefaultWeightedEdge> centralityComputer=new CentralityComputer<>(this.ugraph);
		double clusteringCoeff= centralityComputer.findClusteringOf(vertex);
		return clusteringCoeff;
	}
	
	/**
	 * 
	 * @param vertex for which we compute the betweennes centrality
	 * @return the betweenness
	 */
	public double getBetweennessOf(int vertex){
		CentralityComputer<Integer, DefaultWeightedEdge> centralityComputer=new CentralityComputer<>(this.ugraph);
		double betweenness= centralityComputer.findBetweennessOf(vertex);
		return betweenness;
	}
	
	/**
	 * 
	 * @param vertex for which we compute the closeness centrality
	 * @return the closeness centrality
	 */
	public double getClosenessOf(int vertex){
		CentralityComputer<Integer, DefaultWeightedEdge> centralityComputer=new CentralityComputer<>(this.ugraph);
		double closeness= centralityComputer.findClosenessOf(vertex);
		return closeness;
	}
	
	/**
	 * 
	 * @param vertex for which we compute the degree centrality
	 * @return the degree centrality
	 */
	public double getDegreeOf(int vertex){
		CentralityComputer<Integer, DefaultWeightedEdge> centralityComputer=new CentralityComputer<>(this.ugraph);
		double degree= centralityComputer.findDegreeOf(vertex);
		return degree;
	}
	
	
	/**
	 * 
	 * @param egoNetwork1
	 * @param egoNetwork2
	 * @return the list of nodes existing in egoNetwork2 but do not exist in egoNetwork1
	 */
	public List<Integer> networksDifference(SimpleWeightedGraph<Integer, DefaultWeightedEdge> egoNetwork1, SimpleWeightedGraph<Integer, DefaultWeightedEdge> egoNetwork2)
	{
		List<Integer> newNodes=new ArrayList<Integer>();
		for(int node :egoNetwork2.vertexSet())
		{
			if(!egoNetwork1.containsVertex(node)) newNodes.add(node);
		}
		return newNodes;
	}
	
	/**
	 * 
	 * @param graph
	 * @return the number of triangles inside the ego networks
	 */
	public int countTriangles() {
		int count = 0;

		for (int v : this.ugraph.vertexSet()) {
			int[] neighbors = new int[this.ugraph.edgesOf(v).size()];
			
			int i = 0;
			
			for (DefaultWeightedEdge e : this.ugraph.edgesOf(v)) {
				neighbors[i] = this.ugraph.getEdgeTarget(e);
				if (neighbors[i] == v) {
					neighbors[i] = this.ugraph.getEdgeSource(e);
				}
				i++;
			}
			
			for (i=0; i < this.getNeighbors(v).size(); i++) {
				for (int j = i + 1; j < this.getNeighbors(v).size(); j++) {
			
					if (this.ugraph.containsEdge(neighbors[i], neighbors[j]) || this.ugraph.containsEdge(neighbors[j], neighbors[i])) 
					{
						count++;
					}
				}
			}
		}
		return count/3;
	}
	
	/**
	 * 
	 * @param graph
	 * @return the number of connected triples inside the ego network
	 */
	public int countConnectedTriples() {
		//int count = this.countTriangles()*3;
		int count =0;
		for (Integer v : this.ugraph.vertexSet()) {
			Set<DefaultWeightedEdge> edges = this.ugraph.edgesOf(v);
			int[] neighbors = new int[edges.size()];
			int i = 0;
			for (DefaultWeightedEdge e : edges) {
				neighbors[i] = this.ugraph.getEdgeTarget(e);
				if (neighbors[i] == v) {
					neighbors[i] = this.ugraph.getEdgeSource(e);
				}
				i++;
			}
			for (i=0; i < neighbors.length; i++) {
				for (int j = i + 1; j < neighbors.length; j++) {
					if (!this.ugraph.containsEdge(neighbors[i], neighbors[j]) || 
							!this.ugraph.containsEdge(neighbors[j], neighbors[i])) 
					{
						count++;
					}
				}
			}
		}

		return count;
	}
	
	
	/**
	 * 
	 * @param egoNetwork
	 * @return the global clustering coefficient of an ego network
	 */
	public double getGlobalClusteringCoeff(){
    	// Compute global clustering coefficient: C=3 * #triangles/#connected triples

		double nbTriangles=this.countTriangles();
    	//System.out.println("Nb triangles= "+nbTriangles); 
    	
    	double nbConnectedTriples=this.countConnectedTriples();
    	//System.out.println("Nb connected triples= "+nbConnectedTriples);

    	//double globalClusterinCoeff= (nbTriangles*3)/nbConnectedTriples;	
    	double globalClusterinCoeff= nbTriangles/nbConnectedTriples;
    	return globalClusterinCoeff;
	}
	
	/**
	 * 
	 * @return true if the network exhibits a power law distribution
	 */
	public Boolean isPoweLaw(){

		if(this.ugraph.vertexSet().size()!=1)
		{
			Collection<Integer> collection = new ArrayList<Integer>(this.getDegreesList());
			double exponent = Discrete.fit(collection).fit().exponent();
	
			//System.out.println("Exponent= "+exponent);
			Discrete model = Discrete.fit(collection).fit();
			double significance = 0;
			// Calculate the significance based on 100 trials
			if (exponent>= 2) 
				significance = model.significance(collection, 1000);
			//System.out.println("Significance= "+significance);
			return (significance>0.1);
	
		}
		else return null; //means the graph contains a single vertex
		
	}
	
	/**
	 * 
	 * @param node
	 * @return the weight of the node = sum of all node's edges weights
	 */
	public int getNodeWeight(int node){
		int sj=0;
		for(DefaultWeightedEdge edge:this.ugraph.edgesOf(node))
		{
			sj+=this.ugraph.getEdgeWeight(edge);
		}
		return sj;		
	}
	
	/**
	 * 
	 * @return the number of publications for an author among all venues before year
	 * @throws Exception
	 */
	public int retrieveNumberPublicationsPerAuthorPerYear(MySQLAccess msqla, int idAuthor, int year) throws Exception
	{
			RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
			return retrieveNetwork.retrieveNumberPublicationsPerAuthor(msqla, idAuthor, year);
			
	}
	
	/**
	 * 
	 * @param idAuthor
	 * @param year
	 * @return couples (venue, number publications) realized by an author before year
	 * @throws Exception
	 */
	public Map<String, Integer> retrieveNumberPublicationsPerAuthorPerYearPerVenue(MySQLAccess msqla, int idAuthor, int year) throws Exception
	{
			RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
			return retrieveNetwork.retrieveNumberPublicationsPerAuthorPerVenue(msqla, idAuthor, year);
			
	}
	
	
	
	/**
	 * 
	 * @param msqla
	 * @param idAuthor
	 * @return the year of the first publication of an author
	 * @throws SQLException
	 */
	
	public int retrieveYearFirstPublicationOfAuthor(MySQLAccess msqla,int idAuthor) throws SQLException
	{
		RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
		return retrieveNetwork.retrieveYearOfFirstPublicationOfAuthor(msqla, idAuthor);
	}
	
	/**
	 * 
	 * @param msqla
	 * @param idAuthor
	 * @return 1 if the author belongs to the giant component of the network, 0 if not, -1 if node/answer not found
	 * @throws SQLException
	 */
	public int belongsToGiantComponent(MySQLAccess msqla, int idAuthor)throws SQLException
	{
		RetrieveNetwork retrieveNetwork=new RetrieveNetwork();
		return retrieveNetwork.belongsToGiantComponent(msqla, idAuthor);
	}
	
	
	
	
	/**
	 * 	Select a number of nodes proportional to their degree
 		For each node:
 		Select two neighbors randomly to meet if no edge exists already between them and if the maximum degree allowed is not reached
	 * TODO test the model
	 * 
	 */
	public void evolutionModelGin2001(){
		
		//int maxDegree=9;
		int sommeDegrees=0;
		ArrayList<Integer> nodeSet= new ArrayList<Integer>();
		nodeSet.addAll(this.ugraph.vertexSet());		
		
		for(int s:this.ugraph.vertexSet()){
			int degree=this.ugraph.degreeOf(s);
			for(int i = 1; i < degree; i++)
			{
				nodeSet.add(s);
			}		
		}

		//System.out.println(this.ugraph.vertexSet().toString());
		
		for(int s: this.ugraph.vertexSet()){
			sommeDegrees = sommeDegrees+ this.ugraph.degreeOf(s);
		}
		
			
		int index1 = (new Random().nextInt(this.ugraph.vertexSet().size()));//* sommeDegrees;
		int index2 = (new Random().nextInt(this.ugraph.vertexSet().size()));//* sommeDegrees;
		
		int node1 = nodeSet.get(index1);
		int node2 =nodeSet.get(index2);

	    ArrayList<Integer> selectedNodes=new ArrayList<Integer>();
	    selectedNodes.add(node1);
	    selectedNodes.add(node2);

	    System.out.println("Randomely selected nodes:" +node1 +"- " +node2);
	   
	    //Boolean edgeAlreadyExists=false;
	    
	    ArrayList<DefaultWeightedEdge> edgesList=new ArrayList<>();
	    edgesList.addAll(this.ugraph.edgeSet());
	    
	    NeighborIndex<Integer, DefaultWeightedEdge> neighborIndex= new NeighborIndex<>(this.ugraph);

	    for(int s:selectedNodes){
    	   
	    	System.out.println("For node: " +s);

	    		int indexNeighbor1 = (new Random().nextInt(neighborIndex.neighborListOf(s).size()));//* sommeDegrees;
	    		int indexNeighbor2 = (new Random().nextInt(neighborIndex.neighborListOf(s).size()));
	    		
	    		int neighbor1 = nodeSet.get(indexNeighbor1);
	    		int neighbor2 = nodeSet.get(indexNeighbor2);
	    	
	    	    System.out.println("Randomely selected neighbors:" +neighbor1 +"- " +neighbor2);

	    		//linkNodes(this.ugraph, maxDegree, neighbor1, neighbor2, edgeAlreadyExists, edgesList);	    	    
	    		if(neighbor1==neighbor2) System.out.println("The selected nodes are equal ");
	    		else{
	    			if(this.ugraph.containsEdge(neighbor1, neighbor2))
	    				System.out.println("The edge already exists");
	    			
	    			else{//apply method
	    	    		//ComputeOnEgoNetworks with max degree
	       				//if(egoNetwork.degreeOf(neighbor1)<=maxDegree) // else print max degree achieved
	       					//if(egoNetwork.degreeOf(neighbor2)<=maxDegree)
	       					//{
	       						//edgeAlreadyExists=false;
	       						this.ugraph.addEdge(neighbor1, neighbor2);
	       						System.out.println("*********** Edge added= ("+neighbor1+ "," + neighbor2+")");
	       					//}		    				
	    			}
	    		}
    
	    }//end for
	    
	   // System.out.println("Degree of 1099= " +this.ugraph.degreeOf(1099));
	    //System.out.println("Degree of 4515= " +this.ugraph.degreeOf(4515));
	}
	
	/**
	 * 	At each time step we add a node, the network at the beginning is made of a small set of nodes
		For each new node i, we first pick randomly one similar node from Similar(i) � (1)
		Then, we recommend more friends for the new node: 
		With probability p=1-1/|RFi| do:
		One node is selected from the set RFi  (neighbors of the node selected in (1), friend of friend of I) with probability pj=Wj/Sum Wk, where k belongs to RFi
		With probability 1-p do:
		One node is selected from the set Similar(i)/RFi U NSi (nodes which are inside the neighborhood of the new node but are not similar)  with probability pj=Zj/Sum Zk, where k belongs to Similar(i) but k does not belong to (RFi U NSi)
	 * TODO test the model, reorganize
	 */
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> evolutionModelSun20015(int ego,SimpleWeightedGraph<Integer, DefaultWeightedEdge> nextEgoNetwork, List<Integer> nodesToAdd,Map<Integer, List<String>> authorVenuesMap){
		int maxDegree=5;
	    // DecimalFormat df = new DecimalFormat("0.000");
	    Boolean ignoredNode=false;

	    //remove from authorVenues map, authors not in the egoNetwork
		final Iterator<Integer> iter=authorVenuesMap.keySet().iterator();
		while(iter.hasNext()){
				int a=iter.next();
				if(!this.ugraph.containsVertex(a))    			
				{
					if(!nodesToAdd.contains(a))
						iter.remove();
	    		}
		}
			
		//add new node
		for(int node: nodesToAdd)
		{
			ignoredNode=false;
			List<Integer> similarNodes=new ArrayList<Integer>();
			Map<Integer, Integer> similarNodesCommonVenuesNb= new HashMap<Integer, Integer>();
			List<String> venuesList=new ArrayList<String>();
			//	int nbCommonVenues=0;
				
			System.out.println("****************************************");
			System.out.println("The new node is: " +node);
				
			//1- get its venue
			venuesList.addAll(authorVenuesMap.get(node));
			
			//2-choose a similar node from existing nodes
			for(int existingNode: authorVenuesMap.keySet())
			{
				if(!nodesToAdd.contains(existingNode))
				{
					for(String venue:venuesList)
					{
					if(authorVenuesMap.get(existingNode).contains(venue))
						if(similarNodes!=null)
							if(existingNode!=ego)
								//	if(!similarNodesCommonVenuesNb.containsKey(existingNode))
								//	{	
								if(!similarNodes.contains(existingNode))
									similarNodes.add(existingNode);
												//nbCommonVenues=1;
												//similarNodesCommonVenuesNb.put(existingNode, nbCommonVenues);														
								//	}
								/*	else{
												similarNodesCommonVenuesNb.replace(existingNode, nbCommonVenues++);
									}*/
					}
				}
			}
				
				
			//Fill similar nodes list with repeating a similar node as its corresponding nb of common venues
			/*
			for(int n:similarNodesCommonVenuesNb.keySet())
			{
				for(int i=1; i<=similarNodesCommonVenuesNb.get(n); i++)
				{
						similarNodes.add(n);
				}
			}
			*/
				
			System.out.println(similarNodesCommonVenuesNb.toString());
			System.out.println("Venues of new node: " +venuesList.toString());
			System.out.println("Similar nodes are: " +similarNodes.toString());
			
			// Pick one randomly with probability:
				
			//compute the probability: pj=Sj/Sum Sk,  where k belongs to Similar(i), Si is the sum of weights attached to edges of node i
			// for each similar node
			double sj=0;
			double sk=0;
			Map<Integer, Double> nodeWeight=new java.util.HashMap<Integer, Double>();
			//Sj =?
			for(int similarNode: similarNodes)
			{
			// sum of weights attached to edges of node similar node
				sj = getNodeWeight(similarNode);
			// compute sum 
				nodeWeight.put(similarNode, sj);
				sk+=sj;
			}
				
			List<Pair<Integer,Double>> itemWeights = new ArrayList<>();
			for (int n : nodeWeight.keySet()) {
				    itemWeights.add(new Pair<Integer, Double>(n,(nodeWeight.get(n)/sk)));
			}
				
			int selectedRandomSimilarNode = 0;
			if(!nodeWeight.isEmpty())
			{
				selectedRandomSimilarNode =  new EnumeratedDistribution<Integer>(itemWeights).sample();
				System.out.println("*** First selected node");
				System.out.println("Randomly selected similar existing node: " +selectedRandomSimilarNode +"\n");
			}
			else {
				System.out.println("*** No similar nodes!!");
				this.ugraph.addVertex(node);
				ignoredNode=true;
				break;
			}

				/*
				Random r = new Random();
				if(!similarNodes.isEmpty())
				{
					 selectedRandomSimilarNode=similarNodes.get(r.nextInt(similarNodes.size()));
				}
				else {
					break;
				}
				*/

			//add the new edge and the new link
			this.ugraph.addVertex(node);
			if(node!=selectedRandomSimilarNode)
				this.ugraph.addEdge(node, selectedRandomSimilarNode);
				
			//add the new node to authorVenuesMap
			authorVenuesMap.put(node, venuesList);
			
			//friends of the selected similar node
			List<Integer> friends=new ArrayList<>();//RF(i)
			for(DefaultWeightedEdge e:this.ugraph.edgesOf(selectedRandomSimilarNode))
			{
				if(this.ugraph.getEdgeTarget(e)==selectedRandomSimilarNode) 
					friends.add(this.ugraph.getEdgeSource(e));
				else friends.add(this.ugraph.getEdgeTarget(e));
			}
				
			//remove the new node from friends list
			Iterator<Integer> iterator = friends.iterator();
			while(iterator.hasNext()){
				   int currentNode = iterator.next();
				   if(currentNode==node){
				        iterator.remove();
				    }
			}
			System.out.println("Friends of the similar node: " +friends.toString());
				
			//Recommend more until max degree is reached
			//after updating RFi and NSi
			while (this.ugraph.degreeOf(node)< maxDegree)
			{
				//to keep the ego network at level 2
				/*
						if(egoNetwork.degreeOf(node)==maxDegree-1)
						{
							ComputeOnEgoNetworks.checkMoveToNextLevel(egoNetwork, ego, k);
							if(ComputeOnEgoNetworks.moveToNextLevel)
								if(ComputeOnEgoNetworks.getNodesOfLowerLevels(egoNetwork, ego, k)!=null)
								{
									Random rdm=new Random();
									int randSelectedNodeFromSet= ComputeOnEgoNetworks.getNodesOfLowerLevels(egoNetwork, ego, k).get(rdm.nextInt(ComputeOnEgoNetworks.getNodesOfLowerLevels(egoNetwork, ego, k).size()));
									egoNetwork.addVertex(randSelectedNodeFromSet);
									if(node!=randSelectedNodeFromSet)
										 egoNetwork.addEdge(node, randSelectedNodeFromSet);
									System.out.println("Randomly selected last node from lower levels= "+randSelectedNodeFromSet);
									break;
								}						
						}	
				*/				
				//************** end keep level 2
					
				//Choose one of recommendation types based on the probability: p=1-1/|RFi|
	 			double p=(double)1/(friends.size());
				p=(double)1-p;
				//System.out.println("Probability to select the recommendation type: " +df.format(p));
						
				if(Math.random()<=p ) 
				{
					//1st type of recommendation with probability p
					//select a friend of the selected node (cascading recommendation)
					// compute the probability pj=Wj/Sum Wk , where k belongs to RFi (friends of friend of i)
					double wj=0;
					double wk=0;	
					nodeWeight=new java.util.HashMap<Integer, Double>();
					for(int friendOfFriend: friends)
					{
						// sum of weights attached to edges of node similar node
						wj = getNodeWeight(friendOfFriend);//comment if we want to use the weight between 2 nodes
						//System.out.println("global weight of the node= " +wj);
						//to select the friend with largest weight with selectedRandomSimilarNode instead of largest global weight
						//wj=egoNetwork.getEdgeWeight(egoNetwork.getEdge(friendOfFriend, selectedRandomSimilarNode));
						//System.out.println("weight with "+friendOfFriend +" = " +wj);
						// compute sum 
						nodeWeight.put(friendOfFriend, wj);	
						wk+=wj;
					}
							
					//compute in a map for each friend of friend corresponding probability pj=Wj/Sum Wk
					List<Pair<Integer, Double>> friendWeights = new ArrayList<>();
					for (int n : nodeWeight.keySet()) {
						  friendWeights.add(new Pair<Integer, Double>(n,(nodeWeight.get(n)/wk)));
					}
					System.out.println(friendWeights.toString());

					//choose one node from friends list randomly
					int selectedFriendOfSimilarNode =  new EnumeratedDistribution<Integer>(friendWeights).sample();	
						
						/*
						Random rd = new Random();//with a probability depending on weights
						int selectedFriendOfSimilarNode= friends.get(rd.nextInt(friends.size()));
						*/
					
					System.out.println("Randomly selected friend of similar node (1st recommendation type): " +selectedFriendOfSimilarNode);
					System.out.println("The new node will be connected to: " +selectedFriendOfSimilarNode +"\n");
						
					//add the new edge and the new link
					this.ugraph.addVertex(selectedFriendOfSimilarNode);
					if(node!=selectedFriendOfSimilarNode)
						 this.ugraph.addEdge(node, selectedFriendOfSimilarNode);
						 
					if(!friends.contains(node)) 
							 friends.add(node);
							 
							//System.out.println("NSi: " +newNodeFriends.toString());
							//System.out.println("RFi: " +friends.toString());
							//System.out.println("Similar(i): " +similarNodes.toString());
					}
					/**********************************************************************/
					else{
							
					//2nd type of recommendation with probability (1-p)
					// compute similar(i)/RFi U NSi
					List<Integer> set=new ArrayList<>();

					// NSi=? friends of i (i=new node)
					List<Integer> newNodeFriends=new ArrayList<>();
					for(DefaultWeightedEdge e:this.ugraph.edgesOf(node))
					{
						if(this.ugraph.getEdgeTarget(e)==node)
								newNodeFriends.add(this.ugraph.getEdgeSource(e));
						else newNodeFriends.add(this.ugraph.getEdgeTarget(e));
					}
					//System.out.println("NSi: " +newNodeFriends.toString());
					//System.out.println("RFi: " +friends.toString());
					//System.out.println("Similar(i): " +similarNodes.toString());

					// set= similar(i)/RFi U NSi
					List<Integer> intermediate=new ArrayList<>();
					intermediate.addAll(friends);
					for(int newFriend:newNodeFriends)
					{
						if(!intermediate.contains(newFriend)) intermediate.add(newFriend);
					}
					for(int similar:similarNodes)
					{
						//if (similar n'existe pas dans friends U NSi  ) add to set
						if(!intermediate.contains(similar))
							set.add(similar);							
					}			
						
					// compute the probability pj=Zj/Sum Zk , where k belongs to Similar(i) & k does not belong to (RFi U NSi)
					double zj=0;
					double zk=0;	
					nodeWeight=new java.util.HashMap<Integer, Double>();
					for(int n: set)
					{
							// sum of weights attached to edges of node similar node
							zj = getNodeWeight(n);
							// compute sum 
							nodeWeight.put(n, zj);
							zk+=zj;
					}
						
					//compute in a map for each node in the set corresponding probability pj=Zj/Sum Zk
					List<Pair<Integer,Double>> friendWeights = new ArrayList<>();
					for (int n : nodeWeight.keySet()) {
							if(zk!=0)
								friendWeights.add(new Pair<Integer, Double>(n,(nodeWeight.get(n)/zk)));
							else break;
					}

					//choose one node from friends list randomly
					int selectedNodeFromSet ;
					if(!friendWeights.isEmpty())
					{
						selectedNodeFromSet =  new EnumeratedDistribution<Integer>(friendWeights).sample();	
						System.out.println("Selected node from the subset (2nd recommendation type): " +selectedNodeFromSet);
						System.out.println("The new node will be connected to: " +selectedNodeFromSet);
										
						//add the new edge and the new link					
						this.ugraph.addVertex(selectedNodeFromSet);
						if(node!=selectedNodeFromSet)
							this.ugraph.addEdge(node, selectedNodeFromSet);
					}
					else {
						System.out.println("The set Similar(i)/RF(i) U NS(i) is empty");
						break;
					}
					
					//select a node randomly from the set similar(i)/RFi U NSi (random recommendation) 
					    /*	
						Random rdm=new Random();//with a probability depending on weights
						int selectedNodeFromSet= set.get(rdm.nextInt(set.size()));
						*/
						 //intermediate.clear();		 
					}				
				}	
	 			
	 			if(!ignoredNode)
	 			{
	 				//Get neighbors of new nodes
				//	result+= "Neighbors of new node " +node + " are: "+ ComputeOnEgoNetworks.getNeighbors(egoNetwork, node) +sep;	
					//Check if predicted links exists in the next years
					this.checkEdgeExists(nextEgoNetwork, node);
					//System.out.println("Well predicted edges in the next year(s)");
					//result+= sep+ ComputeOnEgoNetworks.s +" -- Well predicted edges in the next year(s)";
	 			}
				//Reinitialization
				//venuesList.clear();
				friends.clear();
			}
		
			// Well predicted edges
			//TODO write results

		return this.ugraph;	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
