package helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.OrgGene;


public class ReadFile {
	/**
	 * @param args
	 */
	public ArrayList<OrgGene> orgs;
	String line;
	
	// Constructor
	public ReadFile (String input){
		orgs = new ArrayList<OrgGene>();
		read(input);
	}
	
	// read from file input
	public ArrayList<OrgGene> read(String input){
		File file = new File (input);
		/*if (file.exists())
		System.out.println(file.getName());*/

		FileInputStream fstream;
		BufferedReader in;
		int i = 1;
		// try catch 
		try{
			fstream = new FileInputStream(file);
			in = new BufferedReader(new InputStreamReader(fstream));
			
			while((line = in.readLine()) !=null){
				OrgGene org = new OrgGene(line);
				org.pos = i;
				//System.out.println(org.gene);
				orgs.add(org);
				i++;
			}
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return orgs;
		
	}
}
