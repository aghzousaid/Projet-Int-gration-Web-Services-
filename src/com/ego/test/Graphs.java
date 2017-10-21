package com.ego.test;

import javax.xml.bind.annotation.XmlElement;


public class Graphs {
    @XmlElement(name="graph")
    public Graph[] graphs;
}
