package helpers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;

import Model.OrgGene;

public class WriteFile {

	/**
	 * @param args
	 */
	
	// Constructor	
	public WriteFile(String output){
		
	}

	
	public void write(String output, ArrayList<OrgGene> orgs){
		File file = new File (output);	

		PrintWriter pr;
		BufferedWriter out;
		int i =0;
		// try catch 
		
		try{
			pr = new PrintWriter(new FileWriter(file, true));
			out = new BufferedWriter(pr);
			
			while(i !=orgs.size()){
				pr.println(orgs.get(i).gene);
				
				i++;
			}
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void write(String output, OrgGene[] g) throws IOException{
	
		File file = new File (output);
		if (!file.exists())
			file.createNewFile();
		PrintWriter pr;
		BufferedWriter out;
		// try catch 
		int i =0;
		
		try{
			pr = new PrintWriter(new FileWriter(file, true));
			out = new BufferedWriter(pr);			
				
				while(i <g.length){					
					pr.println(g[i].gene);					
					i++;
				}
						
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void write(String output, String x) throws IOException{
		
		File file = new File (output);
		if (!file.exists())
			file.createNewFile();
		PrintWriter pr;
		BufferedWriter out;
		// try catch 
		
		try{
			pr = new PrintWriter(new FileWriter(file, true));
			out = new BufferedWriter(pr);		
				pr.println(x);
				
						
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
		public void clear(String output) throws IOException{
			
			File file = new File (output);
			if (!file.exists())
				file.createNewFile();
			PrintWriter pr;
			BufferedWriter out;
			// try catch 
			
			try{
				pr = new PrintWriter(file);
				out = new BufferedWriter(pr);		
					
							
				out.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		
	}
}


