package animator_model.motion;

import animator_model.shape.IShape;

/**
 * Class represents a motion of a shape in a 2D space
 */
public class ShapeMotion implements IMotion {
  private IShape shape;
  private int xStart, yStart;
  private int wStart, hStart;
  private int rStart, gStart, bStart;
  private int toX, toY;
  private int toW, toH;
  private int toR, toG, toB;
  private int tStart, tEnd;

  public ShapeMotion(IShape shape, int xStart, int yStart, int wStart, int hStart, int rStart,
                     int gStart, int bStart, int toX, int toY, int toW, int toH, int toR, int toG,
                     int toB, int tStart, int tEnd) {
    this.shape = shape;
    this.xStart = xStart;
    this.yStart = yStart;
    this.wStart = wStart;
    this.hStart = hStart;
    this.rStart = rStart;
    this.gStart = gStart;
    this.bStart = bStart;
    this.toX = toX;
    this.toY = toY;
    this.toW = toW;
    this.toH = toH;
    this.toR = toR;
    this.toG = toG;
    this.toB = toB;
    this.tStart = tStart;
    this.tEnd = tEnd;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }

  public int getxStart() {
    return this.xStart;
  }

  public int getyStart() {
    return this.yStart;
  }

  public int getwStart() {
    return this.wStart;
  }

  public int gethStart() {
    return this.hStart;
  }

  public int getrStart() {
    return this.rStart;
  }

  public int getgStart() {
    return this.gStart;
  }

  public int getbStart() {
    return this.bStart;
  }

  public int getToX() {
    return this.toX;
  }

  public int getToY() {
    return  this.toY;
  }

  public int getToW() {
    return this.toW;
  }

  public int getToH() {
    return this.toH;
  }

  public int getToR() {
    return this.toR;
  }

  public int getToG() {
    return this.toG;
  }

  public int getToB() {
    return this.toB;
  }

  public int getTStart() {
    return this.tStart;
  }

  public int getTEnd() {
    return this.tEnd;
  }

  @Override
  public String getTextOutput() {
    StringBuilder textView = new StringBuilder();

      textView = textView.append("shape " + shape.getShapeID() +"motion " + shape.getShapeID() + " "
              + this.tStart + " " + this.xStart + " " + this.yStart
              + this.wStart + " ");

    return textView.toString();
  }

  @Override
  public void interpolate() {
  }
}
