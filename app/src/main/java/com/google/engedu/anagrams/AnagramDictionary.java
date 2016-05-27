package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 3;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    // private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            // wordList.add(word);
            wordSet.add(word);
            int size = word.length();

            String x = sortLetters(word);
            if (lettersToWord.containsKey(x))
                lettersToWord.get(x).add(word);
            else {
                ArrayList<String> anagrams = new ArrayList<>();
                anagrams.add(word);
                lettersToWord.put(x, anagrams);
            }

            if (sizeToWords.containsKey(size))
                sizeToWords.get(size).add(word);
            else {
                ArrayList<String> sameSizeWords = new ArrayList<>();
                sameSizeWords.add(word);
                sizeToWords.put(size, sameSizeWords);
            }
        }
        // printSizeToWords();
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    /*
    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String k = sortLetters(targetWord);
        if (lettersToWord.containsKey(k)) {
            result = lettersToWord.get(k);
        } else {
            for (String x : wordList) {
                if (k.equals(sortLetters(x)))
                    result.add(x);
            }
            lettersToWord.put(k, result);
        }
        return result;
    }

    private void printLettersToWord() {
        for (String key : lettersToWord.keySet()) {
            System.out.print(key + ":\t");
            for (String wordValue : lettersToWord.get(key)) {
                System.out.print(wordValue + ", ");
            }
            System.out.println();
        }
    }

    private void printSizeToWords() {
        for (int key : sizeToWords.keySet()) {
            System.out.print(key + ":\t");
            for (String wordValue : sizeToWords.get(key)) {
                System.out.print(wordValue + ", ");
            }
            System.out.println();
        }
    }
    */

    private String sortLetters(String s) {
        char[] sw = s.toCharArray();
        Arrays.sort(sw);
        return new String(sw);
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(lettersToWord.get(sortLetters(word)));
        String x;

        for (int i = 97; i <= 122; i++) {
            //x = "";
            x = word.concat(Character.toString((char) i));
            x = sortLetters(x);
            if (lettersToWord.containsKey(x))
                result.addAll(lettersToWord.get(x));
        }

        for (String w : result)
            System.out.println("w: " + w);

        return result;
    }

    public String pickGoodStarterWord() {
        String word = "";
        ArrayList<String> validList = sizeToWords.get(wordLength);
        int size = validList.size();
        int r = random.nextInt(size);
        //System.out.println("Size : " + size + "r : " + r);
        int i = 0;
        while (i < size) {
            String x = validList.get(r++);
            if (lettersToWord.get(sortLetters(x)).size() >= MIN_NUM_ANAGRAMS) {
                word = x;
                break;
            }
            if (r >= size)
                r = 0;
            i++;
        }
        if (wordLength != MAX_WORD_LENGTH)
            wordLength++;
        //System.out.println("---------------" + word);
        return word;
    }

}
