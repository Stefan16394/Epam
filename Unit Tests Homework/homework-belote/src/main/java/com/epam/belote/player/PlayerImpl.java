package com.epam.belote.player;

import com.epam.belote.Team;
import com.epam.belote.bonus.Bonus;
import com.epam.belote.bonus.Quad;
import com.epam.belote.bonus.Sequence;
import com.epam.belote.cards.Card;
import com.epam.belote.cards.CardComparator;
import com.epam.belote.cards.CardSuit;
import com.epam.belote.cards.CardType;

import java.util.*;

public class PlayerImpl implements Player {
    private Team team;
    private List<Card> hand;

    public PlayerImpl(Team team) {
        this.team = team;
        this.hand = new ArrayList<>();
    }

    @Override
    public Bid bid() {
        Bid[] bids = Bid.values();
        return bids[new Random().nextInt(bids.length)];
    }

    @Override
    public Set<Bonus> declareBonus() {
        Set<Bonus> bonuses = new HashSet<>();

        checkForQuad(bonuses);
        checkForSequence(bonuses);

        return bonuses;
    }

    private void checkForSequence(Set<Bonus> bonuses) {
        Map<CardSuit, List<CardType>> cards = new HashMap<>();
        for (CardSuit cs : CardSuit.values()) {
            cards.put(cs, new ArrayList<>());
        }
        for (Card card : hand) {
            cards.get(card.getSuit()).add(card.getType());
        }
        for (Map.Entry<CardSuit, List<CardType>> entry : cards.entrySet()) {
            List<CardType> cardsInSuit = entry.getValue();
            if (cardsInSuit.size() > 2) {
                cardsInSuit.sort(new CardComparator());
                List<CardType> possibleSequence = new ArrayList<>();
                for (int i = 0; i < entry.getValue().size()-1;i++) {
                    possibleSequence.add(cardsInSuit.get(i));
                    for(int j=i+1;j<entry.getValue().size();j++){
                        if(cardsInSuit.get(i).getCardValue()+1 == cardsInSuit.get(j).getCardValue()){
                            possibleSequence.add(cardsInSuit.get(j));
                            i++;
                        }else{
                            break;
                        }
                    }
                    if(possibleSequence.size()>2){
                        bonuses.add(new Sequence(entry.getKey(),possibleSequence));
                    }
                    possibleSequence.clear();
                }
            }
        }
    }

    private void checkForQuad(Set<Bonus> bonuses) {
        Map<CardType, Integer> counter = new HashMap<>();
        for (Card card : hand) {
            if (counter.containsKey(card.getType())) {
                counter.put(card.getType(), counter.get(card.getType()) + 1);
            } else {
                counter.put(card.getType(), 1);
            }
        }
        counter.entrySet().stream().filter(card -> !card.getKey().equals(CardType.SEVEN) && !card.getKey().equals(CardType.EIGHT))
                .forEach(card -> {
                    if (card.getValue() == 4) {
                        bonuses.add(new Quad(card.getKey()));
                        System.out.println("QUAD FOUND!");
                    }
                });
    }

    @Override
    public Card playCard() {
        if (hand.isEmpty()) {
            throw new UnsupportedOperationException("Player played all his cards");
        }
        return this.hand.remove(0);
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    public void printHand() {
        for (Card c : hand) {
            System.out.print(c + " ");
        }
        System.out.println("-----------");
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }
}
