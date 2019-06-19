package cs3500.animator.model.shape;

/**
 * Interface to represent a general 2D shape.
 */
public interface IShape extends ReadOnlyIShape {
  /**
   * Used to set a new value for the blue value.
   *
   * @param x the value for x to be set to
   */
  void setX(double x);

  /**
   * Used to set a new value for the y value.
   *
   * @param y the value for y to be set to
   */
  void setY(double y);

  /**
   * Used to set a new value for the width value.
   *
   * @param w the value for blue to be set to
   * @throws IllegalArgumentException when the width value is negative
   */
  void setW(double w) throws IllegalArgumentException;

  /**
   * Used to set a new value for the height value.
   *
   * @param h the value for height to be set to
   * @throws IllegalArgumentException when the height value is negative
   */
  void setH(double h) throws IllegalArgumentException;

  /**
   * Used to set a new value for the red value.
   *
   * @param r the value for red to be set to
   * @throws IllegalArgumentException when the red value is negative or greater than 255
   */
  void setR(int r) throws IllegalArgumentException;

  /**
   * Used to set a new value for the green value.
   *
   * @param g the value for green to be set to
   * @throws IllegalArgumentException when the green value is negative or greater than 255
   */
  void setG(int g) throws IllegalArgumentException;

  /**
   * Used to set a new value for the blue value.
   *
   * @param b the value for blue to be set to
   * @throws IllegalArgumentException when the blue value is negative or greater than 255
   */
  void setB(int b) throws IllegalArgumentException;
}
