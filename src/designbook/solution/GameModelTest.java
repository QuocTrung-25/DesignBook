package designbook.solution;
public class  GameModelTest {
    public static void main(String[] args) {
        GameModel game1 = GameModel.instance();
        GameModel game2 = GameModel.instance();

        System.out.println(game1 == game2);

        game1.newGame();
        game2.newGame();
    }
}

