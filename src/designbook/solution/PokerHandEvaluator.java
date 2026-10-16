package designbook.solution;

import designbook.chapter.Card;
import designbook.chapter.Hand;
import designbook.chapter.Rank;
import designbook.chapter.Suit;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Evaluates and compares five-card poker hands.
 *
 * This first version follows the exercise's simplifications:
 * - Ace-low straights only (A-2-3-4-5).
 * - Compare by category only, without rank/kicker tie-breakers.
 */
public final class PokerHandEvaluator {
    private PokerHandEvaluator() {
        // Utility class; do not instantiate.
    }

    /**
     * Returns the category of a hand containing exactly five cards.
     *
     * @param hand hand to evaluate
     * @return poker category
     * @throws NullPointerException if hand is null
     * @throws IllegalArgumentException if hand does not contain exactly five cards
     */
    public static PokerHandType evaluate(Hand hand) {
        Objects.requireNonNull(hand, "hand must not be null");

        if (hand.size() != 5) {
            throw new IllegalArgumentException(
                "A poker hand must contain exactly 5 cards; received " + hand.size() + "."
            );
        }

        List<Card> cards = new ArrayList<>(hand.getCards());
        Map<Rank, Integer> rankCounts = new EnumMap<>(Rank.class);

        for (Card card : cards) {
            rankCounts.merge(card.getRank(), 1, Integer::sum);
        }

        boolean flush = isFlush(cards);
        boolean straight = isStraight(rankCounts);

        if (straight && flush) {
            return PokerHandType.STRAIGHT_FLUSH;
        }
        if (rankCounts.containsValue(4)) {
            return PokerHandType.FOUR_OF_A_KIND;
        }
        if (rankCounts.containsValue(3) && rankCounts.containsValue(2)) {
            return PokerHandType.FULL_HOUSE;
        }
        if (flush) {
            return PokerHandType.FLUSH;
        }
        if (straight) {
            return PokerHandType.STRAIGHT;
        }
        if (rankCounts.containsValue(3)) {
            return PokerHandType.THREE_OF_A_KIND;
        }

        int pairCount = 0;
        for (int count : rankCounts.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        if (pairCount == 2) {
            return PokerHandType.TWO_PAIR;
        }
        if (pairCount == 1) {
            return PokerHandType.ONE_PAIR;
        }
        return PokerHandType.HIGH_CARD;
    }

    private static boolean isFlush(List<Card> cards) {
        Suit firstSuit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (card.getSuit() != firstSuit) {
                return false;
            }
        }
        return true;
    }

    private static boolean isStraight(Map<Rank, Integer> rankCounts) {
        // A straight must contain five distinct ranks.
        if (rankCounts.size() != 5) {
            return false;
        }

        List<Rank> ranks = new ArrayList<>(rankCounts.keySet());
        ranks.sort(Enum::compareTo);

        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i).ordinal() != ranks.get(i - 1).ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares hands by category only.
     *
     * @return negative if first is weaker, zero if categories match,
     *         positive if first is stronger
     */
    public static int compare(Hand first, Hand second) {
        return evaluate(first).compareTo(evaluate(second));
    }
}
