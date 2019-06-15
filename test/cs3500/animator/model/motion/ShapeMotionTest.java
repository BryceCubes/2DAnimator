package cs3500.animator.model.motion;

import org.junit.Test;

import cs3500.animator.model.shape.AShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the shapemotion class.
 */
public class ShapeMotionTest {
  private IShape frectangle = new AShape("Fred", ShapeType.RECTANGLE);
  private IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
          150, 10, 15, 10, 5, 5, 255, 150, 10,
          0, 10);

  @Test(expected = IllegalArgumentException.class)
  public void widthStartNegative() {
    new ShapeMotion(frectangle, 10, 10, -5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void heightStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, -5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void redStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, -255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void gStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            -150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, -10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void rStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 256,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void gStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            256, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bStartGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 256, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void widthToNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, -5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void heightToNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, -5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toRNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, -255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toGNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, -150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toBNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, -10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toRGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 256, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toGGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 256, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void toBGreaterThan255() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 256,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tStartNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 255,
            -1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tEndNegative() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 255,
            0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tEndLessThanTStart() {
    new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 255,
            15, 10);
  }

  @Test
  public void getTextOutputTest() {
    assertEquals("motion Fred 0 10 10 5 5 255 150 10    10 15 10 5 5 255 150 10\n",
            fredMoveRight.getTextOutput());
  }

  @Test
  public void getTStartTest() {
    assertEquals(0, fredMoveRight.getTStart());
  }

  @Test
  public void getTEndTest() {
    assertEquals(10, fredMoveRight.getTEnd());
  }

  @Test
  public void getShapeTest() {
    assertEquals(frectangle, fredMoveRight.getShape());
  }

  @Test
  public void getXStartTest() {
    assertEquals(10, fredMoveRight.getXStart());
  }

  @Test
  public void getYStartTest() {
    assertEquals(10, fredMoveRight.getYStart());
  }

  @Test
  public void getWStartTest() {
    assertEquals(5, fredMoveRight.getWStart());
  }

  @Test
  public void getHStartTest() {
    assertEquals(5, fredMoveRight.getHStart());
  }

  @Test
  public void getRStartTest() {
    assertEquals(255, fredMoveRight.getRStart());
  }

  @Test
  public void getGStartTest() {
    assertEquals(150, fredMoveRight.getGStart());
  }

  @Test
  public void getBStartTest() {
    assertEquals(10, fredMoveRight.getBStart());
  }

  @Test
  public void getXEndTest() {
    assertEquals(15, fredMoveRight.getXEnd());
  }

  @Test
  public void getYEndTest() {
    assertEquals(10, fredMoveRight.getYEnd());
  }

  @Test
  public void getWEndTest() {
    assertEquals(5, fredMoveRight.getWEnd());
  }

  @Test
  public void getHEndTest() {
    assertEquals(5, fredMoveRight.getHEnd());
  }

  @Test
  public void getREndTest() {
    assertEquals(255, fredMoveRight.getREnd());
  }

  @Test
  public void getGEndTest() {
    assertEquals(150, fredMoveRight.getGEnd());
  }

  @Test
  public void getBEndTest() {
    assertEquals(10, fredMoveRight.getBEnd());
  }
}