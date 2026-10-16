package designbook.solution;

import designbook.chapter.Card;
import designbook.chapter.Hand;
import designbook.chapter.Rank;
import designbook.chapter.Suit;

/** Demonstrates the Exercise 12 poker-hand evaluator. */
public final class Exercise12 {
    private Exercise12() {
    }

    public static void main(String[] args) {
        demonstrate("High card", hand(
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.THREE, Suit.DIAMONDS),
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.EIGHT, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        ), PokerHandType.HIGH_CARD);

        demonstrate("One pair", hand(
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.SEVEN, Suit.DIAMONDS),
            c(Rank.TWO, Suit.HEARTS),
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        ), PokerHandType.ONE_PAIR);

        demonstrate("Two pair", hand(
            c(Rank.FOUR, Suit.CLUBS),
            c(Rank.FOUR, Suit.DIAMONDS),
            c(Rank.NINE, Suit.HEARTS),
            c(Rank.NINE, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS)
        ), PokerHandType.TWO_PAIR);

        demonstrate("Three of a kind", hand(
            c(Rank.SEVEN, Suit.CLUBS),
            c(Rank.SEVEN, Suit.DIAMONDS),
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.TWO, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        ), PokerHandType.THREE_OF_A_KIND);

        demonstrate("Ace-low straight", hand(
            c(Rank.ACE, Suit.CLUBS),
            c(Rank.TWO, Suit.DIAMONDS),
            c(Rank.THREE, Suit.HEARTS),
            c(Rank.FOUR, Suit.SPADES),
            c(Rank.FIVE, Suit.CLUBS)
        ), PokerHandType.STRAIGHT);

        demonstrate("Flush", hand(
            c(Rank.TWO, Suit.HEARTS),
            c(Rank.FIVE, Suit.HEARTS),
            c(Rank.SEVEN, Suit.HEARTS),
            c(Rank.NINE, Suit.HEARTS),
            c(Rank.KING, Suit.HEARTS)
        ), PokerHandType.FLUSH);

        demonstrate("Full house", hand(
            c(Rank.QUEEN, Suit.CLUBS),
            c(Rank.QUEEN, Suit.DIAMONDS),
            c(Rank.QUEEN, Suit.HEARTS),
            c(Rank.THREE, Suit.SPADES),
            c(Rank.THREE, Suit.CLUBS)
        ), PokerHandType.FULL_HOUSE);

        demonstrate("Four of a kind", hand(
            c(Rank.TEN, Suit.CLUBS),
            c(Rank.TEN, Suit.DIAMONDS),
            c(Rank.TEN, Suit.HEARTS),
            c(Rank.TEN, Suit.SPADES),
            c(Rank.ACE, Suit.CLUBS)
        ), PokerHandType.FOUR_OF_A_KIND);

        demonstrate("Straight flush", hand(
            c(Rank.FIVE, Suit.SPADES),
            c(Rank.SIX, Suit.SPADES),
            c(Rank.SEVEN, Suit.SPADES),
            c(Rank.EIGHT, Suit.SPADES),
            c(Rank.NINE, Suit.SPADES)
        ), PokerHandType.STRAIGHT_FLUSH);

        Hand threeSevens = hand(
            c(Rank.SEVEN, Suit.CLUBS), c(Rank.SEVEN, Suit.DIAMONDS),
            c(Rank.SEVEN, Suit.HEARTS), c(Rank.TWO, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        );
        Hand threeKings = hand(
            c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.DIAMONDS),
            c(Rank.KING, Suit.HEARTS), c(Rank.TWO, Suit.SPADES),
            c(Rank.JACK, Suit.CLUBS)
        );

        System.out.println();
        System.out.println("Compare different three-of-a-kind ranks: "
            + comparisonText(PokerHandEvaluator.compare(threeSevens, threeKings)));
        if (PokerHandEvaluator.compare(threeSevens, threeKings) != 0) {
            throw new AssertionError("Same poker category should compare equal.");
        }

        Hand invalid = new Hand(4);
        invalid.add(c(Rank.ACE, Suit.CLUBS));
        try {
            PokerHandEvaluator.evaluate(invalid);
            throw new AssertionError("Expected an exception for a four-card hand.");
        } catch (IllegalArgumentException expected) {
            System.out.println("Invalid hand size handled: " + expected.getMessage());
        }

        System.out.println();
        System.out.println("All Exercise 12 demonstrations passed.");
    }

    private static Card c(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }

    private static Hand hand(Card... cards) {
        Hand hand = new Hand(cards.length);
        for (Card card : cards) {
            hand.add(card);
        }
        return hand;
    }

    private static void demonstrate(String label, Hand hand, PokerHandType expected) {
        PokerHandType actual = PokerHandEvaluator.evaluate(hand);
        System.out.printf("%-20s -> %s%n", label, actual);
        if (actual != expected) {
            throw new AssertionError(
                label + ": expected " + expected + " but got " + actual
            );
        }
    }

    private static String comparisonText(int result) {
        if (result > 0) {
            return "first hand stronger";
        }
        if (result < 0) {
            return "second hand stronger";
        }
        return "equal (same category)";
    }
}
