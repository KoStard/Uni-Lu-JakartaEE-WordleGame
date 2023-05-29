package lu.uni;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SessionScoped
@Named("wordleGameBean")
public class WordleGameBean implements Serializable {
    private WordListsProviderBean wordListsProviderBean;
    private static final int MAX_ATTEMPTS = 6;

    private boolean gameWon;
    private String currentGuess;
    private int remainingAttempts;
    private List<Guess> previousGuesses;
    private String targetWord;

    @Inject
    public WordleGameBean(WordListsProviderBean wordListsProviderBean) {
        this.wordListsProviderBean = wordListsProviderBean;
        restart();
    }

    public void restart() {
        currentGuess = "";
        remainingAttempts = MAX_ATTEMPTS;
        previousGuesses = new ArrayList<>();
        gameWon = false;
        targetWord = wordListsProviderBean.getRandomWord().toUpperCase();
        System.out.println("targetWord: " + targetWord);
    }

    public String guess() {
        if (remainingAttempts <= 0) {
            return "";
        }

        if (!wordListsProviderBean.isGuessAllowed(currentGuess)) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid word", "The provided input either is not a word or is not allowed."));

            return "";
        }

        List<Feedback> feedback = generateFeedback(currentGuess);
        List<GuessItem> guessItems = IntStream.range(0, currentGuess.length()).mapToObj(i -> new GuessItem(currentGuess.charAt(i), feedback.get(i))).toList();
        previousGuesses.add(new Guess(guessItems));
        gameWon = currentGuess.equals(targetWord);
        if (gameWon) {
            return "";
        }

        currentGuess = "";
        remainingAttempts--;

        return "";
    }

    private List<Feedback> generateFeedback(String guess) {
        List<Feedback> feedback = new ArrayList<>();
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            if (targetWord.charAt(i) == guessChar) {
                feedback.add(Feedback.CORRECT);
            } else if (targetWord.contains(String.valueOf(guessChar))) {
                feedback.add(Feedback.MISPLACED);
            } else {
                feedback.add(Feedback.INCORRECT);
            }
        }
        return feedback;
    }


    public String getCurrentGuess() {
        return currentGuess;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public List<Guess> getPreviousGuesses() {
        return previousGuesses;
    }

    public void setCurrentGuess(String currentGuess) {
        currentGuess = currentGuess.toUpperCase();
        this.currentGuess = currentGuess;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public void setPreviousGuesses(List<Guess> previousGuesses) {
        this.previousGuesses = previousGuesses;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}
