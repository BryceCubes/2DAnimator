package animator_model.motion;

import animator_model.shape.IShape;

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
}
