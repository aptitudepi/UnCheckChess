import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
  public JButton btn;
  private boolean isWhite;
  private final Character[] pieces = { 'P', 'N', 'B', 'R', 'Q', 'K' };
  private final ArrayList<Character> pieceTypes = new ArrayList<>(Arrays.asList(pieces));
  private char piece;
  private int numType;

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
    piece = pieceTypes.get(numType);
    try {
      btn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("img/" + ((white) ? "w" : "b") + piece + ".png"))));
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
  
  public int getnumType() {
    return numType;
  }

  public boolean getColor() {
    return isWhite;
  }

  public void setPiece() {
    btn.setIcon(null);
  }

  public static void move(int xi, int yi, int xf, int yf, Tile[][] arr) {
    arr[xf][yf].setPiece(arr[xi][yi].getColor(), arr[xi][yi].getnumType());
    arr[xi][yi].setPiece();
  }
}