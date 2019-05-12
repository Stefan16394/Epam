package com.epam.belote.player;

import com.epam.belote.cards.CardSuit;

public enum Bid {
    PASS(null),
    CLUBS_TRUMP(CardSuit.CLUBS),
    DIAMONDS_TRUMP(CardSuit.DIAMONDS),
    HEARTS_TRUMP(CardSuit.HEARTS),
    SPADES_TRUMP(CardSuit.SPADES),
    NO_TRUMPS(null),
    ALL_TRUMPS(null);

    private CardSuit suit;

    Bid(CardSuit suit) {
        this.suit = suit;
    }

    public CardSuit getSuit() {
        return suit;
    }
}
