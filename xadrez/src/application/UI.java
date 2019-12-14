package application;

import chess.ChessPiece;

public class UI {
	
	// imprimir o tabuleiro no formato desejado
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");	// imprime as linhas
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);		// imprime a pe�a
			}
			System.out.println();	// quebra de linha para mudar de linha
		}
		System.out.println("  a b c d e f g h");	// imprime as colunas
	}

	// imprimir uma �nica pe�a. Se for null, imprime um - pq n�o tem pe�a, caso contr�rio, imprime a pe�a e entre elas um espa�o em branco
	private static void printPiece(ChessPiece piece) {
		if (piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" ");
	}
}
