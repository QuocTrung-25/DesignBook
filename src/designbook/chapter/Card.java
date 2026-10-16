package designbook.chapter;

import java.util.Objects;

/** Represents one standard playing card. */
public final class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank, "rank must not be null");
        this.suit = Objects.requireNonNull(suit, "suit must not be null");
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
