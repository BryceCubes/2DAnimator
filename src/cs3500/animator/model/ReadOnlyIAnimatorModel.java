package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.model.keyframe.ReadOnlyIKeyFrame;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Interface to represent an the model for an animation engine with 2D shapes and motions. This
 * hides direct setters from the view.
 */
public interface ReadOnlyIAnimatorModel {
  /**
   * Method used to add a key frame based on the given shape id and tick. If the key frame comes
   * before the first keyframe or after the last keyframe, the keyframe will mimic the state of
   * the respective keyframe. If no keyframes exist for the given shape, an empty keyframe will
   * be initialized with all values, except for tick, at 0.
   *
   * @param shapeID the given shapid for the associated shape for the keyframe to be added to
   * @param tick    the given tick for the placement of the keyframe in sequence
   * @throws IllegalArgumentException when a keyframe already exists for the given shape at the
   *                                  given tick, or a shape doesn't exist for the given name
   */
  void addKeyFrame(String shapeID, int tick)
          throws IllegalArgumentException;

  /**
   * Allows a user to edit a key frame through giving the shape id for the given shape, the tick to
   * find the specific keyframe associated with the shape to be edited, the field to be edited
   * consisting of either x, y, width, height, red, green, or blue, and the given value for the
   * field to be changed to.
   *
   * @param shapeID the given shapeid associated with the given shape
   * @param tick    the given tick with the associated keyframe
   * @param field   the given field to be edited
   * @param change  the given value for the field to be changed to
   * @throws IllegalArgumentException when shapeid or field is null, or a shape for given shapeid
   *                                  doesn't exist
   */
  void editKeyFrame(String shapeID, int tick, String field, int change)
          throws IllegalArgumentException;

  /**
   * Allows a user to delete a keyframe through giving the shapeID to find the given shape's
   * keyframes, and the tick of the given keyframe to be deleted.
   *
   * @param shapeID the shapeID associated with the shape to find the keyframes
   * @param tick    the tick of the associated keyframe to be deleted
   * @throws IllegalArgumentException when shapeID is null, a shape cannot be found with given
   *                                  shapeID, or a keyframe cannot be found for the given shape
   *                                  with the given tick
   */
  void deleteKeyFrame(String shapeID, int tick) throws IllegalArgumentException;
  // Added these 3 functions right above this comment in order to allow new functionality of editing
  // the model made easier for the user.

  /**
   * Deletes a given shape from the hashmap thus wiping out all of the shapes motions and the shape
   * itself.
   *
   * @param shapeID name of the given shape to be deleted
   * @throws IllegalArgumentException when a shape with given shapeID doesn't exist or is null
   */
  void deleteShape(String shapeID) throws IllegalArgumentException;

  /**
   * Method used to allow the user to add a shape based on the inputted shapeid and shape type such
   * that a new shape will be initialized.
   *
   * @param shapeID the given name for the shape to be added
   * @param type    the given shape type for the shape to be added
   * @throws IllegalArgumentException when the shapeID or type is null or the type doesn't exist
   */
  void addShape(String shapeID, String type) throws IllegalArgumentException;

  /**
   * Method to return all the shapes the view will need to display.
   *
   * @param tick given tick to return shapes at time t
   * @return an Arraylist of shapes that exist at time t
   * @throws IllegalArgumentException if given tick is negative
   */
  ArrayList<ReadOnlyIShape> getShapesAtTick(int tick) throws IllegalArgumentException;

  /**
   * Gives the hashmap of motions.
   *
   * @return a hashmap of motiosn
   */

  HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> returnMotions();

  /**
   * Gives the hashmap of keyframes.
   *
   * @return a hashmap of keyframes
   */
  HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> returnKeyFrames();

  /**
   * Generates the list of keys used to iterate through the hashmap of motions.
   *
   * @return the list of keys of all shapes in the model
   */
  ArrayList<ReadOnlyIShape> getShapes();

  /**
   * Deletes a motion with the given values.
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
   * @throws IllegalArgumentException when motion already exists for given time, if motion is
   *                                  inconsistent, or disjoint, if the shapeID is null, or if a
   *                                  shape does not exist for the given shapeID
   */
  void deleteMotion(String shapeID, int xStart, int yStart, int wStart, int hStart, int rStart,
                    int gStart, int bStart, int toX, int toY, int toW, int toH, int toR, int toG,
                    int toB, int tStart, int tEnd) throws IllegalArgumentException;

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
   * Provides a text representation of the motions.
   *
   * @return A string representing the motions
   */
  String textViewMotions();

  // Removed the original addshape from here and refactored it to be called buildershape because
  // it was only ever used for the builder. Did add a new addshape that could just be given a
  // shape type and a name for the shape to add a new shape
}
