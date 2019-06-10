package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.shape.IShape;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private ArrayList<IMotion> moveList;
  private ArrayList<IShape> shapes;
  private HashMap<String, ArrayList<IMotion>> sortedMoveList;
  private ArrayList<String> keys;

  /**
   * Constructor used to create an animator model. We don't allow a null movelist to be passed
   * because that would mess up our model.
   */
  public AnimatorModelImpl(ArrayList<IMotion> moveList) {
    if (moveList == null) {
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
  public HashMap<String, ArrayList<IMotion>> returnMotions() {
    return this.sortedMoveList;
  }

  @Override
  public ArrayList<IShape> returnShapesAtTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (IMotion currentMove : moveList) {
      if (currentMove.getTStart() <= tick && currentMove.getTEnd() >= tick) {
        currentMove.interpolate(tick);
        shapes.add(currentMove.getShape());
      }
    }

    return shapes;
  }
  //Method is incomplete at the moment because interpolate was not fully implemented as not part
  //of the scope of the assignment. So, do not have any tests for this method.

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
    for (String key : this.keys) {
      if (shape.getShapeID().equals(key)) {
        throw new IllegalArgumentException(key + " shape already exists.");
      } else {
        sortedMoveList.put(shape.getShapeID(), new ArrayList<>());
        this.keys.add(shape.getShapeID());
      }
    }
  }

  @Override
  public void addMotion(IMotion motion) {
    boolean doesShapeExist = false;
    for (String key: this.keys) {
      IShape currentShape = motion.getShape();
      if (currentShape.getShapeID().equals(key)) {
        sortedMoveList.get(key).add(motion);
        this.sortMoveList();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }

  @Override
  public void deleteMotion(IMotion motion) {
    boolean doesShapeExist = false;
    for (String key: this.keys) {
      IShape currentShape = motion.getShape();
      if (currentShape.getShapeID().equals(key)) {
        if (!sortedMoveList.get(key).remove(motion)) {
          throw new IllegalArgumentException("Given motion for given shape does not exist.");
        }

        this.sortMoveList();
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }

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
