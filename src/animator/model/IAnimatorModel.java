package animator.model;

import java.util.ArrayList;

import animator.model.shape.IShape;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel {

  /**
   * Finds a specific shape based on the name given.
   * @param shapeID
   * @return An IShape matching given string
   */
  public IShape findShape(String shapeID) throws IllegalArgumentException;

  /**
   * Method to return all the shapes the view will need to display.
   * @return A list of shapes at time t
   */
  public ArrayList<IShape> returnShapesAtTick(int tick) throws IllegalArgumentException;

  /**
   * Provides a text representation of the motions.
   * @return A string representing the motions
   */
  public String textViewMotions();
}
