package application;

import chess.ChessPiece;

public class UI {
	
	// imprimir o tabuleiro no formato desejado
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");	// imprime as linhas
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);		// imprime a peça
			}
			System.out.println();	// quebra de linha para mudar de linha
		}
		System.out.println("  a b c d e f g h");	// imprime as colunas
	}

	// imprimir uma única peça. Se for null, imprime um - pq não tem peça, caso contrário, imprime a peça e entre elas um espaço em branco
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
