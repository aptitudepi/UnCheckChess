public class Piece {
  private boolean isWhite;
  private PieceType pieceType;
  private PieceColor pieceColor;

  enum PieceColor {
    BLACK,
    WHITE;
  }

  public PieceColor getPieceColor() {
    return this.pieceColor;
  }

  enum PieceType {
    PAWN('P'),
    KNIGHT('N'),
    BISHOP('B'),
    ROOK('R'),
    QUEEN('Q'),
    KING('K');

    private char symbol;

    PieceType(char symbol) {
      this.symbol = symbol;
    }

    public char getSymbol() {
      return symbol;
    }
  }

  public Piece(PieceType pieceType, boolean isWhite) {
    this.pieceType = pieceType;
    this.isWhite = isWhite;
    this.pieceColor = (isWhite) ? PieceColor.WHITE : PieceColor.BLACK;
  }

  public PieceType getPieceType() {
    return pieceType;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public boolean isValidMove(int initX, int initY, int finalX, int finalY) {
    // Implement the logic to check if the move is valid for the piece
    // Return true if the move is valid, false otherwise
    // You'll need to consider the piece type and define the specific rules for each piece's
    // movement

    switch (pieceType) {
      case KING:
        return isValidKingMove(initX, initY, finalX, finalY);
      case QUEEN:
        return isValidQueenMove(initX, initY, finalX, finalY);
      case ROOK:
        return isValidRookMove(initX, initY, finalX, finalY);
      case BISHOP:
        return isValidBishopMove(initX, initY, finalX, finalY);
      case KNIGHT:
        return isValidKnightMove(initX, initY, finalX, finalY);
      case PAWN:
        return isValidPawnMove(initX, initY, finalX, finalY);
      default:
        return false; // Invalid piece type
    }
  }

  private boolean isValidKingMove(int initX, int initY, int finalX, int finalY) {
    // Implement the logic to check if the move is valid for a King piece
    // Return true if the move is valid, false otherwise
    // You'll need to consider factors such as the distance and direction of the move
    // as well as any special rules for castling
    // Note: This is just a basic example, and you'll need to define the specific rules for a King's
    // movement

    int dx = Math.abs(finalX - initX);
    int dy = Math.abs(finalY - initY);

    // Check if the move is within one square in any direction
    if (dx <= 1 && dy <= 1) {
      return true;
    }

    return false;
  }

  private boolean isValidQueenMove(int initX, int initY, int finalX, int finalY) {
    // Implement the logic to check if the move is valid for a Queen piece
    // Return true if the move is valid, false otherwise
    // You'll need to consider factors such as the distance and direction of the move
    // Note: This is just a basic example, and you'll need to define the specific rules for a
    // Queen's movement

    int dx = Math.abs(finalX - initX);
    int dy = Math.abs(finalY - initY);

    // Check if the move is diagonal, horizontal, or vertical
    if (dx == dy || initX == finalX || initY == finalY) {
      return true;
    }

    return false;
  }

  private boolean isValidRookMove(int initX, int initY, int finalX, int finalY) {
    // Check if the move is either horizontal or vertical
    return (initX == finalX || initY == finalY);
  }

  private boolean isValidBishopMove(int initX, int initY, int finalX, int finalY) {
    // Check if the move is diagonal
    int dx = Math.abs(finalX - initX);
    int dy = Math.abs(finalY - initY);
    return (dx == dy);
  }

  private boolean isValidKnightMove(int initX, int initY, int finalX, int finalY) {
    // Check if the move is in an L-shape pattern
    int dx = Math.abs(finalX - initX);
    int dy = Math.abs(finalY - initY);
    return ((dx == 2 && dy == 1) || (dx == 1 && dy == 2));
  }

  private boolean isValidPawnMove(int initX, int initY, int finalX, int finalY) {
    // Check if the move is valid for a pawn
    int dx = finalX - initX;
    int dy = finalY - initY;

    if (isWhite) {
      // White pawn can move forward by 1 or 2 squares (on its first move) and capture diagonally
      if (dx == -1 && Math.abs(dy) == 1) {
        // Capture move
        return true;
      } else if (dx == -2 && dy == 0 && initX == 6) {
        // Two-square move on first move
        return true;
      } else if (dx == -1 && dy == 0) {
        // Single square move
        return true;
      }
    } else {
      // Black pawn can move forward by 1 or 2 squares (on its first move) and capture diagonally
      if (dx == 1 && Math.abs(dy) == 1) {
        // Capture move
        return true;
      } else if (dx == 2 && dy == 0 && initX == 1) {
        // Two-square move on first move
        return true;
      } else if (dx == 1 && dy == 0) {
        // Single square move
        return true;
      }
    }

    return false;
  }
}
