public class King extends Piece {

    private boolean moved;

    King(int x, int y, String colour, String name, boolean moved) {
        super(x, y, colour, name);
        this.moved = moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean getMoved() {
        return moved;
    }

    @Override
    public void print() {
        String colorCode = getColour().equals("white") ? "\u001B[37m" : "\u001B[30m";
        System.out.print(colorCode + " " + getName() + " " + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix, int startX, int startY, int endX, int endY) {
        /// Check out-of-bounds positions
        if (!Matrixinside(endX, endY) || !Matrixinside(startX, startY)) {
            System.out.println("Desired position is out of bounds, try again!");
            return false;
        }

        // Check for self move
        if (startX == endX && startY == endY) {
            System.out.println("Cannot move to the same position. Try again!");
            return false;
        }

        final int[] di = new int[]{1, -1, 0, 0, 1, -1, 1, -1};
        final int[] dj = new int[]{0, 0, 1, -1, 1, -1, -1, 1};

        boolean validMoveFound = false;
        for (int d = 0; d < 8 && !validMoveFound; ++d) {
            int new_x = di[d] + startX;
            int new_y = dj[d] + startY;

            if (endX == new_x && endY == new_y && Matrixinside(new_x, new_y))
                validMoveFound = true;
        }

        if (validMoveFound) {
            if (!(matrix[endX][endY] instanceof EmptyPlace) && matrix[endX][endY].getColour().equals(getColour())) {
                System.out.println("Cannot capture a piece of the same color.");
                return false;
            }
            if(!(matrix[endX][endY] instanceof EmptyPlace) && !matrix[endX][endY].getColour().equals(getColour()) && Board.Check(matrix,endX,endY,getColour())){
                /// tried to attack enemy which then makes it in check
                System.out.println("Position is in check!");
                return false;
            }
            if(matrix[endX][endY] instanceof EmptyPlace && Board.Check(matrix,endX,endY,getColour())){ /// empty space which is in check
                System.out.println("Position is in check!");
                return false;
            }
            moved=true;
            return true;
        }
        else if (!validMoveFound && !moved) { /// if position in not the square along the king , check for possible castling , remaining option
            if (startX == endX && startY < endY) { /// small castling
                for (int i = startY + 1; i < 7 && Matrixinside(startX,i); ++i) { /// check clear path
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
                    if(Board.Check(matrix,startX,i,matrix[startX][startY].getColour())){ /// check position through which the king passes not to be in check
                        System.out.println("King will be in Check");
                        return false;
                    }
                }
                if (matrix[startX][7] instanceof Rook && matrix[startX][7].getColour().equals(getColour()) && !((Rook) matrix[startX][7]).getMoved() && endY == 7) { /// check if there is a valid rook
                     // target position for kingside castling
                        return true;
                }
            }/// big castling
            else if (startX == endX && startY > endY) {
                for (int i = startY - 1; i > 0 && Matrixinside(startX,i); --i) { /// check clear path
                    if (!(matrix[startX][i] instanceof EmptyPlace)) {
                        System.out.println("Blocked move. Path is not clear.");
                        return false;
                    }
                    if(Board.Check(matrix,startX,i,matrix[startX][startY].getColour()) && i!=1){ /// check position through which the king passes not to be in check
                        System.out.println("King will be in Check");
                        return false;
                    }
                }
                if (matrix[startX][0] instanceof Rook && matrix[startX][0].getColour().equals(getColour()) && !((Rook) matrix[startX][0]).getMoved() && endY == 0) { /// check if there is a valid rook
                        return true;
                }
            }
        }
        System.out.println("Invalid move to King");
        return false;
    }

}
