import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    static boolean properName(String s) {
        return s.matches("[A-Z][a-z]+");
    }

    static boolean integer(String s) {
        return s.matches("[+-]?(0|[1-9]\\d*)(\\.\\d+)?");
    }

    static boolean ancestor(String s) {
        return s.matches("((great-)*grand)?(father|mother)");
    }

    static boolean palindrome(String s) {
        return s.matches("(?i)([a-z])([a-z])([a-z])([a-z])([a-z])\\5\\4\\3\\2\\1");
    }

    static List<String> wordList;
    static {
        try {
            wordList = Files.readAllLines(Path.of("src/valid-wordle-words.txt"));
        } catch (IOException e) {
            wordList = new ArrayList<>();
        }
    }
    static List<String> wordleMatches(List <List <WordleResponse> > responses) {
        // return all words from list that match ALL responses
            // meaning, return all possible answers based on previous guess history
        List<String> output = new ArrayList<>();
        for (String word : wordList) {
            if (wordMatchesFullResponseHistory(word, responses)) output.add(word);
        }
        return output;
    }
    //helper method
    static boolean wordMatchesFullResponseHistory(String word, List<List<WordleResponse>> responses) {
        for (List<WordleResponse> response : responses) {
            if (!wordMatchesSingleResponse(word, response)) return false;
        }
        return true;
    }
    // helper method
    static boolean wordMatchesSingleResponse(String word, List<WordleResponse> response) {
        // a word matches a response if it is possible to be the answer based on the WordleResponses for each letter.
        String regex = "(?i)"; // case-insensitive matching
        word = word.toLowerCase();

        for (WordleResponse letter : response) {
            char c = Character.toLowerCase(letter.letter);
            switch (letter.response) {
                case WRONG_LETTER ->
                    // query: the following character does not appear anywhere in the string
                    regex += "(?!.*" + c + ")";
                case WRONG_LOCATION ->
                    // query: the following character appears somewhere in the string, but not at the following location
                    regex += "(?=.*" + c + ")(?!.{" + letter.index + "}" + c + ")";
                case CORRECT_LOCATION ->
                    // query: the following character appears at the following location
                    regex += "(?=.{" + letter.index + "}" + c + ")";
            }
        }

        regex += "[A-Z]{5}";

        return Pattern.compile(regex).matcher(word).matches();
    }
}
