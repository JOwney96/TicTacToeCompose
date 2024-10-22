package repository;

/**
 * Enum representing the possible winners of a Tic-Tac-Toe game.
 * <p>
 * The enumeration encompasses the following outcomes:<br>
 * - PLAYER: Indicates that the player has won the game.<br>
 * - COMPUTER: Indicates that the computer has won the game.<br>
 * - TIE: Indicates that the game ended in a tie, with no winner.<br>
 * - NONE: Indicates that there is currently no winner.<br>
 */
public enum EWinner {
    PLAYER,
    COMPUTER,
    TIE,
    NONE,
}
