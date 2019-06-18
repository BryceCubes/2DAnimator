package cs3500.animator.model;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel extends ReadOnlyIAnimatorModel {
  /**
   * Sets the canvasX to the given value.
   *
   * @param canvasX the x value to place the canvas
   */
  void setCanvasX(int canvasX);

  /**
   * Sets the canvasY to the given value.
   *
   * @param canvasY the y value to place the canvas
   */
  void setCanvasY(int canvasY);

  /**
   * Sets the canvasW to the given value.
   *
   * @param canvasW the width value to place the canvas
   */
  void setCanvasW(int canvasW);

  /**
   * Sets the canvasH to the given value.
   *
   * @param canvasH the width value to place the canvas
   */
  void setCanvasH(int canvasH);

  // Deleted builderIMotion from here because it could be private within the class as it was only
  // going to be used for the builder.
}
