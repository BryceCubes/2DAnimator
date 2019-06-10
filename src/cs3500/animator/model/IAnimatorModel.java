package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.shape.IShape;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel {

  /**
   * Finds a specific shape based on the name given.
   *
   * @param shapeID Enum that represents what type of shape it is
   * @return An IShape matching given string
   * @throws IllegalArgumentException when a shape with given shapeid doesn't exist
   */
  IShape findShape(String shapeID) throws IllegalArgumentException;

  /**
   * Gives the hashmap of motions.
   * @return a hashmap of motiosn
   */
  HashMap<String, ArrayList<IMotion>> returnMotions();


  /**
   * Method to return all the shapes the view will need to display.
   * @param tick given tick to return shapes at time t
   * @return an Arraylist of shapes that exist at time t
   * @throws IllegalArgumentException if given tick is negative
   */
  ArrayList<IShape> returnShapesAtTick(int tick) throws IllegalArgumentException;

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
