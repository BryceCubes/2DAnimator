package cs3500.animator.view.visual;

import java.awt.Graphics;
import java.awt.Color;
import java.util.List;


import javax.swing.JPanel;

import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Class to represent the outer panel for the animation viewer window.
 */
public class AnimationPanel extends JPanel implements IAnimationPanel {
  private List<ReadOnlyIShape> shapes = null;

  /**
   * Default constructor simply calls the JPanel super constructor.
   */
  AnimationPanel() {
    super();
  }

  /**
   * Paints the individual components based on its attributes of shape type, position, and color.
   * @param g Graphic to draw to
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      for (ReadOnlyIShape shape : shapes) {
        Color newColor = new Color(shape.getRed(), shape.getGreen(), shape.getBlue());
        g.setColor(newColor);
        String shapeType = shape.getShapeTypeAsString();
        if (shapeType.equals("rectangle")) {
          g.fillRect((int) Math.round(shape.getXPos()), (int) Math.round(shape.getYPos()),
                  (int) Math.round(shape.getWidth()), (int) Math.round(shape.getHeight()));
        } else if (shapeType.equals("ellipse")) {
          g.fillOval((int) Math.round(shape.getXPos()), (int) Math.round(shape.getYPos()),
                  (int) Math.round(shape.getWidth()), (int) Math.round(shape.getHeight()));
        }
      }
    }
  }

  @Override
  public void draw(List<ReadOnlyIShape> shapes) {
    this.shapes = shapes;
    repaint();
  }
}