package main;

import java.awt.Rectangle;

public class Cell extends Rectangle {

  int row;
  int col;
  String NodeType = "";
  Boolean visitedCell = false;

  Cell Parent = null;
  int cost = 1000000;

  int node;

  public Cell() {}

  public Cell(int x, int y, int width, int height, int row, int col) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.row = row;
    this.col = col;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return (
      "Cell [row=" +
      row +
      ", col=" +
      col +
      ", NodeType=" +
      NodeType +
      ", visitedCell=" +
      visitedCell +
      ", node=" +
      node +
      "]"
    );
  }
}
