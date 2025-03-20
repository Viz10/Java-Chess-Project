public class Rook extends Piece {

    private boolean moved;

    Rook(int x, int y, String colour, String name,boolean moved) {
        super(x, y, colour, name);
        this.moved=moved;
    }

    public void setMoved(boolean moved) { this.moved = moved; }
    public boolean getMoved() { return moved; }

    @Override
    public void print() {

        String colorCode = getColour().equals("white") ? "\u001B[37m" : "\u001B[30m";
        System.out.print(colorCode + " " + getName() + " " + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix, int startX, int startY, int endX, int endY) {

        /// out of bounds positions
        if (!Matrixinside(endX, endY) || !Matrixinside(startX, startY)) {
            System.out.println("Desired position is out of bounds,try again!");
            return false;
        }

        /// Check for not line movement
        if (startX != endX && startY != endY) {
            System.out.println("Rook can only move in straight lines (either row or column).");
            return false;
        }

        /// self move
        if (startX == endX && startY == endY) {
            System.out.println("Cannot move to the same position. Try again!");
            return false;
        }

        if (startX == endX) { /// same row , check if we can move through
            if (endY > startY) {
                for (int i = startY + 1; i < endY; ++i)
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (endY < startY) {
                for (int i = startY - 1; i > endY; --i)
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (!(matrix[endX][endY] instanceof EmptyPlace) && matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("same colour!");
                return false;
            }
            moved=true;
            return true;
        }
        else if (startY == endY) { /// same column, check if we can move through
            if (endX > startX) {
                for (int i = startX + 1; i < endX; ++i)
                    if (!(matrix[i][startY] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (endX < startX) {
                for (int i = startX - 1; i > endX; --i)
                    if (!(matrix[i][startY] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (!(matrix[endX][endY] instanceof EmptyPlace) && matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("same colour!");
                return false;
            }
        }
        moved=true;
        return true;
    }
}
