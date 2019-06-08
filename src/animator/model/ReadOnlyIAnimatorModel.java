package animator.model;

import animator.model.shape.IShape;

import java.util.ArrayList;

/**
 * Interface used to prevent public access to setters that are not yet made.
 */
public interface ReadOnlyIAnimatorModel {

  /**
   * Finds a specific shape based on the name given.
   *
   * @param shapeID Enum that represents what type of shape it is
   * @return An IShape matching given string
   */
  IShape findShape(String shapeID) throws IllegalArgumentException;

  /**
   * Method to return all the shapes the view will need to display.
   *
   * @return A list of shapes at time t
   */
  ArrayList<IShape> returnShapesAtTick(int tick) throws IllegalArgumentException;
  //Cannot test this method yet as was not in the scope of the assignment to fully implement
  //interpolate. So, we will be testing this in the next assignment, but we have not tests for this
  //now.

  /**
   * Provides a text representation of the motions.
   *
   * @return A string representing the motions
   */
  String textViewMotions();
}

