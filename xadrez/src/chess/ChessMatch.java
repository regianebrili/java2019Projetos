package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);	// o board vai receber um novo board nas posi��es 8 x 8
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();		// converte a posi��o do xadrez para uma matriz
		validateSourcePosition(position);						// valida a posi��o de origem
		return board.piece(position).possibleMoves();			// retorna as posi��es poss�veis
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can�t put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		// Movimento especial Promo��o (#specialmove promotion)
		// tem que ser testado antes do check, pois a nova pe�a pode causar o check
		promoted = null;
		// se a pe�a movida foi um pe�o
		if (movedPiece instanceof Pawn) {
			// se a pe�a movida for branca e o local de destino � 0 OU se a pe�a for preta e o local de destino � 7, ent�o ela chegou no final
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				// pe�a promovida foi o pe�o que chegou no final
				promoted = (ChessPiece)board.piece(target);
				// troca a pe�a promovida por uma pe�a maior, por padr�o ser� a rainha (Q)
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		// Movimento especial En Passan (#specialmove en passant)
		// testa se a pe�a que foi movida � um pe�o e andou 2 casas
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;	// ent�o esse pe�o fica vulner�vel a um En Passant
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	// m�todo para trocar a pe�a promovida no movimento Promo��o
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		// se existe uma pe�a a ser promovida, verifica se a letra escolhida � um bispo, cavalo, torre ou rainha
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			throw new InvalidParameterException("Invalid type for promotion");
		}

		// remove a pe�a da posi��o
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		// instancia uma nova pe�a conforme a escolhida
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		// coloca a nova pe�a na posi��o da pe�a promovida
		board.placePiece(newPiece, pos);
		// adiciona a nova pe�a na lista do tabuleiro
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	// m�todo para instanciar uma nova pe�a conforme a letra escolhida
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// movimento especial Roque pequeno (#specialmove castling kingside rook)
		// se a pe�a p for uma instancia de rei e a posi��o de destino for igual a posi��o de origem +2
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);	// posi��o de origem da torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);	// posi��o de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);	// retira a torre para a posi��o de origem
			board.placePiece(rook, targetT);	// coloca a torre na posi��o de destino
			rook.increaseMoveCount();	// acrescenta uma quantidade de movimentos da torre
		}

		// movimento especial Roque grande (#specialmove castling queenside rook)
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// Movimento especial En Passant (#specialmove en passant)
		// se a pe�a movida � uma instancia de pe�o
		if (p instanceof Pawn) {
			// se a posi��o de origem for diferente da posi��o de destino e n�o capturou pe�a
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				// se a cor da pe�a movida for branca, ent�o captura a pe�a abaixo
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				// se a cor da pe�a movida for preta, ent�o captura a pe�a acima
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				// remove a pe�a do tabuleiro
				capturedPiece = board.removePiece(pawnPosition);
				// adiciona a pe�a capturada � lista de pe�as capturadas
				capturedPieces.add(capturedPiece);
				// remove a pe�a capturada � lista de pe�as do tabuleiro
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	// desfazer o movimento (caso o movimento deixe em check, � proibido e deve ser desfeito)
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// desfazer o movimento especial Roque pequeno (#specialmove castling kingside rook)
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// desfazer o movimento especial Roque grande (#specialmove castling queenside rook)
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// Desfaz o movimento especial En Passant (#specialmove en passant)
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				// pega a pe�a devolvida e remove do lugar
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				// se a pe�a movida foi branca, devolve a preta para a linha 3
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				}	// se a pe�a movida foi preta, devolve a branca para a linha 4
				else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		// se a cor atual for diferente do board.piece (tem que fazer um down casting) da cor a ser movimentada
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {	// se n�o tiver movimento poss�vel, gera a exce��o
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {		// se para a pe�a de origem a posi��o de destino n�o � poss�vel, gera uma exce��o
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;		// incrementa o turno
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;		// altera o jogador atual
						// se o jogador atual for branco, ele passa a ser pre�o, se n�o, ele ser� branco
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	// localizar o rei de uma determinada cor
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {	// verifica se est� em check
			return false;
		}
		// cria uma lista com todas as pe�as dessa cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {	// percorre as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) {	// percorre as colunas da matriz
					if (mat[i][j]) {
						// downcast para ChessPiece para poder chamar o getChessPosition e converter para toPosition
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						// destino � a posi��o a ser testada
						Position target = new Position(i, j);
						// movimenta a pe�a
						Piece capturedPiece = makeMove(source, target);
						// teste se ainda est� em check chamando o testCheck
						boolean testCheck = testCheck(color);
						// desfaz o movimento
						undoMove(source, target, capturedPiece);
						// se n�o est� em check, retorna falso, portanto n�o � checkmate
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}	
}
