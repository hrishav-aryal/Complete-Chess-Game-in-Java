import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUIBoard extends JFrame{
	
	ArrayList<Piece> whitePieces;
	ArrayList<Piece> blackPieces;
	
	JButton[][] btns = new JButton[8][8];
	int turnCounter = 0;
	
	Piece activePiece;
	Pawn enPassantPawn;
	
	
	public void start() {
		
		btns = initializeBoard();
		
		//playing the game
		for(int row=0; row<8; row++) {
			for(int col=0; col<8; col++) {
				JButton btn = btns[row][col];
				final int r = row;
				final int c = col;
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mouseClicked(r, c);
						
					}
		        });
			}
		}		
	}
	
	
	public void mouseClicked(int row, int col) {
		
		boolean isWhiteTurn = true;
		if(turnCounter%2 == 1) {
			isWhiteTurn = false;
		}
		
		
		int tempRow = 0, tempCol = 0;
		boolean killedByEnPass = false;
		
		Piece clickedPiece = this.getPiece(row, col);		
		
		//setting up active piece
		if(activePiece == null && clickedPiece != null &&
				((isWhiteTurn && clickedPiece.isIs_white()) || (!isWhiteTurn && !clickedPiece.isIs_white()))){
			activePiece = clickedPiece;
			setBtnActive(btns[activePiece.getX()][activePiece.getY()]);	
			
//			Queen p = (Queen) activePiece;
//			for(Pair pp: p.getLegalMoves())
//				System.out.println(pp.getX()+", "+pp.getY());
		}
		//active piece clicked again
		else if(activePiece != null && activePiece.getX() == row && activePiece.getY() == col) {
			setBtnInactive(btns[activePiece.getX()][activePiece.getY()], activePiece);
			activePiece = null;
		}
		
		else if(activePiece != null && activePiece.canMove(row, col) && 
				((isWhiteTurn && activePiece.isIs_white()) || (!isWhiteTurn && !activePiece.isIs_white()))) {
			
//			removing enpassant possibility
			if(enPassantPawn != null) {
				enPassantPawn.setJust_moved_two_squares(false);
				enPassantPawn = null;
				
				if(activePiece.getClass().equals(Pawn.class)) {
					Pawn p = (Pawn) activePiece;
					if(p.isKilledAnotherenPass()) {
						killedByEnPass = true;
						p.setKilledAnotherenPass(false);
					}
				}
			}
			
			
			//remove piece if killed			
			if(clickedPiece != null) {
				if(clickedPiece.isIs_white()) {
					this.whitePieces.remove(clickedPiece);
				}else {
					this.blackPieces.remove(clickedPiece);
				}
			}
			
			tempRow = activePiece.getX();
			tempCol = activePiece.getY();
			setBtnInactive(btns[tempRow][tempCol], activePiece);
			
			//if pawn, set hasMoved to true
			if(activePiece.getClass().equals(Pawn.class)) {
				Pawn pawn = (Pawn) activePiece;
				pawn.setHas_moved(true);
				pawn.setJust_moved_two_squares(false);
				
				if(Math.abs(row-tempRow)==2) {
					pawn.setJust_moved_two_squares(true);
					enPassantPawn = pawn;
				}
			}
			
			int removeenPassRow = 0;
			if(killedByEnPass) {
				Pawn pawn = (Pawn) this.getPiece(row+1, col);
				Pawn pawn2 = (Pawn) this.getPiece(row-1, col);
				
				if(pawn != null) {
					
					if(pawn.isKilledByEnPass()) {
						killedByEnPass = true;
						pawn.setKilledByEnPass(false);
						this.whitePieces.remove(pawn);
						removeenPassRow = row+1;
					}else {
						killedByEnPass = false;
					}
					
				}else if(pawn2 != null) {
					if(pawn2.isKilledByEnPass()) {
						killedByEnPass = true;
						removeenPassRow = row-1;
						this.blackPieces.remove(pawn);
						pawn2.setKilledByEnPass(false);
					}else {
						killedByEnPass = false;
					}
				}
				
			}
			
			//set new place for active piece
			Piece tempPiece = activePiece;
			activePiece.setX(row);
			activePiece.setY(col);
			
			activePiece = null;
			turnCounter++;
			
			drawNewBoard(tempRow, tempCol, row, col, isWhiteTurn, removeenPassRow, killedByEnPass, tempPiece);
			killedByEnPass = false;				
		}
	}
	
	
	public int checkWin(boolean white) {
		if(whitePieces.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Black is the winner");
//			this.removeAll();
//			initializeBoard();
			return 0;
		}else if(blackPieces.isEmpty()) {
			JOptionPane.showMessageDialog(null, "White is the winner");
//			this.removeAll();
//			initializeBoard();
			return 0;
		}
		
		boolean checkMate = false;
		boolean over = false;
		if(white) {
			
			
			
		}else {
			
			int[] in = getIndex(false);
			
//			System.out.println(in[0]+", "+in[1]);
			
			for(Piece p: blackPieces) {
				
//				System.out.println(p.getClass());
				
				if(!p.getClass().equals(King.class)) {
					
					int tempR = p.getX();
					int tempC = p.getY();
							
					for(Pair pair: p.getLegalMoves()) {
						
						p.setX(pair.getX());
						p.setY(pair.getY());
						
//						System.out.print(pair.getX()+", "+pair.getY()+" | ");
						
						for(Piece pi: whitePieces) {
							if(pi.canMove(in[0], in[1])) {
								checkMate = true;
								break;
							}
						}
						p.setX(tempR);
						p.setY(tempC);
						if(checkMate) {
//							System.out.println("\nhere");
							checkMate = false;
							over = true;
						}else {
							//found the move
//							System.out.println("\nhere1");
							return 0;
						}
						
					}
					
				}
				
			}
			
//			System.out.println("\nhahahhaha "+ over);
			if(over) {
				JOptionPane.showMessageDialog(null, "CheckMate!!");
			}
			
		}		
		
		boolean drawWhite= true;
		boolean drawBlack = true;
		
		for(Piece p: whitePieces) {
			if(checkLegalMoves(p)) {
				drawWhite = false;
			}
		}
		
		for(Piece p: blackPieces) {
			if(checkLegalMoves(p)) {
				drawBlack = false;
			}
		}
		
		
		if((drawWhite && !drawBlack) || (!drawWhite && drawBlack)) {
			JOptionPane.showMessageDialog(null, "Stalemate!!");
//			this.removeAll();
//			initializeBoard();
			return 0;
		}else if(drawWhite && drawBlack) {
			JOptionPane.showMessageDialog(null, "Game draw!!");
//			this.removeAll();
//			initializeBoard();
			return 0;
		}
		
		return 0;
		
	}
	
	
	public boolean checkLegalMoves(Piece p) {
		
		if(p.getClass().equals(Pawn.class)) {
			
			if(p.isIs_white()) {
				int[] rows = new int[] {p.getX()+1, p.getX()+1, p.getX()+1, p.getX()+2};
				int[] cols = new int[] {p.getY(), p.getY()-1, p.getY()+1, p.getY()};
				
				for(int r=0; r<rows.length; r++) {
					if((rows[r]>=0 && rows[r]<=7) && (cols[r]>=0 && cols[r]<=7)) {
						
						if(p.canMove(rows[r], cols[r])) {
							return true;
						}
					}
				}
				return false;
				
			}else {
				
				int[] rows = new int[] {p.getX()-1, p.getX()-1, p.getX()-1, p.getX()-2};
				int[] cols = new int[] {p.getY(), p.getY()-1, p.getY()+1, p.getY()};
				
				for(int r=0; r<rows.length; r++) {
					if((rows[r]>=0 && rows[r]<=7) && (cols[r]>=0 && cols[r]<=7)) {
						if(p.canMove(rows[r], cols[r])) {
							return true;
						}
					}
				}
				return false;
			}
			
		}
		
		return false;
		
	}
	
	
	public void drawNewBoard(int tr, int tc, int r, int c, 
			boolean isWhiteTurn, int enPassRowRemove, boolean enPass, Piece activeP) {
		
		
		
		if(isWhiteTurn) {
//			btns[r][c].setIcon(setWhiteIcon());			
//			btns[tr][tc].setIcon(null);
			
			if(activeP.getClass().equals(Queen.class)) {
				btns[r][c].setIcon(setBtnIcon(true, "queen"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Rook.class)){
				btns[r][c].setIcon(setBtnIcon(true, "rook"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Knight.class)){
				btns[r][c].setIcon(setBtnIcon(true, "knight"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Bishop.class)){
				btns[r][c].setIcon(setBtnIcon(true, "bishop"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(King.class)){
				btns[r][c].setIcon(setBtnIcon(true, "king"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Pawn.class)){
				btns[r][c].setIcon(setBtnIcon(true, "pawn"));			
				btns[tr][tc].setIcon(null);
			}
			
		}else {
//			btns[r][c].setIcon(setBlackIcon());			
//			btns[tr][tc].setIcon(null);
			
			if(activeP.getClass().equals(Queen.class)) {
				btns[r][c].setIcon(setBtnIcon(false, "queen"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Rook.class)){
				btns[r][c].setIcon(setBtnIcon(false, "rook"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Knight.class)){
				btns[r][c].setIcon(setBtnIcon(false, "knight"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Bishop.class)){
				btns[r][c].setIcon(setBtnIcon(false, "bishop"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(King.class)){
				btns[r][c].setIcon(setBtnIcon(false, "king"));			
				btns[tr][tc].setIcon(null);
			}else if(activeP.getClass().equals(Pawn.class)){
				btns[r][c].setIcon(setBtnIcon(false, "pawn"));			
				btns[tr][tc].setIcon(null);
			}
			
		}
		
		if(enPass) {
			btns[enPassRowRemove][c].setIcon(null);
		}
		
//		if(isKingInCheck(isWhiteTurn)) {
//			JOptionPane.showMessageDialog(null, "King in check!!");
//		}
		checkWin(!isWhiteTurn);
	}
	
	
	public boolean isKingInCheck(boolean isWhiteTurn) {
		int[] index = new int[2];
		if(isWhiteTurn) {
			index = getIndex(false);
//			System.out.print("White: "+index[0]+", "+index[1]);
			for(Piece p : whitePieces) {
				if(p.canMove(index[0], index[1])) {
					return true;
				}
			}
		}else {
			index = getIndex(true);
//			System.out.print("Black: "+index[0]+", "+index[1]);
			for(Piece p : blackPieces) {
				if(p.canMove(index[0], index[1])) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public JButton[][] initializeBoard() {
		
		JButton[][] gameBoard = new JButton[8][8];
		
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		
		Color blackColor = Color.DARK_GRAY;
		Color whiteColor = Color.WHITE;
		
		for(int row=0; row<8; row++) {
			for(int col=0; col<8; col++) {
				
				if(col%2==0) {
					gameBoard[row][col] = new JButton();
					JButton chessbtn = gameBoard[row][col];
					chessbtn.setBackground(whiteColor);
					add(gameBoard[row][col]);
				}else {
					gameBoard[row][col] = new JButton();
					JButton chessbtn = gameBoard[row][col];
					chessbtn.setBackground(blackColor);
					add(gameBoard[row][col]);
				}
				
			}
			if(row%2==0) {
				blackColor = Color.WHITE;
				whiteColor = Color.DARK_GRAY;
			}else {
				blackColor = Color.DARK_GRAY;
				whiteColor = Color.WHITE;
			}
			
		}
		
		for(int col=0; col<8; col++) {
				this.whitePieces.add(new Pawn(1,col,true, GUIBoard.this));
				gameBoard[1][col].setIcon(setBtnIcon(true, "pawn"));
		}
		
		for(int col=0; col<8; col++) {
			this.blackPieces.add(new Pawn(6,col,false, GUIBoard.this));
			gameBoard[6][col].setIcon(setBtnIcon(false, "pawn"));
		}		
		
		this.whitePieces.add(new Rook(0,0,true, GUIBoard.this));
		gameBoard[0][0].setIcon(setBtnIcon(true, "rook"));
		this.whitePieces.add(new Rook(0,7,true, GUIBoard.this));
		gameBoard[0][7].setIcon(setBtnIcon(true, "rook"));
		this.whitePieces.add(new Knight(0,1,true, GUIBoard.this));
		gameBoard[0][1].setIcon(setBtnIcon(true, "knight"));
		this.whitePieces.add(new Knight(0,6,true, GUIBoard.this));
		gameBoard[0][6].setIcon(setBtnIcon(true, "knight"));
		this.whitePieces.add(new Bishop(0,2,true, GUIBoard.this));
		gameBoard[0][2].setIcon(setBtnIcon(true, "bishop"));
		this.whitePieces.add(new Bishop(0,5,true, GUIBoard.this));
		gameBoard[0][5].setIcon(setBtnIcon(true, "bishop"));
		this.whitePieces.add(new King(0,3,true, GUIBoard.this));
		gameBoard[0][3].setIcon(setBtnIcon(true, "king"));
		this.whitePieces.add(new Queen(0,4,true, GUIBoard.this));
		gameBoard[0][4].setIcon(setBtnIcon(true, "queen"));
		
		
		this.blackPieces.add(new Rook(7,0,false, GUIBoard.this));
		gameBoard[7][0].setIcon(setBtnIcon(false, "rook"));
		this.blackPieces.add(new Rook(7,7,false, GUIBoard.this));
		gameBoard[7][7].setIcon(setBtnIcon(false, "rook"));
		this.blackPieces.add(new Knight(7,1,false, GUIBoard.this));
		gameBoard[7][1].setIcon(setBtnIcon(false, "knight"));
		this.blackPieces.add(new Knight(7,6,false, GUIBoard.this));
		gameBoard[7][6].setIcon(setBtnIcon(false, "knight"));
		this.blackPieces.add(new Bishop(7,2,false, GUIBoard.this));
		gameBoard[7][2].setIcon(setBtnIcon(false, "bishop"));
		this.blackPieces.add(new Bishop(7,5,false, GUIBoard.this));
		gameBoard[7][5].setIcon(setBtnIcon(false, "bishop"));
		this.blackPieces.add(new King(7,3,false, GUIBoard.this));
		gameBoard[7][3].setIcon(setBtnIcon(false, "king"));
		this.blackPieces.add(new Queen(7,4,false, GUIBoard.this));
		gameBoard[7][4].setIcon(setBtnIcon(false, "queen"));
		
		
		this.setTitle("Chess Board");
		this.setLayout(new GridLayout(8,8));
		this.setSize(700,700);
		this.setVisible(true);
		
		return gameBoard;
	}
	
	
	public Piece getPiece(int x, int y) {
		for(Piece p: whitePieces) {
			if(p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		
		for(Piece p: blackPieces) {
			if(p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		
		return null;
	}
	
	public int[] getIndex(boolean white) {
		int[] x = new int[2];
		
		if(white) {
			for(Piece p: whitePieces) {
				if(p.getClass().equals(King.class)) {
					x[0] = p.getX();
					x[1] = p.getY();
				}
			}
		}else {
			for(Piece p: blackPieces) {
				if(p.getClass().equals(King.class)) {
					x[0] = p.getX();
					x[1] = p.getY();
				}
			}
		}
		
		return x;
	}


	public void setBtnActive(JButton btn) {
		btn.setBackground(Color.yellow);;
	}
	
	
	public void setBtnInactive(JButton btn, Piece p) {
		if((p.getX()%2==0 && p.getY()%2==0) || (p.getX()%2!=0 && p.getY()%2!=0)) {
			btn.setBackground(Color.white);
		}else {
			btn.setBackground(Color.DARK_GRAY);
		}
	}
	
	
	public static void print(String x) {
		System.out.println(x);
	}

	
	public Icon setPawnIcon(boolean isWhite) {
		ImageIcon img;
		if(isWhite) img = new ImageIcon(getClass().getResource("/Images/pawn.jpg"));
		else img = new ImageIcon(getClass().getResource("/Images/bpawn.jpg"));
		Image im = img.getImage();
		Image newimg = im.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH );
		Icon icon = new ImageIcon(newimg);
		
		return icon;
	}
	
	
	public Icon setBtnIcon(boolean isWhite, String piece) {
		ImageIcon img = null;
		if(isWhite) {
			switch(piece) {
			
			case "pawn":
				img = new ImageIcon(getClass().getResource("/Images/wpawn.png"));
				break;
			
			case "rook":
				img = new ImageIcon(getClass().getResource("/Images/wrook.jpg"));
				break;
							
			case "knight":
				img = new ImageIcon(getClass().getResource("/Images/wknight.jpeg"));
				break;
				
			case "bishop":
				img = new ImageIcon(getClass().getResource("/Images/wbishop.jpg"));
				break;
				
			case "queen":
				img = new ImageIcon(getClass().getResource("/Images/wqueen.jpg"));
				break;
				
			case "king":
				img = new ImageIcon(getClass().getResource("/Images/wking.png"));
				break;
			
			}
				
		}else {
			switch(piece) {
			
			case "pawn":
				img = new ImageIcon(getClass().getResource("/Images/bpawn.png"));
				break;
			
			case "rook":
				img = new ImageIcon(getClass().getResource("/Images/brook.png"));
				break;
							
			case "knight":
				img = new ImageIcon(getClass().getResource("/Images/bknight.jpg"));
				break;
				
			case "bishop":
				img = new ImageIcon(getClass().getResource("/Images/bbishop.jpg"));
				break;
				
			case "queen":
				img = new ImageIcon(getClass().getResource("/Images/bqueen.png"));
				break;
				
			case "king":
				img = new ImageIcon(getClass().getResource("/Images/bking.jpg"));
				break;
			
			}
		}
		Image im = img.getImage();
		Image newimg = im.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH );
		Icon icon = new ImageIcon(newimg);
		
		return icon;
	}
	
}
