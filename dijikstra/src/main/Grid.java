package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Grid extends Cell {

  static int totalrows;
  static int totalcols;
  static Boolean setObstacleBoolean = false;
  static Boolean setStarterBoolean = false;
  static Boolean setEndingBoolean = false;
  static Boolean setObstacleButtonBoolean = false;
  static Boolean togPathFinder = false;
  static boolean found = false;
  static boolean endSet = false;
  static boolean startSet = false;

  int margin;

  static ArrayList<Cell> Grid = new ArrayList<Cell>();
  ArrayList<Cell> Unvisited = new ArrayList<Cell>();
  static ArrayList<Cell> Queue = new ArrayList<Cell>();

  public Grid(
    int totalrows,
    int totalcols,
    int cellwidth,
    int cellheight,
    int margin
  ) {
    super();
    this.totalrows = totalrows;
    this.totalcols = totalcols;

    for (int row = 0; row < totalrows; row++) {
      for (int col = 0; col < totalcols; col++) {
        Grid.add(
          new Cell(
            (col * cellwidth) + (col * margin) + margin,
            (row * cellheight) + (row * margin) + margin,
            cellwidth,
            cellheight,
            row,
            col
          )
        );
        Grid.get(getIndex(row, col)).node = getIndex(row, col);

        if (col > totalcols) {
          col = 0;
        }
      }
      this.margin = margin;
    }
  }

  public void draw(Graphics2D g2) {
    //draw connections
    g2.setColor(Color.gray);
    for (int i = 0; i < Grid.size(); i++) {
      if (Grid.get(i).col < totalcols - 1) {
        g2.drawLine(
          Grid.get(i).x + Grid.get(i).width,
          Grid.get(i).y + Grid.get(i).height / 2,
          Grid.get(i).x + Grid.get(i).width + margin,
          Grid.get(i).y + Grid.get(i).height / 2
        );
      }

      if (Grid.get(i).row < totalrows - 1) {
        g2.drawLine(
          Grid.get(i).x + Grid.get(i).width / 2,
          Grid.get(i).y + Grid.get(i).height,
          Grid.get(i).x + Grid.get(i).width / 2,
          Grid.get(i).y + Grid.get(i).height + margin
        );
      }
    }

    //draw squares
    for (int i = 0; i < Grid.size(); i++) {
      g2.draw(Grid.get(i));

      if (Grid.get(i).NodeType.equals("")) {
        g2.setColor(Color.gray);
        g2.draw(Grid.get(i));
        g2.setColor(Color.black);
        g2.fill(Grid.get(i));
      }

      if (Grid.get(i).visitedCell == true) {
        g2.setColor(Color.blue);
        g2.fill(Grid.get(i));
        // g2.setColor(Color.gray);
        // g2.drawString(
        //   Integer.toString(Grid.get(i).cost),
        //   Grid.get(i).x + 5,
        //   Grid.get(i).y + 10
        // );
        // g2.drawString(
        //   Integer.toString(Grid.get(i).Parent.node),
        //   Grid.get(i).x + 5,
        //   Grid.get(i).y + 20
        // );
        g2.setColor(Color.gray);
      }

      if (Grid.get(i).NodeType.equals("ObstacleNode")) {
        g2.setColor(Color.orange);
        g2.fill(Grid.get(i));
        g2.setColor(Color.gray);
      }

      if (Grid.get(i).NodeType.equals("StartNode")) {
        g2.setColor(Color.green);
        g2.fill(Grid.get(i));
        g2.setColor(Color.gray);
      }

      if (Grid.get(i).NodeType.equals("EndNode")) {
        g2.setColor(Color.red);
        g2.fill(Grid.get(i));
        g2.setColor(Color.gray);
      }

      if (Grid.get(i).NodeType.equals("PathNode")) {
        g2.setColor(Color.magenta);
        g2.fill(Grid.get(i));
        g2.setColor(Color.black);
        g2.drawString(
          Integer.toString(Grid.get(i).cost),
          Grid.get(i).x + 5,
          Grid.get(i).y + 10
        );
        g2.drawString(
          Integer.toString(Grid.get(i).Parent.node),
          Grid.get(i).x + 5,
          Grid.get(i).y + 20
        );
        g2.setColor(Color.gray);
      }

      if (togPathFinder == true && found == false) {
        PathFinderStart(g2);
      }
    }
  }

  public void fillCell(Point point) {
    if (setObstacleBoolean == true && setObstacleButtonBoolean == true) {
      for (int i = 0; i < Grid.size(); i++) {
        if (Grid.get(i).contains(point)) {
          Grid.get(i).NodeType = "ObstacleNode";
        }
      }
      found = false;
    } else if (
      setObstacleBoolean == false && setObstacleButtonBoolean == true
    ) {
      for (int i = 0; i < Grid.size(); i++) {
        if (Grid.get(i).contains(point)) {
          Grid.get(i).NodeType = "";
        }
      }
      found = false;
    } else if (setEndingBoolean) {
      for (int i = 0; i < Grid.size(); i++) {
        if (
          !(
            Grid.get(i).NodeType.equals("StartNode") ||
            Grid.get(i).NodeType.equals("ObstacleNode")
          )
        ) {
          Grid.get(i).NodeType = "";
        }
        Grid.get(i).visitedCell = false;
        Grid.get(i).cost = 1000000;
        Grid.get(i).Parent = null;

        if (Grid.get(i).contains(point)) {
          Grid.get(i).NodeType = "EndNode";
          endSet = true;
        }
      }
      found = false;
    } else if (setStarterBoolean) {
      for (int i = 0; i < Grid.size(); i++) {
        if (
          !(
            Grid.get(i).NodeType.equals("EndNode") ||
            Grid.get(i).NodeType.equals("ObstacleNode")
          )
        ) {
          Grid.get(i).NodeType = "";
        }

        Grid.get(i).visitedCell = false;
        Grid.get(i).cost = 1000000;
        Grid.get(i).Parent = null;

        if (Grid.get(i).contains(point)) {
          Grid.get(i).NodeType = "StartNode";
          startSet = true;
        }
      }
      found = false;
    }
  }

  public void toggleFiller(Point point) {
    for (int i = 0; i < Grid.size(); i++) {
      if (
        Grid.get(i).contains(point) &&
        !(Grid.get(i).NodeType.equals("ObstacleNode"))
      ) {
        setObstacleBoolean = true;
      } else if (
        Grid.get(i).contains(point) &&
        Grid.get(i).NodeType.equals("ObstacleNode")
      ) {
        setObstacleBoolean = false;
      }
    }

    fillCell(point);
  }

  public static void PathFinderStart(Graphics2D g2) {
    if (togPathFinder && found == false && endSet == true && startSet == true) {
      int startRow = -1;
      int startCol = -1;
      Queue.clear();
      System.out.println("in pathfinder start");
      //Get Starting Rows/cols
      for (int i = 0; i < Grid.size(); i++) {
        if (Grid.get(i).NodeType.equals("StartNode")) {
          startRow = Grid.get(i).row;
          startCol = Grid.get(i).col;
          Grid.get(i).cost = 0;
          Grid.get(i).Parent = Grid.get(i);
        }
      }

      getNeighborCells(
        startRow,
        startCol,
        Grid.get(getIndex(startRow, startCol)).Parent.cost
      );
      int index = 0;

      while (!(found)) {
        getNeighborCells(
          Queue.get(index).row,
          Queue.get(index).col,
          Queue.get(index).Parent.cost
        );

        for (int i = 0; i < Queue.size(); i++) {
          Grid.get(Queue.get(i).node).visitedCell = true;

          if (Grid.get(Queue.get(i).node).NodeType.equals("EndNode")) {
            found = true;
            int parentNode = 0;
            for (int j = Queue.size() - 1; j >= 0; j--) {
              if (Queue.get(j).node == parentNode) {
                Queue.get(j).NodeType = "PathNode";
                parentNode = Queue.get(j).Parent.node;
              }
              if (Queue.get(j).NodeType == "EndNode") {
                parentNode = Queue.get(j).Parent.node;
              }
              if (Queue.get(j).NodeType == "StartNode") {}
            }

            break;
          }
        }

        index++;
      }
    }
  }

  public static int getIndex(int row, int col) {
    return (row * totalcols) + col;
  }

  public static void getNeighborCells(int row, int col, int cost) {
    int[] dr = { -1, 1, 0, 0 };
    int[] dc = { 0, 0, 1, -1 };
    int rr = 0;
    int cc = 0;

    for (int i = 0; i < 4; i++) {
      rr = row + dr[i];
      cc = col + dc[i];

      if (rr < 0 || cc < 0) {
        continue;
      }

      if (rr >= totalrows || cc >= totalcols) {
        continue;
      }

      if (Grid.get(getIndex(rr, cc)).NodeType.equals("ObstacleNode")) {
        continue;
      }

      if (!(Grid.get(getIndex(rr, cc)).visitedCell)) {
        if (Grid.get(getIndex(rr, cc)).Parent == null) {
          Grid.get(getIndex(rr, cc)).Parent = Grid.get(getIndex(row, col));
        }
        if (cost < Grid.get(getIndex(rr, cc)).cost) {
          Grid.get(getIndex(rr, cc)).cost =
            Grid.get(getIndex(rr, cc)).Parent.cost + 1;
        }
        Queue.add(Grid.get(getIndex(rr, cc)));
      }
    }
  }
}
