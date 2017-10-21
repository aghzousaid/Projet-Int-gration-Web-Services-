package com.ego.test;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Foo {
 
   @XmlJavaTypeAdapter(MyGraphAdapter.class)
   SimpleWeightedGraph<Integer, DefaultWeightedEdge> map = 
		   new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
 
   public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getMap() {
      return map;
   }
 
   public void setMap(SimpleWeightedGraph<Integer, DefaultWeightedEdge> map) {
      this.map = map;
   }
 
}
