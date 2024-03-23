public class Move {
  private int initX;
  private int initY;
  private int finalX;
  private int finalY;

  public Move(int initX, int initY, int finalX, int finalY) {
    this.initX = initX;
    this.initY = initY;
    this.finalX = finalX;
    this.finalY = finalY;
  }

  public int getInitX() {
    return initX;
  }

  public int getInitY() {
    return initY;
  }

  public int getFinalX() {
    return finalX;
  }

  public int getFinalY() {
    return finalY;
  }
}
