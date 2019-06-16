package cs3500.animator.view.visual;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.*;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.SVGView;

public class AnimationView extends JFrame implements IAnimatorView {
  private AnimationPanel panel;
  private JScrollPane scrollPane;
  private ReadOnlyIAnimatorModel model;
  private Timer timer;
  private int speed;
  private int tick;
  private String out;

  private AnimationView() {
    super();
    timer = new Timer(1000 / this.speed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ArrayList<ReadOnlyIShape> shapesToRender = model.returnShapesAtTick(tick++);
      }
    });
    
    panel = new AnimationPanel();
    panel.setMinimumSize(new Dimension(500, 500));
    panel.setPreferredSize(new Dimension(2000, 2000));
    panel.setBackground(Color.white);


    scrollPane = new JScrollPane(panel);

    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocation(200, 200);

    add(scrollPane);

    setVisible(true);
  }

  public static class Builder {
    private AnimationView animationView;
    private IAnimatorModel model = null;
    private int speed = 1;
    private String out = "Animation view";

    public AnimationView build() {
      this.animationView = new AnimationView();
      if (this.model == null) {
        throw new IllegalArgumentException("Model must be set to a value.");
      } else {
        animationView.setModel(this.model);
        animationView.setOut(this.out);
        animationView.setSpeed(this.speed);
      }

      return animationView;
    }

    public Builder setModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    public Builder setSpeed(int speed) {
      if (speed < 1) {
        throw new IllegalArgumentException("Speed cannot be less than 1.");
      }
      this.speed = speed;
      return this;
    }
  }

  @Override
  public void setOut(String out) {
    this.out = out;
  }

  @Override
  public void setModel(IAnimatorModel model) {
    this.model = model;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void animate() {
    timer.start();
    panel.draw(model.returnShapesAtTick(tick++));
  }
}
