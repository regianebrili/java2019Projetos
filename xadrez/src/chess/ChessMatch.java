package chess;

import boardgame.Board;

public class ChessMatch {
	
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);	// o board vai receber um novo board nas posi��es 8 x 8
	}

	// retorna uma matriz de pe�as de xadrez corespondente a partida
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];	// cria a matriz com o nro de linhas e colunas do tabuleiro
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
				// percorre a matriz e para cada posi��o do tabuleiro recebe uma pe�a de xadrez
			}
		}
		return mat;
	}
}
