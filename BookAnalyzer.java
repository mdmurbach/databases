import java.util.*;
import java.io.*;

public class BookAnalyzer {
    public static void main(String[] args) 
                        throws FileNotFoundException {
        String exclude = "exclude_list.txt";  // File of words to exclude     
        Map<String,Integer> wordCount = new TreeMap<String,Integer>();
        Set<String> wordsToExclude = new TreeSet<String>();
        Scanner console = new Scanner(System.in);
        System.out.print("File to scan: ");
        String filename = console.next();
        System.out.print("\nNumber of top words to count: ");
        int topWords = console.nextInt();
        System.out.println("\nFor " + filename);
        Scanner in = new Scanner(new File(filename));
        Scanner toExclude = new Scanner(new File(exclude));
        getWordsToExclude(toExclude,wordsToExclude);
        int totalWords = createWordMap(in, wordCount, wordsToExclude);
        getTopWords(wordCount,totalWords,topWords);   
    }
    
    //pre: toExclude points to file of words to not consider, wordsToExclude 
    public static void getWordsToExclude(Scanner toExclude, Set<String> wordsToExclude) {
        while(toExclude.hasNext()) {
                wordsToExclude.add(toExclude.next());
        }
        System.out.println("\nWords excluded from top words: " + wordsToExclude);
    }
    
    //
    public static int createWordMap(Scanner in, Map<String,Integer> wordCount, Set<String> wordsToExclude){
        int totalWords = 0;
        while(in.hasNext()) {
            totalWords++;
            String word = in.next().toLowerCase();
            if(!wordsToExclude.contains(word)) {
                if(wordCount.keySet().contains(word)) {
                    int count = wordCount.get(word);
                    count++;
                    wordCount.put(word,count);
                }
                else {
                    wordCount.put(word,1);
                }
            }
        }
        System.out.println("Total Words: " + totalWords);
        return totalWords;
    }
    
    //
    public static void getTopWords(Map<String,Integer> wordCount, int totalWords, int topWords) {
        for(int i = 0; i < topWords; i++) {
            int highCount = 0;
            String highWord = "";
            for(String word: wordCount.keySet()) {
                if(wordCount.get(word) > highCount) {
                    highCount = wordCount.get(word);
                    highWord = word;
                }
            }
            double percent = Math.round(highCount*10000.0/totalWords) / 100.0;
            System.out.println("\t" + (i+1) + ". " + highWord + " occured " + highCount + " times (" + percent + "%).");
            wordCount.remove(highWord);
        }
    }
}