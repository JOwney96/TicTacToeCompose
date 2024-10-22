package repository;

/**
 * Represents the different types of characters (tokens) that can appear on a Tic-Tac-Toe board.
 * The tokens include the player ('X'), the computer ('O'), and an empty space (' ').
 */
public enum ECharToken {
    PLAYER('X'), COMPUTER('O'), EMPTY(' ');

    private final Character token;

    ECharToken(Character token) {
        this.token = token;
    }

    public Character token() {
        return this.token;
    }
}
