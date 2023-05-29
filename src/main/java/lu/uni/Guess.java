package lu.uni;

import java.io.Serializable;
import java.util.List;

public class Guess implements Serializable {
    private List<GuessItem> guessItems;

    public Guess(List<GuessItem> guessItems) {
        this.guessItems = guessItems;
    }

    public List<GuessItem> getGuessItems() {
        return guessItems;
    }

    public void setGuessItems(List<GuessItem> guessItems) {
        this.guessItems = guessItems;
    }
}

