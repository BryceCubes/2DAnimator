package cs3500.animator.view.visual;

import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.view.IAnimatorView;

/**
 * Represents the outer window that contains the animation panel.
 */
public class AnimationView extends JFrame implements IAnimatorView {
  private AnimationPanel panel;
  private ReadOnlyIAnimatorModel model;
  private ArrayList<ReadOnlyIShape> shapesToRender;
  private Timer timer;
  private int tick = 0;

  private AnimationView() {
    super();
  }

  /**
   * Class to build Animation views.
   */
  public static class Builder {
    private IAnimatorModel model = null;
    private int speed = 1;

    /**
     * Builds the completed AnimationView based on the previously added fields.
     *
     * @return finished AnimationView
     */
    public AnimationView build() {
      AnimationView animationView = new AnimationView();
      if (this.model == null) {
        throw new IllegalArgumentException("Model must be set to a value.");
      } else {
        animationView.setModel(this.model);
        animationView.setTimer(this.speed);
        animationView.setPanel();

        return animationView;
      }
    }

    /**
     * Declares a new model to be added to the AnimationView.
     *
     * @param model the model to be added
     * @return the builder is returned back so it can continue building
     */
    public Builder declareModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    /**
     * Declares a new speed to be added for the AnimationView to make a timer.
     *
     * @param speed the speed to be added
     * @return the builder is returned back so it can continue building
     */
    public Builder declareSpeed(int speed) {
      if (speed < 1) {
        throw new IllegalArgumentException("Speed cannot be less than 1.");
      }
      this.speed = speed;
      return this;
    }
  }

  /**
   * Sets the final model for the AnimationView.
   *
   * @param model the model to be set
   */
  private void setModel(IAnimatorModel model) {
    this.model = model;
  }


  @Override
  public void animate() {
    timer.start();
  }

  /**
   * Renders the actual shapes based on the list of shapes passed.
   *
   * @param shapes the list of shapes to render
   */
  private void render(ArrayList<ReadOnlyIShape> shapes) {
    panel.draw(shapes);
  }

  /**
   * Sets the timer to determine the animation rate.
   *
   * @param speed the speed of the timer in 10ms
   */
  private void setTimer(int speed) {
    this.timer = new Timer(1000 / speed, e -> {
      shapesToRender = model.getShapesAtTick(tick++);
      render(shapesToRender);
    });
  }

  /**
   * Sets the Panel attributes for the AnimationView.
   */
  private void setPanel() {

    int width = model.getCanvasX() + model.getCanvasW();
    int height = model.getCanvasY() + model.getCanvasH();

    panel = new AnimationPanel();
    panel.setMinimumSize(new Dimension(200, 200));
    panel.setPreferredSize(new Dimension(width, height));
    panel.setBackground(Color.white);

    JScrollPane scrollPane = new JScrollPane(panel);

    setSize(width, height);
    setLocation(model.getCanvasX(), model.getCanvasY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    add(scrollPane);

    setVisible(true);
  }
}



