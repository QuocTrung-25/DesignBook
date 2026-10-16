package designbook.chapter;

/**
 * Card ranks ordered for this exercise.
 *
 * Ace is placed first so A-2-3-4-5 can be detected as an ace-low straight.
 * This ordering intentionally does not treat Q-K-A as a straight.
 */
public enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
    EIGHT, NINE, TEN, JACK, QUEEN, KING
}
