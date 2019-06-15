package cs3500.animator.view.visual;

import java.awt.*;


import javax.swing.*;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IAnimatorView;

public class AnimationView extends JFrame implements IAnimatorView {
  private AnimationPanel panel;
  private JScrollPane scrollPane;
  private IAnimatorModel model;
  private Timer timer;
  private int tick;

  public AnimationView(IAnimatorModel model){
    super();

    this.model = model;

    //TODO: 1500?
    timer = new Timer(1000, e -> animate());

    panel = new AnimationPanel();
    panel.setMinimumSize( new Dimension(500,500));
    panel.setPreferredSize( new Dimension(2000,2000));
    panel.setBackground(Color.white);


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
    panel.draw(model.returnShapesAtTick(tick++));
  }
}
