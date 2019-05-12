package com.epam.belote.cards;

import java.util.Comparator;

public class CardComparator implements Comparator<CardType> {
    @Override
    public int compare(CardType cardOne, CardType cardTwo) {
        return cardOne.getCardValue() - cardTwo.getCardValue();
    }
}
