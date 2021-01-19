import java.util.ArrayList;

public class Knight extends Piece{

	public Knight(int x, int y, boolean is_white, GUIBoard chessBoard2) {
		super(x, y, is_white, chessBoard2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canMove(int x, int y) {
		
		Piece p = chboard.getPiece(x, y);
		
		// He cannot attack her own pieces.
		if(p != null && ((this.isIs_white() && p.isIs_white()) || (!this.isIs_white() && !p.isIs_white()))) {
			return false;
		}
		
		int a = Math.abs(x-this.getX());
		int b = Math.abs(y-this.getY());
		
		if(a*b != 2) return false;
		
		
		return true;
	}
	
	
	@Override
	public ArrayList<Pair> getLegalMoves(){
		ArrayList<Pair> moves = new ArrayList();
		
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				if(this.canMove(x, y)) {
					moves.add(new Pair(x,y));
				}
			}
		}
		
		return moves;
	}

}
