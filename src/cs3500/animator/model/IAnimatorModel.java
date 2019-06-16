package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel extends ReadOnlyIAnimatorModel{
  /**
   * Sets the canvasX to the given value.
   * @param canvasX the x value to place the canvas
   */
  void setCanvasX(int canvasX);

  /**
   * Sets the canvasY to the given value.
   * @param canvasY the y value to place the canvas
   */
  void setCanvasY(int canvasY);

  /**
   * Sets the canvasW to the given value.
   * @param canvasW the width value to place the canvas
   */
  void setCanvasW(int canvasW);

  /**
   * Sets the canvasH to the given value.
   * @param canvasH the width value to place the canvas
   */
  void setCanvasH(int canvasH);

  /**
   * Sets the speed that the animation will run at.
   * @param speed the speed that the animator will be set to run at
   */
  void setSpeed(int speed);

  /**
   * Adds motion to the animation for an already existing shape used for the builder.
   *
   * @param motion motion to be added to a shapes arraylist of motions
   * @throws IllegalArgumentException when motion already exists for given time, if motion is
   *                                  inconsistent, or disjoint
   */
  void builderMotion(IMotion motion) throws IllegalArgumentException;
}
