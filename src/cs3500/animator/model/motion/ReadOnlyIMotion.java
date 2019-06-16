package cs3500.animator.model.motion;

/**
 * IMotion interface only available publicly.
 */
public interface ReadOnlyIMotion {
  /**
   * Method to get the text output of the motions of the given shape.
   *
   * @return a string representation of the motions of a shape
   */
  String getTextOutput();

  /**
   * Method used to get the start time of the given motion. We put a standard 4 spaces in between
   * the start values and stop values because implementing the values to line up perfectly seemed
   * a bit out of scope. It was confusing seeing the different space patterns on the example text
   * output
   *
   * @return an int representing the start time of the motion
   */
  int getTStart();

  /**
   * Method used to get the end time of a given motion.
   *
   * @return an int representing the end time of the motion
   */
  int getTEnd();

  /**
   * Used to get the x value at the start of a motion.
   *
   * @return the x value of the start of a motion
   */
  int getXStart();

  /**
   * Used to get the y value at the start of a motion.
   *
   * @return the y value at the start of the motion
   */
  int getYStart();

  /**
   * Used to get the y value at the start of a motion.
   *
   * @return the y value at the start of the motion
   */
  int getWStart();

  /**
   * Used to get the height value at the start of a motion.
   *
   * @return the height value at the start of the motion
   */
  int getHStart();

  /**
   * Used to get the red value at the start of a motion.
   *
   * @return the red value at the start of the motion
   */
  int getRStart();

  /**
   * Used to get the green value at the start of a motion.
   *
   * @return the green value at the start of the motion
   */
  int getGStart();

  /**
   * Used to get the blue value at the start of a motion.
   *
   * @return the blue value at the start of the motion
   */
  int getBStart();

  /**
   * Used to get the x value at the end of the motion.
   *
   * @return the x value at the end of the motion.
   */
  int getXEnd();

  /**
   * Used to get the y value at the end of the motion.
   *
   * @return the y value at the end of the motion
   */
  int getYEnd();

  /**
   * Used to get the width value at the end of the motion.
   *
   * @return the width value at the end of the motion
   */
  int getWEnd();

  /**
   * Used to get the height value at the end of the motion.
   *
   * @return the height value at the end of the motion
   */
  int getHEnd();

  /**
   * Used to get the red value at the end of the motion.
   *
   * @return the red value at the end of the motion
   */
  int getREnd();

  /**
   * Used to get the green value at the end of the motion.
   *
   * @return the green value at the end of the motion
   */
  int getGEnd();

  /**
   * Used to get the blue value at the end of the motion.
   *
   * @return the blue value at the end of the motion
   */
  int getBEnd();

  /**
   * Gets the shape name of the given motion.
   *
   * @return the name of the shape the motion is acting upon
   */
  String getShapeName();
}
