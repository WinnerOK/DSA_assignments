import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @Test
    void testSuggestCorrection() {
        Checker c = new Checker("worda wordb wordc apple");
        LinkedList<String> result = c.suggestCorrection("world");
        LinkedList<String> correct = new LinkedList<>(Arrays.asList("worda", "wordb", "wordc"));
        assertIterableEquals(correct, result);
    }

    @Test
    void testSuggestCorrectionExceptions() {
        Checker c = new Checker("something");
        assertThrows(IllegalArgumentException.class, () -> c.suggestCorrection(null));
        assertThrows(IllegalArgumentException.class, () -> c.suggestCorrection(""));
        assertThrows(IllegalArgumentException.class, () -> c.suggestCorrection("try to correct this, bi"));

        Checker d = new Checker("");
        assertThrows(IllegalStateException.class, () -> d.suggestCorrection("lol"));

    }

    @Test
    void testAutoCorrect() {
        Checker c = new Checker("some dictionary of words");
        assertEquals("some words of dictionary", c.autoCorrect("sme word for dictonary"));
    }


    @Test
    void testAutoCorrectFreq() {
        Checker c = new Checker("of of  fo fo fo");
        assertEquals("fo", c.autoCorrect("o"));
    }

    @Test
    void testAutoCorrectExceptions() {
        Checker c = new Checker("some dictionary of words");
        assertThrows(IllegalArgumentException.class, () -> c.autoCorrect(null));
    }


    @Test
    void testEstimate() {
        Checker c = new Checker("");
        assertEquals(3, c.estimate("ca", "abc"));
        assertEquals(3, c.estimate("abcdef", "badcfe"));
        assertEquals(4, c.estimate("vlad", "top")); // should it be 0 ?
        assertEquals(6, c.estimate("javacoder", "eiffelcoder"));
        assertEquals(3, c.estimate("qwer", "w"));
    }
}