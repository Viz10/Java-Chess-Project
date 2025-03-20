import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    /**
     * this fucntion checks if coordinates are inside the matrix
     *
     * @param someX
     * @param someY
     * @return
     */
    private static boolean matrixinside(int someX, int someY) {
        return (someX >= 0 && someX <= 7 && someY >= 0 && someY <= 7);
    }

    /**
     * this function cheks one cell`s diagonals , L shape , line and column to find if there exists a piece that endangers it
     *
     * @param matrix
     * @param x
     * @param y
     * @param colour
     * @return
     */
    static boolean Check(Piece[][] matrix, int x, int y, String colour) {

        /// Vertical Check North
        for (int i = x + 1; i < 8 && matrixinside(i, y); i++) {
            Piece piece = matrix[i][y]; /// cells checked

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King))
                break; /// Friendly piece block and not current king instance before move pos.
            if (!piece.getColour().equals(colour)) {
                /// Enemy piece
                if (piece instanceof Queen || piece instanceof Rook ||
                        (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;  /// makes the check
                }
                break; /// other non-dangerous enemy piece
            }
        }

        /// Vertical Check South
        for (int i = x - 1; i >= 0 && matrixinside(i, y); --i) {
            Piece piece = matrix[i][y]; /// cells checked

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King))
                break; /// Friendly piece blocks and not current king pos.
            if (!piece.getColour().equals(colour)) {
                /// Enemy piece
                if (piece instanceof Queen || piece instanceof Rook ||
                        (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;  /// makes the check
                }
                break; /// other piece
            }
        }

        /// Horizontal Check Vest
        for (int j = y - 1; j >= 0 && matrixinside(x, j); --j) {
            Piece piece = matrix[x][j]; /// cells checked

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King))
                break; /// Friendly piece blocks and not current king pos.
            if (!piece.getColour().equals(colour)) {
                /// Enemy piece
                if (piece instanceof Queen || piece instanceof Rook ||
                        (piece instanceof King && Math.abs(j - y) == 1)) {
                    return true;  /// makes the check
                }
                break; /// other piece
            }
        }

        /// Horizontal Check Est
        for (int j = y + 1; j < 8 && matrixinside(x, j); ++j) {
            Piece piece = matrix[x][j]; /// cells checked

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King))
                break; /// Friendly piece blocks and not current king pos.
            if (!piece.getColour().equals(colour)) {
                /// Enemy piece
                if (piece instanceof Queen || piece instanceof Rook ||
                        (piece instanceof King && Math.abs(j - y) == 1)) {
                    return true;  /// makes the check
                }
                break; /// other piece
            }
        }

        /// Check for knights
        int[] knightMovesX = {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] knightMovesY = {-1, 1, 2, 2, 1, -1, -2, -2}; /// directions array

        for (int i = 0; i < 8; i++) {
            int newX = x + knightMovesX[i];
            int newY = y + knightMovesY[i];
            if (matrixinside(newX, newY) && matrix[newX][newY] instanceof Knight &&
                    !colour.equals(matrix[newX][newY].getColour())) {
                return true;
            }
        }

        /// Check for diagonal attacks

        for (int i = x + 1, j = y + 1; i < 8 && j < 8 && matrixinside(i, j); i++, j++) { /// top left
            Piece piece = matrix[i][j];

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King)) break; /// Friendly piece blocks

            if (!piece.getColour().equals(colour)) {
                /// Enemy piece
                if (piece instanceof Queen || piece instanceof Bishop || (piece instanceof Pawn && i - x == 1) || (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;  /// Threat found
                }
                break; /// Other piece
            }
        }
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0 && matrixinside(i, j); i--, j--) { /// bottom left
            Piece piece = matrix[i][j];

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King)) break;

            if (!piece.getColour().equals(colour)) {

                if (piece instanceof Queen || piece instanceof Bishop || (piece instanceof Pawn && x - i == 1) || (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;
                }
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0 && matrixinside(i, j); i++, j--) { /// top left
            Piece piece = matrix[i][j];

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King)) break;

            if (!piece.getColour().equals(colour)) {

                if (piece instanceof Queen || piece instanceof Bishop || (piece instanceof Pawn && i - x == 1) || (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;
                }
                break;
            }
        }
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8 && matrixinside(i, j); i--, j++) { /// bottom right
            Piece piece = matrix[i][j];

            if (piece instanceof EmptyPlace) continue;

            if (piece.getColour().equals(colour) && !(piece instanceof King)) break;

            if (!piece.getColour().equals(colour)) {
                if (piece instanceof Queen || piece instanceof Bishop || (piece instanceof Pawn && x - i == 1) || (piece instanceof King && Math.abs(i - x) == 1)) {
                    return true;
                }
                break;
            }
        }

        return false; // No threats found
    }

    /**
     * this function will first simulate the move , if the move leaves the particular move colour king in check , reverts back the pieces on board and returnes false
     * upon succeding , the move will be done and maybe captured piece added to list
     *
     * @param matrix
     * @param White_captured
     * @param Black_captured
     * @param king_X         current x for king
     * @param king_Y         current y for king
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param colour         piece which wants to be moved colout
     * @return
     */
    private static boolean swap(Piece[][] matrix, ArrayList<Piece> White_captured, ArrayList<Piece> Black_captured, int king_X, int king_Y, int x1, int y1, int x2, int y2, String colour) {

        Piece start_piece = matrix[x1][y1];
        Piece end_piece = matrix[x2][y2];
        Piece captured_piece = null;

        /// Swap pieces
        matrix[x2][y2] = start_piece;
        matrix[x1][y1] = new EmptyPlace(start_piece.getx(), start_piece.gety(), "Free");

        /// Update moved piece coordinates
        matrix[x2][y2].setx(x2);
        matrix[x2][y2].sety(y2);

        /// Check if move puts king in check
        if (Check(matrix, king_X, king_Y, colour)) {
            matrix[x1][y1] = start_piece;
            matrix[x2][y2] = end_piece; /// Undo the moves
            System.out.println(start_piece.getColour() + " king will be in check!");
            return false;
        }

        /// capture procedure
        if (!(end_piece instanceof EmptyPlace)) {
            captured_piece = end_piece;
            if (captured_piece.getColour().equals("white")) {
                White_captured.add(captured_piece);
            } else {
                Black_captured.add(captured_piece);
            }
        }
        return true;
    }

    /**
     * this fucnion sets up the board
     *
     * @param matrix
     */
    private static void matrixSet(Piece[][] matrix) {
        for (int i = matrix.length - 1; i >= 0; --i)
            for (int j = 0; j < matrix[i].length; ++j) {
                if (i == 1)
                    matrix[i][j] = new Pawn(i, j, "white", "Pawn", false, false);
                else if (i == 6)
                    matrix[i][j] = new Pawn(i, j, "black", "Pawn", false, false);
                else if (i == 0) {

                    matrix[i][0] = new Rook(i, 0, "white", "Rook", false);
                    matrix[i][7] = new Rook(i, 7, "white", "Rook", false);

                    matrix[i][1] = new Knight(i, 1, "white", "Knight");
                    matrix[i][6] = new Knight(i, 6, "white", "Knight");

                    matrix[i][2] = new Bishop(i, 2, "white", "Bishop");
                    matrix[i][5] = new Bishop(i, 5, "white", "Bishop");

                    matrix[i][3] = new Queen(i, 3, "white", "Queen");
                    matrix[i][4] = new King(i, 4, "white", "King", false);
                } else if (i == 7) {

                    matrix[i][0] = new Rook(i, 0, "black", "Rook", false);
                    matrix[i][7] = new Rook(i, 7, "black", "Rook", false);

                    matrix[i][1] = new Knight(i, 1, "black", "Knight");
                    matrix[i][6] = new Knight(i, 6, "black", "Knight");

                    matrix[i][2] = new Bishop(i, 2, "black", "Bishop");
                    matrix[i][5] = new Bishop(i, 5, "black", "Bishop");

                    matrix[i][3] = new Queen(i, 3, "black", "Queen");
                    matrix[i][4] = new King(i, 4, "black", "King", false);

                } else matrix[i][j] = new EmptyPlace(i, j, "Free");
            }
    }

    /**
     * print function of chess board alongside the captured pieces
     *
     * @param matrix
     * @param White_captured
     * @param Black_captured
     */
    private static void matrixPrint(Piece[][] matrix, ArrayList<Piece> White_captured, ArrayList<Piece> Black_captured) {

        /// Print each captured pieces
        System.out.print("\nBlack player captured: ");
        for (Piece piece : White_captured) {
            if (piece != null)
                piece.print();
        }

        System.out.print("\nWhite player captured: ");
        for (Piece piece : Black_captured) {
            if (piece != null)
                piece.print();
        }
        System.out.println();

        /// Print board
        for (int i = matrix.length - 1; i >= 0; --i) { /// in memory the board starts from top left , downwards but it will be printed , as a normal chess board
            System.out.printf("%110d  ", (i + 1));
            for (int j = 0; j < matrix[i].length; ++j) {
                matrix[i][j].print();
            }
            System.out.println();
        }
        System.out.printf("%115s%7s%7s%7s%7s%7s%7s%7s\n", "a", "b", "c", "d", "e", "f", "g", "h");
        System.out.println();
    }

    /**
     * Asks user to which new piece wants to promote the pawn entered on the last row
     *
     * @param matrix
     * @param x
     * @param y
     */
    private static void promote_pawn(Piece[][] matrix, int x, int y) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter choice of promotional: Queen,Rook,Bishop,Knight");
        String choice;
        choice = scanner.nextLine();

        switch (choice) {
            case "Queen":
                matrix[x][y] = new Queen(x, y, matrix[x][y].getColour(), choice);
                break;
            case "Rook":
                matrix[x][y] = new Rook(x, y, matrix[x][y].getColour(), choice, false);
                break;
            case "Bishop":
                matrix[x][y] = new Bishop(x, y, matrix[x][y].getColour(), choice);
                break;
            case "Knight":
                matrix[x][y] = new Knight(x, y, matrix[x][y].getColour(), choice);
                break;
            default:
                System.out.println("invalid input");
                break;
        }
    }

    /**
     * This fucntion performs the castling operation on the left or right of any king , if king and rook were not moved , same line and space between them
     * also the positions of king passing through the + 2 position must not be in check and king`s next move equals the rook`s coordinate
     *
     * @param matrix    = chess board
     * @param king_X
     * @param king_Y
     * @param king_newX
     * @param king_newY
     */
    private static void Castling(Piece[][] matrix, int king_X, int king_Y, int king_newX, int king_newY) {
        if (king_Y < king_newY) { /// right side castling
        /// Move the King 2 steps to the right
            matrix[king_X][king_Y + 2] = matrix[king_X][king_Y];
            matrix[king_X][king_Y + 2].sety(king_Y + 2);
            matrix[king_X][king_Y] = new EmptyPlace(king_X, king_Y, "Free");

            /// move Rook to the left of king
            matrix[king_X][king_Y + 1] = matrix[king_X][7];
            matrix[king_X][king_Y + 1].sety(king_Y + 1);
            matrix[king_X][7] = new EmptyPlace(king_X, 7, "Free");

        } else if (king_Y > king_newY) { /// left side castling
        /// Move the King 2 steps to the left
            matrix[king_X][king_Y - 2] = matrix[king_X][king_Y];
            matrix[king_X][king_Y - 2].sety(king_Y - 2);
            matrix[king_X][king_Y] = new EmptyPlace(king_X, king_Y, "Free");

            /// move Rook to the right of king
            matrix[king_X][king_Y - 1] = matrix[king_X][0];
            matrix[king_X][king_Y - 1].sety(king_Y - 1);
            matrix[king_X][0] = new EmptyPlace(king_X, 0, "Free");

        }
    }

    /**
     * @param str is the second coordinate given
     * @return if it is a character
     */
    private static boolean isletter(String str) {
        return str.length() == 1;
    }

    /**
     * @param str is the first coordinate given
     * @return weather the string is or not a number
     */
    private static boolean isnumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * CLI simulation of playing chess , players will decide when to end the game , if constantly on check and no other moves left or just spontaneous...
     */
    static void run() {
        int turn = 0;

        Scanner scanner = new Scanner(System.in);
        Piece[][] matrix = new Piece[8][8]; /// Chessboard matrix

        ArrayList<Piece> White_captured = new ArrayList<>();
        ArrayList<Piece> Black_captured = new ArrayList<>(); /// arrays to display captured pieces

        matrixSet(matrix);
        matrixPrint(matrix, White_captured, Black_captured);

        int WK_x = 0;
        int WK_y = 4;
        /// coordinates for the 2 kings (to constantly verify Check)
        int BK_x = 7;
        int BK_y = 4;

        System.out.println("Coordinates must be entered with 1 space between them:\n2 h\n");

        while (true) {
            int first_x = -1, first_y = -1, last_x = -1, last_y = -1;

            if (Check(matrix, WK_x, WK_y, "white")) System.out.println("White King is in check! Move it");
            if (Check(matrix, BK_x, BK_y, "black")) System.out.println("Black King is in check! Move it");

            System.out.println((turn % 2 == 0 ? "White" : "Black") + " player makes a move!");
            System.out.println("Enter current x and y values. Enter 'exit' to terminate program:");

            /// Read and validate input for the starting position
            String input = scanner.nextLine();
            String[] startPos = input.split(" "); /// array formed by the 2 strings , separated by space

            if (startPos[0].equalsIgnoreCase("exit")) {
                System.out.println("Program exited!");
                break;
            }

            if (startPos.length != 2) { /// Check if input contains two parts
                System.out.println("Invalid input. Please provide both x and y coordinates.\n");
                continue;
            }

            if (!isnumber(startPos[0]) || !isletter(startPos[1])) { /// Check if input is valid
                System.out.println("Invalid input. x must be a number and y must be a letter.\n");
                continue;
            }

            first_x = Integer.parseInt(startPos[0]) - 1; /// Convert to 0-based index cordonate
            first_y = startPos[1].charAt(0) - 'a'; /// transform from ascii into number

            if (first_x < 0 || first_x > 7 || startPos[1].charAt(0) < 'a' || startPos[1].charAt(0) > 'h') { /// Check if x is within the valid range
                System.out.println("Invalid input. x must be between 1 and 8 and letter between a and h\n");
                continue;
            }

            /// Check if cell is an empty place
            if (matrix[first_x][first_y] instanceof EmptyPlace) {
                System.out.println("Cannot move an empty space!\n");
                continue;
            }

            /// Validate turn
            if (turn % 2 == 0 && matrix[first_x][first_y].getColour().equals("black")) {
                System.out.println("Not black player's turn.");
                continue;
            }
            if (turn % 2 == 1 && matrix[first_x][first_y].getColour().equals("white")) {
                System.out.println("Not white player's turn.");
                continue;
            }

            System.out.println("Enter destination x and y values. Enter 'exit' to terminate program:");

            input = scanner.nextLine();
            String[] endPos = input.split(" ");

            if (endPos[0].equalsIgnoreCase("exit")) {
                System.out.println("Program exited!");
                break;
            }

            if (endPos.length != 2) {
                System.out.println("Invalid input. Please provide both x and y coordinates.\n");
                continue;
            }

            if (!isnumber(endPos[0]) || !isletter(endPos[1])) {
                System.out.println("Invalid input. x must be a number and y must be a letter.\n");
                continue;
            }

            last_x = Integer.parseInt(endPos[0]) - 1;
            last_y = endPos[1].charAt(0) - 'a';

            if (last_x < 0 || last_y > 7 || startPos[1].charAt(0) < 'a' || startPos[1].charAt(0) > 'h') {
                System.out.println("Invalid input. x must be between 1 and 8 and letter between a and h\n");
                continue;
            }

            /////////// validate movement :

            if (matrix[first_x][first_y].isValidMove(matrix, first_x, first_y, last_x, last_y)) { /// obj instance move validated
                if (matrix[first_x][first_y].getName().equals("King") && Math.abs(last_y - first_y) > 1) { /// castling

                    String colour = matrix[first_x][first_y].getColour();
                    Castling(matrix, first_x, first_y, last_x, last_y);

                    if (last_y > first_y) { /// small castling
                        if (colour.equals("white")) WK_y = WK_y + 2;
                        else BK_y = BK_y + 2;
                    } else { /// big castling
                        if (colour.equals("white")) WK_y = WK_y - 2;
                        else BK_y = BK_y - 2;
                    }
                } else { /// simple move
                    if (matrix[first_x][first_y] instanceof King) { /// piece moves is king
                        String colour = matrix[first_x][first_y].getColour();
                        if (colour.equals("white")) {
                            WK_y = last_y;
                            WK_x = last_x;
                            swap(matrix, White_captured, Black_captured, WK_x, WK_y, first_x, first_y, last_x, last_y, colour);
                        } else {
                            BK_y = last_y;
                            BK_x = last_x;
                            swap(matrix, White_captured, Black_captured, BK_x, BK_y, first_x, first_y, last_x, last_y, colour);
                        }
                    } else { /// must check if move leaves king in check
                        if (matrix[first_x][first_y].getColour().equals("white")) {
                            if (!swap(matrix, White_captured, Black_captured, WK_x, WK_y, first_x, first_y, last_x, last_y, "white")) {
                                continue;
                            }
                        } else {
                            if (!swap(matrix, White_captured, Black_captured, BK_x, BK_y, first_x, first_y, last_x, last_y, "black")) {
                                continue;
                            }
                        }
                    }
                }
                if (matrix[last_x][last_y] instanceof Pawn && (last_x == 0 || last_x == 7)) { /// check for promotion of pawn
                    promote_pawn(matrix, last_x, last_y);
                }
                turn++; /// next colour player follows
            }
            matrixPrint(matrix, White_captured, Black_captured); /// after every round print the freshly updated board
        }
    }
}
