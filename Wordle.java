import java.util.Scanner;
import java.io.File;

public class Wordle {
    
    private File wordList = new File("WordList.txt");
    private int LAST_GUESS = 6;
    private int GUESS_LENGTH = 5;
    private String[] guesses = {"     ", "     ", "     ", "     ", "     ", "     "};
    private boolean shouldContinue = true;
    private Scanner scanner = new Scanner(System.in);
    private Scanner fileScanner;
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
            if (guess.length() == GUESS_LENGTH && validGuess()) {    
                guesses[guessNum - 1] = processGuess();
                if (guessNum == LAST_GUESS || guess.equals(word)) {
                    shouldContinue = false;
                }
                guessNum++;
            } else if (guess.length() != GUESS_LENGTH) {
                System.out.println("Guess is not five letters");
            } else {
                System.out.println("Guess not found in word list");
            }
        }
        printBoard();
        System.out.println("The word was " + word);
    }

    public String getWord() {
        int fileLength = fileLength();
        int random = (int) (Math.random() * fileLength + 1);
        int lines = 1;
        String word = "";
        try {
            fileScanner = new Scanner(wordList);
            String line;
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();
                if (lines == random) {
                    word = line;
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

    public boolean validGuess() {
        boolean found = false;
        try {
            fileScanner = new Scanner(wordList);
            String line;
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();
                if (line.equals(guess.toLowerCase())) {
                    found = true;
                }
            }
            fileScanner.close();
        } catch (Exception FileNotFoundException) {
            return false;
        }
        return found;
    }

    public String processGuess() {
        String[] wordSplit = word.split("");
        String[] guessSplit = guess.split("");
        String processedGuess = "";
        String letter;
        boolean halfCorrect;
        for (int i = 0; i < guessSplit.length; i++) {
            halfCorrect = false;
            letter = guessSplit[i].toLowerCase();
            for (int a = 0; a < wordSplit.length; a++) {
                if (wordSplit[a].equals(letter) && a != i) {
                    halfCorrect = true;
                }
            }
            if (wordSplit[i].equals(letter)) {
                processedGuess += GREEN + letter + RESET;
            } else if (halfCorrect) {
                processedGuess += YELLOW + letter + RESET;
            } else {
                processedGuess += letter;
            }
        }
        return processedGuess;
    }

    public int fileLength() {
        int count = 0;
        try {
            fileScanner = new Scanner(wordList);
            while (fileScanner.hasNextLine()) {
                count++;
                fileScanner.nextLine();
            }
        } catch (Exception FileNotFoundException) {
            return 0;
        }
        return count;
    }

}