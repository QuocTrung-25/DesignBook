# DesignBook – Exercise 12: Poker Hand

Standalone Java solution for the starred exercise about evaluating five-card poker hands.

## Singleton GameModel addition\n\n- `designbook.solutions3.GameModel` uses the Singleton pattern: a private constructor and one `getInstance()` access point.\n- The model owns a shuffled 52-card `Deck`, can start a new game, deal five-card hands, and report remaining cards.\n- `designbook.chapter3.Deck` was added because the uploaded project did not include a deck class.\n\n## Assumptions in this first version

1. Only ace-low straights are supported: A-2-3-4-5 is a straight, but Q-K-A is not.
2. Hands are compared by poker category only. Two hands of the same category compare equal, regardless of card ranks.
3. A hand must contain exactly five cards to be evaluated. Otherwise `PokerHandEvaluator.evaluate()` throws `IllegalArgumentException`.

## Requirements

- JDK 17 or newer
- No external libraries or build tools required

## Run from the project root

### Windows PowerShell
```powershell
javac -d out src\designbook\chapter3\*.java src\designbook\solutions3\*.java
java -cp out designbook.solutions3.Exercise12
```

### macOS / Linux
```bash
mkdir -p out
javac -d out src/designbook/chapter3/*.java src/designbook/solutions3/*.java
java -cp out designbook.solutions3.Exercise12
```

## Project structure

- `designbook.chapter3.Card`, `Hand`, `Rank`, `Suit`: small self-contained versions of the supporting classes needed by this exercise.
- `designbook.solutions3.PokerHandType`: categories in ascending strength order.
- `designbook.solutions3.PokerHandEvaluator`: detects categories and compares hands.
- `designbook.solutions3.Exercise12`: runnable demonstrations and simple assertions.

## Notes for integrating into the original DesignBook repository

The `chapter3` classes included here make this ZIP independently compilable. If your original repository already has `Card`, `Hand`, `Rank`, and `Suit`, copy only the files under `designbook/solutions3` and adjust method/enum names if they differ. Do not overwrite the original classes without checking them first.

This implementation deliberately follows the two simplifications in the prompt; it does not implement kicker comparisons or choose the best five cards from a hand containing more than five cards.
