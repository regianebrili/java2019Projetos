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

	public Piece piece(Position position) {			// sobrecarga do método, retornando a peça pela posição
		return pieces[position.getRow()][position.getColumn()];
	}
}
