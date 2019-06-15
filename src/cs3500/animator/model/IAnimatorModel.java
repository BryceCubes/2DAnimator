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
   * Sets the sorted list of shapes to the given ArrayList.
   * @param shapes the list of shapes to set for the model
   */
  void setShapes(ArrayList<ReadOnlyIShape> shapes);


  /**
   * Sets the sorted HashMap of IMotions to the given HashMap.
   * @param moveList A HashMap of ArrayLists of IMotions sorted by IShape keys
   */
  void setMoveList(HashMap<ReadOnlyIShape, ArrayList<IMotion>> moveList);

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
}
