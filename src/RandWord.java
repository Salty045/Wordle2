import java.io.*;
import java.util.*;
public class RandWord {

    public RandWord(){}

    //Uses a scanner to traverse through the list of valid wordle answers, then stores each valid answer in an ArrayList and returns it
    public ArrayList<String> getWordList() throws FileNotFoundException {
        File answers = new File("wordle-answers-alphabetical.txt");
        Scanner sc = new Scanner(answers);
        ArrayList<String> wordList = new ArrayList<String>();

        while(sc.hasNextLine()){
            wordList.add(sc.nextLine());
        }
        sc.close();

        return wordList;
    }

    //Uses a scanner to traverse through the list of valid worldle guesses, then stores each valid guess in an ArrayList and returns it
    public ArrayList<String> getPossibleGuesses() throws FileNotFoundException {
        File guesses = new File("wordle-words.txt");
        Scanner sc = new Scanner(guesses);
        ArrayList<String> guessList = new ArrayList<String>();

        while(sc.hasNextLine()){
            guessList.add(sc.nextLine());
        }
        sc.close();

        return guessList;
    }

    //returns a string at a random index in the answer ArrayList
    public String getAnswer() throws FileNotFoundException {
        ArrayList<String> answ = getWordList();
        return answ.get((int)(Math.random()*answ.size()));
    }

    /*
    Recursive binary search through a String ArrayList
    Returns the index of target in the list
    Returns -1 if the target is not found in the list
     */
    public int wordSearch(ArrayList<String> arr, String target, int start, int end){
        int mid = (start+end)/2;
        String mids = arr.get(mid);

        if(start > end)
            return -1;

        if(mids.equals(target))
            return mid;

        if(target.compareTo(mids) < 0)
            return wordSearch(arr, target, start, mid-1);

        if(target.compareTo(mids) > 0)
            return wordSearch(arr, target, mid+1, end);

        return -1;
    }

}
