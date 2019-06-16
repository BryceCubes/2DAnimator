package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ReadOnlyIShape;

public interface ReadOnlyIAnimatorModel {

  /**
   * Used to find a given shape based on its shapeid.
   *
   * @param shapeID name of given shape
   * @return a shape with same name
   */
  ReadOnlyIShape findShape(String shapeID);

  /**
   * Gives the hashmap of motions.
   *
   * @return a hashmap of motiosn
   */
  HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> returnMotions();


  /**
   * Method to return all the shapes the view will need to display.
   *
   * @param tick given tick to return shapes at time t
   * @return an Arraylist of shapes that exist at time t
   * @throws IllegalArgumentException if given tick is negative
   */
  ArrayList<ReadOnlyIShape> returnShapesAtTick(int tick) throws IllegalArgumentException;

  /**
   * Provides a text representation of the motions.
   *
   * @return A string representing the motions
   */
  String textViewMotions();

  /**
   * Adds a new shape to the hashmap without any motions attached.
   *
   * @param shape the given shape to be added to the hashmap
   * @throws IllegalArgumentException if shape already exists
   */
  void addShape(IShape shape) throws IllegalArgumentException;

  /**
   * Deletes a given shape from the hashmap thus wiping out all of the shapes motions and the shape
   * itself.
   *
   * @param shapeID name of the given shape to be deleted
   * @throws IllegalArgumentException when a shape with given shapeid doesn't exist
   */
  void deleteShape(String shapeID) throws IllegalArgumentException;

  /**
   * Deletes a given motion for a given shape.
   *
   * @param motion motion to be deleted from a given shape
   * @throws IllegalArgumentException when given shape doesnt exist or given motion for shape
   *                                  does not exist
   */
  void deleteMotion(IMotion motion) throws IllegalArgumentException;

  /**
   * Generates the list of keys used to iterate through the hashmap of motions.
   *
   * @return the list of keys of all shapes in the model
   */
  ArrayList<ReadOnlyIShape> returnShapes();

  /**
   * Gets the x value of the top left of the canvas frame.
   * @return The x value of the top left of the canvas frame
   */
  int getCanvasX();

  /**
   * Gets the y value of the top left of the canvas frame.
   * @return The y value of the top left of the canvas frame
   */
  int getCanvasY();

  /**
   * Gets the width value of the canvas.
   * @return the width value of the canvas
   */
  int getCanvasW();

  /**
   * Gets the height of the canvas.
   * @return the height value of the canvas
   */
  int getCanvasH();

  /**
   * Lets a user add a motion for a shape with the attributes as follows as long as it keeps the
   * motions for the shape continuous.
   * @param name
   * @param t1
   * @param x1
   * @param y1
   * @param w1
   * @param h1
   * @param r1
   * @param g1
   * @param b1
   * @param t2
   * @param x2
   * @param y2
   * @param w2
   * @param h2
   * @param r2
   * @param g2
   * @param b2
   */
  void declareMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                     int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);
}
