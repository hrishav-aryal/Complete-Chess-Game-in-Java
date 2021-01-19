import java.util.ArrayList;

public class Rook extends Piece{

	public Rook(int x, int y, boolean is_white, GUIBoard chessBoard2) {
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
		
		//straight move only
		if(x != this.getX() && y != this.getY()) {
			return false;
		}
		
		//something is on the way (straight line)
		String dir = "";
		if(x - this.getX() > 0) {
			dir = "south";
		}else if(this.getX() - x > 0) {
			dir = "north";
		}else if(y - this.getY() > 0) {
			dir = "east";
		}else if(this.getY() - y > 0) {
			dir = "west";
		}
	
		if(dir.equals("south")) {
			int spacesTomove = Math.abs(x - this.getX());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, y);
				if(pi!=null) {
					return false;
				}
			}
		}
		
		if(dir.equals("north")) {
			int spacesTomove = Math.abs(x - this.getX());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(this.getX()-i, y);
				if(pi!=null) {
					return false;
				}
			}
		}
		
		if(dir.equals("east")) {
			int spacesTomove = Math.abs(y - this.getY());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(x, this.getY()+i);
				if(pi!=null) {
					return false;
				}
			}
		}
		
		if(dir.equals("west")) {
			int spacesTomove = Math.abs(y - this.getY());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(x, this.getY()-1);
				if(pi!=null) {
					return false;
				}
			}
		}
		
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
