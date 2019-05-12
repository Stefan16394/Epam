package com.epam.belote;

import com.epam.belote.cards.Card;
import com.epam.belote.deck.Deck;
import com.epam.belote.player.Player;

public class Dealer implements CardDealer {
    private Deck deck;

    public Dealer(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void deal5Cards(Player player) {
        this.dealCards(5, player);
    }

    @Override
    public void deal3Cards(Player player) {
        this.dealCards(3, player);
    }

    private void dealCards(int numberOfCards, Player player) {
        for (int i = 0; i < numberOfCards; i++) {
            Card card = this.deck.dealCard();
            player.addCard(card);
        }
    }
}
