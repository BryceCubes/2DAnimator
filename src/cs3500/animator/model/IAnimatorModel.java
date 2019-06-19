package cs3500.animator.model;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel extends ReadOnlyIAnimatorModel {
  /**
   * Sets the canvasX to the given value.
   *
   * @param canvasX the x value to place the canvas
   * @throws IllegalArgumentException when canvas x is negative
   */
  void setCanvasX(int canvasX) throws IllegalArgumentException;

  /**
   * Sets the canvasY to the given value.
   *
   * @param canvasY the y value to place the canvas
   * @throws IllegalArgumentException when canvas y is negative
   */
  void setCanvasY(int canvasY) throws IllegalArgumentException;

  /**
   * Sets the canvasW to the given value.
   *
   * @param canvasW the width value to place the canvas
   * @throws IllegalArgumentException when canvas w is less than 1
   */
  void setCanvasW(int canvasW) throws IllegalArgumentException;

  /**
   * Sets the canvasH to the given value.
   *
   * @param canvasH the width value to place the canvas
   * @throws IllegalArgumentException when canvas h is less than 1
   */
  void setCanvasH(int canvasH) throws IllegalArgumentException;

  // Deleted builderIMotion from here because it could be private within the class as it was only
  // going to be used for the builder.
}
