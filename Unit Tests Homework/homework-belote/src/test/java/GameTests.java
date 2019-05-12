import com.epam.belote.Dealer;
import com.epam.belote.Game;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.Bid;
import com.epam.belote.player.Player;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Arrays;

public class GameTests {

    @Test
    public void playerWithHighestBidShouldChangeCurrentBid(){
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);
        Bid expected = Bid.ALL_TRUMPS;

        Mockito.when(playerOne.bid()).thenReturn(expected);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.ALL_TRUMPS);

        Game game = new Game(Arrays.asList(playerOne,playerTwo),new Dealer(new DeckImpl()));
        game.startGame();

        Assertions.assertEquals(expected,game.getCurrentBid());
    }

    @Test
    public void biddingWithLowerBidThanCurrentShouldNotChangeCurrentBid(){
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);
        Bid expected = Bid.ALL_TRUMPS;

        Mockito.when(playerOne.bid()).thenReturn(expected);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.CLUBS_TRUMP);

        Game game = new Game(Arrays.asList(playerOne,playerTwo),new Dealer(new DeckImpl()));
        game.startGame();

        Assertions.assertEquals(expected,game.getCurrentBid());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void allPassShouldNotStartGame(){
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);

        Mockito.when(playerOne.bid()).thenReturn(Bid.PASS);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.PASS);

        Game game = new Game(Arrays.asList(playerOne,playerTwo),new Dealer(new DeckImpl()));
        game.startGame();
    }

    @Test
    public void allNotPassShouldStartGame(){
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);

        Mockito.when(playerOne.bid()).thenReturn(Bid.PASS);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.DIAMONDS_TRUMP);

        Game game = new Game(Arrays.asList(playerOne,playerTwo),new Dealer(new DeckImpl()));
        game.startGame();
    }

}
