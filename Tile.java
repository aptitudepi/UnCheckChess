import java.awt.Color;
import javax.swing.*;

public class Tile {
  public JButton btn;
  private boolean isWhite;
  private Piece piece;

  public Tile() {
    isWhite = true;
    btn = new JButton();
  }

  public Tile(ChessGame chessGame, boolean white) {
    isWhite = white;
    btn = new JButton();
    btn.setBackground((isWhite) ? Color.decode("#C0B9B1") : Color.decode("#95744B"));
  }

  public void setPiece(boolean white, Piece.PieceType pieceType) {
    this.piece = new Piece(pieceType, white);
    try {
      ImageIcon icon = getPieceIcon(white, pieceType.getSymbol());
      btn.setIcon(icon);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public String getIcon(String icon) {
    icon = "img/" + icon + ".png";
    return icon;
  }

  public void setPiece() {
    this.piece = null;
    btn.setIcon(null);
}

  private ImageIcon getPieceIcon(boolean isWhite, char piece) {
    String color = (isWhite) ? "w" : "b";
    String iconPath = getIcon(color + piece);
    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
    return icon;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
    if (piece != null) {
      try {
        ImageIcon icon = getPieceIcon(piece.isWhite(), piece.getPieceType().getSymbol());
        btn.setIcon(icon);
      } catch (Exception ex) {
        System.out.println(ex);
      }
    } else {
      btn.setIcon(null);
    }
  }

  public Piece getPiece() {
    return piece;
}
}
