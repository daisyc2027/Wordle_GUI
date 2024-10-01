
public class WordleGame {
    String[] wordsList = {"Piano", "Occur", "Faint", "Voice", "Value", "Float", "Quiet", "Sauce", "Noise"};
    String wordToGuess;
    int numberGuesses;
    int currentGuess;
    boolean gameWon;

    WordleGame(){
        wordToGuess = generateWord(this.wordsList).toLowerCase();
        this.numberGuesses = 6;
        this.currentGuess = 0;
        this.gameWon = false;
    }


    public String makeGuess(String guess){
        numberGuesses--;
        StringBuilder feedback = new StringBuilder();
        for(int i = 0; i<guess.length(); i++){
            if(guess.charAt(i) == wordToGuess.charAt(i)){
                feedback.append("G");
            }
            else if(wordToGuess.contains(String.valueOf(guess.charAt(i)))){
                feedback.append("Y");
            }
            else {
                feedback.append("B");
            }
        }
        currentGuess++;
        return feedback.toString();
    }

    public static String generateWord(String[] wordList){
        int i = (int)(Math.random()*wordList.length-1);
        return wordList[i];
    }

    public boolean isGameOver(boolean gameWon, int numberGuesses){
        return gameWon || numberGuesses == 0;
    }

}
