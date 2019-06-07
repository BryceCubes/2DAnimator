package animator.model;

import animator.model.shape.IShape;

import java.util.ArrayList;

public interface ReadOnlyIAnimatorModel {

  /**
   * Finds a specific shape based on the name given.
   * @param  shapeID Enum that represents what type of shape it is
   * @return An IShape matching given string
   */
  IShape findShape(String shapeID) throws IllegalArgumentException;

  /**
   * Method to return all the shapes the view will need to display.
   * @return A list of shapes at time t
   */
  ArrayList<IShape> returnShapesAtTick(int tick) throws IllegalArgumentException;

  /**
   * Provides a text representation of the motions.
   * @return A string representing the motions
   */
  String textViewMotions();
}

