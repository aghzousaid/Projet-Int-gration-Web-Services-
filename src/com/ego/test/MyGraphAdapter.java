package com.ego.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
  
public final class MyGraphAdapter extends
  
   XmlAdapter<SimpleWeightedGraph<Integer, DefaultWeightedEdge>,SimpleWeightedGraph<Integer, DefaultWeightedEdge>> {

	@Override
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> marshal(
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> arg0) throws Exception {
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			ugraph.builder(arg0.getEdgeFactory());
			return ugraph;
	}

	@Override
	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> unmarshal(
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> arg0) throws Exception {
			SimpleWeightedGraph<Integer, DefaultWeightedEdge> ugraph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			ugraph.builder(arg0.getEdgeFactory());
		return ugraph;
	}
  
  
}
