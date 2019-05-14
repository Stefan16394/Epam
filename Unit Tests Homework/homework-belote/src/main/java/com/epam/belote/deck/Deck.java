package com.epam.belote.deck;

import com.epam.belote.cards.Card;

import java.util.List;

public interface Deck {
    void cutDeck();
    Card dealCard();
    int getNumberOfCards();
    List<Card> getCards();
}
