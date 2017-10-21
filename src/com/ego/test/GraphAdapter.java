package com.ego.test;

import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.EdgeSetFactory;
import org.jgrapht.graph.SimpleWeightedGraph;

//import com.sun.javafx.collections.MappingChange.Map;


public class GraphAdapter  extends XmlAdapter<EdgeSetFactory<Integer, DefaultWeightedEdge>,SimpleWeightedGraph<Integer, DefaultWeightedEdge>> {

	@Override
	public EdgeSetFactory<Integer, DefaultWeightedEdge> marshal(SimpleWeightedGraph<Integer, DefaultWeightedEdge> v)
			throws Exception {
		return (EdgeSetFactory<Integer, DefaultWeightedEdge>) v.getEdgeFactory();
	}

	@Override
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> unmarshal(EdgeSetFactory<Integer, DefaultWeightedEdge> v)
			throws Exception {
		SimpleWeightedGraph<Integer, DefaultWeightedEdge>s = null;
		s.setEdgeSetFactory(v);
		return s;
	}


 

}