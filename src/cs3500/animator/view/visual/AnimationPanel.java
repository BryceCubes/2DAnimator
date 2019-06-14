package cs3500.animator.view.visual;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.shape.ReadOnlyIShape;

public class AnimationPanel extends JPanel implements IAnimationPanel {
  List<ReadOnlyIShape> shapes = null;

  public AnimationPanel(){
    super();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if ( shapes != null ){
      g.setColor( Color.pink );
      for ( ReadOnlyIShape shape : shapes ){
        g.fillRect((int)shape.getXPos(),(int)shape.getYPos(),(int)shape.getWidth(), (int)shape.getHeight());
        //TODO: don't cast this
      }
    }
  }

  @Override
  public void draw(List<ReadOnlyIShape> shapes) {
    this.shapes = shapes;
    repaint();
  }
}