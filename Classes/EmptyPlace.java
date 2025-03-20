public class EmptyPlace extends Piece{

    EmptyPlace(int x,int y,String name){
        super(x, y,name);
    }

    @Override
    public void print() {
        // ANSI code for gray
        String grayColorCode = "\u001B[90m";
        System.out.print(grayColorCode + " ____  " + "\u001B[0m");
    }

    @Override
    public boolean isValidMove(Piece[][] matrix,int startX, int startY, int endX, int endY){
        System.out.println("Cannot move an empty position!");
        return false;
    }
}
