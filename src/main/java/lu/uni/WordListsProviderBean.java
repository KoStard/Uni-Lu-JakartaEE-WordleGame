package lu.uni;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named("WordListsProviderBean")
public class WordListsProviderBean {
    private final List<String> allowedGuesses;
    private final List<String> targetWords;

    public List<String> getAllowedGuesses() {
        return allowedGuesses;
    }

    public List<String> getTargetWords() {
        return targetWords;
    }

    public WordListsProviderBean() {
        allowedGuesses = readFile("wordlist.txt");
        targetWords = readFile("answerlist.txt");
    }

    private List<String> readFile(String fileName) {
        List<String> words = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 5 || !line.matches("[a-zA-Z]+")) {
                    System.out.println("Invalid word: " + line);
                    continue;
                }
                words.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return words;
    }

    public String getRandomWord() {
        return targetWords.get((int) (Math.random() * targetWords.size()));
    }

    public boolean isGuessAllowed(String guess) {
        return allowedGuesses.contains(guess.toLowerCase());
    }
}
