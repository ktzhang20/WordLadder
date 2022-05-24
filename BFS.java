/*
 * EE422C Project 3 submission by
 * Vivek Mahapatra
 * vsm485
 * 17805
 * Fall 2021
 * Slip days used: 0
 */

package assignment3;
import java.io.*;
import java.util.*;
public class BFS {
	static String alphabet[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	static String alphaCaps[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static void recurBFS(String current, String end, ArrayList<String> ladder){
		if(ladder.size() == 0)
			ladder.add(current);
		int i = 0;
		int j = 0;
		if(current.equals(end)){				//success
			return;
		}
		//Object[] diction = (Main.makeDictionary()).toArray();
		for(int n = 0; n < current.length();n++) {
			i = n;
			if(current.substring(n,n+1).equals(end.substring(n,n+1)) == false)
				break;
		}
		while(i < current.length()) {
			while(j < 26) {
				Scanner dict = initDict("five_letter_words.txt");
				String check = current.substring(0,i) + alphabet[j] + current.substring(i+1,current.length());
				//String checkLow = current.substring(0,i) + alphabet[j] + current.substring(i+1,current.length());
				//System.out.println(check);
				//int k = 0;
				while(dict.hasNext()) {
					if(check.equals(dict.next())&& (check.equals(current) == false)) {
						int ladChk = 0;
						for(int m = 0; m < ladder.size(); m++) {
							if(check.equals(ladder.get(m))){
								ladChk++;
								break;
							}								
						}
						if(ladChk != 0) {
							ladChk = 0;
							continue;
						}
						//System.out.println("SIMILAR: " + check);
						ladder.add(check);
						recurBFS(check,end,ladder);
						//System.out.println(check);
						return;
					}
					//k++;
				}
				j++;
				dict.close();
			}
			j = 0;
			i++;
		}
			ladder.clear();//failure
			return;
		
	
	}
	
	public static Scanner initDict(String txtName) {
		File text = new File(txtName);
		Scanner dict = null;
		try {
			dict = new Scanner(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dict;
	}
	public static int iterate(int j) {
		j = j+1;
		return j;
	}
	

}
