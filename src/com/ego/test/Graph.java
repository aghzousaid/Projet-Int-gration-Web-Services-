package com.ego.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
@XmlAccessorType(XmlAccessType.FIELD)
public class Graph {
	//@XmlAttribute
	SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph;//=new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getUgraph() {
		return ugraph;
	}

	public void setUgraph(SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph) {
		this.ugraph = ugraph;
	}
	

}
