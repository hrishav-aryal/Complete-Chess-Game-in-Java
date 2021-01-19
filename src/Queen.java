import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(int x, int y, boolean is_white, GUIBoard chessBoard2) {
		super(x, y, is_white, chessBoard2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canMove(int x, int y) {
		
		Piece p = chboard.getPiece(x, y);
		
		// Remember: A Queen can move as many squares as she wants forward, 
        // backward, sideways, or diagonally, without jumping over any pieces.
		
		//diagonal move
		if(x!=this.getX() && y != this.getY()) {
			if(Math.abs(x-this.getX()) != Math.abs(y- this.getY())) {
//				System.out.print("1");
				return false;
			}
		}
		
		//something is on the way (straight line and diagonal)
		String dir = "";
		String dig = "";
		if(x - this.getX() > 0 && y == this.getY()) {
			dir = "south";
		}else if(this.getX() - x > 0 && y == this.getY()) {
			dir = "north";
		}else if(y - this.getY() > 0 && x == this.getX()) {
			dir = "east";
		}else if(this.getY() - y > 0 && x == this.getX()) {
			dir = "west";
		}
		
		if(y>this.getY() && x>this.getX()) {
			dig = "downeast";
		}else if(y<this.getY() && x>this.getX()) {
			dig = "downwest";
		}else if(y>this.getY() && x<this.getX()) {
			dig = "upeast";
		}else if(y<this.getY() && x<this.getX()) {
			dig = "upwest";
		}
		
		if(dir.equals("south")) {
			int spacesTomove = Math.abs(x - this.getX());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, y);
				if(pi!=null) {
//					System.out.print("2");
					return false;
				}
			}
		}
		
		if(dir.equals("north")) {
			int spacesTomove = Math.abs(x - this.getX());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(this.getX()-i, y);
				if(pi!=null) {
//					System.out.print("3");
					return false;
				}
			}
		}
		
		if(dir.equals("east")) {
			int spacesTomove = Math.abs(y - this.getY());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(x, this.getY()+i);
				if(pi!=null) {
//					System.out.print("4");
					return false;
				}
			}
		}
		
		if(dir.equals("west")) {
			int spacesTomove = Math.abs(y - this.getY());
			for(int i=1; i<spacesTomove; i++) {
				Piece pi = chboard.getPiece(x, this.getY()-1);
				if(pi!=null) {
//					System.out.print("5");
					return false;
				}
			}
		}
		
		if(dig.equals("downeast")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, this.getY()+i);
				if(pi!=null) {
//					System.out.print("6");
					return false;
				}
			}	
		}
		
		if(dig.equals("downwest")) {
			int spaces =  Math.abs(y - this.getY());
			for(int i=1; i<spaces; i++) {
				Piece pi = chboard.getPiece(this.getX()+i, this.getY()-i);
				if(pi!=null) {
//					System.out.print("7");
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
		
        
		
		
		
		// She cannot attack her own pieces.
		if(p != null && ((this.isIs_white() && p.isIs_white()) || (!this.isIs_white() && !p.isIs_white()))) {
			return false;
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
