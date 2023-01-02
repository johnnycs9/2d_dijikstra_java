package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class GridPanel extends JPanel implements Runnable {

  //GridPanel Size
  int originalTileSize = 32;
  int scale = 1;
  int tileSize = originalTileSize * scale;
  int outerMargin = 10;

  int maxScreenRow = 30;
  int maxScreenCol = 50;

  int screenWidth =
    ((maxScreenCol * tileSize) + (outerMargin * (maxScreenCol + 1)));
  int screenHeight =
    ((maxScreenRow * tileSize) + (outerMargin * (maxScreenRow + 4)));

  int FPS = 60;

  Thread gameThread;
  Grid newGrid = new Grid(
    maxScreenRow,
    maxScreenCol,
    tileSize,
    tileSize,
    outerMargin
  );

  public GridPanel() {
    setPreferredSize(new Dimension(screenWidth, screenHeight));
    setBackground(Color.black);
    setDoubleBuffered(true);

    this.addMouseMotionListener(
        new MouseAdapter() {
          public void mouseDragged(MouseEvent e) {
            newGrid.fillCell(e.getPoint());
          }
        }
      );

    this.addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            newGrid.toggleFiller(e.getPoint());
          }
        }
      );
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    int timer = 0;
    int drawCount = 0;

    while (gameThread != null) {
      currentTime = System.nanoTime();

      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }

      if (timer >= 1000000000) {
        drawCount = 0;
        timer = 0;
        System.out.println(drawCount);
      }
    }
  }

  public void update() {}

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    newGrid.draw(g2);

    g2.dispose();
  }
}
