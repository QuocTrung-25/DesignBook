package designbook.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A collection of cards.
 *
 * The capacity constructor is provided for compatibility with the exercises:
 * it specifies the initial capacity, not the required final number of cards.
 */
public class Hand implements Iterable<Card> {
    private final List<Card> cards;

    public Hand(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative.");
        }
        this.cards = new ArrayList<>(initialCapacity);
    }

    public void add(Card card) {
        cards.add(Objects.requireNonNull(card, "card must not be null"));
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public Iterator<Card> iterator() {
        return getCards().iterator();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
