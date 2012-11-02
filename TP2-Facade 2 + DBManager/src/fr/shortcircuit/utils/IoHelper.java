package fr.shortcircuit.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IoHelper
{

	//////////////////////////
	//Read
	//////////////////////////
	
	public static List readVectorFromFile(String fileName)
	{
		ArrayList lines						= new ArrayList();
		
     	try
     	{   
     		String strContent;	
	     		  	
     		BufferedReader 	bufReader			= new BufferedReader(new FileReader(fileName));
			
			while ((strContent = bufReader.readLine()) != null)
				lines.add(strContent);
				
			bufReader.close();
	  	}
		catch (Exception e) {System.out.println("readVectorFromFile " + fileName + " returns : " + e.toString());}
		
		return lines;	
	}
	
	public static StringBuffer loadURL(String urlDesc)
	{
		StringBuffer buffer										= new StringBuffer();

		try
		{
			URL urlContent										= new URL(urlDesc);
	
			BufferedReader inBuf								= new BufferedReader(new InputStreamReader(urlContent.openStream()));
			
			String strTemp;
			
			while ((strTemp = inBuf.readLine()) != null)
				buffer.append(strTemp + "\r\n");

			inBuf.close();

			//System.out.println("buf size " + buffer.length());
		}
		catch (Exception e) {System.out.println("loadURL" + urlDesc + " : " + e.toString());}
		
		return buffer;
	}

	//////////////////////////
	//Write
	//////////////////////////

	public static void writeFile(String directory, String content, String fileName, boolean deleteFileBeforeWriting)
	{
		try
		{
			if (deleteFileBeforeWriting)
				deleteFile(directory + fileName);
			
			BufferedWriter out									= new BufferedWriter(new FileWriter(directory + fileName, deleteFileBeforeWriting)); 
			out.write(content);
			
			//out.newLine();
				
			out.close();
		}
		catch (Exception e) {System.out.println("failed to write query");}	
	}

	
	public static void writeFile(String content, String fileName, boolean deleteFileBeforeWriting)
	{
		try
		{
			if (deleteFileBeforeWriting)
				deleteFile(fileName);
			
			BufferedWriter out									= new BufferedWriter(new FileWriter(fileName, deleteFileBeforeWriting)); 
			out.write(content);
			
			//out.newLine();
				
			out.close();
		}
		catch (Exception e) {System.out.println("failed to write query");}	
	}

	//////////////////////////
	//Delete
	//////////////////////////

	public static void deleteFile(String fileName)
	{
		try
		{
			File file2delete									= new File(fileName);
			file2delete.delete();				
		}
		catch (Exception e) {System.out.println("failed to write query");}			
	}

}
