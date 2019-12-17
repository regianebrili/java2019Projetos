package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) {		// retorna a matriz piece na linha e coluna
		return pieces[row][column];
	}

	public Piece piece(Position position) {			// sobrecarga do m�todo, retornando a pe�a pela posi��o
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;  // atribui nessa posi��o (linha, coluna) a pe�a informada
		piece.position = position;	// a posi��o da pe�a recebe o position informado no par�metro
	}
}
