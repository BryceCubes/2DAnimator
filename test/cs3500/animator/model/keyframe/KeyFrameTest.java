package cs3500.animator.model.keyframe;

import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.shape.AShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Testing class used to ensure that the builder works correctly for a keyframe, and that the
 * getters and setters function properly and throw the correct errors.
 */
public class KeyFrameTest {
  private IShape shape;
  private IKeyFrame keyFrame;

  @Before
  public void setTest() {
    shape = new AShape("asdf", ShapeType.ELLIPSE);
    keyFrame = new KeyFrame.Builder().declareShape(shape).declareT(5).declareX(5).declareY(5)
            .declareW(5).declareH(5).declareR(5).declareG(5).declareB(5).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void shapeAndTickNotSet() {
    new KeyFrame.Builder().build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void shapeNotSet() {
    new KeyFrame.Builder().declareT(5).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void tickNotSet() {
    new KeyFrame.Builder().declareShape(shape).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void shapeIsNull() {
    new KeyFrame.Builder().declareShape(null).declareT(5).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void tickIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void widthIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareW(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void heightIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareH(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void redIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareR(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void greenIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareG(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void blueIsNegative() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareB(-1).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void redIsGreaterThan255() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareR(256).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void greenIsGreaternThan255() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareG(256).build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void blueIsGreaterThan255() {
    new KeyFrame.Builder().declareShape(shape).declareT(5).declareB(256).build();
  }

  @Test
  public void getX() {
    setTest();
    assertEquals(5.0, this.keyFrame.getX(), .0);
  }

  @Test
  public void getY() {
    setTest();
    assertEquals(5.0, this.keyFrame.getY(), .0);
  }

  @Test
  public void getW() {
    setTest();
    assertEquals(5.0, this.keyFrame.getW(), .0);
  }

  @Test
  public void getH() {
    setTest();
    assertEquals(5.0, this.keyFrame.getH(), .0);
  }

  @Test
  public void getR() {
    setTest();
    assertEquals(5, this.keyFrame.getR());
  }

  @Test
  public void getG() {
    setTest();
    assertEquals(5, this.keyFrame.getG());
  }

  @Test
  public void getB() {
    setTest();
    assertEquals(5, this.keyFrame.getB());
  }

  @Test
  public void getT() {
    setTest();
    assertEquals(5, this.keyFrame.getT());
  }

  @Test
  public void getShape() {
    setTest();
    assertEquals(shape, this.keyFrame.getShape());
  }

  @Test
  public void setX() {
    setTest();
    this.keyFrame.setX(10.0);
    assertEquals(10.0, this.keyFrame.getX(), .0);
  }

  @Test
  public void setY() {
    setTest();
    this.keyFrame.setY(10.0);
    assertEquals(10.0, this.keyFrame.getY(), .0);
  }

  @Test
  public void setW() {
    setTest();
    this.keyFrame.setW(10.0);
    assertEquals(10.0, this.keyFrame.getW(), .0);
  }

  @Test
  public void setH() {
    setTest();
    this.keyFrame.setH(10.0);
    assertEquals(10.0, this.keyFrame.getH(), .0);
  }

  @Test
  public void setR() {
    setTest();
    this.keyFrame.setR(10);
    assertEquals(10, this.keyFrame.getR());
  }

  @Test
  public void setG() {
    setTest();
    this.keyFrame.setG(10);
    assertEquals(10, this.keyFrame.getG());
  }

  @Test
  public void setB() {
    setTest();
    this.keyFrame.setB(10);
    assertEquals(10, this.keyFrame.getB());
  }

  @Test
  public void setT() {
    setTest();
    this.keyFrame.setT(10);
    assertEquals(10, this.keyFrame.getT());
  }

  @Test
  public void setShape() {
    setTest();
    IShape ellipse = new AShape("asdfg", ShapeType.ELLIPSE);
    this.keyFrame.setShape(ellipse);
    assertEquals(ellipse, this.keyFrame.getShape());
  }
}