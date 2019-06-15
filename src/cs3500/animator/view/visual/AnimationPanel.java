package cs3500.animator.view.visual;

import java.awt.*;
import java.util.List;


import javax.swing.*;

import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.model.shape.ShapeType;

public class AnimationPanel extends JPanel implements IAnimationPanel {
  List<ReadOnlyIShape> shapes = null;

  public AnimationPanel(){
    super();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if ( shapes != null ){
      for ( ReadOnlyIShape shape : shapes ){
        Color newColor = new Color(shape.getRed(), shape.getGreen(), shape.getBlue());
        g.setColor(newColor);
        if(shape.getShapeTypeAsString().equals("rectangle")) {
          g.fillRect((int)shape.getXPos(),(int)shape.getYPos(),(int)shape.getWidth(), (int)shape.getHeight());
        } else { //TODO: some error checking needed here
          g.fillOval((int)shape.getXPos(),(int)shape.getYPos(),(int)shape.getWidth(), (int)shape.getHeight());
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