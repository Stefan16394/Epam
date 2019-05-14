package com.epam.belote.deck;

import com.epam.belote.cards.Card;

public interface Deck {
    void cutDeck();
    Card dealCard();
    int getNumberOfCards();
}
