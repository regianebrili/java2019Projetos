package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		while (true) {
			try {
				UI.clearScreen();		// limpa a tela
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				// imprime as posi��es poss�veis a partir da posi��o de origem
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);	// imprime o tabuleiro pintando as posi��es poss�veis
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				// sempre que fizer uma opera��o e tiver uma pe�a capturada, inclui na lista capturedPiece
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
			}
			catch (ChessException e) {	// imprime a mensagem de erro e aguarda o usu�rio clicar em "Enter"
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
