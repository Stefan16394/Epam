package com.epam.belote.bonus;

import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;

import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * The Sequence bonus is declared when the player has a sequence of three or more cards of the same suit, for example Jack, Queen and King of Spades
 */
public class Sequence implements Bonus {
    private final CardSuit suit;
    private final List<CardType> cards;

    public Sequence(CardSuit suit, List<CardType> cards) {
        this.suit = suit;
        this.cards = unmodifiableList(cards);
    }

    public CardSuit getSuit() {
        return suit;
    }

    public List<CardType> getCards() {
        return cards;
    }

    public int getBonus() {
        switch (cards.size()) {
            case 3:
                return 20;
            case 4:
                return 50;
            default:
                return 100;
        }
    }
}
