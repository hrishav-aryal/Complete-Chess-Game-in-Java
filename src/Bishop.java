import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(int x, int y, boolean is_white, GUIBoard chessBoard2) {
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
		
		
		//diagonal move only
		if(x==this.getX() || y==this.getY()) {
			return false;
		}
		
		if(x!=this.getX() && y != this.getY()) {
			if(Math.abs(x-this.getX()) != Math.abs(y- this.getY())) {
				return false;
			}
		}
		
		
		//something is on the way (diagonal)
		String dig = "";
		
		if(y>this.getY() && x>this.getX()) {
			dig = "downeast";
		}else if(y<this.getY() && x>this.getX()) {
			dig = "downwest";
		}else if(y>this.getY() && x<this.getX()) {
			dig = "upeast";
		}else if(y<this.getY() && x<this.getX()) {
			dig = "upwest";
		}
		
		if(dig.equals("downeast")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, this.getY()+i);
				if(pi!=null) {
					return false;
				}
			}	
		}
		
		if(dig.equals("downwest")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, this.getY()-i);
				if(pi!=null) {
					return false;
				}
			}	
		}
		
		if(dig.equals("upwest")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()-i, this.getY()-i);
				if(pi!=null) {
					return false;
				}
			}	
		}
		
		if(dig.equals("upeast")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()-i, this.getY()+i);
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
