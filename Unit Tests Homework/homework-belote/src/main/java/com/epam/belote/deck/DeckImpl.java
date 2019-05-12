package com.epam.belote.deck;

import com.epam.belote.cards.Card;
import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;

import java.util.*;

public class DeckImpl implements Deck{
    private List<Card> cards = new ArrayList<Card>();

    public DeckImpl() {
        generateCards();
    }

    private void generateCards() {
        for (CardType cardType : CardType.values()) {
            for (CardSuit cardSuit : CardSuit.values()) {
                cards.add(new Card(cardType, cardSuit));
            }
        }
        shuffleDeck();
    }

    @Override
    public void cutDeck() {
        int cutIndex = generateRandomIndex(1, cards.size() - 1);
        List<Card> cutDeck = new ArrayList<>();
        for (int i = cutIndex; i < cards.size(); i++) {
            cutDeck.add(cards.get(i));
        }
        for (int i = 0; i < cutIndex; i++) {
            cutDeck.add(cards.get(i));
        }
        this.cards = cutDeck;
    }

    @Override
    public void printDeck() {
        for (Card c : cards) {
            System.out.println(c);
        }
    }

    @Override
    public Card dealCard(){
        return this.cards.remove(0);
    }

    @Override
    public int getNumberOfCards() {
        return cards.size();
    }

    private void shuffleDeck() {
        for (int i = 0; i < 1000; i++) {
            int firstIndex = generateRandomIndex(0, cards.size());
            int secondIndex = generateRandomIndex(0, cards.size());
            swap(firstIndex, secondIndex);
        }
    }

    private int generateRandomIndex(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    private void swap(int firstIndex, int secondIndex) {
        Card temp = cards.get(firstIndex);
        cards.set(firstIndex, cards.get(secondIndex));
        cards.set(secondIndex, temp);
    }

    public static void main(String[] args) {
        DeckImpl deck = new DeckImpl();
        deck.cutDeck();
    }
}
