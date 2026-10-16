package designbook.solution;

import designbook.chapter.Rank;
import designbook.chapter.Suit;

public class Exercise8 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                EagerCardFactory.getCard(rank, suit);
            }
        }

        long eagerTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();

        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                CardFactory.getCard(rank, suit);
            }
        }

        long lazyTime = System.currentTimeMillis() - start;

        System.out.println("Eager: " + eagerTime + " ms");
        System.out.println("Lazy: " + lazyTime + " ms");
        System.out.println("Eager card count: " + EagerCardFactory.size());
        System.out.println("Lazy card count: " + CardFactory.size());

        if (eagerTime < lazyTime) {
            System.out.println("Conclusion: Eager Initialization is faster in this run.");
        } else if (lazyTime < eagerTime) {
            System.out.println("Conclusion: Lazy Initialization is faster in this run.");
        } else {
            System.out.println("Conclusion: Both approaches are similar in this run.");
        }
    }
}
