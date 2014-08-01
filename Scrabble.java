/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainingday5;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author vidv
 */
public class Scrabble {

    private final HashMap<String, Integer> scrabbleDictionary = new HashMap<String, Integer>();
    private final HashSet<String> wordSet = new HashSet<String>();
    private char[] c;

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

    public Integer getScrabbleWordScore(char[] characters) {
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
    }**//

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

    public String findMaxScoreWord() {
        ArrayList<String> finalList = new ArrayList<String>();
        for (String word : wordSet) {
            if (scrabbleDictionary.containsKey(word)) {
                Integer score = scrabbleDictionary.get(word);
                finalList.add("" + score + "-" + word);
            }
        }
        return Collections.max(finalList);
    }
    
    public static void main(String[] args) {
        Scrabble scrabbleBoard = new Scrabble();
        scrabbleBoard.creatingTheDictionary();
        String input = "HOESMTL";
        input = input.toUpperCase();
        scrabbleBoard.c = input.toCharArray();
        scrabbleBoard.permutation(0);
        System.out.println(scrabbleBoard.findMaxScoreWord());
    }
}
