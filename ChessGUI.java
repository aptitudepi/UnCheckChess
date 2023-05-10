import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame implements ActionListener {
  // declare component variables
  private JPanel masterPanel;
  private JPanel chessBoard;
  private JButton[][] arrSquare;
  private ImageIcon bg;
  JLabel lbBG;
  private Image[][] chessPieceImages;
  private String cols = "HGFEDCBA";
  private String rows = "87654321";
  public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
  public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };

  public static void main(String[] args) {
    JFrame frame = new ChessGUI();
    frame.setPreferredSize(new Dimension(2000, 1080));
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // constructor
  ChessGUI() {
    super("Chess GUI"); // Window Title
    // create components
    chessPieceImages = new Image[2][6];
    arrSquare = new JButton[8][8];
    // bg = new ImageIcon("./img/wood3.jpg");
    // lbBG = new JLabel();
    // lbBG.setIcon(bg);
    // Font(font name, 0-normal/1-bold/2-italics/3-bold and italics, font size)

    // create JPanel
    masterPanel = new JPanel(new BorderLayout(3, 3));
    chessBoard = new JPanel(new GridLayout(8,8));
    // add components to JPanel
    // masterPanel.add(lbBG);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        arrSquare[i][j] = new JButton();
        chessBoard.add(arrSquare[i][j]);
        arrSquare[i][j].setBackground(((i % 2 == 0 & j % 2 == 1) | (i % 2 == 1 & j % 2 == 0))? Color.decode("#95744B"): Color.decode("#C0B9B1")); // Sets Image background to White/Black
        switch (j) {
          case 0:
          case 7:
            if (i == 7) {
              try {
                Image img = ImageIO.read(getClass().getResource("img/wR.png"));
                arrSquare[i][j].setIcon(new ImageIcon(img));
              } catch (Exception ex) {
                System.out.println(ex);
              }
            }
            break;
          case 1:
          case 6:
            if (i == 7) {
              try {
                Image img = ImageIO.read(getClass().getResource("img/wN.png"));
                arrSquare[i][j].setIcon(new ImageIcon(img));
              } catch (Exception ex) {
                System.out.println(ex);
              }
            }
            break;       
          case 2:
          case 5:
            if (i == 7) {
              try {
                Image img = ImageIO.read(getClass().getResource("img/wB.png"));
                arrSquare[i][j].setIcon(new ImageIcon(img));
              } catch (Exception ex) {
                System.out.println(ex);
              }
            }
            break;
          case 3:
            if (i == 7) {
              try {
                Image img = ImageIO.read(getClass().getResource("img/wQ.png"));
                arrSquare[i][j].setIcon(new ImageIcon(img));
              } catch (Exception ex) {
                System.out.println(ex);
              }
            }
            break;       
          case 4:
            if (i == 7) {
              try {
                Image img = ImageIO.read(getClass().getResource("img/wK.png"));
                arrSquare[i][j].setIcon(new ImageIcon(img));
              } catch (Exception ex) {
                System.out.println(ex);
              }
            }
            break;       
        }
          
      }
    }
  masterPanel.add(chessBoard);
  // set JPanel to be the content pane
  setContentPane(masterPanel);
  // selector.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    // if (e.getSource() == selector) {}
  }
}