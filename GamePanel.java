import model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GamePanel extends JPanel {
    private final static int CELLSIZE = 50;

    private final static Color backgroundColor = Color.BLACK;
    private final static Color foregroundColor = Color.MAGENTA;
    private final static Color gridColor = Color.PINK;

    private int topBottomMargin;
    private int leftRightMargin;
    private World world;

    public GamePanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int row = (e.getY() - topBottomMargin) / CELLSIZE;
                int col = (e.getX() - leftRightMargin) / CELLSIZE;
                if (row >= world.getRows() || col >= world.getColumns()) {
                    return;
                }
                boolean status = world.getCell(row, col);
                world.setCell(row, col, !status);
                repaint();
            }
        });


    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        int rows = (height - 2 * topBottomMargin) / CELLSIZE;
        int columns = (width - 2 * leftRightMargin) / CELLSIZE;
        if (world == null) {
            world = new World(rows, columns);
        } else {
            if (world.getRows() != rows || world.getColumns() != columns) {
                world = new World(rows, columns);
            }
        }

        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, width, height);

        leftRightMargin = (width % CELLSIZE + CELLSIZE) / 2;
        topBottomMargin = (height % CELLSIZE + CELLSIZE) / 2;

        drawGrid(g2, width, height);
        fillCell(g2, 0, 0, true);

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                boolean status = world.getCell(row, col);
                fillCell(g2, row, col, status);
            }
        }
    }

    public void fillCell(Graphics2D g2, int row, int col, boolean status) {
        Color color = status ? foregroundColor : backgroundColor;
        g2.setColor(color);
        int x = leftRightMargin + col * CELLSIZE;
        int y = topBottomMargin + row * CELLSIZE;
        g2.fillRect(x + 1, y + 1, CELLSIZE - 1, CELLSIZE - 1);
    }

    private void drawGrid(Graphics2D g2, int width, int height) {
        g2.setColor(gridColor);
//        below
        for (int i = 0; i < width / CELLSIZE; i++) {
            //vertical lines
            g2.drawLine(leftRightMargin + CELLSIZE * i, topBottomMargin, leftRightMargin + CELLSIZE * i, height - topBottomMargin);
        }
        for (int y = topBottomMargin; y < height - topBottomMargin; y += CELLSIZE) {
            g2.drawLine(leftRightMargin, y, width - leftRightMargin, y);
        }

    }

    //enter ket set grid randomly
    public void randomize() {
        world.randomize();
        repaint();//call paint component
    }

    //back space clear the setteltedd grid
    public void clear() {
        world.clear();
        repaint();
    }

    public void next() {
        world.next();
        repaint();
    }

    public void save(File selectedFile) {
        world.save(selectedFile);
    }

    public void open(File selectedFile) {
        world.load(selectedFile);
        repaint();
    }
}
