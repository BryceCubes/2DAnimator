package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IShape;

/**
 * Class used to represent a keyframe along with all the values associated with it. Each keyframe
 * represents a shape at a point in time and what changes should be associated with it. It is a
 * change from the motions in that everything important in a motion happens at the very end, so the
 * keyframe represents the state of a shape at the very start of the first motion, and represents
 * all the resulting states given by the ends of motions.
 */
public class KeyFrame implements IKeyFrame {
  private IShape shape;
  private double x;
  private double y;
  private double w;
  private double h;
  private int r;
  private int g;
  private int b;
  private int t;

  /**
   * Builder class used to set all the values of the keyframe to the correct state given the input.
   * We chose to represent the keyframes with all starting values if only the shape and the tick
   * were given because if a keyframe was added to the end or start of the animation, we thought
   * it would be best to just give it values such that the shape would be the same as those at the
   * end and the start. This allows the person to see the changes they made, and allow them to edit
   * it once it has been made. If there were no keyframes in the first place, then all the values,
   * except for the tick, would be set to 0 such that the shape wouldn't even show up unless they
   * edited it to do so.
   */
  public static class Builder {
    private IShape shape = null;
    private double x = 0;
    private double y = 0;
    private double w = 0;
    private double h = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private Integer t = null;

    /**
     * Method used to build the new keyframe by setting all the values, and returning a new keyframe
     * ensuring that the shape and the tick are not null.
     *
     * @return a new KeyFrame
     */
    public KeyFrame build() {
      KeyFrame keyFrame = new KeyFrame();
      keyFrame.setX(this.x);
      keyFrame.setY(this.y);
      keyFrame.setW(this.w);
      keyFrame.setH(this.h);
      keyFrame.setR(this.r);
      keyFrame.setG(this.g);
      keyFrame.setB(this.b);
      keyFrame.setT(this.t);
      keyFrame.setShape(this.shape);
      if (this.shape == null || this.t == null) {
        throw new IllegalArgumentException("Shape and tick must be set.");
      }
      return keyFrame;
    }

    /**
     * Method used to set the tick for a new keyframe, also ensuring that it is not negative.
     *
     * @param tick the given tick for the new keyframe
     * @return a builder to be continued to be build on for a keyframe
     * @throws IllegalArgumentException when tick is negative
     */
    public Builder declareT(int tick) throws IllegalArgumentException {
      if (tick < 0) {
        throw new IllegalArgumentException("Tick cannot be negative.");
      }
      this.t = tick;
      return this;
    }

    /**
     * Method used to set the x value for the new keyframe.
     *
     * @param x the x value for the keyframe to be set to
     * @return a builder to be continued to be built on for a keyframe
     */
    public Builder declareX(double x) {
      this.x = x;
      return this;
    }

    /**
     * Method used to set the y value for the new keyframe.
     *
     * @param y the y value for the keyframe to be set to
     * @return a builder to be continued to be built on for a keyframe
     */
    public Builder declareY(double y) {
      this.y = y;
      return this;
    }

    /**
     * Method used to set the width value for a new keyframe.
     *
     * @param w the given width for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when width is negative
     */
    public Builder declareW(double w) throws IllegalArgumentException {
      if (w < 0) {
        throw new IllegalArgumentException("Width cannot be less than 0.");
      }
      this.w = w;
      return this;
    }

    /**
     * Method used to set the height for a new keyframe.
     *
     * @param h the given height for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when height is negative
     */
    public Builder declareH(double h) throws IllegalArgumentException {
      if (h < 0) {
        throw new IllegalArgumentException("Height cannot be less than 0.");
      }
      this.h = h;
      return this;
    }

    /**
     * Method used to set the red value for a new keyframe.
     *
     * @param r the given red value for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when r is negative or greater than 255
     */
    public Builder declareR(int r) throws IllegalArgumentException {
      if (r < 0 || r > 255) {
        throw new IllegalArgumentException("Red cannot be less than 0 or greater than 255.");
      }
      this.r = r;
      return this;
    }

    /**
     * Method used to set the green value for a new keyframe.
     *
     * @param g the given green value for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when g is negative or greater than 255
     */
    public Builder declareG(int g) throws IllegalArgumentException {
      if (g < 0 || g > 255) {
        throw new IllegalArgumentException("Green cannot be less than 0 or greater than 255.");
      }
      this.g = g;
      return this;
    }

    /**
     * Method used to set the blue value for a new keyframe.
     *
     * @param b the given blue value for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when b is negative or greater than 255
     */
    public Builder declareB(int b) throws IllegalArgumentException {
      if (b < 0 || b > 255) {
        throw new IllegalArgumentException("Blue cannot be less than 0 or greater than 255.");
      }
      this.b = b;
      return this;
    }

    /**
     * Method used to set the shape for a new keyframe.
     *
     * @param shape the given shape for the new keyframe
     * @return a builder to be continued to be built on for a keyframe
     * @throws IllegalArgumentException when shape is null
     */
    public Builder declareShape(IShape shape) throws IllegalArgumentException {
      if (shape == null) {
        throw new IllegalArgumentException("Shape cannot be null.");
      }
      this.shape = shape;
      return this;
    }
  }

  @Override
  public void setX(double x) {
    this.x = x;
  }

  @Override
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public void setW(double w) throws IllegalArgumentException {
    if (w < 1) {
      throw new IllegalArgumentException("Width cannot be less than 1.");
    }
    this.w = w;
  }

  @Override
  public void setH(double h) throws IllegalArgumentException {
    if (h < 1) {
      throw new IllegalArgumentException("Height cannot be less than 1.");
    }
    this.h = h;
  }

  @Override
  public void setR(int r) throws IllegalArgumentException {
    if (r < 0 || r > 255) {
      throw new IllegalArgumentException("Red value cannot be less than 0 or greater than 255");
    }
    this.r = r;
  }

  @Override
  public void setG(int g) throws IllegalArgumentException {
    if (g < 0 || g > 255) {
      throw new IllegalArgumentException("Green value cannot be less than 0 or greater than 255");
    }
    this.g = g;
  }

  @Override
  public void setB(int b) throws IllegalArgumentException {
    if (b < 0 || b > 255) {
      throw new IllegalArgumentException("Blue value cannot be less than 0 or greater than 255");
    }
    this.b = b;
  }

  @Override
  public void setT(int t) throws IllegalArgumentException {
    if (t < 0) {
      throw new IllegalArgumentException("Tick cannot be negative.");
    }
    this.t = t;
  }

  @Override
  public void setShape(IShape shape) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    this.shape = shape;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getW() {
    return this.w;
  }

  @Override
  public double getH() {
    return this.h;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }

  @Override
  public int getT() {
    return this.t;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }
}
