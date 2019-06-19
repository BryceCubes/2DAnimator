package cs3500.animator.model.keyframe;

/**
 * Interface used to keep the user from having access to any of the setter methods for an IKeyFrame
 * and to keep them from getting access to the getter for the shape as it returns an IShape.
 */
public interface ReadOnlyIKeyFrame {
  /**
   * Method used to get the x value of the given keyframe.
   *
   * @return the x value of the given keyframe
   */
  double getX();

  /**
   * Method used to get the y value of the given keyframe.
   *
   * @return the y value of the given keyframe
   */
  double getY();

  /**
   * Method used to get the width value of the given keyframe.
   *
   * @return the width value of the given keyframe
   */
  double getW();

  /**
   * Method used to get the height value of the given keyframe.
   *
   * @return the height value of the given keyframe
   */
  double getH();

  /**
   * Method used to get the red value of the given keyframe.
   *
   * @return the red value of the given keyframe
   */
  int getR();

  /**
   * Method used to get the green value of the given keyframe.
   *
   * @return the green value of the given keyframe
   */
  int getG();

  /**
   * Method used to get the blue value of the given keyframe.
   *
   * @return the blue value of the given keyframe
   */
  int getB();

  /**
   * Method used to get the tick value of the given keyframe.
   *
   * @return the t value of the given keyframe
   */
  int getT();
}
