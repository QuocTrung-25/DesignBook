package designbook.solution;

import designbook.chapter.Card;
import designbook.chapter.Rank;
import designbook.chapter.Suit;

import java.util.HashMap;
import java.util.Map;

/**
 * Eager flyweight factory for playing cards.
 * All 52 cards are created when the class is initialized.
 */
public final class EagerCardFactory {
    private static final Map<String, Card> CARDS = new HashMap<>();

    static {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                String key = rank + "-" + suit;
                CARDS.put(key, new Card(rank, suit));
            }
        }
    }

    private EagerCardFactory() {
    }

    public static Card getCard(Rank rank, Suit suit) {
        return CARDS.get(rank + "-" + suit);
    }

    public static int size() {
        return CARDS.size();
    }
}
