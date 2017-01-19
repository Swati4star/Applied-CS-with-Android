/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet wordSet;
    private ArrayList wordList;
    private HashMap lettersToWord;

    AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line, key;
        wordSet = new HashSet();
        wordList = new ArrayList();
        lettersToWord = new HashMap();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            ArrayList arrayList = new ArrayList();
            key = alphabeticalOrder(word);
            if(!lettersToWord.containsKey(key)){
                arrayList.add(word);
                lettersToWord.put(key,arrayList);
            }
            else{
                arrayList = (ArrayList) lettersToWord.get(key);
                arrayList.add(word);
                lettersToWord.put(key, arrayList);
            }
        }
    }

    /**
     * Verifies if word is good for a given base word
     * @param word  Word to be checked
     * @param base  Base word
     * @return      boolean : true if word is good
     */
    public boolean isGoodWord(String word, String base) {
        // Word should not be subset
        if (wordSet.contains(word) && !base.contains(word))
            return true;
        else
            return false;
    }

    /**
     * Returns all possible words for a given word by adding one more letter
     * @param word  Base Word
     * @return      Array lit containing possible words
     */
    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();
        for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
            String newWord = word + alphabet;
            String extendedKey = alphabeticalOrder(newWord);
            if(lettersToWord.containsKey(extendedKey) ){
                resultant = (ArrayList) lettersToWord.get(extendedKey);
                for(int i = 0;i< resultant.size();i++)
                    result.add(String.valueOf(resultant.get(i)));
            }
        }
        return result;
    }

    /**
     * Returns a valid and good starter word
     * @return  A starter word
     */
    public String pickGoodStarterWord() {

        while(true) {
            int num = random.nextInt(wordList.size());
            String word = (String) wordList.get(num);
            ArrayList res = (ArrayList) lettersToWord.get(alphabeticalOrder(word));
            // A good word's length should be greater than 7 && must have atleast 5 anagrams
            if(word.length() <= MAX_WORD_LENGTH && res.size() >= MIN_NUM_ANAGRAMS)
                return (String) wordList.get(num);
        }
    }

    /**
     * Sorts given word in ascending order
     * @param word  word to be sorted
     * @return      sorted word
     */
    public String alphabeticalOrder(String word){
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
