/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vidv
 */
public class Scrabble {

    private static final HashMap<String, Integer> scrabbleDictionary = new HashMap<String, Integer>();
    private static HashSet<String> wordSet = new HashSet<String>();
    private char[] c;
    private static int maxScore = 0;
    private static List<String> maxString = new LinkedList<String>( );
    private static List<String> inputList = new LinkedList<String>();

    public void creatingTheDictionary() {
        try {
            Scanner scan = new Scanner(new FileReader("sowpods.txt"));
            while (scan.hasNext()) {
                String word = scan.next();
                this.scrabbleDictionary.put(word, this.getScrabbleWordScore(word.toCharArray()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Integer getScrabbleWordScore(char[] characters) {
        int[] letterScores = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        Integer sum = 0;
        for (char character : characters) {
            sum += letterScores[(int) character - 'A'];
        }
        return sum;
    }

    /**public Set<String> constructValidWords(String input) {
        HashSet<String> wordSet = new HashSet<String>();
        
        return wordSet;
    }**/

    public void swap(int pos1, int pos2) {
        char temp = c[pos1];
        c[pos1] = c[pos2];
        c[pos2] = temp;
    }

    public void permutation(int start) {
        if (start != 0) {
            String s = "";
            for (int i = 0; i < start; i++) {
                s = s + Character.toString(c[i]);
            }
            if( s.length() > 1)
            {
              wordSet.add(s);
            }
        }

        for (int i = start; i < c.length; i++) {
            swap(start, i);
            permutation(start + 1);
            swap(start, i);
        }
    }

    public   String findMaxScoreWord(boolean isSevenLetter, String addedChar) {
        HashMap<Integer,String> wordScoreMap = new HashMap<Integer,String>();
        if(isSevenLetter){
	        for (String word : wordSet) {
	            if (scrabbleDictionary.containsKey(word)) {
	                Integer score = scrabbleDictionary.get(word);
	                wordScoreMap.put( score ,word);
	            }
	        } 
        }
        else{
        	for (String word : wordSet) {
	            if (scrabbleDictionary.containsKey(word)) {
	            	String newWord = word.replaceFirst(addedChar, "");
	                Integer score = getScrabbleWordScore(newWord.toCharArray());
	                wordScoreMap.put( score ,word);
	            }
	        }
        }
        if(wordScoreMap.keySet().size() == 0){
        	return "false";
        }
    	int max = Collections.max(wordScoreMap.keySet());
    	if(max >= maxScore){
    		if(max != maxScore)
    			maxString = new LinkedList<String>();
	    	maxString.add(wordScoreMap.get(max));
	    	maxScore = max;
    	}
        return wordScoreMap.get(max) + " " + max;
          
    }
    
    public static void readInputFromFile(String f) throws IOException
    {
    	BufferedReader reader = new BufferedReader(new FileReader(f));
    	String line = null;
    	while((line = reader.readLine())!=null)
    	{
    		inputList.add(line);
    	}
    	reader.close();
    }
    
    public static void main(String[] args) {
    	
        Scrabble scrabbleBoard = new Scrabble();
        scrabbleBoard.creatingTheDictionary();
        
        try {
			readInputFromFile("input.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(String input:inputList)
        {
        	maxScore = 0;
        	maxString = new LinkedList<String>();
        	input = input.toUpperCase();
            scrabbleBoard.c = input.toCharArray();
            
            if(input.length() < 7){
            	for( char ch = 'A'; ch <= 'Z'; ch++){
    	        	String newInput  = input + String.valueOf(ch);
    	        	scrabbleBoard.c = newInput.toCharArray();
    	        	wordSet = new HashSet<String>();
    	            scrabbleBoard.permutation(0);
    	            scrabbleBoard.findMaxScoreWord(false,String.valueOf(ch));
            	}
            }
            else{
            	wordSet = new HashSet<String>();
            	scrabbleBoard.permutation(0);
                scrabbleBoard.findMaxScoreWord(true,"");
            }
            if(maxScore != 0)
            	System.out.println(maxString+" "+maxScore);
            else
            	System.out.println("No Word Possible");
        }
        
        
    }
}
