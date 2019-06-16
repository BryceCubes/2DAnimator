package cs3500.animator.view.visual;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.view.IAnimatorView;

public class AnimationView extends JFrame implements IAnimatorView {
  private AnimationPanel panel;
  private JScrollPane scrollPane;
  private ReadOnlyIAnimatorModel model;
  private ArrayList<ReadOnlyIShape> shapesToRender;
  private Timer timer;
  private int speed = 1;
  private int tick = 0;

  private AnimationView() {
    super();
    timer = new Timer(60 / this.speed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shapesToRender = model.getShapesAtTick(tick++);
        render(shapesToRender);
      }
    });

    panel = new AnimationPanel();
    panel.setMinimumSize(new Dimension(360, 360));
    panel.setPreferredSize(new Dimension(360, 360));
    panel.setBackground(Color.white);


    scrollPane = new JScrollPane(panel);

    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocation(200, 200);

    add(scrollPane);

    setVisible(true);
  }

  public static class Builder {
    private IAnimatorModel model = null;
    private Timer timer = new Timer(60 , new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shapesToRender = model.getShapesAtTick(tick++);
        render(shapesToRender);
      }
    });

    public AnimationView build() {
      AnimationView animationView = new AnimationView();
      if (this.model == null) {
        throw new IllegalArgumentException("Model must be set to a value.");
      } else {
        animationView.setModel(this.model);
        animationView.setSpeed(this.speed);
      }

      return animationView;
    }

    public Builder declareModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    public Builder declareSpeed(int speed) {
      if (speed < 1) {
        throw new IllegalArgumentException("Speed cannot be less than 1.");
      }
      this.timer = new Timer(60 / speed, e -> {
        shapesToRender = model.getShapesAtTick(tick++);
        render(shapesToRender);
      });
      return this;
    }
  }

  private void setModel(IAnimatorModel model) {
    this.model = model;
  }

  private void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void animate() {
    timer.start();
  }

  private void render(ArrayList<ReadOnlyIShape> shapes) {
    panel.draw(shapes);
  }

  private void setTimer(int speed) {
    this.timer = timer = new Timer(60 / speed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        shapesToRender = model.getShapesAtTick(tick++);
        render(shapesToRender);
      }
    });
  }
}
