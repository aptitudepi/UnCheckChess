import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame implements ActionListener {
  // declare component variables
  private JPanel masterPanel;
  private JPanel chessBoard;
  private Tile[][] arrSquare;
  private boolean pieceSelected;
  private int initX, initY, finalX, finalY;
  JLabel lbBG;

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
    arrSquare = new Tile[8][8];
    // bg = new ImageIcon("./img/wood3.jpg");
    // lbBG = new JLabel();
    // lbBG.setIcon(bg);
    // Font(font name, 0-normal/1-bold/2-italics/3-bold and italics, font size)

    // create JPanel
    masterPanel = new JPanel(new BorderLayout(3, 3));
    chessBoard = new JPanel(new GridLayout(8, 8));
    // add components to JPanel
    // masterPanel.add(lbBG);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        arrSquare[i][j] = new Tile(((i % 2 == 0 & j % 2 == 1) | (i % 2 == 1 & j % 2 == 0)) ? false : true);
        chessBoard.add(arrSquare[i][j].btn);
        switch (j) {
          case 0:
          case 7:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, 3);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, 3);
            }
            break;
          case 1:
          case 6:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, 1);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, 1);
            }
            break;
          case 2:
          case 5:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, 2);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, 2);
            }
            break;
          case 3:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, 4);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, 4);
            }
            break;
          case 4:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, 5);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, 5);
            }
            break;
        }
        if (i == 6) {
          arrSquare[i][j].setPiece(true, 0);
        }
        if (i == 1) {
          arrSquare[i][j].setPiece(false, 0);
        }

      }
    }
    masterPanel.add(chessBoard);

    // set JPanel to be the content pane
    setContentPane(masterPanel);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        arrSquare[i][j].btn.addActionListener(this);
      }
    }
  }

  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    if (pieceSelected) {
      for (int i = 0; i < arrSquare.length; i++) {
        for (int j = 0; j < arrSquare[i].length; j++) {
          if (arrSquare[i][j].btn == button) {
            finalX = i;
            finalY = j;
            pieceSelected = true;
            break;
          }
        }
      }
    } else {
      for (int i = 0; i < arrSquare.length; i++) {
        for (int j = 0; j < arrSquare[i].length; j++) {
          if (arrSquare[i][j].btn == button) {
            initX = i;
            initY = j;
            pieceSelected = true;
            break;
          }
        }
      }
      Tile.move(initX, initY, finalX, finalY);
    }
    

    // if (e.getSource() == selector) {}
  }
}