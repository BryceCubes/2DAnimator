package cs3500.animator.model;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.shape.IShape;

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
   * @throws IllegalArgumentException when a shape with given shapeid doesn't exist
   */
  IShape findShape(String shapeID) throws IllegalArgumentException;


  /**
   * Method to return all the shapes the view will need to display.
   * @param tick given tick to return shapes at time t
   * @return an Arraylist of shapes that exist at time t
   * @throws IllegalArgumentException if given tick is negative
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

  /**
   * Adds a new shape to the hashmap without any motions attached.
   * @param shape the given shape to be added to the hashmap
   * @throws IllegalArgumentException if shape already exists
   */
  void addShape(IShape shape) throws IllegalArgumentException;

  /**
   * Adds motion to the animation for an already existing shape.
   * @param motion motion to be added to a shapes arraylist of motions
   * @throws IllegalArgumentException when motion already exists for given time, if motion is
   * inconsistent, or disjoint
   */
  void addMotion(IMotion motion) throws IllegalArgumentException;

  /**
   * Deletes a given motion for a given shape.
   * @param motion motion to be deleted from a given shape
   * @throws IllegalArgumentException when given shape doesnt exist or given motion for shape
   * does not exist
   */
  void deleteMotion(IMotion motion) throws IllegalArgumentException;
}

