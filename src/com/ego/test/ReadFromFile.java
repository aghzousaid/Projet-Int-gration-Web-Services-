package com.ego.test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {
	
	public List<Integer> readNodesFromFile(String fileName) throws IOException
	{
		List<Integer> list=new ArrayList<Integer>();

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] aLine = line.split("\t");
		        line = br.readLine();
				// Fill egos List  
		        list.add(Integer.parseInt(aLine[0]));

		    }
		    return list;
		} 
		finally {
		    br.close();
		} 
	}
}
