import java.io.*;
import java.util.*;
public class RandWord {

    public RandWord(){}

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

    public ArrayList<String> getSpellList() throws FileNotFoundException{
        File list = new File("words.txt");
        Scanner sc = new Scanner(list);
        ArrayList<String> words = new ArrayList<String>();

        while(sc.hasNextLine()){
            words.add(sc.nextLine());
        }

        sc.close();

        return words;
    }

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

    public String getAnswer() throws FileNotFoundException {
        ArrayList<String> answ = getWordList();
        return answ.get((int)(Math.random()*answ.size()));
    }

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
