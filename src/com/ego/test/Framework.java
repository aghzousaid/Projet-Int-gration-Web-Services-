package com.ego.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;
import java.text.DecimalFormat;

import java.util.HashMap;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.jgrapht.ext.GmlExporter;
import org.jgrapht.graph.DefaultWeightedEdge;




import dk.aaue.sna.alg.centrality.BrandesBetweennessCentrality;
import dk.aaue.sna.alg.centrality.CentralityResult;



@Path("/framework")
public class Framework {
	private String separator1="-";
	private String separator2="|";
	/***
	 * Function returning informations about a ego network specified by the author id, k and the year
	 * @author Benoit
	 * @param id
	 * @param client_k
	 * @param client_year
	 * @return informations of the ego network 
	 * @throws Exception
	 */
	  @Path("/networkViewer/{author-id}/{client-k}/{client-year}")
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String networkViewer(@PathParam("author-id") int id,@PathParam("client-k") String client_k,@PathParam("client-year") String client_year) throws Exception {
		  int k_init,k_final,year_init,year_final;
		  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
		  String str1[]=client_k.split(separator1);
		  k_init=Integer.parseInt(str1[0]);
		  k_final=Integer.parseInt(str1[1])+1;
		  //We are splitting the year-k (in the form "0-0") in two parts: initial year and final year
		  String str2[]=client_year.split(separator1);
		  year_init=Integer.parseInt(str2[0]);
		  year_final=Integer.parseInt(str2[1])+1;
		MySQLAccess msqla;
		
		String output="";

			msqla = new MySQLAccess();

			 output="";
						for(int year=year_init; year<year_final;year++)
			{
				for(int k=k_init;k<k_final;k++)
				{
					PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id, k, year);
					personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
					
				
					output+=  "Ego: "+id +"; K: "+ k +"; Year: "+year+"; Nodes number: "+personalUndirectedNetwork.nodesNumber() +"; Edges number: "+personalUndirectedNetwork.edgesNumber()+separator2+"\n" ;
				
				}		
			}

			msqla.close();

		return output;
		
	}
	  
	  /***
	   * Function returning informations on the exported ego network graph specified by the author id, k and year 
	   * @param id
	   * @param client_k
	   * @param client_year
	   * @return informations of an exported ego network graph
	   * @throws Exception
	   */
	
	  @Path("/networkViewerXML/{author-id}/{client-k}/{client-year}")
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	public String networkViewerXML(@PathParam("author-id") int id,@PathParam("client-k") String client_k,@PathParam("client-year") String client_year) throws Exception {
		  int k_init,k_final,year_init,year_final;
		//We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
		  String str1[]=client_k.split(separator1);
		  k_init=Integer.parseInt(str1[0]);
		  k_final=Integer.parseInt(str1[1])+1;
		//We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
		  String str2[]=client_year.split(separator1);
		  year_init=Integer.parseInt(str2[0]);
		  year_final=Integer.parseInt(str2[1])+1;

		
		MySQLAccess msqla;
		
		
		
		
		String output="";
	
			msqla = new MySQLAccess();
	
			 output="";
			 //We are exporting informations of an ego network in a text file
			 Writer writer=new FileWriter("GML.txt");
			
			for(int year=year_init; year<year_final;year++)
			{
				for(int k=k_init;k<k_final;k++)
				{
					PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id, k, year);
					personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
					GmlExporter<Integer, DefaultWeightedEdge> gml=new GmlExporter<>();
					
					gml.export(writer, personalUndirectedNetwork.ugraph);
					
				
					
				
				}		
			}

			msqla.close();
		writer.close();
		
		//We are returning the content of the file 
		String l;
		try {
			BufferedReader br = new BufferedReader(new FileReader("GML.txt"));
			while ((l = br.readLine()) != null) {
				output+=l;
			
				
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Le chemin du fichier texte entré est incorrect");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return output+"| ;";
	  }
		
		/***
		 * Function calculating the eigenvector centrality of an ego network 
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @throws Exception
		 */
		@Path("/EigenvectorCentrality/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String EigenvectorCentrality(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
			
	
		
			MySQLAccess msqla = new MySQLAccess();

		
				String output="";
				for(int year=year_init; year<year_final;year++)
				{
					for(int k=k_init;k<k_final;k++)
					{
						PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,1, year);
						personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
						
						output+="Ego: "+id+"; K: " +k+"; Year: "+year+separator2+"\n" ;

					}
				}
				


		
		return output;
	
		
	}
		
		
		/***
		 * Function calculating the betweenness centrality of an ego network 
		 * @param id
		 * @return
		 * @throws Exception
		 */
		@Path("/BetweennessCentrality/{author-id}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String BetweennessCentrality(@PathParam("author-id")int id) throws Exception{
			String result="";
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("authorsFrom2004.txt"));
				bw.write(id);
				bw.newLine();
				bw.close();
			}catch (IOException e) {
				System.err.println(e.getMessage());
			}

			
			  MySQLAccess msqla = new MySQLAccess();
			  UndirectedNetwork undirectedNetwork=new UndirectedNetwork();
			  undirectedNetwork.retrieveWholeUndirectedNetwork(msqla,2004);
			  String output="";
			  CentralityResult<Integer> c = new BrandesBetweennessCentrality(undirectedNetwork.ugraph).calculate();

				  output+="Ego: "+id+"; Beetweenness centrality: " +c.get(id)+separator2+"\n" ;
				  result+=output;
	
			  msqla.close();
		
			  return result;
			}  
		
		/***
		 * Function returning the apparition year of an author
		 * @param id
		 * @return
		 * @throws Exception
		 */
		
		@Path("/YearApparition/{author-id}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String YearApparition (@PathParam("author-id")int id) throws Exception{
		MySQLAccess msqla = new MySQLAccess();
	


		UndirectedNetwork network=new UndirectedNetwork();

			int yearApparition=network.retrieveYearFirstPublicationOfAuthor(msqla, id);
			String output="Ego: "+id+"; Year apparition:  "+ yearApparition+separator2;
		
		
		msqla.close();

	 	return output;
		}
		
		/******
		 * Function calculating if the ego network of an author belongs to a giant component
		 * @param id
		 * @return
		 * @throws Exception
		 */
		
		@Path("/GiantComponent/{author-id}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String GiantComponent (@PathParam("author-id")int id) throws Exception{
		MySQLAccess msqla = new MySQLAccess();
	
		UndirectedNetwork network=new UndirectedNetwork();
	
			int belongs=network.belongsToGiantComponent(msqla, id);
			//belongs=1 -> true
			//belongs=0 -> false
			//belongs=-1 -> node not found -> single author, do not belong to coauthors network (table)
			String output="Ego: "+id+"; Belongs(1:true,0:false,-1:node not found): "+ belongs+separator2+"\n";
	
		msqla.close();

		return output;
		}
	  
		/***
		 * Function calculating the Kmax of an ego network 
		 * @param id
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		@Path("/Kmax/{author-id}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String Kmax (@PathParam("author-id")int id,@PathParam("client-year")String client_year) throws Exception{
			  int year_init,year_final;
		
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
	

MySQLAccess msqla = new MySQLAccess();

	
			String output="";
			for(int year=year_init; year<year_final;year++)
			{
				PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id, year);
				personalUndirectedNetwork.retrieveMaxPersonalUndirectedNetworkEgoYear(msqla);
			

				output+= "Ego: "+ id +"; Year: "+year+"; Nodes number: "+personalUndirectedNetwork.nodesNumber() +"; Edges number: "+personalUndirectedNetwork.edgesNumber()+"; KMax: "+personalUndirectedNetwork.getK()+separator2+"\n" ;
				
				
				System.out.println(year);
				
			}
			
			msqla.close();

		 return output;
		 }
		
/*******
 * Function calculating the number of publications of an author
 * @param id
 * @param client_year
 * @return
 * @throws Exception
 */
		
		@Path("/NumberOfPublications/{author-id}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String NumberOfPublications(@PathParam("author-id")int id,@PathParam("client-year")String client_year) throws Exception{
			  int year_init,year_final;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;



		
			MySQLAccess msqla = new MySQLAccess();

			//System.out.println("ego is= "+ego);
			String output="";
			for(int year=year_init; year<year_final;year++)
			{
				PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id, year);
				
				
		
				
				
				int nbPublications=personalUndirectedNetwork.retrieveNumberPublicationsPerAuthorPerYear(msqla, id, year);
				output+="Ego: "+  id +"; Year: "+year+"; Number of publications: "+nbPublications+separator2+"\n" ;
			
				
			}
						msqla.close();


	 	return output;
	 	}
		
		/*********
		 * Function calculating the global clustering of an ego network 
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		
		@Path("/GlobalClustering/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String GlobalClustering(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
	
	
		DecimalFormat df = new DecimalFormat("0.000");
	
			MySQLAccess msqla = new MySQLAccess();

	
			String output="";
			for(int year=year_init; year<year_final;year++)
			{
				for(int k=k_init;k<k_final;k++)
				{
					PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,k, year);
					personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
					double triangles=personalUndirectedNetwork.countTriangles();
					double connectedTriples=personalUndirectedNetwork.countConnectedTriples();
					double clusteringCoefficient=triangles/((triangles*3)+connectedTriples);

					output+="Ego: "+  id +"; K: "+k+"; Year: "+year+"; Clustering coefficient: "+df.format(clusteringCoefficient)+separator2 +"\n" ;
				
				}
			}
			

	
			msqla.close();


	    return output;
	    }
	  
		/***
		 * Function calculating the number of author per paper
		 * @return
		 * @throws Exception
		 */
		
		@Path("/NumberOfAuthorPerPaper/{author-id}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String NumberOfAuthorPerPaper() throws Exception{

			Map<Integer, Integer> nbAuthorsPerPaper=new HashMap<Integer, Integer>();

			MySQLAccess msqla = new MySQLAccess();

					String output="";
					
					RetrieveNetwork retrieve=new RetrieveNetwork();
					nbAuthorsPerPaper=retrieve.retrieveNumberAuthorsPerPaper(msqla);
					
					for(int paper:nbAuthorsPerPaper.keySet())
					{
						output+="Paper: "+  paper +"; Number of authors per paper: "+nbAuthorsPerPaper.get(paper)+separator2 +"\n" ;
					}
								
					
				
			
					msqla.close();

				
			
		return output;
		}
/****
 * Function calculating the effective size of an ego network 
 * @param id
 * @param client_year
 * @return
 * @throws Exception
 */
		
		@Path("/EffectiveSize/{author-id}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String EffectiveSize(@PathParam("author-id")int id,@PathParam("client-year")String client_year) throws Exception{
			  int year_init,year_final;

			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
		

				DecimalFormat df = new DecimalFormat("0.000");
			
					MySQLAccess msqla = new MySQLAccess();

		
					String output="";
					for(int year=year_init; year<year_final;year++)
					{
						
							PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,1, year);
							personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
							double effectiveSize=personalUndirectedNetwork.getEffectiveSize();
							output+= "Ego: "+ id +";Year: "+year+"; Nodes number: "+personalUndirectedNetwork.nodesNumber()+"; Edges number: "+personalUndirectedNetwork.edgesNumber()+"; Effective size: "+df.format(effectiveSize)+separator2 +"\n" ;
						
					}
				
			msqla.close();

		
		return output;
		}
		
		/***
		 * Function calculating the degree centrality of an ego network 
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		
		@Path("/DegreeCentrality/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String DegreeCentrality(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
	


				MySQLAccess msqla = new MySQLAccess();			
				String output="";
			
				for(int year=year_init; year<year_final;year++)
				{
					for(int k=k_init;k<k_final;k++)
					{
						PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,k, year);
						personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);


						output+="Ego: "+ id +"; K: "+k+";Year: "+year+"; Ego degree: "+personalUndirectedNetwork.egoDegree()+separator2+"\n" ;

					}
				}
	
				msqla.close();
		
		 return output;
		 }
		
		/***
		 * Function calculating the betweenness of an ego network 		
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		@Path("/Betweenness/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String Betweenness(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
		
	
			MySQLAccess msqla = new MySQLAccess();
			DecimalFormat df = new DecimalFormat("0.000");
	
			String output="";
			for(int year=year_init; year<year_final;year++)
			{
				for(int k=k_init;k<k_final;k++)
				{
					PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,k, year);
					personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
			 
			
					 CentralityResult<Integer> c = new BrandesBetweennessCentrality(personalUndirectedNetwork.ugraph).calculate();
					 double betweenness=c.get(id);
					
					
					output+= "Ego: "+ id +"; K: "+k+"; Year: "+year+"; Beetweenness: "+df.format(betweenness)+separator2 +"\n" ;
				}
			}
	
			msqla.close();

	
		
		return output;
		}
		
		/***
		 * Function calculating if an ego network follow a power law
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		
		@Path("/PowerLaw/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String PowerLaw(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
			 
			
					MySQLAccess msqla = new MySQLAccess();

		
					String output="";
					for(int year=year_init; year<year_final;year++)
					{
						for(int k=k_init;k<k_final;k++)
						{
							PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,k, year);
							personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
							Boolean isPowerLaw=personalUndirectedNetwork.isPoweLaw();
							output+= "Ego: "+ id +"; K: "+k+"; Year: "+year+"; Power law: "+isPowerLaw+separator2 +"\n" ;
						}
					}
					
			
					msqla.close();

			
	
				return output;
			}
		
		/***
		 * Function calculating the density of an ego network 
		 * @param id
		 * @param client_k
		 * @param client_year
		 * @return
		 * @throws Exception
		 */
		
		@Path("/Density/{author-id}/{client-k}/{client-year}")
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String Density(@PathParam("author-id")int id,@PathParam("client-k")String client_k,@PathParam("client-year")String client_year) throws Exception{
			  int k_init,k_final,year_init,year_final;
			  //We are splitting the client-k (in the form "0-0") in two parts: initial k and final k
			  String str1[]=client_k.split(separator1);
			  k_init=Integer.parseInt(str1[0]);
			  k_final=Integer.parseInt(str1[1])+1;
			  //We are splitting the client-year (in the form "0-0") in two parts: initial year and final year
			  String str2[]=client_year.split(separator1);
			  year_init=Integer.parseInt(str2[0]);
			  year_final=Integer.parseInt(str2[1])+1;
			 
		
			MySQLAccess msqla = new MySQLAccess();
			DecimalFormat df = new DecimalFormat("0.000");
	
			String output="";
			for(int year=year_init; year<year_final;year++)
			{
				for(int k=k_init;k<k_final;k++)
				{
					PersonalUndirectedNetwork personalUndirectedNetwork= new PersonalUndirectedNetwork(id,k, year);
					personalUndirectedNetwork.retrievePersonalUndirectedNetworkEgoKYear(msqla);
					double density=personalUndirectedNetwork.density();
					output+= "Ego: "+ id +"; K: "+k+"; Year: "+year+"; Density: "+df.format(density)+separator2 +"\n" ;
				}
			}
			
	
			msqla.close();

		return output;
		}
	  
	  
	  
}