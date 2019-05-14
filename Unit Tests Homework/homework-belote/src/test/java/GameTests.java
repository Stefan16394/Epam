import com.epam.belote.Dealer;
import com.epam.belote.Game;
import com.epam.belote.Team;
import com.epam.belote.bonus.Bonus;
import com.epam.belote.bonus.Quad;
import com.epam.belote.bonus.Sequence;
import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.Bid;
import com.epam.belote.player.Player;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameTests {

    @Test
    public void playerWithHighestBidShouldChangeCurrentBid(){
        Player playerOne = Mockito.mock(Player.class);
        Player playerTwo = Mockito.mock(Player.class);
        Bid expected = Bid.ALL_TRUMPS;

        Mockito.when(playerOne.bid()).thenReturn(Bid.HEARTS_TRUMP);
        Mockito.when(playerTwo.bid()).thenReturn(expected);

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

    @Test
    public void bonusesShouldAddToPlayerTeamScore(){
        Team team = new Team();
        Player playerOne = Mockito.mock(Player.class);
        Set<Bonus> bonusSet = new HashSet<>();
        bonusSet.add(new Quad(CardType.ACE));

        Mockito.when(playerOne.declareBonus()).thenReturn(bonusSet);
        Mockito.when(playerOne.bid()).thenReturn(Bid.CLUBS_TRUMP);
        Mockito.when(playerOne.getTeam()).thenReturn(team);

        Game game = new Game(Arrays.asList(playerOne),new Dealer(new DeckImpl()));
        game.startGame();
        Assertions.assertEquals(100, team.getPoints());
    }

    @Test
    public void bothPlayersShouldContributeToTeamScore(){
        Team team = new Team();
        Player playerOne = Mockito.mock(Player.class);
        Set<Bonus> bonusSet = new HashSet<>();
        bonusSet.add(new Quad(CardType.ACE));
        Player playerTwo = Mockito.mock(Player.class);
        Set<Bonus> bonusSet2 = new HashSet<>();
        bonusSet2.add(new Sequence(CardSuit.SPADES,Arrays.asList(CardType.NINE,CardType.TEN,CardType.JACK,CardType.QUEEN)));

        Mockito.when(playerOne.declareBonus()).thenReturn(bonusSet);
        Mockito.when(playerTwo.declareBonus()).thenReturn(bonusSet2);
        Mockito.when(playerOne.getTeam()).thenReturn(team);
        Mockito.when(playerTwo.getTeam()).thenReturn(team);
        Mockito.when(playerOne.bid()).thenReturn(Bid.CLUBS_TRUMP);
        Mockito.when(playerTwo.bid()).thenReturn(Bid.DIAMONDS_TRUMP);

        Game game = new Game(Arrays.asList(playerOne,playerTwo),new Dealer(new DeckImpl()));
        game.startGame();
        Assertions.assertEquals(150, team.getPoints());
    }



}
