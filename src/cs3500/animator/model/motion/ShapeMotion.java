package cs3500.animator.model.motion;

import cs3500.animator.model.shape.IShape;

/**
 * Class represents a motion of a shape in a 2D space with a position, color, and dimensions.
 */
public class ShapeMotion implements IMotion {
  private IShape shape;
  private int xStart;
  private int yStart;
  private int wStart;
  private int hStart;
  private int rStart;
  private int gStart;
  private int bStart;
  private int toX;
  private int toY;
  private int toW;
  private int toH;
  private int toR;
  private int toG;
  private int toB;
  private int tStart;
  private int tEnd;
  //Changed the x, y, w, and h values to doubles so that movement could be smooth.

  /**
   * Constructor for a shape motion. Invariants are both time values cannot be less than 0 because
   * time can never be negative, and end time cannot be less than start time because time always
   * needs to be moving forward. All width and height values cannot be less than 0. All rgb values
   * cannot be less than 0 or greater than 255 because rgb hex values can only have that range.
   *
   * @param shape  given shape to be moved
   * @param xStart starting x value at the center of the shape
   * @param yStart starting y value at the center of the shape
   * @param wStart starting width of the shape
   * @param hStart starting height of the shape
   * @param rStart starting red value of the shape
   * @param gStart starting green value of the shape
   * @param bStart starting blue value of the shape
   * @param toX    center x value at the end of the motion
   * @param toY    center y value of the shape at the end of the motion
   * @param toW    width of the shape at the end of the motion
   * @param toH    height of the shape at the end of the motion
   * @param toR    red value of the shape at the end of the motion
   * @param toG    green value of the shape at the end of the motion
   * @param toB    blue value of the shape at the end of the motion
   * @param tStart start time of the shape's motion
   * @param tEnd   end value of the shape's motion
   */
  public ShapeMotion(IShape shape, int xStart, int yStart, int wStart, int hStart, int rStart,
                     int gStart, int bStart, int toX, int toY, int toW, int toH, int toR, int toG,
                     int toB, int tStart, int tEnd) {
    if (shape == null || tStart < 0 || tEnd < 0 || tEnd < tStart || wStart < 0 || hStart < 0
            || rStart < 0 || rStart > 255 || gStart < 0 || gStart > 255 || bStart < 0
            || bStart > 255 || toW < 0 || toH < 0 || toR < 0 || toR > 255 || toG < 0 || toG > 255
            || toB < 0 || toB > 255) {
      throw new IllegalArgumentException("Shape cannot be null. Time start, time end cannot be less"
              + " than 0, time end cannot be less than time start. W start, w end, h start or h end"
              + " cannot be less than 0. rgb values cannot be less than 0 or greater than  255.");
    }
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

  @Override
  public int getTStart() {
    return this.tStart;
  }

  @Override
  public int getTEnd() {
    return this.tEnd;
  }

  @Override
  public int getXStart() {
    return this.xStart;
  }

  @Override
  public int getYStart() {
    return this.yStart;
  }

  @Override
  public int getWStart() {
    return this.wStart;
  }

  @Override
  public int getHStart() {
    return this.hStart;
  }

  @Override
  public int getRStart() {
    return this.rStart;
  }

  @Override
  public int getGStart() {
    return this.gStart;
  }

  @Override
  public int getBStart() {
    return this.bStart;
  }

  @Override
  public int getXEnd() {
    return this.toX;
  }

  @Override
  public int getYEnd() {
    return this.toY;
  }

  @Override
  public int getWEnd() {
    return this.toW;
  }

  @Override
  public int getHEnd() {
    return this.toH;
  }

  @Override
  public int getREnd() {
    return this.toR;
  }

  @Override
  public int getGEnd() {
    return this.toG;
  }

  @Override
  public int getBEnd() {
    return this.toB;
  }

  @Override
  public String getShapeName() {
    return this.shape.getShapeID();
  }

  @Override
  public String getTextOutput() {
    StringBuilder textView = new StringBuilder();

    textView.append("motion ").append(shape.getShapeID()).append(" ").append(this.tStart)
            .append(" ").append(this.xStart).append(" ").append(this.yStart).append(" ")
            .append(this.wStart).append(" ").append(this.hStart).append(" ").append(this.rStart)
            .append(" ").append(this.gStart).append(" ").append(this.bStart).append("    ")
            .append(this.tEnd).append(" ").append(this.toX).append(" ").append(this.toY).append(" ")
            .append(this.toW).append(" ").append(this.toH).append(" ").append(this.toR).append(" ")
            .append(this.toG).append(" ").append(this.toB).append("\n");

    return textView.toString();
  }

  // Deleted the interpolate method from here as it was no longer used from the motions because of
  // the addition of the keyframes. The interpolate function was basically moved to the model.
}
