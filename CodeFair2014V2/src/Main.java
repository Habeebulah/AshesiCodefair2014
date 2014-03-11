import helpers.ReadFile;
import helpers.WriteFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.OrgGene;



public class Main {

	/**
	 * @param args
	 */
	private final static String OUTPUTPATH = "output.txt";
	private final static String INPUTPATH = "input.txt";
	private static int deleteCost=1;
	private  static int insertCost=1;
	private static int replaceCost=1;
	private static int swapCost=1;
	static ArrayList<OrgGene> org2 = new ArrayList<OrgGene>();
	private static ArrayList<OrgGene> family= new ArrayList<OrgGene>();
	private static ArrayList<OrgGene> notfamily= new ArrayList<OrgGene>();
	private static OrgGene[] numbers;
	private static int number=0;
	private static WriteFile wr = new WriteFile(OUTPUTPATH);
	/*
	 * 
	 */
	public Main(int deleteCost, int insertCost,
			int replaceCost, int swapCost) throws IOException {
		// Required to facilitate the premise to the algorithm that two swaps of
		// the same character are never required for optimality.
		
		if (2 * swapCost < insertCost + deleteCost) {
			throw new IllegalArgumentException("Unsupported cost assignment");
		}
		this.deleteCost = deleteCost;
		this.insertCost = insertCost;
		this.replaceCost = replaceCost;
		this.swapCost = swapCost;
	}
	
	
	/*
	 * 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-orgrated method stub
		wr.clear(OUTPUTPATH);
		ReadFile r = new ReadFile(INPUTPATH );
		sort(r.orgs);
		
	}
	
	
	/*
	 * 
	 */
	public static void sort(ArrayList<OrgGene> org) throws IOException {
		int start =1;
		int dist;
		ArrayList<OrgGene> Temp =new ArrayList<OrgGene>(); 
	    // check for empty or null array
	    if (org ==null || org.size()==0){
	      return;
	    }
	  
	    numbers = new OrgGene[org.size()];
	    
	    Temp.add(org.get(0));
	    int run;
	    int size =org.size();
	    int mindist;
	    int smindist=4;
	    int minIndex=0;	  
	    int sminIndex=-1; 
	    //mindist=compare(numbers[0].gene, org.get(1).gene);
	    org.remove(0);	    
	    for (int i=0; org.size()>0; i++){
	    	mindist=compare(Temp.get(i).gene, org.get(0).gene);
	    	
	    	 for (int j=0; j<org.size(); j++){	
	    		 if(sminIndex!=-1){
	    		    	if (compare(Temp.get(i).gene, org.get(sminIndex).gene)<=2){
	    		    		minIndex=sminIndex;
	    		    		sminIndex=-1;
	    		    		break;
	    		    	}
	    		      }
	   		      dist= compare(Temp.get(i).gene, org.get(j).gene);
	   		      System.out.println(dist);
	   		   if (dist==1){	   
	   		    	  //sminIndex=minIndex;
	   		    	  minIndex=j;
	   		    	  mindist=1;
	   		    	  break;
	   		      }
	   		      else if (dist==2){	   		    	
	   		    	mindist=2;
	   		    	sminIndex=j;
	   		      }   		      
	   		      
	   		      else if (mindist>=dist){		   		    		 	
	   		    	  //sminIndex=minIndex;	   		    	
	   		    	  minIndex = j;	   		    	 
	   		    	  mindist=dist;
	   		      }
	 	    }	    	
	    	
	    	 System.out.println("The min distance is " + mindist );
	    	 if (mindist>=3)
	    		 break;
	    	 Temp.add(org.get(minIndex));
	    	 if(sminIndex!=-1){
	    		 if(minIndex<sminIndex)
	    			 sminIndex--;
	    	 }
	    	 org.remove(minIndex);
	    	
	    }    
	    
	    
	    wr.write(OUTPUTPATH, "");
	    wr.write(OUTPUTPATH, "Family");		
	    wr.write(OUTPUTPATH, Temp);	
	    wr.write(OUTPUTPATH, "");         
	    wr.write(OUTPUTPATH, "");	   
	    wr.write(OUTPUTPATH, "Not Family");	    
	    wr.write(OUTPUTPATH, org);
	    }
	
	
	
	/*
	 * 
	 */
	private static void  qSort(int low, int high){
		
		    int i = low, j = high;
		    // Get the pivot element from the middle of the list
		    int pivot = numbers[low + (high-low)/2].pos;

		    // Divide into two lists
		    while (i <= j) {
		      // If the current value from the left list is smaller then the pivot
		      // element then get the next element from the left list
		      while (numbers[i].pos < pivot) {
		        i++;
		      }
		      // If the current value from the right list is larger then the pivot
		      // element then get the next element from the right list
		      while (numbers[j].pos > pivot) {
		        j--;
		      }

		      // If we have found a values in the left list which is larger then
		      // the pivot element and if we have found a value in the right list
		      // which is smaller then the pivot element then we exchange the
		      // values.
		      // As we are done we can increase i and j
		      if (i <= j) {
		        exchange(i, j);
		        i++;
		        j--;
		      }
		    }
		    // Recursion
		    if (low < j)
		      qSort(low, j);
		    if (i < high)
		      qSort(i, high);
		    
	}

		  
	
	/*
	 * 
	 */
	private static void exchange(int i, int j) {
		OrgGene temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}
	
	
	/*
	 * 
	 */
	//This function is refernced from https://github.com/KevinStern/software-and-algorithms
	private static int compare(String s0, String s1){
		

		/**
		 * Compute the Damerau-Levenshtein distance between the specified s0
		 * string and the specified s1 string.		 */
		
			if (s0.length() == 0) {
				return s1.length() * insertCost;
			}
			if (s1.length() == 0) {
				return s0.length() * deleteCost;
			}
			int[][] table = new int[s0.length()][s1.length()];
			Map<Character, Integer> s0IndexByCharacter = new HashMap<Character, Integer>();
			if (s0.charAt(0) != s1.charAt(0)) {
				table[0][0] = Math.min(replaceCost, deleteCost + insertCost);
			}
			s0IndexByCharacter.put(s0.charAt(0), 0);
			for (int i = 1; i < s0.length(); i++) {
				int deleteDistance = table[i - 1][0] + deleteCost;
				int insertDistance = (i + 1) * deleteCost + insertCost;
				int matchDistance = i * deleteCost
						+ (s0.charAt(i) == s1.charAt(0) ? 0 : replaceCost);
				table[i][0] = Math.min(Math.min(deleteDistance, insertDistance),
						matchDistance);
			}
			for (int j = 1; j < s1.length(); j++) {
				int deleteDistance = table[0][j - 1] + insertCost;
				int insertDistance = (j + 1) * insertCost + deleteCost;
				int matchDistance = j * insertCost
						+ (s0.charAt(0) == s1.charAt(j) ? 0 : replaceCost);
				table[0][j] = Math.min(Math.min(deleteDistance, insertDistance),
						matchDistance);
			}
			for (int i = 1; i < s0.length(); i++) {
				int maxs0LetterMatchIndex = s0.charAt(i) == s1
						.charAt(0) ? 0 : -1;
				for (int j = 1; j < s1.length(); j++) {
					Integer candidateSwapIndex = s0IndexByCharacter.get(s1
							.charAt(j));
					int jSwap = maxs0LetterMatchIndex;
					int deleteDistance = table[i - 1][j] + deleteCost;
					int insertDistance = table[i][j - 1] + insertCost;
					int matchDistance = table[i - 1][j - 1];
					if (s0.charAt(i) != s1.charAt(j)) {
						matchDistance += replaceCost;
					} else {
						maxs0LetterMatchIndex = j;
					}
					int swapDistance;
					if (candidateSwapIndex != null && jSwap != -1) {
						int iSwap = candidateSwapIndex;
						int preSwapCost;
						if (iSwap == 0 && jSwap == 0) {
							preSwapCost = 0;
						} else {
							preSwapCost = table[Math.max(0, iSwap - 1)][Math.max(0,
									jSwap - 1)];
						}
						swapDistance = preSwapCost + (i - iSwap - 1) * deleteCost
								+ (j - jSwap - 1) * insertCost + swapCost;
					} else {
						swapDistance = Integer.MAX_VALUE;
					}
					table[i][j] = Math.min(
							Math.min(Math.min(deleteDistance, insertDistance),
									matchDistance), swapDistance);
				}
				s0IndexByCharacter.put(s0.charAt(i), i);
			}
			return table[s0.length() - 1][s1.length() - 1];
	}

}
