package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
		position = null;	// inicialmente a pe�a n�o foi colocada no tabuleiro, ent�o � null. Por padr�o o sistema j� coloca null, n�o precisa declarar
	}

	protected Board getBoard() {		// protect somente classes dentro do mesmo pacote e subclasses poder�o acessar esse m�todo
		return board;
	}
	
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
