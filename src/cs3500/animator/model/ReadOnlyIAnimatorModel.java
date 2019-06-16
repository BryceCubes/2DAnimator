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
  ArrayList<ReadOnlyIShape> returnShapes();

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
   * Lets a user add a motion for a shape with the attributes as follows as long as it keeps the
   * motions for the shape continuous.
   * @param name name of shape to add motion to
   * @param t1 start time of motion
   * @param x1 x value at start of motion
   * @param y1 y value at start of motion
   * @param w1 width at start of motion
   * @param h1 height at start of motion
   * @param r1 red value at start of motion
   * @param g1 green value at start of motion
   * @param b1 blue value at start of motion
   * @param t2 time value at end of motion
   * @param x2 x value at end of motion
   * @param y2 y value at end of motion
   * @param w2 width value at end of motion
   * @param h2 height value at end of motion
   * @param r2 red value at end of motion
   * @param g2 green value at end of motion
   * @param b2 blue value at end of motion
   */
  void declareMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                     int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2);
}
