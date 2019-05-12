import com.epam.belote.bonus.Sequence;
import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceBonusesTest {

    @Test
    public void test3CardSequence() {
        List<CardType> cards = Arrays.asList(
               CardType.SEVEN,CardType.EIGHT,CardType.NINE
        );

        Sequence sequence = new Sequence(CardSuit.CLUBS, cards);
        Assertions.assertEquals(20, sequence.getBonus());
    }

    @Test
    public void test4CardSequence() {
        List<CardType> cards =Arrays.asList(
                CardType.SEVEN,CardType.EIGHT,CardType.NINE,CardType.TEN
        );

        Sequence sequence = new Sequence(CardSuit.CLUBS, cards);
        Assertions.assertEquals(50, sequence.getBonus());
    }

    @Test
    public void test5CardSequence() {
        List<CardType> cards = Arrays.asList(
                CardType.SEVEN,CardType.EIGHT,CardType.NINE,CardType.TEN,CardType.JACK

        );

        Sequence sequence = new Sequence(CardSuit.CLUBS, cards);
        Assertions.assertEquals(100, sequence.getBonus());
    }

    @Test
    public void testMoreThan5CardSequence(){
        List<CardType> cards = Arrays.asList(
                CardType.SEVEN,CardType.EIGHT,CardType.NINE,CardType.TEN,CardType.JACK,CardType.QUEEN
        );

        Sequence sequence = new Sequence(CardSuit.CLUBS, cards);
        Assertions.assertEquals(100, sequence.getBonus());
    }
}
