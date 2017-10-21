package com.ego.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;






@XmlRootElement(name="xmlData")
@XmlAccessorType(XmlAccessType.FIELD)
public class GraphXmlData {
	@XmlElement
	 int ego;
	@XmlElement
	 int k;
	@XmlElement
	 int year;
	
	//@XmlJavaTypeAdapter(GraphAdapter.class)
	 Graph sgraph;//=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	public GraphXmlData() {
		
	}
	
	public GraphXmlData(int ego, int k, int year) {
		super();
		this.ego = ego;
		this.k = k;
		this.year = year;
	}

	public int getEgo() {
		return ego;
	}

	public void setEgo(int ego) {
		this.ego = ego;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Sets a graph
	 * @param graph
	 */
	public void setNetwork(Graph graph){
		this.sgraph=graph;
	}
	
	/**
	 * @return graph
	 */
	public Graph getNetwork(){
		return this.sgraph;
	}
	


	


}
