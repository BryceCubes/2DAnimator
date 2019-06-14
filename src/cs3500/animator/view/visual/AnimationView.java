package cs3500.animator.view.visual;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.view.IAnimatorView;

public class AnimationView extends JFrame implements IAnimatorView {
  AnimationPanel panel;
  JScrollPane scrollPane;
  IAnimatorModel model;
  Timer timer;
  int tick;

  public AnimationView(IAnimatorModel model){
    super();

    this.model = model;

    timer = new Timer(1500, new ActionListener() { //TODO: 1500?
      @Override
      public void actionPerformed(ActionEvent e) {
        animate();
      }
    });

    panel = new AnimationPanel();
    panel.setMinimumSize( new Dimension(500,500));
    panel.setPreferredSize( new Dimension(2000,2000));
    panel.setBackground(Color.yellow);


    scrollPane = new JScrollPane(panel);

    setSize(800,800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocation(200,200);

    add(scrollPane);

    setVisible(true);
  }


  @Override
  public void animate() {
    timer.start();
    panel.draw(model.returnShapesAtTick(tick));
  }
}
