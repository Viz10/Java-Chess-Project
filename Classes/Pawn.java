public class Pawn extends Piece {

    private boolean moved_1; /// piece moved 1 cell after initial position
    private boolean moved_2; /// piece moved 2 cells after initial position

    Pawn(int x, int y, String colour, String name, boolean moved_1, boolean move_2) {
        super(x, y, colour, name);
        this.moved_1 = moved_1;
        this.moved_2 = move_2;
    }

    public boolean getMoved1() { return moved_1; }
    public boolean getMoved2() { return moved_2; }

    @Override
    public void print() {
        String colorCode = getColour().equals("white") ? "\u001B[37m" : "\u001B[30m";
        System.out.print(colorCode + " " + getName() + "  " + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix, int startX, int startY, int endX, int endY) {

        /// out of bounds positions
        if (!Matrixinside(endX, endY) || !Matrixinside(startX, startY)) {
            System.out.println("Desired position is out of bounds,try again!");
            return false;
        }

        /// self move
        if (startX == endX && startY == endY) {
            System.out.println("Cannot move to the same position. Try again!");
            return false;
        }

        final int[] di_white = new int[]{1, 2, 1, 1};
        final int[] dj_white = new int[]{0, 0, 1, -1};

        final int[] di_black = new int[]{-1, -2, -1, -1};
        final int[] dj_black = new int[]{0, 0, -1, 1};

        boolean validMoveFound = false;
        int[] di = getColour().equals("white") ? di_white : di_black;
        int[] dj = getColour().equals("white") ? dj_white : dj_black; /// select depending on which colour the pawn is , the movement

        for (int d = 0; d < 4 && !validMoveFound; ++d) {

            int new_x = di[d] + startX;
            int new_y = dj[d] + startY;

            if (endX == new_x && endY == new_y && Matrixinside(new_x, new_y)) /// found the desired poz through all possible ones for this piece
                validMoveFound = true;
        }


        ///  next move not found for pawn
        if (!validMoveFound) {
            System.out.println("Desired position is not specific to Pawn ,try again!");
            return false;
        }

        /// check for 2 steps ahead move
        if (getColour().equals("white")) {
            if ((moved_1 || moved_2) && endX == startX + 2) {
                System.out.println("pawn moves 2 cells only at its first move!");
                return false;
            } else if (!moved_1 && !moved_2 && endX == startX + 2) {
                if (!(matrix[startX + 1][startY] instanceof EmptyPlace) || !(matrix[startX + 2][startY] instanceof EmptyPlace)) {
                    System.out.println("Blocked move. Path is not clear.");
                    return false;
                }
                moved_2 = true;
                return true;
            }
        } else {
            if ((moved_1 || moved_2) && endX == startX - 2) {
                System.out.println("pawn moves 2 cells only at its first move!");
                return false;
            } else if (!moved_1 && !moved_2 && endX == startX - 2) {
                if (!(matrix[startX - 1][startY] instanceof EmptyPlace) || !(matrix[startX - 2][startY] instanceof EmptyPlace)) {
                    System.out.println("Blocked move. Path is not clear.");
                    return false;
                }
                moved_2 = true;
                return true;
            }
        }

        /// En Passant attack
        if (endX == startX + 1) { /// white
            if ((endY > startY || endY < startY) && matrix[endX][endY] instanceof EmptyPlace && matrix[endX - 1][endY] instanceof Pawn && ((Pawn) matrix[endX - 1][endY]).getMoved2()
                    && !((Pawn) matrix[endX - 1][endY]).getMoved1() && endX == 5) {
                matrix[endX - 1][endY] = new EmptyPlace(endX - 1, endY, "Free");
                return true;
            }
        }
        if (endX == startX - 1) { /// black
            if ((endY > startY || endY < startY) && matrix[endX][endY] instanceof EmptyPlace && matrix[endX + 1][endY] instanceof Pawn && ((Pawn) matrix[endX + 1][endY]).getMoved2()
                    && !((Pawn) matrix[endX + 1][endY]).getMoved1() && endX == 2) {
                matrix[endX + 1][endY] = new EmptyPlace(endX + 1, endY, "Free");
                return true;
            }
        }

        /// vertical cell blocked
        if ((endX == startX + 1 || endX == startX - 1) && endY == startY && !(matrix[endX][endY] instanceof EmptyPlace)) {
            System.out.println("next cell already blocked!");
            return false;
        }

        /// diagonal attack
        if ((endX == startX + 1 || endX == startX - 1) && endY != startY && (matrix[endX][endY] instanceof EmptyPlace || matrix[endX][endY].getColour().equals(getColour()))) {
            System.out.println("attacked position is not an opponent piece!");
            return false;
        }

        moved_1 = true;
        return true;
    }
}
