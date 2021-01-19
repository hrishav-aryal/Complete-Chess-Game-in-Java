import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard {

	public ArrayList<Piece> whitePawns;
	public ArrayList<Piece> blackPawns;

	
	public void start() {
		String[][] gboard = initializeBoard();
		
		printGameBoard(gboard);
		
		//playing the game
		
		int turnCounter = 0;
		
		while(true) {
			
			boolean isWhiteTurn = true;
			if(turnCounter%2 == 1) {
				isWhiteTurn = false;
			}
			
			Scanner sc = new Scanner(System.in);
			if(isWhiteTurn) print("White's Turn\n");
			else print("Black's Turn\n");
			print("\nEnter move((FROM)Row,Col to (TO)Row,Col): ");
			String move = sc.next();
			
			String[] m = move.split(",");
			int fromRow = Integer.parseInt(m[0]);
			int fromCol = Integer.parseInt(m[1]);
			int toRow = Integer.parseInt(m[2]);
			int toCol = Integer.parseInt(m[3]);
			
			Piece p = this.getPiece(fromRow, fromCol);
			Piece targetPiece = this.getPiece(toRow, toCol);
			
			if(p.canMove(toRow, toCol) && ((isWhiteTurn && p.isIs_white()) || (!isWhiteTurn && !p.isIs_white()))) {
				
				//remove piece if killed
				if(targetPiece != null) {
					if(targetPiece.isIs_white()) {
						this.whitePawns.remove(targetPiece);
					}else {
						this.blackPawns.remove(targetPiece);
					}
				}
				
				//set new place for active piece
				p.setX(toRow);
				p.setY(toCol);
				
				//if pawn, set hasMoved to true
				if(p.getClass().equals(Pawn.class)) {
					Pawn pawn = (Pawn) p;
					pawn.setHas_moved(true);
				}
				
				turnCounter++;
			}
			drawNewBoard();
		}
	}
	
	
	public void drawNewBoard() {
		
		String[][] gameBoard = new String[8][8];
		
		for(int row=0; row<8; row++) {
			for(int col=0; col<8; col++) {
				gameBoard[row][col] = "--";
			}
		}
		
		for(Piece p: this.whitePawns) {
			gameBoard[p.getX()][p.getY()] = "wP";
		}
		
		for(Piece p: this.blackPawns) {
			gameBoard[p.getX()][p.getY()] = "bP";
		}
		
		printGameBoard(gameBoard);
		
	}
	
	
	public String[][] initializeBoard() {
		
		String[][] gameBoard = new String[8][8];
		this.whitePawns = new ArrayList();
		this.blackPawns = new ArrayList();
		
		for(int row=0; row<8; row++) {
			for(int col=0; col<8; col++) {
				gameBoard[row][col] = "--";
			}
		}
		
		for(int row=0; row<2; row++) {
			for(int col=0; col<8; col++) {
				this.whitePawns.add(new Pawn(row,col,true, this));
				gameBoard[row][col] = "wP";
			}
		}
		
		for(int row=6; row<8; row++) {
			for(int col=0; col<8; col++) {
				this.blackPawns.add(new Pawn(row,col,false,this));
				gameBoard[row][col] = "bP";
			}
		}
		
		return gameBoard;
	}
	
	
	public Piece getPiece(int x, int y) {
		for(Piece p: whitePawns) {
			if(p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		
		for(Piece p: blackPawns) {
			if(p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		
		return null;
	}
	
	
	public static void printGameBoard(String[][] board) {
		System.out.println("  0   1   2   3   4   5   6   7");
		for(int row=0; row<8; row++) {
			System.out.print(row);
			for(int col=0; col<8; col++) {
				print(" ");
				System.out.print(board[row][col]);
				print(" ");
			}
//			System.out.println();
			System.out.println();
		}
	}
	
	
	public static void print(String x) {
		System.out.print(x);
	}
	
}


