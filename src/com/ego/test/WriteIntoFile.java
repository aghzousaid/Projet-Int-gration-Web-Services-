package com.ego.test;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WriteIntoFile {
	public PrintWriter writer;
	
	WriteIntoFile(String fileName) throws FileNotFoundException
	{
		writer= new PrintWriter(fileName);
	}

	public void writeResults(String output) throws FileNotFoundException, UnsupportedEncodingException{
		
		writer.println(output);
		writer.flush();	
	   
	}
    
	public void close()
	{
		this.writer.close();
	}
	
}
