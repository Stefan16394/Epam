package com.epam.belote;

import com.epam.belote.deck.Deck;
import com.epam.belote.deck.DeckImpl;
import com.epam.belote.player.Player;
import com.epam.belote.player.PlayerImpl;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Team team1 = new Team();
        Team team2 = new Team();

        List<Player> playerList = Arrays.asList(
                new PlayerImpl(team1), new PlayerImpl(team1),
                new PlayerImpl(team2), new PlayerImpl(team2)
        );

        Deck deck = new DeckImpl();
        CardDealer cardDealer = new Dealer(deck);
        Game game = new Game(playerList, cardDealer);
        game.startGame();
    }
}
