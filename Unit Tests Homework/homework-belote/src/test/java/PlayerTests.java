import com.epam.belote.Dealer;
import com.epam.belote.Game;
import com.epam.belote.Team;
import com.epam.belote.bonus.Bonus;
import com.epam.belote.cards.Card;
import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.Bid;
import com.epam.belote.player.Player;
import com.epam.belote.player.PlayerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Set;

public class PlayerTests {
    private Player player;

    @Before
    public void init(){
        this.player = new PlayerImpl(new Team());
    }

    @Test
    public void playerBidShouldChangeGameBid() {
        Player player = Mockito.mock(Player.class);
        Bid expectedBid = Bid.CLUBS_TRUMP;
        Mockito.when(player.bid()).thenReturn(expectedBid);

        Game game = new Game(Arrays.asList(player), new Dealer(new DeckImpl()));
        game.startGame();
        Assertions.assertEquals(expectedBid, game.getCurrentBid());
    }

    @Test
    public void declareBonusTestForQuadOfSevenShouldBeIgnored() {
        for (CardSuit suit : CardSuit.values()) {
            player.addCard(new Card(CardType.SEVEN, suit));
        }
        Set<Bonus> bonuses= player.declareBonus();
        Assertions.assertTrue(bonuses.isEmpty());
    }

    @Test
    public void declareBonusTestForQuadOfEightsShouldBeIgnored() {
        for (CardSuit suit : CardSuit.values()) {
            player.addCard(new Card(CardType.EIGHT, suit));
        }
        Set<Bonus> bonuses= player.declareBonus();
        Assertions.assertTrue(bonuses.isEmpty());
    }

    @Test
    public void declareBonusWithNoQuadPresentShouldReturnNoBonus(){
        player.addCard(new Card(CardType.ACE,CardSuit.CLUBS));
        player.addCard(new Card(CardType.ACE,CardSuit.CLUBS));
        player.addCard(new Card(CardType.ACE,CardSuit.CLUBS));
        player.addCard(new Card(CardType.SEVEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.TEN,CardSuit.CLUBS));

        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertTrue(bonuses.isEmpty());
    }

    @Test
    public void declareBonusWithQuadOfNinesShouldCreateBonus(){
        for(CardSuit suit:CardSuit.values()){
            player.addCard(new Card(CardType.NINE,suit));
        }
        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertEquals(1, bonuses.size());
    }

    @Test
    public void declareBonusForNoSequenceShouldReturnEmpty(){
        player.addCard(new Card(CardType.SEVEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.EIGHT,CardSuit.DIAMONDS));
        player.addCard(new Card(CardType.ACE,CardSuit.HEARTS));
        player.addCard(new Card(CardType.TEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.JACK,CardSuit.SPADES));

        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertTrue(bonuses.isEmpty());
    }

    @Test
    public void declareBonusForSequenceWithDifferentSuitShouldReturnEmpty(){
        player.addCard(new Card(CardType.SEVEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.EIGHT,CardSuit.DIAMONDS));
        player.addCard(new Card(CardType.NINE,CardSuit.HEARTS));
        player.addCard(new Card(CardType.TEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.JACK,CardSuit.SPADES));

        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertTrue(bonuses.isEmpty());
    }

    @Test
    public void declareBonusForSequenceOfThreeCardsShouldReturnBonus(){
        player.addCard(new Card(CardType.SEVEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.EIGHT,CardSuit.CLUBS));
        player.addCard(new Card(CardType.NINE,CardSuit.CLUBS));
        player.addCard(new Card(CardType.TEN,CardSuit.DIAMONDS));
        player.addCard(new Card(CardType.JACK,CardSuit.SPADES));

        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertEquals(1, bonuses.size());
    }

    @Test
    public void declareBonusForTwoSuitSequencesShouldReturnTwoBonuses(){
        player.addCard(new Card(CardType.SEVEN,CardSuit.CLUBS));
        player.addCard(new Card(CardType.EIGHT,CardSuit.CLUBS));
        player.addCard(new Card(CardType.NINE,CardSuit.CLUBS));
        player.addCard(new Card(CardType.TEN,CardSuit.DIAMONDS));
        player.addCard(new Card(CardType.JACK,CardSuit.DIAMONDS));
        player.addCard(new Card(CardType.QUEEN,CardSuit.DIAMONDS));

        Set<Bonus> bonuses = player.declareBonus();
        Assertions.assertEquals(2, bonuses.size());
    }

    @Test
    public void playerShouldPlayFirstCard() {
        Player player = new PlayerImpl(new Team());
        Card expected = new Card(CardType.TEN, CardSuit.DIAMONDS);
        player.addCard(expected);
        player.addCard(new Card(CardType.JACK, CardSuit.HEARTS));
        player.addCard(new Card(CardType.NINE, CardSuit.DIAMONDS));
        Assertions.assertEquals(expected, player.playCard());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionWhenPlayerHasNoCardsLeft() {
        Player player = new PlayerImpl(new Team());
        player.addCard(new Card(CardType.TEN, CardSuit.DIAMONDS));
        for (int i = 0; i < 5; i++) {
            player.playCard();
        }
    }
}
