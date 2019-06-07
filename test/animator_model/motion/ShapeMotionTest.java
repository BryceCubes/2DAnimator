package animator_model.motion;

import org.junit.Test;

import animator_model.shape.AShape;
import animator_model.shape.IShape;
import animator_model.shape.ShapeType;

import static org.junit.Assert.assertEquals;

public class ShapeMotionTest {
  private IShape frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5,
          255, 150, 10);
  private IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
          150, 10, 15, 10, 5, 5, 255, 150, 10,
          0, 10);

  @Test (expected = IllegalArgumentException.class)
  public void widthStartNegative() {
    new ShapeMotion(frectangle, 10, 10, -5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void heightStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, -5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void redStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, -255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void gStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            -150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void bStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, -10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void rStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 256,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void gStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            256, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void bStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 256, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void widthToNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, -5, 5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void heightToNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, -5, 255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toRNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, -255, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toGNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, -150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toBNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, -10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toRGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 256, 150, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toGGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 256, 10,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void toBGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 256,
            0, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void tStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 256,
            -1, 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void tEndNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 256,
            0, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void tEndLessThanTStart() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 256,
            15, 10);
  }
}