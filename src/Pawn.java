import java.util.ArrayList;

public class Pawn extends Piece{
	
	private boolean has_moved;
	private boolean just_moved_two_squares;
	private boolean killedAnotherenPass;
	private boolean killedByEnPass;

	public Pawn(int x, int y, boolean is_white, ChessBoard chessBoard) {
		super(x, y, is_white, chessBoard);
		this.has_moved = false;
		this.just_moved_two_squares = false;
		this.killedAnotherenPass = false;
		this.killedByEnPass = false;
	}
	
	public Pawn(int x, int y, boolean is_white, GUIBoard chessBoardGUI) {
		super(x, y, is_white, chessBoardGUI);
		this.has_moved = false;
		this.just_moved_two_squares = false;
		this.killedAnotherenPass = false;
		this.killedByEnPass = false;
	}

	public boolean isHas_moved() {
		return has_moved;
	}

	public void setHas_moved(boolean has_moved) {
		this.has_moved = has_moved;
	}	
	
	public boolean isJust_moved_two_squares() {
		return just_moved_two_squares;
	}

	public void setJust_moved_two_squares(boolean just_moved_two_squares) {
		this.just_moved_two_squares = just_moved_two_squares;
	}

	public boolean isKilledAnotherenPass() {
		return killedAnotherenPass;
	}

	public void setKilledAnotherenPass(boolean killedAnotherenPass) {
		this.killedAnotherenPass = killedAnotherenPass;
	}

	public boolean isKilledByEnPass() {
		return killedByEnPass;
	}

	public void setKilledByEnPass(boolean killedByEnPass) {
		this.killedByEnPass = killedByEnPass;
	}

	@Override
    public boolean canMove(int x, int y)
    {	
//		Piece p = chessBoard.getPiece(x,y);
		Piece p = chboard.getPiece(x,y);
		

//		System.out.println("Moving to: "+x+", "+y+"   FROm: "+ this.getX()+);
		
		
		
		if(p != null) {
			if(p.isIs_white() && this.isIs_white()) {
				return false;
			}if(!p.isIs_white() && !this.isIs_white()) {
				return false;
			}
		}
		
		// Remember: A pawn may only move towards the oponent's side of the board.
		if(this.isIs_white() && this.getX() > x) {
			return false;
		}
		
		if(!this.isIs_white() && this.getX() < x) {
			return false;
		}
		
		//something not in the way
		if(this.isIs_white()) {
			
			int spacesToMove = Math.abs(x-this.getX());
			for(int i=1; i<spacesToMove; i++) {
//				Piece pc = chessBoard.getPiece(this.getX()+i, this.getY());
				Piece pc = chboard.getPiece(this.getX()+i, this.getY());
				if(pc != null) {
					return false;
				}
			}
			
		}else {
			
			int spacesToMove = Math.abs(x-this.getX());
			for(int i=1; i<spacesToMove; i++) {
//				Piece pc = chessBoard.getPiece(this.getX()-i, this.getY());
				Piece pc = chboard.getPiece(this.getX()-i, this.getY());
				if(pc != null) {
					return false;
				}
			}
			
		}
		
        // If the pawn has not moved yet in the game, for its first move it can 
        // move two spaces forward. Otherwise, it may only move one space.
		if(this.getY() == y && this.getX() != x) {
			
			if(this.isHas_moved()) {
				//pawn has already been moved
				if(Math.abs(this.getX()-x) > 1) {
					return false;
				}
			}else {
				//pawn has not moved yet
				if(Math.abs(this.getX()-x) > 2) {
					return false;
				}
			}
			
			
			if(p != null) {
				return false;
			}
			
		}
		
		//cannot move on sides
		if(this.getX() == x && this.getY() != y) {
			return false;
		}
		
		
        // When not attacking it may only move straight ahead.
		// When attacking it may only move space diagonally forward
		if(this.getX() != x && this.getY() != y) {
			
			if((p != null) && ((this.isIs_white() && !p.isIs_white()) || (!this.isIs_white() && p.isIs_white()))) {
				if((Math.abs(this.getX()-p.getX()) == 1) && (Math.abs(this.getY()-p.getY()) == 1)) {
					return true;
				}
			}
			
			
//			en-passant rule
			if(p == null) {
				
				if((Math.abs(this.getX()-x) == 1) && (Math.abs(this.getY()-y) == 1)){
					if(y - this.getY() < 0) {
						Piece pp = chboard.getPiece(this.getX(), this.getY()-1);
						if(pp!= null && pp.getClass().equals(this.getClass())) {
							Pawn pa = (Pawn) pp;
							if(pp!=null && pa.just_moved_two_squares) {
								this.setKilledAnotherenPass(true);
								pa.setKilledByEnPass(true);
//								System.out.println("1");
								return true;
							}
						}
						
					}else {
						Piece pp = chboard.getPiece(this.getX(), this.getY()+1);
						if(pp!= null && pp.getClass().equals(this.getClass())) {
							
							Pawn pa = (Pawn) pp;
							if(pp!=null && pa.just_moved_two_squares) {
								this.setKilledAnotherenPass(true);
								pa.setKilledByEnPass(true);
//								System.out.println("2");
								return true;
							}
						}
						
					}
				}
				
			}
			
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
