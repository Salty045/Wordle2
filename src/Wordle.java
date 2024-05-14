import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {

    //ANSI codes for color
    private final String green = "\u001B[32m";
    private final String yellow = "\u001B[33m";
    private final String reset = "\u001B[0m";


    //Instance Variables
    private final RandWord randWord = new RandWord();
    private final ArrayList<String> VALID_GUESSES = randWord.getPossibleGuesses();
    private String answer;
    private int attempts;

    public Wordle() throws FileNotFoundException {
        answer = randWord.getAnswer().toUpperCase();
        attempts = 0;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAttempts(){
        return attempts;
    }

    public void printHeader(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Wordle! Would you like instructions? (yes or no)");
        String str = sc.nextLine();
        if(str.equalsIgnoreCase("yes")){
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("The goal of the game is to guess a random 5-letter word.");
            System.out.println("\nEach letter in your guess will be assigned a color-");
            System.out.println("Green: The letter is in the correct spot of the answer.");
            System.out.println("Yellow: The letter is not in the correct spot of the answer, but still in the word.");
            System.out.println("Grey: The letter is not in the word");
            System.out.println("\nYou have six attempts. Good Luck!");
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

    public boolean checkWin(String str){
        return str.equalsIgnoreCase(answer);
    }

    public String buildString(String str){

        str = str.toUpperCase();
        StringBuilder build = new StringBuilder();

        for(int i = 0; i < str.length(); i++){
            char cur = str.charAt(i);

            if(cur == answer.charAt(i)){
                build.append(green + cur +  reset);
            }
            else if(answer.indexOf(cur) != -1){
                build.append(yellow + cur + reset);
            }
            else{
                build.append(cur);
            }
        }

        return build.toString();
    }

    public boolean isValidGuess(String str){
        int last = VALID_GUESSES.size() - 1;
        int start = 0;
        str = str.toLowerCase();
        return randWord.wordSearch(VALID_GUESSES, str, start, last) != -1;
    }

    public String makeGuess(String str){

        if(!isValidGuess(str)){
            return "Guess is invalid, try again";
        }
        attempts++;
        if(attempts == 6)
            return "You are out of attempts. The correct answer was " + answer;
        else if(checkWin(str)){
            return buildString(str) + "\nCorrect!";
        }

        return buildString(str);
    }

    public void playGame(){
        Scanner input = new Scanner(System.in);
        printHeader();
        String guess = "";
        System.out.println(getAnswer());
        while(!checkWin(guess) && getAttempts() < 6){
            System.out.println("Enter a guess:");
            guess = input.nextLine();
            System.out.println(makeGuess(guess));
            System.out.println("\033[H\033[2J");
        }
    }

}
