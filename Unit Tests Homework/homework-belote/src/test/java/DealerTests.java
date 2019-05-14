import com.epam.belote.CardDealer;
import com.epam.belote.Dealer;
import com.epam.belote.Team;
import com.epam.belote.cards.Card;
import com.epam.belote.deck.Deck;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.PlayerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class DealerTests {
    private CardDealer cardDealer;
    private Deck deck;

    @Before
    public void init() {
        this.deck = new DeckImpl();
        this.cardDealer = new Dealer(this.deck);
    }

    @Test
    public void dealerShouldDeal5Cards() {
        int expected = deck.getNumberOfCards() - 5;
        cardDealer.deal5Cards(new PlayerImpl(new Team()));
        Assertions.assertEquals(expected, deck.getNumberOfCards());
    }

    @Test
    public void dealerShouldDeal3Cards() {
        int expected = deck.getNumberOfCards() - 3;
        cardDealer.deal3Cards(new PlayerImpl(new Team()));
        Assertions.assertEquals(expected, deck.getNumberOfCards());
    }
}
