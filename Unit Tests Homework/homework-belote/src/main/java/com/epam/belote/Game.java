package com.epam.belote;

import com.epam.belote.bonus.Bonus;
import com.epam.belote.player.Bid;
import com.epam.belote.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game {
    private Bid currentBid = Bid.PASS;
    private List<Player> players;
    private CardDealer cardDealer;

    public Game(List<Player> playerList, CardDealer cardDealer) {
        this.players = new ArrayList<>(playerList);
        this.cardDealer = cardDealer;
    }

    public void startGame() {
        bidPhase();
        cardDealingPhase();
        checkForBonuses();
        playPhase();
    }

    private void bidPhase() {
        for (Player player : players) {
            Bid bid = player.bid();
            if(currentBid.ordinal()<bid.ordinal()){
                this.currentBid = player.bid();
            }
        }
    }

    private void checkForBonuses() {
        for (Player player : players) {
            Set<Bonus> bonuses = player.declareBonus();
            bonuses.forEach(bonus -> player.getTeam().increasePoints(bonus.getBonus()));
        }
    }

    private void playPhase() {
        int round = 1;
        while (round <= 8) {
            System.out.println("Round: " + round++);
            for (Player player : players) {
                System.out.println(player.playCard() + " was played");
            }
        }
    }

    private void cardDealingPhase() {
        for (Player player : players) {
            cardDealer.deal5Cards(player);
        }

        if (currentBid.equals(Bid.PASS)) {
            throw new UnsupportedOperationException("All players bid Pass");
        }

        for (Player player : players) {
            cardDealer.deal3Cards(player);
        }
    }

    public Bid getCurrentBid() {
        return this.currentBid;
    }
}
