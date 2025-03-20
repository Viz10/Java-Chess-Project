/**
 * same moving as a bishop combined with a rook
 */
public class Queen extends Piece{

    Queen(int x,int y,String colour,String name){
        super(x, y, colour,name);
    }

    @Override
    public void print() {
        // Set color code based on the color of the piece
        String colorCode = getColour().equals("white") ? "\u001B[37m" : "\u001B[30m";
        System.out.print(colorCode + " "+getName()+" " + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix,int startX, int startY, int endX, int endY){

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

        /// check line and column
        if (startX == endX) { /// same line
            if (endY > startY) {
                for (int i = startY + 1; i < endY && Matrixinside(startX,i); ++i)
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (endY < startY) {
                for (int i = startY - 1; i > endY && Matrixinside(startX,i); --i)
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (!(matrix[endX][endY] instanceof EmptyPlace) && matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("same colour!");
                return false;
            }
            return true;
        }
        else if (startY == endY) { /// same column
            if (endX > startX) {
                for (int i = startX + 1; i < endX && Matrixinside(i, startY); ++i)
                    if (!(matrix[i][startY] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (endX < startX) {
                for (int i = startX - 1; i > endX  && Matrixinside(i, startY) ; --i)
                    if (!(matrix[i][startY] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
            }
            if (!(matrix[endX][endY] instanceof EmptyPlace) && matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("same colour!");
                return false;
            }
            return true;
        }

        /// check diagonals
        int dx = (endX > startX) ? 1 : -1;
        int dy = (endY > startY) ? 1 : -1;

        /// Check the path for obstacles
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
            System.out.println("Invalid move for Queen!");
            return false;
        }

    }
}
