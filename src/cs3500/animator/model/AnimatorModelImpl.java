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
  private HashMap<String, ArrayList<IMotion>> sortedMoveList;
  private ArrayList<String> keys;

  /**
   * Constructor used to create an animator model. We don't allow a null movelist to be passed
   * because that would mess up our model.
   */
  public AnimatorModelImpl(ArrayList<IMotion> moveList) {
    if (moveList == null || moveList.isEmpty()) {
      throw new IllegalArgumentException("Move list cannot be null.");
    }
    this.moveList = moveList;
    this.shapes = new ArrayList<>();
    this.sortedMoveList = new HashMap<>();
    this.keys = new ArrayList<>();
    this.sortMoveList();
  }

  @Override
  public IShape findShape(String shapeID) {
    IShape returnShape = null;

    for (String key : this.keys) {
      if (shapeID.equals(key)) {
        returnShape = this.sortedMoveList.get(key).get(0).getShape();
      }
    }

    if (returnShape == null) {
      throw new IllegalArgumentException("A shape with input shapeID does not exist.");
    }

    return returnShape;
  }

  @Override
  public HashMap<String, ArrayList<ReadOnlyIMotion>> returnMotions() {
    HashMap<String, ArrayList<ReadOnlyIMotion>> motions = new HashMap<>();
    for (String key : this.keys) {
      motions.put(key, new ArrayList<>());
      for (ReadOnlyIMotion motion : sortedMoveList.get(key)) {
        motions.get(key).add(motion);
      }
    }

    return motions;
  }

  @Override
  public ArrayList<ReadOnlyIShape> returnShapesAtTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (String key : this.keys) {
      for (IMotion motion : this.sortedMoveList.get(key)) {
        if (motion.getTStart() <= tick && motion.getTEnd() >= tick) {
          shapes.add(motion.getShape());
          break;
        }
      }
    }

    return shapes;
  }

  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();

    for (String key : this.keys) {
      IShape currentShape = this.findShape(key);
      textView.append("shape ").append(currentShape.getShapeID()).append(" ").append(currentShape
              .getShapeTypeAsString()).append("\n");
      for (IMotion motion : this.sortedMoveList.get(key)) {
        textView.append(motion.getTextOutput());
      }
    }

    return textView.toString();
  }

  @Override
  public void addShape(IShape shape) {
    boolean doesShapeExist = false;
    String shapeName = null;

    for (String key : this.keys) {
      if (shape.getShapeID().equals(key)) {
        doesShapeExist = true;
        shapeName = key;
        break;
      }
    }

    if (doesShapeExist) {
      throw new IllegalArgumentException(shapeName + " shape already exists.");
    }

    sortedMoveList.put(shape.getShapeID(), new ArrayList<>());
    this.keys.add(shape.getShapeID());
  }
  // Fixed from last time so it is easier to add shapes to our hashmap

  @Override
  public void deleteShape(String shapeID) {
    boolean doesShapeExist = false;
    for (String key : this.keys) {
      if (key == shapeID) {
        this.sortedMoveList.remove(key);
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
    String shapeName = motion.getShape().getShapeID();

    for (String key : this.keys) {
      if (shapeName.equals(key)) {
        sortedMoveList.get(key).add(motion);
        this.bubbleSort();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }

    this.bubbleSort();

    if (!this.isContinuous(sortedMoveList.get(shapeName))) {
      throw new IllegalArgumentException("Adding given motion causes motions to be noncontinuous.");
    }
  }
  // Added for additional functionality to commands when creating a model

  @Override
  public void deleteMotion(IMotion motion) {
    boolean doesShapeExist = false;
    String shapeName = motion.getShape().getShapeID();

    for (String key : this.keys) {
      if (shapeName.equals(key)) {
        doesShapeExist = true;
        int motionIndex = sortedMoveList.get(key).indexOf(motion);

        if (motionIndex == -1) {
          throw new IllegalArgumentException("Given motion for given shape does not exist.");
        }

        sortedMoveList.get(key).remove(motionIndex);

        if (!this.isContinuous(sortedMoveList.get(key))) {
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
  public ArrayList<String> returnKeys() {
    return this.keys;
  }
  //Added so that could access and iterate through all of the data in the view

  private void sortMoveList() {
    for (IMotion motion : moveList) {
      IShape currentShape = motion.getShape();
      String key = currentShape.getShapeID();

      // This is to add a new key to the hashmap
      if (sortedMoveList.get(key) == null) {
        sortedMoveList.put(key, new ArrayList<>());
        this.keys.add(key);
      }

      // This is to add a starting motion to a specific key
      if (sortedMoveList.get(key).isEmpty()) {
        sortedMoveList.get(key).add(motion);
      } else {
        int size = sortedMoveList.get(key).size();
        boolean isOverlapping = false;

        // Are the motions overlapping?
        for (int i = 0; i < size; i++) {
          int motionStartTime = motion.getTStart();
          int motionEndTime = motion.getTEnd();
          int indexedMotionStart = sortedMoveList.get(key).get(i).getTStart();
          int indexedMotionEnd = sortedMoveList.get(key).get(i).getTEnd();
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
          sortedMoveList.get(key).add(motion);
        }
      }
    }

    this.bubbleSort();

    for (String key : this.keys) {
      if (!this.isContinuous(sortedMoveList.get(key))) {
        throw new IllegalArgumentException("Motions for " + key + " are not continuous.");
      }
    }
  }

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   */
  private void bubbleSort() {
    for (String key : this.keys) {
      int size = sortedMoveList.get(key).size();

      for (int i = 0; i < size; i++) {
        for (int j = i; j < size; j++) {
          IMotion currentMotion = sortedMoveList.get(key).get(i);
          IMotion checkingMotion = sortedMoveList.get(key).get(j);

          if (currentMotion.getTStart() > checkingMotion.getTStart()) {
            Collections.swap(sortedMoveList.get(key), j, i);
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
