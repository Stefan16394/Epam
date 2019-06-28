import com.epam.belote.CardDealer;
import com.epam.belote.Dealer;
import com.epam.belote.Game;
import com.epam.belote.Team;
import com.epam.belote.cards.Card;
import com.epam.belote.deck.Deck;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.Bid;
import com.epam.belote.player.Player;
import com.epam.belote.player.PlayerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

public class CardDealingTests {
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

    @Test
    public void allNotPassShouldDealAllCardsInDeck() {
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);
        Player playerThree = Mockito.mock(Player.class);
        Player playerFour = Mockito.mock(Player.class);

        Mockito.when(playerOne.bid()).thenReturn(Bid.PASS);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.DIAMONDS_TRUMP);
        Mockito.when(playerThree.bid()).thenReturn(Bid.PASS);
        Mockito.when(playerFour.bid()).thenReturn(Bid.ALL_TRUMPS);

        Game game = new Game(Arrays.asList(playerOne, playerTwo,playerThree,playerFour), cardDealer);
        game.startGame();

        Assertions.assertEquals(0, deck.getNumberOfCards());
    }

    @Test
    public void generateDeck() {
        Assertions.assertEquals(32, this.deck.getNumberOfCards());
    }

    @Test
    public void deckDealOneCard() {
        Card card = this.deck.dealCard();

        Assertions.assertFalse(this.deck.getCards().contains(card));
    }

    @Test
    public void test(){
        Player player = Mockito.mock(Player.class);
//        Mockito.when(player.addCard(org.mockito.ArgumentMatchers.any(Card.class))).thenReturn(null);
    }
}
