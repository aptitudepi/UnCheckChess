import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class Tile {
  public JButton btn;
  private boolean isWhite;
  private final char[] pieceTypes = { 'P', 'N', 'B', 'R', 'Q', 'K' };
  private char piece;

  public Tile() {
    isWhite = true;
    btn = new JButton();
  }

  public Tile(boolean white) {
    isWhite = white;
    btn = new JButton();
    btn.setBackground((isWhite) ? Color.decode("#C0B9B1") : Color.decode("#95744B"));
  }

  public void setPiece(boolean white, int numType) {
    piece = pieceTypes[numType];
    try {
      btn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("img/" + ((white) ? "w" : "b") + piece + ".png"))));
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void setPiece() {
    btn.setIcon(null);
  }

  public void move(int xi, int yi, int xf, int yf, Tile[][] arr) {
    arr[xi][yi].setPiece();
  }
}
