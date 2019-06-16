package cs3500.animator.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Testing class used to ensure that the text view prints, outputs, and constructs properly.
 */
public class TextViewTest {
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
  private IAnimatorView modelView = new TextView.Builder().declareModel(model).build();
  private final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStream() {
    System.setOut(new PrintStream(newOut));
  }

  @After
  public void restoreStream() {
    System.setOut(originalOut);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modelNotSet() {
    new TextView.Builder().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void modelSetToNull() {
    new TextView.Builder().declareModel(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outLessThan5Chars() {
    new TextView.Builder().declareModel(model).declareOut("ab");
  }

  @Test(expected = IllegalArgumentException.class)
  public void outDoesntContainTXT() {
    new TextView.Builder().declareModel(model).declareOut("abcdefg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void outIsNull() {
    new TextView.Builder().declareModel(model).declareOut(null);
  }

  @Test
  public void systemOut() {
    modelView.animate();
    assertEquals("canvas 1 2 3 4\n"
            + "shape Fred rectangle\n"
            + "motion Fred 0 10 10 5 5 255 150 10    10 15 10 5 5 255 150 10\n"
            + "motion Fred 10 15 10 5 5 255 150 10    20 5 10 5 5 255 150 10\n"
            + "motion Fred 20 5 10 5 5 255 150 10    30 5 5 5 5 255 150 10\n"
            + "motion Fred 30 5 5 5 5 255 150 10    40 5 15 5 5 255 150 10\n"
            + "motion Fred 40 5 15 5 5 255 150 10    50 15 5 5 5 255 150 10\n"
            + "motion Fred 50 15 5 5 5 255 150 10    60 10 20 5 5 255 150 10\n"
            + "shape Amy ellipse\n"
            + "motion Amy 0 50 50 10 20 0 100 255    25 50 50 10 25 0 100 255\n"
            + "motion Amy 25 50 50 10 25 0 100 255    50 50 50 20 25 0 100 255\n"
            + "motion Amy 50 50 50 20 25 0 100 255    75 50 50 30 35 0 100 255\n"
            + "motion Amy 75 50 50 30 35 0 100 255    100 50 50 30 30 0 100 255\n"
            + "motion Amy 100 50 50 30 30 0 100 255    125 50 50 20 30 0 100 255\n"
            + "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n"
            + "shape Ethan ellipse\n"
            + "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n"
            + "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n\n",
            newOut.toString());
  }
}