package com.epam.belote.cards;

public class Card {
    private final CardType type;
    private final CardSuit suit;

    public Card(CardType type, CardSuit suit) {
        this.type = type;
        this.suit = suit;
    }

    public CardType getType() {
        return type;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public int getCardValue(){
        return this.type.getCardValue();
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;
        return this.type.equals(card.type) && this.suit.equals(card.suit);
    }

    @Override
    public String toString() {
        return this.type + " " + this.suit;
    }
}
