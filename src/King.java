import java.util.ArrayList;

public class King extends Piece{
	
	private boolean isCastlingDone;

	public King(int x, int y, boolean is_white, GUIBoard chessBoard2) {
		super(x, y, is_white, chessBoard2);
		
		isCastlingDone = false;
	}
	
	@Override
	public boolean canMove(int x, int y) {
		
		// Remember: a king can move one square up, right, left, or down, or
        // diagonally, but he can never put himself in danger of an oposing 
        // piece attacking him on the next turn.
        
		Piece p = chboard.getPiece(x, y);
		
		// He cannot attack her own pieces.
		if(p != null && ((this.isIs_white() && p.isIs_white()) || (!this.isIs_white() && !p.isIs_white()))) {
			return false;
		}
		
		
		//check if the moving square is attacked
		if(this.isIs_white()) {
//			System.out.print("1");
			for(Piece pi: chboard.blackPieces) {
//				System.out.print("2");
				if(pi.canMove(x, y)) {
//					System.out.print("3");
					return false;
				}
			}
		}else {
			for(Piece pi: chboard.whitePieces) {
				if(pi.canMove(x, y)) {
					return false;
				}
			}
		}
		
		
		// castling to consider hai
		int a = Math.abs(x-this.getX());
		int b = Math.abs(y-this.getY());
		
		if(a+b == 1) {
			return true;
		}else if(a+b > 2) {			
			return false;
		}else if(a+b == 2) {
			if(a==1&&b==1) return true;
			
			//no diagonal move .. check castle
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
