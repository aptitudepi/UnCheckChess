import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame {
  public Piece[][] board;
  private List<String> previousPositions;
  private int movesWithoutCaptureOrPawnMove;
  private ChessGUI gui;
  private boolean whiteTurn;

  public enum GameResult {
    WHITE_WINS,
    BLACK_WINS,
    DRAW,
    ONGOING
  }

  public void registerMoveListener(ChessGUI gui) {
    this.gui = gui;
  }

  public ChessGame() {
    // Initialize the board and set up the initial pieces
    board = new Piece[8][8];
    previousPositions = new ArrayList<>();
    movesWithoutCaptureOrPawnMove = 0;
    whiteTurn = true;

    // Set up the pawns
    for (int col = 0; col < 8; col++) {
      board[1][col] = new Piece(Piece.PieceType.PAWN, false); // Black pawns
      board[6][col] = new Piece(Piece.PieceType.PAWN, true); // White pawns
    }

    // Set up the rooks
    board[0][0] = new Piece(Piece.PieceType.ROOK, false); // Black rook
    board[0][7] = new Piece(Piece.PieceType.ROOK, false); // Black rook
    board[7][0] = new Piece(Piece.PieceType.ROOK, true); // White rook
    board[7][7] = new Piece(Piece.PieceType.ROOK, true); // White rook

    // Set up the knights
    board[0][1] = new Piece(Piece.PieceType.KNIGHT, false); // Black knight
    board[0][6] = new Piece(Piece.PieceType.KNIGHT, false); // Black knight
    board[7][1] = new Piece(Piece.PieceType.KNIGHT, true); // White knight
    board[7][6] = new Piece(Piece.PieceType.KNIGHT, true); // White knight

    // Set up the bishops
    board[0][2] = new Piece(Piece.PieceType.BISHOP, false); // Black bishop
    board[0][5] = new Piece(Piece.PieceType.BISHOP, false); // Black bishop
    board[7][2] = new Piece(Piece.PieceType.BISHOP, true); // White bishop
    board[7][5] = new Piece(Piece.PieceType.BISHOP, true); // White bishop

    // Set up the queens
    board[0][3] = new Piece(Piece.PieceType.QUEEN, false); // Black queen
    board[7][3] = new Piece(Piece.PieceType.QUEEN, true); // White queen

    // Set up the kings
    board[0][4] = new Piece(Piece.PieceType.KING, false); // Black king
    board[7][4] = new Piece(Piece.PieceType.KING, true); // White king
  }

  public boolean isValidMove(int initX, int initY, int finalX, int finalY) {
    Piece piece = board[initX][initY];
    if (piece == null || piece.isWhite() != whiteTurn) {
      return false; // No piece at the initial position
    }

    if (board[finalX][finalY] == board[initX][initY]) {
      return false;
    }

    if (board[finalX][finalY] != null) {
      if (piece.getPieceColor() == board[finalX][finalY].getPieceColor()) {
        return false;
      }
    }

    // Check if the final position is within the bounds of the board
    if (finalX < 0 || finalX >= 8 || finalY < 0 || finalY >= 8) {
      return false; // Final position is out of bounds
    }

    // Check if the move is valid according to the piece's rules
    int deltaX = finalX - initX;
    int deltaY = finalY - initY;

    // Handle different piece types
    switch (piece.getPieceType()) {
      case PAWN: // Pawn
        if (piece.isWhite()) {
          // White pawn moves
          if (deltaY == 0 && deltaX == -1 && board[finalX][finalY] == null) {
            return true; // Move one step forward
          }
          if (deltaY == 0
              && deltaX == -2
              && initX == 6
              && board[finalX][finalY] == null
              && board[5][finalY] == null) {
            return true; // Move two steps forward from starting position
          }
          if (deltaY == 1
              && Math.abs(deltaX) == 1
              && board[finalX][finalY] != null
              && !board[finalX][finalY].isWhite()) {
            return true; // Capture diagonally
          }
        } else {
          // Black pawn moves
          if (deltaY == 0 && deltaX == 1 && board[finalX][finalY] == null) {
            return true; // Move one step forward
          }
          if (deltaY == 0
              && deltaX == 2
              && initX == 1
              && board[finalX][finalY] == null
              && board[2][finalY] == null) {
            return true; // Move two steps forward from starting position
          }
          if (deltaY == -1
              && Math.abs(deltaX) == 1
              && board[finalX][finalY] != null
              && board[finalX][finalY].isWhite()) {
            return true; // Capture diagonally
          }
          return false;
        }
        break;

      case KNIGHT: // Knight
        if ((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1)
            || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2)) {
          return true; // Valid knight move
        }
        break;

      case BISHOP: // Bishop
        if (Math.abs(deltaX) == Math.abs(deltaY)) {
          int xDir = (deltaX > 0) ? 1 : -1;
          int yDir = (deltaY > 0) ? 1 : -1;
          int x = initX + xDir;
          int y = initY + yDir;
          while (x != finalX) {
            if (board[x][y] != null) {
              return false; // Path is blocked
            }
            x += xDir;
            y += yDir;
          }
          return true; // Valid bishop move
        }
        break;

      case ROOK: // Rook
        if ((deltaX == 0 && deltaY != 0) || (deltaX != 0 && deltaY == 0)) {
          if (deltaX == 0) {
            int step = (deltaY > 0) ? 1 : -1;
            int y = initY + step;
            while (y != finalY) {
              if (board[finalX][y] != null) {
                return false; // Path is blocked
              }
              y += step;
            }
            return true; // Valid rook move
          } else {
            int step = (deltaX > 0) ? 1 : -1;
            int x = initX + step;
            while (x != finalX) {
              if (board[x][finalY] != null) {
                return false; // Path is blocked
              }
              x += step;
            }
            return true; // Valid rook move
          }
        }
        break;

      case QUEEN: // Queen
        if (Math.abs(deltaX) == Math.abs(deltaY)) {
          int xDir = (deltaX > 0) ? 1 : -1;
          int yDir = (deltaY > 0) ? 1 : -1;
          int x = initX + xDir;
          int y = initY + yDir;
          while (x != finalX) {
            if (board[x][y] != null) {
              return false; // Path is blocked
            }
            x += xDir;
            y += yDir;
          }
          return true; // Valid queen move (diagonal)
        } else if ((deltaX == 0 && deltaY != 0) || (deltaX != 0 && deltaY == 0)) {
          if (deltaX == 0) {
            int step = (deltaY > 0) ? 1 : -1;
            int y = initY + step;
            while (y != finalY) {
              if (board[finalX][y] != null) {
                return false; // Path is blocked
              }
              y += step;
            }
            return true; // Valid queen move (vertical)
          } else {
            int step = (deltaX > 0) ? 1 : -1;
            int x = initX + step;
            while (x != finalX) {
              if (board[x][finalY] != null) {
                return false; // Path is blocked
              }
              x += step;
            }
            return true; // Valid queen move (horizontal)
          }
        }
        break;

      case KING: // King
        if (Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1) {
          return true; // Valid king move
        }
        break;
      default:
        return false; // Invalid piece type
    }
    return false;
  }

  public Piece getPieceAt(int x, int y) {
    if (x < 0 || x >= 8 || y < 0 || y >= 8) {
      return null; // Invalid position, return null
    }

    return board[x][y];
  }

  public boolean isCheck(boolean isWhiteTurn) {
    // Find the current player's king position
    int[] kingPosition = findKingPosition(isWhiteTurn);

    if (kingPosition[0] == -1 || kingPosition[1] == -1) {
      return false; // King not found, return false
    }

    // Check if any opponent's pieces can attack the king
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];
        if (piece != null && piece.isWhite() != isWhiteTurn) {
          if (isValidMove(x, y, kingPosition[0], kingPosition[1])) {
            return true; // King is under attack (in check)
          }
        }
      }
    }

    return false; // King is not under attack (not in check)
  }

  public boolean isCheckmate() {
    int count = 0;
    // Check if any move can get the king out of check
    for (int initX = 0; initX < 8; initX++) {
      for (int initY = 0; initY < 8; initY++) {
        Piece piece = board[initX][initY];
        if (piece != null && piece.getPieceType() == Piece.PieceType.KING) {
          count++;
        }
      }
    }
    if (count < 2) {
      return true; // King is in checkmate since there are less than 2 kings.
    }

    return false;
  }

  public boolean isStalemate() {
    // Check if the current player is in stalemate
    boolean isWhiteTurn =
        true; // determine the current player's turn (e.g., based on a variable or game state)

    if (isCheck(isWhiteTurn)) {
      return false; // If the player is in check, it's not a stalemate
    }

    // Iterate through all pieces on the board
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];

        // Check if the piece belongs to the current player
        if (piece != null && piece.isWhite() == isWhiteTurn) {
          // Generate all possible moves for the piece
          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              if (isValidMove(x, y, i, j)) {
                // If there's at least one valid move, it's not a stalemate
                return false;
              }
            }
          }
        }
      }
    }
    return true; // If no valid moves are found for any piece, it's a stalemate
  }

  public boolean isInsufficientMaterial() {
    // Count the number of pieces remaining on the board
    int whitePiecesCount = countPieces(true);
    int blackPiecesCount = countPieces(false);

    // Draw due to insufficient material
    if (whitePiecesCount == 1 && blackPiecesCount == 1) {
      // Only kings remaining
      return true;
    } else if (whitePiecesCount == 1 && blackPiecesCount == 2) {
      // White king vs. black king and bishop or knight
      if (containsBishopOrKnight(false)) {
        return true;
      }
    } else if (whitePiecesCount == 2 && blackPiecesCount == 1) {
      // White king and bishop or knight vs. black king
      if (containsBishopOrKnight(true)) {
        return true;
      }
    } else if (whitePiecesCount == 2 && blackPiecesCount == 2) {
      // White king and bishop or knight vs. black king and bishop or knighte
      if (!containsSameColoredBishops()) {
        return true;
      }
    }

    return false;
  }

  private int countPieces(boolean isWhite) {
    int count = 0;

    // Iterate through all pieces on the board
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];
        if (piece != null && piece.isWhite() == isWhite) {
          count++;
        }
      }
    }

    return count;
  }

  private boolean containsBishopOrKnight(boolean isWhite) {
    // Iterate through all pieces on the board
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];
        if (piece != null && piece.isWhite() == isWhite) {
          Piece.PieceType type = piece.getPieceType();
          if (type == Piece.PieceType.BISHOP || type == Piece.PieceType.KNIGHT) {
            return true;
          }
        }
      }
    }

    return false;
  }

  private boolean containsSameColoredBishops() {
    boolean whiteBishopFound = false;
    boolean blackBishopFound = false;

    // Iterate through all pieces on the board
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];
        if (piece != null) {
          Piece.PieceType type = piece.getPieceType();
          if (type == Piece.PieceType.BISHOP) {
            if (piece.isWhite()) {
              whiteBishopFound = true;
            } else {
              blackBishopFound = true;
            }
          }
        }
      }
    }

    return whiteBishopFound && blackBishopFound;
  }

  public boolean isThreefoldRepetition() {
    // Count the occurrences of the current position in the previous positions
    String currentPosition = getPositionString();
    int repetitionCount = Collections.frequency(previousPositions, currentPosition);

    // Check if the current position has occurred three times
    return repetitionCount >= 3;
  }

  private String getPositionString() {
    // Generate a string representation of the current board position
    StringBuilder sb = new StringBuilder();
    for (Piece[] row : board) {
      for (Piece piece : row) {
        sb.append(piece != null ? piece.getPieceType().toString() : "-");
      }
    }
    return sb.toString();
  }

  public boolean isFiftyMoveRule() {
    return movesWithoutCaptureOrPawnMove >= 50;
  }

  public boolean isDraw() {
    return isStalemate()
        || isInsufficientMaterial()
        || isThreefoldRepetition()
        || isFiftyMoveRule();
  }

  public GameResult getResult() {
    // Check if the current player is in checkmate
    if (isCheckmate()) {
      for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
          Piece piece = board[x][y];
          if (piece != null
              && piece.getPieceType() == Piece.PieceType.KING
              && piece.getPieceColor() == Piece.PieceColor.BLACK) {
            return GameResult.BLACK_WINS;
          } else {
            return GameResult.WHITE_WINS;
          }
        }
      }
    }

    // Check if the game is in a stalemate
    if (isStalemate()) {
      return GameResult.DRAW; // Stalemate, game is a draw
    }

    // Check if the game is in insufficient material
    if (isInsufficientMaterial()) {
      return GameResult.DRAW; // Insufficient material, game is a draw
    }

    // Check if the game is in threefold repetition
    if (isThreefoldRepetition()) {
      return GameResult.DRAW; // Threefold repetition, game is a draw
    }

    // Check if the game is in the fifty-move rule
    if (isFiftyMoveRule()) {
      return GameResult.DRAW; // Fifty-move rule, game is a draw
    }

    // If none of the above conditions are met, the game is still ongoing
    return GameResult.ONGOING;
  }

  public void move(int initX, int initY, int finalX, int finalY) {
    Piece initialPiece = getPieceAt(initX, initY);
    Piece finalPiece = getPieceAt(finalX, finalY);
    // Check if the move is valid for the piece on the initial position
    if (initialPiece != null && isValidMove(initX, initY, finalX, finalY)) {
      // Move the piece from the initial position to the final position
      board[finalX][finalY] = initialPiece;
      board[initX][initY] = null;

      // Increment the movesWithoutCaptureOrPawnMove counter
      if (initialPiece.getPieceType() != Piece.PieceType.PAWN && finalPiece == null) {
        movesWithoutCaptureOrPawnMove++;
      } else {
        movesWithoutCaptureOrPawnMove = 0;
      }

      whiteTurn = !whiteTurn;

      // Notify the GUI about the move
      if (gui != null) {
        gui.onMoveMade(initX, initY, finalX, finalY);
      }
    }
  }

  public int[] findKingPosition(boolean isWhiteTurn) {
    int[] kingPosition = {-1, -1};

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Piece piece = board[x][y];
        if (piece != null
            && piece.getPieceType() == Piece.PieceType.KING
            && piece.isWhite() == isWhiteTurn) {
          kingPosition[0] = x;
          kingPosition[1] = y;
          return kingPosition;
        }
      }
    }

    return kingPosition;
  }
}
