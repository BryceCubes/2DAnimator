package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

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
  ArrayList<ReadOnlyIShape> getShapesAtTick(int tick) throws IllegalArgumentException;

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
   * Deletes a motion with the given values
   *
   * @param shapeID the name of the shape that we are trying to add a motion to
   * @param xStart  the starting x coordinate
   * @param yStart  the starting y coordinate
   * @param wStart  the starting width
   * @param hStart  the starting height
   * @param rStart  the starting red value
   * @param gStart  the starting green value
   * @param bStart  the starting blue value
   * @param toX     the destination x coordinate
   * @param toY     the destination y coordinate
   * @param toW     the destination width
   * @param toH     the destination height
   * @param toR     the destination red value
   * @param toG     the destination green value
   * @param toB     the destination blue value
   * @param tStart  the starting tick
   * @param tEnd    the destination tick
   * @throws IllegalArgumentException when motion already exists for given time, if motion is *
   *                                  inconsistent, or disjoint
   */
  void deleteMotion(String shapeID, int xStart, int yStart, int wStart, int hStart, int rStart,
                    int gStart, int bStart, int toX, int toY, int toW, int toH, int toR, int toG,
                    int toB, int tStart, int tEnd) throws IllegalArgumentException;


  /**
   * Generates the list of keys used to iterate through the hashmap of motions.
   *
   * @return the list of keys of all shapes in the model
   */
  ArrayList<ReadOnlyIShape> getShapes();

  /**
   * Gets the x value of the top left of the canvas frame.
   *
   * @return The x value of the top left of the canvas frame
   */
  int getCanvasX();

  /**
   * Gets the y value of the top left of the canvas frame.
   *
   * @return The y value of the top left of the canvas frame
   */
  int getCanvasY();

  /**
   * Gets the width value of the canvas.
   *
   * @return the width value of the canvas
   */
  int getCanvasW();

  /**
   * Gets the height of the canvas.
   *
   * @return the height value of the canvas
   */
  int getCanvasH();

  /**
   * Adds motion to the animation for an already existing shape.
   *
   * @param shapeID the name of the shape that we are trying to add a motion to
   * @param xStart  the starting x coordinate
   * @param yStart  the starting y coordinate
   * @param wStart  the starting width
   * @param hStart  the starting height
   * @param rStart  the starting red value
   * @param gStart  the starting green value
   * @param bStart  the starting blue value
   * @param toX     the destination x coordinate
   * @param toY     the destination y coordinate
   * @param toW     the destination width
   * @param toH     the destination height
   * @param toR     the destination red value
   * @param toG     the destination green value
   * @param toB     the destination blue value
   * @param tStart  the starting tick
   * @param tEnd    the destination tick
   * @throws IllegalArgumentException when motion already exists for given time, if motion is *
   *                                  inconsistent, or disjoint
   */
  void declareMotion(String shapeID, int xStart, int yStart, int wStart, int hStart, int rStart,
                     int gStart, int bStart, int toX, int toY, int toW, int toH, int toR, int toG,
                     int toB, int tStart, int tEnd) throws IllegalArgumentException;
}
