package animator.model.shape;

/**
 * Interface to represent a general 2D shape.
 */
public interface IShape extends ReadOnlyIShape {
  /**
   * Used to set a new value for the x
   */
  void setX(double x);

  /**
   * Used to set a new value for the y
   */
  void setY(double y);

  /**
   * Used to set a new value for the width
   */
  void setW(double w);

  /**
   * Used to set a new value for the height
   */
  void setH(double h);

  /**
   * Used to set a new value for the red value
   */
  void setR(int r);

  /**
   * Used to set a new value for the green value
   */
  void setG(int g);

  /**
   * Used to set a new value for the blue value
   */
  void setB(int b);
}
