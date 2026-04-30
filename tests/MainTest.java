import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void properName() {
        assertTrue(Main.properName("Fred"));
        assertTrue(Main.properName("Bob"));
        assertFalse(Main.properName("asdf"));
        assertFalse(Main.properName("joey"));
        assertTrue(Main.properName("Joey"));
    }

    @org.junit.jupiter.api.Test
    void integer() {
        assertTrue(Main.integer("12"));
        assertTrue(Main.integer("43.23"));
        assertTrue(Main.integer("-34.5"));
        assertTrue(Main.integer("+98.7"));
        assertTrue(Main.integer("0"));
        assertTrue(Main.integer("0.0230"));
        assertFalse(Main.integer("023"));
        assertFalse(Main.integer("asdf"));
        assertFalse(Main.integer("Joey"));
    }

    @org.junit.jupiter.api.Test
    void ancestor() {
        assertTrue(Main.ancestor("father"));
        assertTrue(Main.ancestor("mother"));
        assertTrue(Main.ancestor("grandfather"));
        assertTrue(Main.ancestor("grandmother"));
        assertTrue(Main.ancestor("great-grandfather"));
        assertTrue(Main.ancestor("great-grandmother"));
        assertTrue(Main.ancestor("great-great-great-great-great-great-great-great-grandmother"));
        assertFalse(Main.ancestor("Joey"));
        assertFalse(Main.ancestor("asdf"));
        assertFalse(Main.ancestor("1234"));
    }

    @org.junit.jupiter.api.Test
    void palindrome() { // 10-letter words only
        assertTrue(Main.palindrome("asdfggfdsa"));
        assertTrue(Main.palindrome("envyllyvne"));
        assertTrue(Main.palindrome("dromppmord"));
        assertFalse(Main.palindrome("racecar"));
        assertFalse(Main.palindrome("asdf"));
        assertFalse(Main.palindrome("Joey"));
        assertFalse(Main.palindrome("1234"));
        assertFalse(Main.palindrome("grandfather"));
    }

    @org.junit.jupiter.api.Test
    void wordleMatches() {
        // test that all returned words are truly possible solutions based on the response history,
        // and that all possible solutions are returned based on the response history

    }

    @org.junit.jupiter.api.Test
    void noFalseNegatives() {
        List<List<WordleResponse>> history = List.of(
                // STALE all gray
                List.of(
                        new WordleResponse('S', 0, LetterResponse.WRONG_LETTER),
                        new WordleResponse('T', 1, LetterResponse.WRONG_LETTER),
                        new WordleResponse('A', 2, LetterResponse.WRONG_LETTER),
                        new WordleResponse('L', 3, LetterResponse.WRONG_LETTER),
                        new WordleResponse('E', 4, LetterResponse.WRONG_LETTER)
                ),
                // COUGH all gray
                List.of(
                        new WordleResponse('C', 0, LetterResponse.WRONG_LETTER),
                        new WordleResponse('O', 1, LetterResponse.WRONG_LETTER),
                        new WordleResponse('U', 2, LetterResponse.WRONG_LETTER),
                        new WordleResponse('G', 3, LetterResponse.WRONG_LETTER),
                        new WordleResponse('H', 4, LetterResponse.WRONG_LETTER)
                ),
                // W green slot 0, Y green slot 4, rest gray
                List.of(
                        new WordleResponse('W', 0, LetterResponse.CORRECT_LOCATION),
                        new WordleResponse('B', 1, LetterResponse.WRONG_LETTER),
                        new WordleResponse('F', 2, LetterResponse.WRONG_LETTER),
                        new WordleResponse('J', 3, LetterResponse.WRONG_LETTER),
                        new WordleResponse('Y', 4, LetterResponse.CORRECT_LOCATION)
                )
        );

        List<String> results = Main.wordleMatches(history);
        List<String> expected = List.of("widdy", "wimpy", "windy", "winky");

        // Every expected word must appear — no false negatives
        for (String word : expected)
            assertTrue(results.contains(word), word + " was missing from results");

        // No word outside expected should appear — no false positives
        for (String word : results)
            assertTrue(expected.contains(word), word + " should not have been returned");
    }
}