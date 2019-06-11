package cs3500.animator.model.shape;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the AShape class.
 */
public class AShapeTest {
  private IShape frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10.0, 10.0, 5.0, 5.0,
          255, 150, 10);

  @Test(expected = IllegalArgumentException.class)
  public void widthNegative() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, -1, 5, 255, 150, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void heightNegative() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, -1, 255, 150, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void rNegative() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, -1, 150, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void gNegative() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, 255, -1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bNegative() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, 255, 150, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void rGreaterThan255() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, 256, 150, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void gGreaterThan255() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, 255, 256, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void bGreaterThan255() {
    new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5, 255, 150, 256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nameEmptyString() {
    new AShape("", ShapeType.RECTANGLE, 10, 10, 5, 5, 255, 150, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nameNull() {
    new AShape(null, ShapeType.RECTANGLE, 10, 10, 5, 5, 255, 150, 10);
  }

  @Test
  public void getShapeIDTest() {
    assertEquals("Fred", frectangle.getShapeID());
  }

  @Test
  public void getShapeTypeTest() {
    assertEquals(ShapeType.RECTANGLE, frectangle.getShapeType());
  }

  @Test
  public void getXPosTest() {
    assertEquals(10.0, frectangle.getXPos(), .0);
  }

  @Test
  public void getYPosTest() {
    assertEquals(10.0, frectangle.getYPos(), .0);
  }

  @Test
  public void getWidthTest() {
    assertEquals(5.0, frectangle.getWidth(), .0);
  }

  @Test
  public void getHeightTest() {
    assertEquals(5.0, frectangle.getHeight(), .0);
  }

  @Test
  public void getRedTest() {
    assertEquals(255, frectangle.getRed());
  }

  @Test
  public void getGreenTest() {
    assertEquals(150, frectangle.getGreen());
  }

  @Test
  public void getBlueTest() {
    assertEquals(10, frectangle.getBlue());
  }

  @Test
  public void getShapeTypeAsStringTest() {
    assertEquals("rectangle", frectangle.getShapeTypeAsString());
  }

  @Test
  public void setXTest() {
    frectangle.setX(15.0);
    assertEquals(15.0, frectangle.getXPos(), .0);
  }

  @Test
  public void setYTest() {
    frectangle.setY(15.0);
    assertEquals(15.0, frectangle.getYPos(), .0);
  }

  @Test
  public void setWTest() {
    frectangle.setW(15.0);
    assertEquals(15.0, frectangle.getWidth(), .0);
  }

  @Test
  public void setHTest() {
    frectangle.setH(15.0);
    assertEquals(15.0, frectangle.getHeight(), .0);
  }

  @Test
  public void setRTest() {
    frectangle.setR(66);
    assertEquals(66, frectangle.getRed());
  }

  @Test
  public void setGTest() {
    frectangle.setG(66);
    assertEquals(66, frectangle.getGreen());
  }

  @Test
  public void setBTest() {
    frectangle.setB(66);
    assertEquals(66, frectangle.getBlue());
  }


}