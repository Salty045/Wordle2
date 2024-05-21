import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {

    //ANSI codes for color
    private final String green = "\u001B[32m";
    private final String yellow = "\u001B[33m";
    private final String white = "\u001B[37m";
    private final String reset = "\u001B[0m";


    //Instance Variables
    private final RandWord randWord = new RandWord();
    private final ArrayList<String> VALID_GUESSES = randWord.getPossibleGuesses();
    private String answer;
    private int attempts;
    private String[][] keyboard;

    /*
        Constructor, intializes answer to a random valid answer in the answer list from randWord
        Initializes attempts to 0
        Initializes keyboard to the letters found on a QWERTY keyboard
     */
    public Wordle() throws FileNotFoundException {
        answer = randWord.getAnswer().toUpperCase();
        attempts = 0;
        keyboard = new String[][]{
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"Z", "X", "C", "V", "B", "N", "M"}
        };
    }

    //returns the answer
    public String getAnswer() {
        return answer;
    }

    //returns the current amount of attempts
    public int getAttempts(){
        return attempts;
    }

    //Prints out the instructions to wordle based on if the user wants them or not
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

    //Returns true, if str equals answer, returns false is otherwise
    public boolean checkWin(String str){
        return str.equalsIgnoreCase(answer);
    }

    /*
    Creates a string made up of various colors based on conditions
    Green: Letter in guess equals letter in answer
    Yellow: Letter in guess is in contained in the answer, but not at the same location
    White: Letter is not in the word
    Returns the colored string
    */
    public String buildString(String str){

        str = str.toUpperCase();

        String str2 = "";

        for(int i = 0; i < str.length(); i++){
            char cur = str.charAt(i);

            if(cur == answer.charAt(i)){
                str2 += green + cur + reset;
            }
            else if(answer.indexOf(cur) != -1){
                str2 += yellow + cur + reset;
            }
            else{
                str2 += white + cur + reset;
            }
        }

        return str2;
    }

    public boolean isValidGuess(String str){
        int last = VALID_GUESSES.size() - 1;
        int start = 0;
        str = str.toLowerCase();
        return randWord.wordSearch(VALID_GUESSES, str, start, last) != -1;
    }

    /*
    Returns "Guess is invalid" if the word is not in the list of valid words
    Returns "You are out of attempts, when attempts = 6
    Returns "Correct" is str equals answer

    If none of the above correct, run setKeyboard and print the keyboard, then return the colored string by calling the buildString method
     */
    public String makeGuess(String str){

        if(!isValidGuess(str)){
            return "Guess is invalid, try again";
        }
        attempts++;

        if(checkWin(str))
            return buildString(str) + "\nCorrect!";
        else if(attempts >= 6)
            return "You are out of attempts. The correct answer was " + answer;


        setKeyboard(str);
        printKeyboard();
        return buildString(str);
    }

    /*
    Runs the actual world game, uses a while loop to run the game while under the max attempts and checkWin is false
     */
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

    //Logic shown line by line
    public void setKeyboard(String str){

            for(int i = 0; i < str.length(); i++){
                //Current letter in str and answer, toUpperCase() for easier equality checking.
                String cur = str.substring(i,i+1).toUpperCase();
                String ans = answer.substring(i,i+1).toUpperCase();

                //iterates through the keyboard 2d array
                for(int j = 0; j < keyboard.length; j++){
                    for(int k = 0; k < keyboard[j].length; k++){
                        if(keyboard[j][k].equals(cur)){

                            //if the current letter in str and answer are equal, set that letter to green in the keyboard
                            if(cur.equals(ans))
                                keyboard[j][k] = green + keyboard[j][k] + reset;

                            //if the current letter in str is in answer, but not in the same position set that letter to yellow in keyboard
                            else if(answer.indexOf(cur) != -1)
                                keyboard[j][k] = yellow + keyboard[j][k] + reset;

                            //if the current letter of str is not in answer, set that letter to white in keyboard
                            else
                                keyboard[j][k] = white + keyboard[j][k] + reset;
                        }
                    }
                }
            }
    }

    //traverses through the keyboard 2d array with two for-each loops and prints it out
    public void printKeyboard(){
        for(String row[] : keyboard){
            for(String str : row)
                System.out.print(str + " ");
            System.out.println();
        }
    }



}
