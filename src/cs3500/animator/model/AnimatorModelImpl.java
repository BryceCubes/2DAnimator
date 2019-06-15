package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private ArrayList<IMotion> moveList;
  private ArrayList<ReadOnlyIShape> shapes;
  private ArrayList<ReadOnlyIShape> shapesAtTick;
  private HashMap<ReadOnlyIShape, ArrayList<IMotion>> sortedMoveList;
  private int canvasX;
  private int canvasY;
  private int canvasW;
  private int canvasH;

  /**
   * Constructor used to create an animator model. We don't allow a null movelist to be passed
   * because that would mess up our model.
   */
  public AnimatorModelImpl(ArrayList<IMotion> moveList, int canvasX, int canvasY, int canvasW,
                           int canvasH) {
    if (moveList == null || moveList.isEmpty()) {
      throw new IllegalArgumentException("Move list cannot be null.");
    }
    if (canvasX < 0 || canvasY < 0) {
      throw new IllegalArgumentException("The canvas xy coordinate cannot be negative.");
    }
    if (canvasW < 1 || canvasH < 1) {
      throw new IllegalArgumentException("The canvas width and height cannot be less than 1.");
    }
    this.moveList = moveList;
    this.shapes = new ArrayList<>();
    this.sortedMoveList = new HashMap<>();
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasW = canvasW;
    this.canvasH = canvasH;
    this.sortMoveList();
  }

  @Override
  public ReadOnlyIShape findShape(String shapeID) {
    ReadOnlyIShape returnShape = null;

    for (ReadOnlyIShape shape : this.shapes) {
      if (shapeID.equals(shape.getShapeID())) {
        returnShape = shape;
      }
    }

    if (returnShape == null) {
      throw new IllegalArgumentException("A shape with input shapeID does not exist.");
    }

    return returnShape;
  }

  @Override
  public HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> returnMotions() {
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> motions = new HashMap<>();
    for (ReadOnlyIShape shape : this.shapes) {
      motions.put(shape, new ArrayList<>());
      for (ReadOnlyIMotion motion : sortedMoveList.get(shape)) {
        motions.get(shape).add(motion);
      }
    }

    return motions;
  }

  @Override
  public ArrayList<ReadOnlyIShape> returnShapesAtTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (ReadOnlyIShape shape : this.shapes) {
      for (IMotion motion : this.sortedMoveList.get(shape)) {
        if (motion.getTStart() <= tick && motion.getTEnd() >= tick) {
          shapesAtTick.add(motion.getShape());
          break;
        }
      }
    }

    return this.shapesAtTick;
  }

  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();

    for (ReadOnlyIShape shape : this.shapes) {
      textView.append("shape ").append(shape.getShapeID()).append(" ").append(shape
              .getShapeTypeAsString()).append("\n");
      for (IMotion motion : this.sortedMoveList.get(shape)) {
        textView.append(motion.getTextOutput());
      }
    }

    return textView.toString();
  }

  @Override
  public void addShape(ReadOnlyIShape shape) {
    boolean doesShapeExist = false;
    String shapeName = null;

    for (ReadOnlyIShape key : this.shapes) {
      if (shape.getShapeID().equals(key.getShapeID())) {
        doesShapeExist = true;
        shapeName = key.getShapeID();
        break;
      }
    }

    if (doesShapeExist) {
      throw new IllegalArgumentException(shapeName + " shape already exists.");
    }

    sortedMoveList.put(shape, new ArrayList<>());
    this.shapes.add(shape);
  }
  // Fixed from last time so it is easier to add shapes to our hashmap

  @Override
  public void deleteShape(String shapeID) {
    boolean doesShapeExist = false;
    for (ReadOnlyIShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        this.sortedMoveList.remove(shape);
        doesShapeExist = true;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException(shapeID + " shape does not exist.");
    }
  }
  // Added so that a shape can be removed with ease.

  @Override
  public void addMotion(IMotion motion) {
    boolean doesShapeExist = false;
    ReadOnlyIShape currentShape = motion.getShape();
    String shapeName = currentShape.getShapeID();

    for (ReadOnlyIShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        sortedMoveList.get(shape).add(motion);
        this.bubbleSort();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }

    this.bubbleSort();

    if (!this.isContinuous(sortedMoveList.get(currentShape))) {
      throw new IllegalArgumentException("Adding given motion causes motions to be noncontinuous.");
    }
  }
  // Added for additional functionality to commands when creating a model

  @Override
  public void deleteMotion(IMotion motion) {
    boolean doesShapeExist = false;
    ReadOnlyIShape currentShape = motion.getShape();
    String shapeName = currentShape.getShapeID();

    for (ReadOnlyIShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        doesShapeExist = true;
        int motionIndex = sortedMoveList.get(shape).indexOf(motion);

        if (motionIndex == -1) {
          throw new IllegalArgumentException("Given motion for given shape does not exist.");
        }

        sortedMoveList.get(shape).remove(motionIndex);

        if (!this.isContinuous(sortedMoveList.get(shape))) {
          throw new IllegalArgumentException("Deleting given motion causes motions to be "
                  + "noncontinuous.");
        }
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }
  // Added so that we could offer additional functionality to the commands

  @Override
  public ArrayList<ReadOnlyIShape> returnShapes() {
    return this.shapes;
  }

  @Override
  public int getCanvasX() {
    return this.canvasX;
  }

  @Override
  public int getCanvasY() {
    return this.canvasY;
  }

  @Override
  public int getCanvasW() {
    return this.canvasW;
  }

  @Override
  public int getCanvasH() {
    return this.canvasH;
  }
  //Added so that could access and iterate through all of the data in the view

  private void sortMoveList() {
    for (IMotion motion : moveList) {
      ReadOnlyIShape currentShape = motion.getShape();

      // This is to add a new key to the hashmap
      if (sortedMoveList.get(currentShape) == null) {
        sortedMoveList.put(currentShape, new ArrayList<>());
        this.shapes.add(currentShape);
      }

      // This is to add a starting motion to a specific key
      if (sortedMoveList.get(currentShape).isEmpty()) {
        sortedMoveList.get(currentShape).add(motion);
      } else {
        int size = sortedMoveList.get(currentShape).size();
        boolean isOverlapping = false;

        // Are the motions overlapping?
        for (int i = 0; i < size; i++) {
          int motionStartTime = motion.getTStart();
          int motionEndTime = motion.getTEnd();
          int indexedMotionStart = sortedMoveList.get(currentShape).get(i).getTStart();
          int indexedMotionEnd = sortedMoveList.get(currentShape).get(i).getTEnd();
          if ((motionStartTime >= indexedMotionStart && motionStartTime < indexedMotionEnd)
                  || (motionEndTime > indexedMotionStart && motionEndTime <= indexedMotionEnd)
                  || (motionStartTime <= indexedMotionStart && motionEndTime >= indexedMotionEnd)) {
            isOverlapping = true;
            break;
          }
        }

        if (isOverlapping) {
          throw new IllegalArgumentException("Overlapping moves for shape "
                  + currentShape.getShapeID() + ".");
        } else {
          sortedMoveList.get(currentShape).add(motion);
        }
      }
    }

    this.bubbleSort();

    for (ReadOnlyIShape shape : this.shapes) {
      if (!this.isContinuous(sortedMoveList.get(shape))) {
        throw new IllegalArgumentException("Motions for " + shape.getShapeID() + " are not "
                + "continuous.");
      }
    }
  }

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   */
  private void bubbleSort() {
    for (ReadOnlyIShape shape : this.shapes) {
      int size = sortedMoveList.get(shape).size();

      for (int i = 0; i < size; i++) {
        for (int j = i; j < size; j++) {
          IMotion currentMotion = sortedMoveList.get(shape).get(i);
          IMotion checkingMotion = sortedMoveList.get(shape).get(j);

          if (currentMotion.getTStart() > checkingMotion.getTStart()) {
            Collections.swap(sortedMoveList.get(shape), j, i);
          }
        }
      }
    }
  }

  /**
   * Used to return whether or not a list is in sequence given the start and stop times,
   * and the starting and ending x and y coordinates.
   *
   * @param list list given from the hashmap associated with a certain key
   * @return a boolean regarding whether or not the list is in sequence
   */
  private Boolean isContinuous(ArrayList<IMotion> list) {
    int size = list.size();
    boolean isConsistent = true;

    for (int i = 1; i < size; i++) {
      IMotion currentMotion = list.get(i - 1);
      IMotion nextMotion = list.get(i);

      if (currentMotion.getTEnd() != nextMotion.getTStart()
              || currentMotion.getXEnd() != nextMotion.getXStart()
              || currentMotion.getYEnd() != nextMotion.getYStart()
              || currentMotion.getWEnd() != nextMotion.getWStart()
              || currentMotion.getHEnd() != nextMotion.getHStart()
              || currentMotion.getREnd() != nextMotion.getRStart()
              || currentMotion.getGEnd() != nextMotion.getGStart()
              || currentMotion.getBEnd() != nextMotion.getBStart()) {
        isConsistent = false;
        break;
      }
    }

    return isConsistent;
  }
}
