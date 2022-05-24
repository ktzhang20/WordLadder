/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Ken Zhang
 * ktz85
 * 17805
 * Vivek Mahapatra
 * vsm485
 * 17805
 * Slip days used: <0>
 * Git URL: https://github.com/EE422C/ee-422c-fall-21-a3-fa21-pr3-pair-44
 * Fall 2021
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	private static String startWord;
	private static String endWord;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		ArrayList<String> words = parse(kb);
		words = getWordLadderBFS(words.get(0),words.get(1));
		printLadder(words);
		kb.close();
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		startWord = "";
		endWord = "";
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		 //TO DO
		ArrayList<String> words = new ArrayList<String>();
		startWord = keyboard.next();
		if(startWord.equals("/quit")) {
			words = null;
			return words;
		}
		endWord = keyboard.next();
		words.add(startWord);
		words.add(endWord);
		return words;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// initialize global variables
	    startWord = start;
	    endWord = end;

	    Set<String> dict = makeDictionary();
	    // dict makes strings uppercase, have to initialize start and end as Upper
	    start = start.toUpperCase();
	    end = end.toUpperCase();

	    // make sure start and end are in dict + if start == end
	    if (!dict.contains(start) || !dict.contains(end))
	      return null;
	    if (start.equals(end)) {
	      return null;
	    }

	    ArrayList<String> result = DFS(start, end, dict);

	    // if empty
	    if (result == null)
	      return null;

	    // sorted in-place by swapping Strings
	    int left = 0;
	    int right = result.size() - 1;
	    while (left < right) {
	      Collections.swap(result, left, right);
	      left += 1;
	      right -= 1;
	    }

	    // since dict is in upper case, we lowerCase everything
	    result.add(0, startWord);
	    result.add(endWord);
	    for (int x = 0; x < result.size() - 1; x++) {
	      String lowerCase = result.get(x).toLowerCase();
	      result.set(x, lowerCase);
	    }

	    return result;
	}
	
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	startWord = start;
    	endWord = end;
    	ArrayList<String> ladder = new ArrayList<String>();
		System.out.println(start + " " + end);
		BFS.recurBFS(start,end,ladder);
		if(ladder.size() == 0) {
			ladder = null;}
		return ladder; // replace this line later with real return
	}
    
	
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder == null) {
			System.out.println("no word ladder can be found between " + startWord + " and " + endWord);
			return;}
		System.out.println("A " + ladder.size() + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size()-1));
		for(int i = 0; i < ladder.size(); i++) {
			System.out.println(ladder.get(i));
		}
	}
	// TODO
	// Other private static methods here
	
	public static ArrayList<String> DFS(String start, String end, Set<String> dict) {

	    // create ArrayList to find all words with 1 letter difference
	    ArrayList<String> edges = new ArrayList<String>();
	    edges = findEdges(start, dict);

	    // for loop with recursion checks if return value is null
	    // if null, that means the path doesn't work
	    // if it contains end, then it just needs to return an arraylist
	    if (edges.isEmpty())
	      return null;

	    if (edges.contains(end)) {
	      return new ArrayList<String>();
	    }

	    // initialize variables to optimize the DFS search
	    ArrayList<String> newList = new ArrayList<String>();
	    int len = dict.size(); // set initially to max possible value
	    newList = null;
	    // required or else stackoverflow (prevent loops + repeating same branches)
	    dict.removeAll(edges);

	    // for each statement to loop through edge cases
	    for (String word : edges) {
	      // recursion
	      ArrayList<String> ladder = DFS(word, end, dict);
	      // make sure it's not null
	      if (!(ladder == null)) {
	        // this if statement is to optimize the length of the ladder
	        if (ladder.size() < len) {

	          newList = ladder;
	          len = ladder.size();
	          newList.add(word);
	          // re-add the edge case that led to a new array of edges that contained the end
	          // word
	          dict.add(word);

	        }
	      }
	    }
	    // if ladder never finds a branch, newList will just be null
	    return newList;
	  }



    private static ArrayList<String> findEdges(String init, Set<String> dict) {
	    ArrayList<String> aList = new ArrayList<String>();
	    // need to use a char array to change specific elements in String
	    char[] arr = init.toCharArray();
	    //char[] end = endWord.toCharArray();
	    for (int i = 0; i < arr.length; i++) {
	      // to backtrack from the for loop

	      char origChar = arr[i];
	      // using uppercase so I don't have to use toLowerCase multiple times
	      for (char j = 'A'; j <= 'Z'; j++) {
	        arr[i] = j;
	        if (dict.contains(String.valueOf(arr)))
	          aList.add(String.valueOf(arr));

	      }
	      arr[i] = origChar;
	    }
	    return aList;
	  }


	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	
}


