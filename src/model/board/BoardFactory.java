package model.board;

public class BoardFactory {
    public static GameBoard newEmptyBoard(int width, int height) {
        return new ArrayBoard(width, height);
    }
}
