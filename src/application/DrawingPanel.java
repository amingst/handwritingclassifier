package application;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;

public class DrawingPanel extends JComponent {
    private Graphics2D graphics2D;
    private BufferedImage image;
    private int posX, posY, prevPosX, prevPosY;
    String imgPath = "c:\\Users\\Andrew\\desktop\\handwritingclassifier\\NeuralNet\\images\\image.PNG";

    public DrawingPanel() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevPosX = e.getX();
                prevPosY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();

                if (graphics2D != null) {
                    graphics2D.setStroke(new BasicStroke(40));
                    graphics2D.drawLine(prevPosX, prevPosY, posX, posY);
                    repaint();
                    prevPosX = posX;
                    prevPosY = posY;
                }
            }
        });
    }

    //@Override
    public void paintComponent(Graphics graphics) {
        if (image == null) {
            image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
            graphics2D = (Graphics2D)image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }

        graphics.drawImage(image, 0, 0, null);
    }

    public void clear() {
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.BLACK);
        repaint();
    }

    private void save(BufferedImage bufferedImage) {
        try {
            ImageIO.write(bufferedImage, "PNG", new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.save(image);
        ImageClassifier.runImageClassifier();
    }

}
