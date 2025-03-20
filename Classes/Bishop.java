public class Bishop extends Piece {

    Bishop(int x, int y, String colour, String name) {
        super(x, y, colour, name);
    }

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

        /// self move
        if (startX == endX && startY == endY) {
            System.out.println("Cannot move to the same position. Try again!");
            return false;
        }

        /// Ensure the move is along a diagonal
        if (Math.abs(endX - startX) != Math.abs(endY - startY))
        { System.out.println("Bishop moves must be along a diagonal!");
            return false;
        }

        /// check diagonals
        int dx = (endX > startX) ? 1 : -1;
        int dy = (endY > startY) ? 1 : -1;

        /// check the path for obstacles
        int x = startX + dx;
        int y = startY + dy;
        while (x != endX && y != endY && Matrixinside(x, y)) {
            if (!(matrix[x][y] instanceof EmptyPlace)) {
                System.out.println("Blocked move. Path is not clear.");
                return false;
            }
            x += dx;
            y += dy;
        }

        /// Check the destination square
        if (x == endX && y == endY) {
            if (!(matrix[endX][endY] instanceof EmptyPlace) &&
                    matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("Cannot capture a piece of the same color.");
                return false;
            }
            return true;
        } else {
            System.out.println("Bishop moves must be along a diagonal!");
            return false;
        }

    }
}

