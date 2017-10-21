package com.ego.test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * This class allow to assign the result get from database to a graph object (nodes and edges)
 * @author sarah
 *
 */
public class RetrieveNetwork {

	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	public DefaultDirectedGraph<Integer, DefaultEdge> dgraph=new DefaultDirectedGraph<>(DefaultEdge.class);
	private int level=0;
	private List <Integer> visitedNodes=new ArrayList<Integer>();
	//private Statement statement =MySQLAccess.statement;
		
	/**
	 * Returns the personal network of an author
	 * @param authorsList
	 * @param k
	 * @param year
	 * @throws Exception
	 */
	public void retrieveUndirectedCoAuthorsEgoNetwork(MySQLAccess msqla, List<Integer> authorsList, int k, int year) throws Exception
	{		
		//Map<Integer, Integer> alterWeightWithEgo=new HashMap<Integer,Integer>();
		List <Integer> altersToVisit=new ArrayList<Integer>();
		ResultSet resultSet = null;
		
		try{
			for (int id : authorsList)
			{
				//List<Integer>immediateAlters=new ArrayList<Integer>();	
				resultSet= msqla.executeQuery("select count(weight) as globalWeight, coAuthor, egoAuthor  from coauthors where egoAuthor= "+id +" && yearPaper<= "+year +" group by weight, coAuthor");
				if(!ugraph.containsVertex(id)) {
					ugraph.addVertex(id);
				}		
				if(resultSet.next())
				{ 				
						do{
							int i = resultSet.getInt("coAuthor");
							if (!visitedNodes.contains(i))
								{
									//immediateAlters.add(i);
									//To get weights //TODO 
									//alterWeightWithEgo.put(i, resultSet.getInt("globalWeight"));
									
									if(!altersToVisit.contains(i)) 
										altersToVisit.add(i);
									
									if(!ugraph.containsVertex(i)) 
									{
										ugraph.addVertex(i);
									}
									
									if(!ugraph.containsEdge(id, i))
									{
										ugraph.addEdge(id, i);
										//to assign weights to the edges
										//ugraph.setEdgeWeight(ugraph.getEdge(id, i), alterWeightWithEgo.get(i));
										ugraph.setEdgeWeight(ugraph.getEdge(id, i), resultSet.getInt("globalWeight"));

									}
									
								}
							

						} while (resultSet.next());
						

	
						if(!visitedNodes.contains(id)) visitedNodes.add(id);
						altersToVisit.removeAll(visitedNodes);
					}	
				
			}
			level++;
			
		//	System.out.println(level+" "+ugraph.toString());
			
			if(level<k)
			{
				if (altersToVisit!=null )
					{
						retrieveUndirectedCoAuthorsEgoNetwork(msqla, altersToVisit, k, year);
							
					}
			}
			else if (level==k){ 
				//add links between last level nodes (leaves)
				retrieveUndirectedLinksBetweenLeaves(msqla, altersToVisit, year);
			//	System.out.println(level+" "+ugraph.toString());

			}
			
		}
		catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		finally {
			
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
			msqla.closeStatement();
			//if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			//if (connect != null) try { connect.close(); } catch (SQLException ignore) {}
		}
		
	}

	
	
	
	public void retrieveWholeCoAuthorsNetwork(MySQLAccess msqla, int year) throws Exception
	{		
		ResultSet resultSet = null;
		
		try{
		
				resultSet= msqla.executeQuery("SELECT DISTINCT LEAST(coAuthor , egoAuthor) as node1, GREATEST(coAuthor, egoAuthor) as node2 FROM coauthors where yearPaper<="+year);
						
				if(resultSet.next())
				{ 				
						do{
							int node1 = resultSet.getInt("node1");
							int node2 = resultSet.getInt("node2");

									if(!ugraph.containsVertex(node1)) {
										
										ugraph.addVertex(node1);
									}
									
									if(!ugraph.containsVertex(node2)) 
									{
										ugraph.addVertex(node2);
									}
									
									if(!ugraph.containsEdge(node1, node2))
									{
										ugraph.addEdge(node1, node2);
						
									}

						} while (resultSet.next());
				}

			
		}
		catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		finally {
			
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
			msqla.closeStatement();
			//if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			//if (connect != null) try { connect.close(); } catch (SQLException ignore) {}
		}
		
	}

	
	
	
	
	/**
	 * Returns the personal network augmented with the links between last level nodes (leaves)
	 * @param list
	 * @param year
	 * @throws Exception
	 */
	private void retrieveUndirectedLinksBetweenLeaves(MySQLAccess msqla, List<Integer> list, int year) throws Exception {
		int alter = 0;
		Map<Integer, Integer> alterWeightWithEgo=new HashMap<Integer,Integer>();
		ResultSet resultSet = null;

		try{

			for (int id : list) {
				List<Integer>immediateAlters=new ArrayList<Integer>();	
				resultSet= msqla.executeQuery("select count(weight) as globalWeight, coAuthor, egoAuthor  from coauthors where egoAuthor= "+id +" && yearPaper<= "+year +" group by weight, coAuthor");
					if(resultSet.next())
					{ 
							do{
								alter = resultSet.getInt("coAuthor");
								immediateAlters.add(alter);
								alterWeightWithEgo.put(alter, resultSet.getInt("globalWeight"));
		
							} while (resultSet.next());
					}		
							
					for(int i:immediateAlters){
						if(list.contains(i))
						{
							if(!ugraph.containsEdge(id, i))
							{
								ugraph.addEdge(id, i);
								//to assign weights to the edges
								ugraph.setEdgeWeight(ugraph.getEdge(id, i), alterWeightWithEgo.get(i));
							}						
						}
					}
	
			}

		}
		catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
			msqla.closeStatement();			
			//if (resultSetUndirected != null) try { resultSetUndirected.close(); } catch (SQLException ignore) {}
			//if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
			//if (connect != null) try { connect.close(); } catch (SQLException ignore) {}
		}
	}
	
	/**
	 * to retrieve the max co-authors personal network
	 * @param list of authors
	 * @param year
	 * @throws Exception
	 */
	/*
	public void retrieveMaxCoAuthorsEgoNetwork(MySQLAccess msqla, List<Integer> list, int year) throws Exception{
		int alter=0;
		ResultSet resultSet=null;

		try{
			for (int id : list) {
				if(!visitedNodes.contains(id))
				{	
				List <Integer> altersToVisit=new ArrayList<Integer>();
				resultSet= msqla.executeQuery("select coAuthor from coauthors where egoAuthor= "+id +" && yearPaper<= "+year);
					if(resultSet.next())
					{ 
						do{					
							alter = resultSet.getInt("coAuthor");
							if(!visitedNodes.contains(alter))
							{
									if(!altersToVisit.contains(alter))
								
									{
										altersToVisit.add(alter);
									
										// construct the ego network
										
										if(!this.ugraph.containsVertex(id)) 
										{
											this.ugraph.addVertex(id);
										}
										
										if(!this.ugraph.containsVertex(alter)) 
										{
											this.ugraph.addVertex(alter);
										}
										
										if(!this.ugraph.containsEdge(id, alter))
										{
											this.ugraph.addEdge(id, alter);
										}
										
									}
								}
							} while (resultSet.next());
														
							if(!visitedNodes.contains(id)) {
								visitedNodes.add(id);
							}
							
							altersToVisit.removeAll(visitedNodes);
							
							if (!altersToVisit.isEmpty() )
							{
								//System.out.println(altersToVisit.toString());
								retrieveMaxCoAuthorsEgoNetwork(msqla, altersToVisit, year);												
							}	
							//else return;
					}					
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		throw e;
		}
		finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
			msqla.closeStatement();	

		}
	}
	
	*/
	public int retrieveMaxCoAuthorsEgoNetwork(MySQLAccess msqla, List<Integer> list, int year) throws Exception{
		List <Integer> altersToVisit=new ArrayList<Integer>();
		ResultSet resultSet=null;
		String orSection="";
		int s=0;
		
		for(int idEgo:list)
		{
				s++;
				if(s<list.size())
				orSection+="egoAuthor= " +idEgo +" or " ;
				else orSection+="egoAuthor= " +idEgo;
				
		}

		String query="SELECT egoAuthor, coAuthor FROM coauthors where yearPaper<="+year +" and ("+orSection +")" ;
		try{
					resultSet= msqla.executeQuery(query);
					if(resultSet.next())
					{ 				
						do{
								int coAuthor = resultSet.getInt("coAuthor");
								int egoAuthor =resultSet.getInt("egoAuthor");
								
								if(!ugraph.containsVertex(egoAuthor)) 
								{
									ugraph.addVertex(egoAuthor);
								}		
										
								if(!ugraph.containsVertex(coAuthor)) 
							    {
									ugraph.addVertex(coAuthor);
								}
										
								if(!ugraph.containsEdge(coAuthor, egoAuthor))
								{
									ugraph.addEdge(coAuthor, egoAuthor);
								}
																	
								if (!visitedNodes.contains(coAuthor))
								{
									if(!altersToVisit.contains(coAuthor)) 
											altersToVisit.add(coAuthor);							
								}
								
								if(!visitedNodes.contains(egoAuthor)) visitedNodes.add(egoAuthor);

						} while (resultSet.next());
							
						altersToVisit.removeAll(visitedNodes);							
					}	
					
					if (!altersToVisit.isEmpty() )
					{
						level++;		
						retrieveMaxCoAuthorsEgoNetwork(msqla, altersToVisit, year);								
					}

			}
			
			catch (Exception e) {
				System.out.println(e);
			throw e;
			}
			finally {
				
				if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
				msqla.closeStatement();		
			}
		return level;
	
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param idAuthor
	 * @param year
	 * @return the number of publications made by an author before year among all venues 
	 * @throws Exception
	 */
	
	public int retrieveNumberPublicationsPerAuthor(MySQLAccess msqla, int idAuthor, int year) throws Exception{
		ResultSet resultSet=null;
		int numberPublications=0;
		try{

				resultSet= msqla.executeQuery ("SELECT count(venue) from paper_author where idAuthor= "+idAuthor +" and yearPaper<= "+year);//gives out-EN

				if(resultSet.next())
				{ 
					do{
							numberPublications= resultSet.getInt("count(venue)");
						
					} while (resultSet.next());	
				}
			}
			catch (Exception e) {
				System.out.println(e);
				throw e;
			}
			finally {		

				if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
//				if (statement != null) try { statement.close(); } catch (SQLException ignore) {} 
			  //  if (connect != null) try { connect.close(); } catch (SQLException ignore) {}	
			}
		
		return numberPublications;
		
	}
	
	/**
	 * 
	 * @param idAuthor
	 * @param year
	 * @return a map (venue, numberPublications) the number of publications made by an author before year for each venue 
	 * @throws Exception
	 */
	public Map<String, Integer> retrieveNumberPublicationsPerAuthorPerVenue(MySQLAccess msqla, int idAuthor, int year) throws Exception{
		ResultSet resultSet=null;
		Map<String, Integer> numberOfPublicationsPerAuthorPerVenue=new HashMap<>();
		try{

				resultSet= msqla.executeQuery ("SELECT venue, count(venue) from paper_author where idAuthor= "+idAuthor +" and yearPaper<= "+year+" group by venue");//gives out-EN

				if(resultSet.next())
				{ 
					do{
						numberOfPublicationsPerAuthorPerVenue.put(resultSet.getString("venue"), resultSet.getInt("count(venue)")) ;
						
					} while (resultSet.next());	
				}
			}
			catch (Exception e) {
				System.out.println(e);
				throw e;
			}
			finally {		

				if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
//				if (statement != null) try { statement.close(); } catch (SQLException ignore) {} 
			  //  if (connect != null) try { connect.close(); } catch (SQLException ignore) {}	
			}
		return numberOfPublicationsPerAuthorPerVenue;
		
	}
	
	
	
	public Map<Integer, Integer> retrieveNumberAuthorsPerPaper(MySQLAccess msqla) throws Exception{
		ResultSet resultSet=null;
		Map<Integer, Integer> nbAuthorsPerPaper=new HashMap<Integer, Integer>();
		try{

				resultSet= msqla.executeQuery ("SELECT count(idAuthor) as numberOfAuthorsPerPaper,idPaper FROM `paper_author` group by idPaper");//gives out-EN

				if(resultSet.next())
				{ 
					do{
							int idPaper=resultSet.getInt("idPaper");
							int numberAuthors= resultSet.getInt("numberOfAuthorsPerPaper");
							nbAuthorsPerPaper.put(idPaper, numberAuthors);
							
					} while (resultSet.next());	
				}
			}
			catch (Exception e) {
				System.out.println(e);
				throw e;
			}
			finally {		
				if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
				//if (statement != null) try { statement.close(); } catch (SQLException ignore) {} 
			   // if (connect != null) try { connect.close(); } catch (SQLException ignore) {}	
			}
		return nbAuthorsPerPaper;

		
	}
	
	
	
	public int retrieveYearOfFirstPublicationOfAuthor(MySQLAccess msqla, int idAuthor) throws SQLException
	{
		ResultSet resultSet=msqla.executeQuery ("select yearApparition from authors_apparition where egoAuthor= "+idAuthor);
		if(resultSet.next())
			return resultSet.getInt("yearApparition");
		else return -1; //single author, do not belong to coauthors network (table)
	}
	
	
	
	
	public int belongsToGiantComponent(MySQLAccess msqla, int idAuthor) throws SQLException
	{
		ResultSet resultSet=msqla.executeQuery ("select idComponent from authors_component where idAuthor= "+idAuthor);
		if(resultSet.next())
		{
			int idComponent=resultSet.getInt("idComponent");
			if (idComponent==0) return 1;
			else return 0;
		}
			
		else return -1; //single author, do not belong to coauthors network (table)
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
