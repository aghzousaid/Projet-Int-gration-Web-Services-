package com.ego.test.client;

import java.io.File;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.glassfish.jersey.client.ClientConfig;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.*;

import com.ego.test.MySQLAccess;
import com.ego.test.PersonalUndirectedNetwork;

public class Test {
//	private static EdgeFactory<Integer, DefaultWeightedEdge> ef;
	
	public static void main(String[] args) throws Exception {

		MySQLAccess msql=new MySQLAccess();
		PersonalUndirectedNetwork pn=new PersonalUndirectedNetwork(1, 2004);
		pn.retrievePersonalUndirectedNetworkEgoKYear(msql);
		System.out.println(pn.ugraph.vertexSet().size());
		pn.viewNetwork();
	

			//we are creating a client by the url of our rest service
		
		    ClientConfig config = new ClientConfig();

		    Client client = ClientBuilder.newClient(config);

		    WebTarget target = client.target(getBaseURI());
		    //We are sending via http protocol our request and then showing the result of the service by getting it again via http protocol
		    
		    
		         System.out.println(target.path("rest").path("framework").path("networkViewer").path("0").request()

		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString()); 
		  
	
		     System.out.println(target.path("rest").path("framework").path("networkViewerXML").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		   
		    
		    
		 
		   	  System.out.println(target.path("rest").path("framework").path("BetweennessCentrality").path("20").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		 
		    
		    /*
		     System.out.println(target.path("rest").path("framework").path("YearApparition").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		    */
		    
		    /*
		     System.out.println(target.path("rest").path("framework").path("GiantComponent").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		     */
		     
		     
		     /*
		     	System.out.println(target.path("rest").path("framework").path("Kmax").path("15").request()
		    	.accept(MediaType.TEXT_PLAIN).get(String.class)
		    	.toString());
		      */
		     
		     /*
		     System.out.println(target.path("rest").path("framework").path("NumberOfPublications").path("15").request()
		 		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		 		    .toString());
		    */
		     
		     /*
		     System.out.println(target.path("rest").path("framework").path("GlobalClustering").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		     
		     */
		     
		     
		     /*
		     System.out.println(target.path("rest").path("framework").path("Density").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		  */
		     
		   /*
		     System.out.println(target.path("rest").path("framework").path("PowerLaw").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		   */  
		    
		     /*
		     System.out.println(target.path("rest").path("framework").path("Betweenness").path("20").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		     */
		     
		     /*
		     System.out.println(target.path("rest").path("framework").path("EffectiveSize").path("20").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		     */
		     
		     /*
		     System.out.println(target.path("rest").path("framework").path("DegreeCentrality").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		     */
		     
			 /*   
		     System.out.println(target.path("rest").path("framework").path("EigenvectorCentrality").path("15").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		    */
		     
			  /*
		   	  System.out.println(target.path("rest").path("framework").path("NumberOfAuthorPerPaper").path("20").request()
		    .accept(MediaType.TEXT_PLAIN).get(String.class)
		    .toString());
		    */
		     
		   // DefaultWeightedEdge e = ef.createEdge(0, 0);
		   
		    
		    //JAXBContext context = JAXBContext.newInstance(EdgeFactory.class);
		    //Marshaller m = context.createMarshaller();
		    //m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		    
			// Write to System.out
		   //m.marshal(ef, System.out);


		  }

		  private static URI getBaseURI() {
			  //Here we are calling our RESTful web service
		    return UriBuilder.fromUri("http://localhost:8081/com.ego.test").build();

		  }

}

