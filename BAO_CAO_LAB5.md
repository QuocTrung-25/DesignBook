# BÁO CÁO LAB 5

## Exercise 8 – Flyweight Design Pattern

### 1. Mục tiêu
- Áp dụng Flyweight Design Pattern để tái sử dụng các đối tượng `Card`.
- Phân biệt khởi tạo trước (Eager Initialization) và khởi tạo lười (Lazy Initialization).
- Đo thời gian lấy đủ 52 lá bài bằng `System.currentTimeMillis()`.
- So sánh hiệu năng và rút ra kết luận về hai cách triển khai.

### 2. Phân tích thiết kế
Flyweight là mẫu thiết kế giúp giảm số lượng đối tượng được tạo ra bằng cách tái sử dụng các đối tượng có trạng thái giống nhau.

Trong bộ bài Poker, mỗi lá bài được xác định bởi `Rank` và `Suit`. Với 13 hạng và 4 chất, ta có:

- 13 x 4 = 52 lá bài khác nhau

Thay vì tạo mới một đối tượng `Card` mỗi khi cần dùng, lớp `Card` có thể lưu các đối tượng trong một `Map` và trả về đối tượng đã tồn tại.

Hai phương pháp được so sánh:

#### Phương pháp 1 – Eager Initialization
- Tạo sẵn toàn bộ 52 đối tượng `Card` khi lớp được khởi tạo.
- Khi cần sử dụng, lấy trực tiếp từ `Map`.
- Tốn thời gian khởi tạo ban đầu ngay cả khi không dùng hết tất cả các lá bài.

#### Phương pháp 2 – Lazy Initialization
- Ban đầu chưa tạo các đối tượng `Card`.
- Khi cần một lá bài, kiểm tra trong `Map`.
- Nếu chưa có, tạo mới và lưu lại; nếu đã có, trả về đối tượng cũ.
- Tiết kiệm chi phí khởi tạo nếu chỉ dùng một phần bộ bài.

### 3. Ứng dụng trong dự án hiện tại
Trong dự án DesignBook, các lớp hỗ trợ nằm trong package `designbook.chapter`:

- `Card`
- `Rank`
- `Suit`
- `Deck`

Mỗi lá bài có thể được định danh theo `rank + suit`, vì vậy việc lưu trữ trong `Map` là hợp lý và có thể đảm bảo tính duy nhất của từng instance `Card`.

### 4. Hiện thực mã nguồn Java

#### 4.1. Lazy Initialization
```java
import java.util.HashMap;
import java.util.Map;

public class CardFactory {
    private static final Map<String, Card> CARDS = new HashMap<>();

    public static Card getCard(Rank rank, Suit suit) {
        String key = rank + "-" + suit;
        return CARDS.computeIfAbsent(key, k -> new Card(rank, suit));
    }
}
```

`computeIfAbsent()` kiểm tra khóa trong `Map`. Nếu key chưa tồn tại, chương trình sẽ tạo mới và lưu vào `Map`; nếu đã có, sẽ tái sử dụng đối tượng đã tồn tại.

#### 4.2. Eager Initialization
```java
import java.util.HashMap;
import java.util.Map;

public class EagerCardFactory {
    private static final Map<String, Card> CARDS = new HashMap<>();

    static {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                String key = rank + "-" + suit;
                CARDS.put(key, new Card(rank, suit));
            }
        }
    }

    public static Card getCard(Rank rank, Suit suit) {
        return CARDS.get(rank + "-" + suit);
    }
}
```

Ở cách này, tất cả các lá bài được tạo ngay khi lớp `EagerCardFactory` được khởi tạo lần đầu.

### 5. Đo thời gian thực thi
```java
public class CardBenchmark {
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
    }
}
```

### Lưu ý về phép đo
Đoạn mã trên chỉ mang tính minh họa. Do `Eager` có thể đã được khởi tạo sớm hơn thời điểm đo, kết quả chưa phản ánh đầy đủ chi phí khởi tạo của cả hai cách. Để so sánh chính xác hơn, nên:
- chạy từng phương pháp trong một JVM riêng;
- đo cả thời gian khởi tạo lẫn thời gian lấy đủ 52 lá bài;
- thực hiện nhiều lần để giảm sai số.

### 6. So sánh và đánh giá

| Tiêu chí | Eager Initialization | Lazy Initialization |
|---|---|---|
| Thời điểm tạo đối tượng | Khi lớp được khởi tạo | Khi cần sử dụng |
| Chi phí ban đầu | Cao hơn vì tạo toàn bộ đối tượng | Thấp hơn nếu chưa cần dùng hết |
| Lần truy cập đầu tiên | Thường nhanh hơn vì đối tượng đã có | Có thể chậm hơn do phải tạo mới |
| Bộ nhớ | Lưu sẵn toàn bộ 52 lá bài | Chỉ lưu các lá bài đã được yêu cầu |
| Khi lấy đủ 52 lá bài | Không cần tạo thêm | Vẫn phải tạo nếu chưa có trong Map |

Khi lấy đủ 52 lá bài, Lazy Initialization vẫn phải tạo tất cả các đối tượng nếu ban đầu chưa có lá bài nào được yêu cầu. Vì vậy, nó không nhất thiết nhanh hơn Eager Initialization trong trường hợp sử dụng cả bộ bài.

### 7. Kết luận
Qua bài tập, ta thấy Flyweight giúp tái sử dụng các đối tượng `Card`, hạn chế việc tạo nhiều đối tượng tương đương. 

- `Eager Initialization` phù hợp khi chắc chắn cần sử dụng gần như toàn bộ 52 lá bài và muốn tránh chi phí tạo đối tượng trong lúc truy cập.
- `Lazy Initialization` phù hợp khi chỉ cần một phần bộ bài, vì các đối tượng không được sử dụng sẽ không cần khởi tạo.

Không thể kết luận phương pháp nào luôn nhanh hơn nếu chưa đo trên cùng điều kiện. Kết quả phụ thuộc vào cách khởi tạo, số lượng lá bài được sử dụng và chi phí quản lý `Map`. Do đó, cần thực nghiệm và đánh giá theo nhu cầu thực tế của chương trình.

---

## Exercise 9 – Singleton Design Pattern

### Mục tiêu
- Áp dụng Singleton Pattern để đảm bảo chỉ có một thể hiện duy nhất của `GameModel`.
- Bộ bài được khởi tạo một lần và tái sử dụng trong suốt vòng chơi.

### Thiết kế
Trong project hiện tại, `GameModel` được triển khai như sau:

```java
package designbook.solution;

import designbook.chapter.Deck;

class GameModel {
    private static final GameModel INSTANCE = new GameModel();

    private final Deck aDeck = new Deck();

    public static GameModel instance() {
        return INSTANCE;
    }

    private GameModel() {}

    public void newGame() {
        aDeck.shuffle();
    }
}
```

#### Đặc điểm
- `INSTANCE` là biến static duy nhất.
- Constructor là `private` nên không thể tạo đối tượng mới từ ngoài lớp.
- `newGame()` khởi tạo lại bộ bài bằng cách xáo trộn `Deck` hiện có.
- Đây là kiểu Singleton theo phương pháp Eager Initialization.

### Test mẫu
```java
package designbook.solution;

public class GameModelTest {
    public static void main(String[] args) {
        GameModel game1 = GameModel.instance();
        GameModel game2 = GameModel.instance();

        System.out.println(game1 == game2);

        game1.newGame();
        game2.newGame();
    }
}
```

### Kết luận
Singleton đảm bảo tất cả các phần trong chương trình chia sẻ cùng một trạng thái `Deck` và không tạo thêm nhiều đối tượng không cần thiết.

---

## Tổng kết chung Lab 5
- Flyweight giúp giới hạn số lượng object `Card` được tạo ra và tiết kiệm bộ nhớ khi nhiều lá bài có cùng giá trị/hạng/chất.
- Singleton giúp chỉ có một đối tượng `GameModel` trong suốt ứng dụng.
- Cả hai mẫu thiết kế đều nhằm tối ưu tài nguyên và duy trì tính nhất quán trong hệ thống.
