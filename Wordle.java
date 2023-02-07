import java.util.Scanner;
import java.io.File;

public class Wordle {
    
    private File wordList = new File("WordList.txt");
    private int fileLength = 1005;
    private int LAST_GUESS = 6;
    private int GUESS_LENGTH = 5;
    private String[] guesses = {"     ", "     ", "     ", "     ", "     ", "     "};
    private boolean shouldContinue = true;
    private Scanner scanner = new Scanner(System.in);
    private String guess;
    private String word = getWord();
    private String YELLOW = "\u001b[33m";
    private String GREEN = "\u001b[32m";
    private String RESET = "\u001b[0m";

    public Wordle() {
        // Nothing needs to be here atm
    }
    
    public static void main(String[] args) {
        Wordle wordle = new Wordle();
        wordle.play();
    }

    public void play() {
        int guessNum = 1;
        while (shouldContinue) {
            printBoard();
            System.out.println("Guess " + guessNum + ": ");
            guess = scanner.nextLine();
            if (guess.length() == GUESS_LENGTH) {    
                guesses[guessNum - 1] = processGuess();
                if (guessNum == LAST_GUESS || guess.equals(word)) {
                    shouldContinue = false;
                }
                guessNum++;
            } else {
                System.out.println("Make a guess with five letters");
            }
        }
        printBoard();
        System.out.println("The word was " + word);
    }

    public String getWord() {
        int random = (int) (Math.random() * fileLength + 1);
        int lines = 1;
        String word = "";
        try {
            Scanner fileScanner = new Scanner(wordList);
            while (lines < fileLength) {
                if (lines == random) {
                    word = fileScanner.nextLine();
                } else {
                    fileScanner.nextLine();
                }
                lines++;
            }
            fileScanner.close();
        } catch (Exception FileNotFoundException){
            return "Word list not found";
        }
        return word;
    }

    public void printBoard() {
        System.out.println("+-----+");
        for (int i = 0; i < guesses.length; i++) {
            System.out.println("|" + guesses[i] + "|");
        }
        System.out.println("+-----+");
    }

    public String processGuess() {
        String[] wordSplit = word.split("");
        String[] guessSplit = guess.split("");
        String processedGuess = "";
        boolean halfCorrect;
        for (int i = 0; i < guessSplit.length; i++) {
            halfCorrect = false;
            for (int a = 0; a < wordSplit.length; a++) {
                if (wordSplit[a].equals(guessSplit[i]) && a != i) {
                    halfCorrect = true;
                }
            }
            if (wordSplit[i].equals(guessSplit[i])) {
                processedGuess += GREEN + guessSplit[i] + RESET;
            } else if (halfCorrect) {
                processedGuess += YELLOW + guessSplit[i] + RESET;
            } else {
                processedGuess += guessSplit[i];
            }
        }
        return processedGuess;
    }

}