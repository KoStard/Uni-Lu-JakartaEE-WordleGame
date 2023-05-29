package lu.uni;


import java.io.Serializable;

public class GuessItem implements Serializable {
    private char letter;
    private Feedback feedback;

    public GuessItem(char letter, Feedback feedback) {
        this.letter = letter;
        this.feedback = feedback;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
