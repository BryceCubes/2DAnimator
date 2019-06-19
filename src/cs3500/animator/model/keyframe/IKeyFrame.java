package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IShape;

/**
 * Class used to represent a keyframe by only representing values from the ends of the motions and
 * the start of the first motion along with the shape involved.
 */
public interface IKeyFrame extends ReadOnlyIKeyFrame {
  /**
   * Method used to set the x value of the keyframe to the given x.
   *
   * @param x the given value to set the x value of the key frame to
   */
  void setX(double x);

  /**
   * Method used to set the y value of the keyframe to the given y.
   *
   * @param y the given value to set the y value of the key frame to
   */
  void setY(double y);

  /**
   * Method used to set the w value of the keyframe to the given w.
   *
   * @param w the given value to set the w value of the key frame to
   * @throws IllegalArgumentException when w is negative
   */
  void setW(double w) throws IllegalArgumentException;

  /**
   * Method used to set the h value of the keyframe to the given h.
   *
   * @param h the given value to set the h value of the key frame to
   * @throws IllegalArgumentException when h is negative
   */
  void setH(double h) throws IllegalArgumentException;

  /**
   * Method used to set the r value of the keyframe to the given r.
   *
   * @param r the given value to set the red value of the key frame to
   * @throws IllegalArgumentException when r is negative or greater than 255
   */
  void setR(int r) throws IllegalArgumentException;

  /**
   * Method used to set the g value of the keyframe to the given g.
   *
   * @param g the given value to set the green value of the key frame to
   * @throws IllegalArgumentException when g is negative or greater than 255
   */
  void setG(int g) throws IllegalArgumentException;

  /**
   * Method used to set the b value of the keyframe to the given b.
   *
   * @param b the given value to set the blue value of the key frame to
   * @throws IllegalArgumentException when b is negative or greater than 255
   */
  void setB(int b) throws IllegalArgumentException;

  /**
   * Method used to set the t value of the keyframe to the given tick.
   *
   * @param t the given value to set the t value of the key frame to
   * @throws IllegalArgumentException when the tick is negative
   */
  void setT(int t) throws IllegalArgumentException;

  /**
   * Method used to set the shape value of the keyframe to the given shape.
   *
   * @param shape the given value to set the shape value of the key frame to
   * @throws IllegalArgumentException when the shape is null
   */
  void setShape(IShape shape) throws IllegalArgumentException;

  /**
   * Method used to get the shape associated with the given key frame. It has been placed here, and
   * not in the read only interface because it returns an IShape rather than a read only version.
   *
   * @return an IShape from the given keyframe
   */
  IShape getShape();
}
