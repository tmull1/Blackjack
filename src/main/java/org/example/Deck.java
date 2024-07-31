package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (String suit : Suits.SUITS) {
            for (String value : Values.VALUES) {
                cards.add(new Card(suit, value));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder deckString = new StringBuilder();
        for (Card card : cards) {
            deckString.append(card.toString()).append("\n");
        }
        return deckString.toString();
    }
}



