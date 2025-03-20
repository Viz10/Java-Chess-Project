public class Knight extends Piece{ /// by extending piece it`s like all the other attributes and functions were already here , and we can access the public/protected ones

    Knight(int x,int y,String colour,String name){
        super(x, y, colour,name); /// set the Piece part variables of Knight in place
    }

    @Override
    public void print() {
        /// set color code based on the color of the piece
        String colorCode = getColour().equals("white") ? "\u001B[37m" : "\u001B[30m"; /// white or black text color
        System.out.print(colorCode + " "+getName() + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix,int startX, int startY, int endX, int endY){

        if(!Matrixinside(endX, endY) || !Matrixinside(startX, startY)) {
            System.out.println("Desired position is out of bounds,try again!");
            return false;
        }

        if (startX == endX && startY == endY) {
            System.out.println("Cannot move to the same position. Try again!");
            return false;
        }

        final int[] di= new int[]{-2 , -2 , -1 , 1 , 2 ,  2 ,  1 , -1};
        final int[] dj= new int[]{-1 ,  1 ,  2 , 2 , 1 , -1 , -2 , -2};

        boolean validMoveFound=false;
        for(int d=0;d<8 && !validMoveFound;++d)
        {
            int new_x = di[d] + startX;
            int new_y = dj[d] + startY;

            if(endX==new_x && endY==new_y && Matrixinside(new_x,new_y)) /// found the desired poz through all possible ones for this piece
                validMoveFound=true;
        }

        if(!validMoveFound){
            System.out.println("Desired position is not specific to Knight ,try again!");
            return false;
        }

        if(matrix[endX][endY] instanceof EmptyPlace){
            return true;  ///if destination is empty
        }

        /// it`s not empty => check weather occupied by opponent's piece
        if(matrix[endX][endY].getColour().equals(getColour())){
            System.out.println("Desired position is the same colour as the Knight ,try again!");
            return false;
        }
            return  true;
    }
}

