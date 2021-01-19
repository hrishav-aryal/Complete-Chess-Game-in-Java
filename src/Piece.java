import java.util.ArrayList;

public class Piece {
	
	private int x, y;
	private boolean is_white;
	public ChessBoard chessBoard;
	public GUIBoard chboard;
	
	public Piece(int x, int y, boolean is_white, ChessBoard chessBoard2) {
		super();
		this.x = x;
		this.y = y;
		this.is_white = is_white;
		this.chessBoard = chessBoard2;
	}
	
	public Piece(int x, int y, boolean is_white, GUIBoard chessBoard2) {
		super();
		this.x = x;
		this.y = y;
		this.is_white = is_white;
		this.chboard = chessBoard2;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isIs_white() {
		return is_white;
	}

	public void setIs_white(boolean is_white) {
		this.is_white = is_white;
	}
	
	public boolean canMove(int x, int y)
    {
        return false;
    }
	
	public ArrayList<Pair> getLegalMoves(){
		return null;
	}

}
