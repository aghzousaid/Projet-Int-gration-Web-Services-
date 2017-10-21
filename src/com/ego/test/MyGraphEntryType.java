package com.ego.test;

import javax.xml.bind.annotation.XmlValue;

import org.jgrapht.graph.DefaultWeightedEdge;

import javax.xml.bind.annotation.XmlAttribute;
 
public class MyGraphEntryType {
 
   @XmlAttribute
   public Integer key; 
 
   @XmlValue
   public DefaultWeightedEdge value;
 
}
