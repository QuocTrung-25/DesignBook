package designbook.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

/**
 * A standard 52-card deck. A new deck is shuffled by default.
 */
public final class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        this(new Random());
    }

    public Deck(Random random) {
        Objects.requireNonNull(random, "random must not be null");
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards, random);
    }

    /** Deals and removes the next card from the deck. */
    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("No cards remain in the deck.");
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /** Restores all 52 cards and shuffles them again. */
    public void shuffle() {
        if (cards.size() != 52) {
            throw new IllegalStateException(
                "Cannot shuffle a partially dealt deck; create a new Deck instead."
            );
        }
        Collections.shuffle(cards);
    }
}
