abstract public class  Piece { /// piece base class

    private String colour;
    private String name;
    private int x;
    private int y;

    Piece(){ /// base constructor
        this.colour="";
        this.name="";
        this.x=0;
        this.y=0;
    }

    Piece(int x,int y,String name){ /// Piece constructor for free cell
        this.x=x;
        this.y=y;
        this.name=name;
    }

    Piece(int x,int y,String colour,String name){ /// Piece constructor for non-free cell
        this(x, y,name);
        this.colour=colour;
    }

    /// getters functions
    public String getColour(){ return colour;}
    public String getName(){ return name;}
    public int getx(){return x;}
    public int gety(){return y;}

    /// mutator functions
    public void setColour(String colour){this.colour=colour;}
    public void setName(String name){this.name=name;}
    public void setx(int new_x){this.x=new_x;}
    public void sety(int new_y){this.y=new_y;}

    /// verifies if inside matrix
    public boolean Matrixinside(int someX,int someY){return (someX>=0 && someX<=7 && someY>=0 && someY<=7);}

    /**
     * this function is a virtual function that expects its derived classes to override it,to validate the move, it depends on subclass how to implement
     * the matrix , start and end positions are being passed for checking
     * @param startX the actual x int the moment the function was called
     * @param startY the actual y int the moment the function was called
     * @param endX desired x new location
     * @param endY desired y new location
     * @return if it is valid the swap will take place
     */
    public abstract boolean isValidMove(Piece[][] matrix, int startX, int startY, int endX, int endY);

    /**
     * prints name in the current square object , also abstract , it depends on each subclass how to manage
     */
    public abstract void print();

}
