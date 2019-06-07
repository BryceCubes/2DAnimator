package animator.model.motion;

import animator.model.shape.IShape;

/**
 * Interface represents a motion of a shape.
 */
public interface IMotion {
  /**
   * Method to get the text output of the motions of the given shape.
   * @return a string representation of the motions of a shape
   */
  String getTextOutput();

  /**
   * Method used to get the start time of the given motion.
   * @return an int representing the start time of the motion
   */
  int getTStart();

  /**
   * Method used to get the end time of a given motion.
   * @return an int representing the end time of the motion
   */
  int getTEnd();

  /**
   * Method used to return a shape object associated with a given motion
   * @return an IShape associated with the given motion
   */
  IShape getShape();

  /**
   * Mutates the shape associated with the given motion at the time t given by the tick.
   */
  void interpolate(int tick);

  /**
   * Used to get the x value at the start of a motion.
   * @return the x value of the start of a motion
   */
  int getXStart();

  /**
   * Used to get the y value at the start of a motion
   * @return the y value at the start of the motion
   */
  int getYStart();

  /**
   * Used to get the x value at the end of the motion.
   * @return the x value at the start of the motion.
   */
  int getXEnd();

  /**
   * Used to get the y value at the end of the motion.
   * @return the y value at the start of the motion
   */
  int getYEnd();
}
