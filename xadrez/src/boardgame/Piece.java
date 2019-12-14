package boardgame;

public class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
		position = null;	// inicialmente a peça não foi colocada no tabuleiro, então é null. Por padrão o sistema já coloca null, não precisa declarar
	}

	protected Board getBoard() {		// protect somente classes dentro do mesmo pacote e subclasses poderão acessar esse método
		return board;
	}
}
