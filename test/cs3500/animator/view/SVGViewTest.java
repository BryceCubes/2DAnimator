package cs3500.animator.view;

import org.junit.Test;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Class used to ensure that the svg view outputs, formats code, and constructs correctly.
 */
public class SVGViewTest {
  IAnimatorModel model = new AnimatorModelImpl.Builder().declareShape("Fred", "RecTanGle")
          .declareShape("Amy", "ellipse")
          .declareShape("Ethan", "ELLIPSE")
          .addMotion("Ethan", 30, 25, 25, 15, 15, 120,
                  180, 95, 60, 40, 15, 20, 30,
                  180, 120, 230)
          .addMotion("Ethan", 0, 25, 25, 15, 15, 180,
                  120, 230, 30, 25, 25, 15, 15,
                  120, 180, 95)
          .addMotion("Amy", 125, 50, 50, 20, 30, 0,
                  100, 255, 150, 50, 50, 10, 20,
                  0, 100, 255)
          .addMotion("Amy", 100, 50, 50, 30, 30, 0,
                  100, 255, 125, 50, 50, 20, 30, 0, 100, 255)
          .addMotion("Amy", 75, 50, 50, 30, 35, 0,
                  100, 255, 100, 50, 50, 30, 30,
                  0, 100, 255)
          .addMotion("Amy", 50, 50, 50, 20, 25, 0,
                  100, 255, 75, 50, 50, 30, 35,
                  0, 100, 255)
          .addMotion("Amy", 25, 50, 50, 10, 25, 0,
                  100, 255, 50, 50, 50, 20, 25,
                  0, 100, 255)
          .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                  100, 255, 25, 50, 50, 10, 25,
                  0, 100, 255)
          .addMotion("Fred", 50, 15, 5, 5, 5, 255,
                  150, 10, 60, 10, 20, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 40, 5, 15, 5, 5, 255,
                  150, 10, 50, 15, 5, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 30, 5, 5, 5, 5, 255,
                  150, 10, 40, 5, 15, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 20, 5, 10, 5, 5, 255,
                  150, 10, 30, 5, 5, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 10, 15, 10, 5, 5, 255,
                  150, 10, 20, 5, 10, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 0, 10, 10, 5, 5, 255,
                  150, 10, 10, 15, 10, 5, 5,
                  255, 150, 10).setBounds(1, 2, 3, 4).build();
  @Test (expected = IllegalArgumentException.class)
  public void modelNotSet() {
    new SVGView.Builder().build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void modelSetToNull() {
    new SVGView.Builder().declareModel(null).build();
  }
}