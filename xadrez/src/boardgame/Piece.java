package boardgame;

public class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
		position = null;	// inicialmente a pe�a n�o foi colocada no tabuleiro, ent�o � null. Por padr�o o sistema j� coloca null, n�o precisa declarar
	}

	protected Board getBoard() {		// protect somente classes dentro do mesmo pacote e subclasses poder�o acessar esse m�todo
		return board;
	}
}
