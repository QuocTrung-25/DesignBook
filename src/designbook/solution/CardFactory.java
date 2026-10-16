package designbook.solution;

import designbook.chapter.Card;
import designbook.chapter.Rank;
import designbook.chapter.Suit;

import java.util.HashMap;
import java.util.Map;

/**
 * Lazy flyweight factory for playing cards.
 * Each unique (rank, suit) pair is created only once and reused.
 */
public final class CardFactory {
    private static final Map<String, Card> CARDS = new HashMap<>();

    private CardFactory() {
    }

    public static Card getCard(Rank rank, Suit suit) {
        String key = rank + "-" + suit;
        return CARDS.computeIfAbsent(key, k -> new Card(rank, suit));
    }

    public static int size() {
        return CARDS.size();
    }
}
