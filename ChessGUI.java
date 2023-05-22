import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChessGUI extends JFrame implements ActionListener {
  // declare component variables
  private JPanel masterPanel;
  private JPanel chessBoard;
  private Tile[][] arrSquare;
  private boolean pieceSelected;
  private int initX;
  private int initY;
  private int finalX;
  private int finalY;
  private ChessGame chessGame;

  public static void main(String[] args) {
    JFrame frame = new ChessGUI();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame.setPreferredSize(new Dimension(2000, 1080));
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      }
    });
  }

  public void onMoveMade(int initX, int initY, int finalX, int finalY) {
    Tile initialTile = arrSquare[initX][initY];
    Tile finalTile = arrSquare[finalX][finalY];
    finalTile.setPiece(initialTile.getPiece());
    initialTile.setPiece();
    if (chessGame.getResult() == ChessGame.GameResult.BLACK_WINS) {
      showWinner("Black");
    } else if (chessGame.getResult() == ChessGame.GameResult.WHITE_WINS) {
      showWinner("White");
    }
  }

  // constructor
  ChessGUI() {
    super("Chess GUI"); // Window Title
    // create components
    arrSquare = new Tile[8][8];
    chessGame = new ChessGame(); // Initialize the chessGame object
    // Font(font name, 0-normal/1-bold/2-italics/3-bold and italics, font size)

    // create JPanel
    masterPanel = new JPanel(new BorderLayout(3, 3));
    chessBoard = new JPanel(new GridLayout(8, 8));
    // add components to JPanel
    // masterPanel.add(lbBG);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        arrSquare[i][j] = new Tile(chessGame, !((i % 2 == 0 & j % 2 != 0) | (i % 2 != 0 & j % 2 == 0)));
        chessBoard.add(arrSquare[i][j].btn);
        switch (j) {
          case 0:
          case 7:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, Piece.PieceType.ROOK);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, Piece.PieceType.ROOK);
            }
            break;
          case 1:
          case 6:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, Piece.PieceType.KNIGHT);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, Piece.PieceType.KNIGHT);
            }
            break;
          case 2:
          case 5:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, Piece.PieceType.BISHOP);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, Piece.PieceType.BISHOP);
            }
            break;
          case 3:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, Piece.PieceType.QUEEN);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, Piece.PieceType.QUEEN);
            }
            break;
          case 4:
            if (i == 7) {
              arrSquare[i][j].setPiece(true, Piece.PieceType.KING);
            }
            if (i == 0) {
              arrSquare[i][j].setPiece(false, Piece.PieceType.KING);
            }
            break;
        }
        if (i == 6) {
          arrSquare[i][j].setPiece(true, Piece.PieceType.PAWN);
        }
        if (i == 1) {
          arrSquare[i][j].setPiece(false, Piece.PieceType.PAWN);
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
    chessGame.registerMoveListener(this);
  }

  public void showWinner(String winner) {
    JOptionPane.showMessageDialog(this, winner + " has won the game!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    Window[] windows = Window.getWindows();
    for (Window window : windows) {
      window.dispose();
    }
    System.exit(0);
  }

  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();

    // Find the button in the arrSquare array
    for (int i = 0; i < arrSquare.length; i++) {
      for (int j = 0; j < arrSquare[i].length; j++) {
        if (arrSquare[i][j].btn == button) {
          // If a piece is already selected, assign the current position as the final
          // position
          if (pieceSelected) {
            finalX = i;
            finalY = j;
            chessGame.move(initX, initY, finalX, finalY);
            pieceSelected = false;
          } else { // Otherwise, assign the current position as the initial position
            initX = i;
            initY = j;
            if (chessGame.board[initX][initY] != null) {
              pieceSelected = true;
            }
          }
          break;
        }
      }
    }
  }
}
