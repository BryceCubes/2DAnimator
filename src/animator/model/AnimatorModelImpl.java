package animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import animator.model.motion.IMotion;
import animator.model.shape.IShape;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private ArrayList<IMotion> moveList;
  private ArrayList<IShape> shapes;
  private Map<String, ArrayList<IMotion>> sortedMoveList;
  private ArrayList<String> keys;

  /**
   * Constructor used to create an animator model.
   */
  public AnimatorModelImpl(ArrayList moveList) {
    if (moveList == null) {
      throw new IllegalArgumentException("Move list cannot be null and/or tick must be positive.");
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
      if (shapeID == key) {
        returnShape = this.sortedMoveList.get(key).get(0).getShape();
      }
    }

    if (returnShape == null) {
      throw new IllegalArgumentException("A shape with input shapeID does not exist.");
    }

    return returnShape;
  }

  @Override
  public ArrayList<IShape> returnShapesAtTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (int i = 0; i < moveList.size(); i++) {
      IMotion currentMove = moveList.get(i);
      if (currentMove.getTStart() <= tick && currentMove.getTEnd() >= tick) {
        currentMove.interpolate(tick);
        shapes.add(currentMove.getShape());
      }
    }

    return shapes;
  }

  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();
    for (String key : this.keys) {
      IShape currentShape = this.findShape(key);
      textView.append("shape " + currentShape.getShapeID() + " "
              + currentShape.getShapeTypeAsString() + "\n");
      for (IMotion motion : this.sortedMoveList.get(key)) {
        textView = textView.append(motion.getTextOutput());
      }
    }

    return textView.toString();
  }

  private void sortMoveList() {
    for (IMotion motion : moveList) {
      IShape currentShape = motion.getShape();
      String key = currentShape.getShapeID();

      // This is to add a new key to the hashmap
      if (sortedMoveList.get(key) == null) {
        sortedMoveList.put(key, new ArrayList<IMotion>());
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
          throw new IllegalArgumentException("Overlapping moves for same shape.");
        }

        else {
          sortedMoveList.get(key).add(motion);
        }
      }
    }

    for (String key: this.keys) {
      sortedMoveList.put(key, this.bubbleSort(sortedMoveList.get(key)));
      if (!this.isInSequence(sortedMoveList.get(key))) {
        throw new IllegalArgumentException("Motions are not continuous.");
      }
    }
  }

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   *
   * @param list list given from the hasmap that includes all motions associated with a key
   * @return a sorted list of IMotions based on start times
   */
  private ArrayList<IMotion> bubbleSort(ArrayList<IMotion> list) {
    int size = list.size();
    for (int i = 0; i < size; i++) {
      for (int j = i; j < size; j++) {
        IMotion currentMotion = list.get(i);
        IMotion checkingMotion = list.get(j);
        if (currentMotion.getTStart() > checkingMotion.getTStart()) {
          list.add(j, currentMotion);
          list.add(i, checkingMotion);
        }
      }
    }

    return list;
  }

  /**
   * Used to return whether or not a list is in sequence given the start and stop times,
   * and the starting and ending x and y coordinates.
   *
   * @param list list given from the hashmap associated with a certain key
   * @return a boolean regarding whether or not the list is in sequence
   */
  private Boolean isInSequence(ArrayList<IMotion> list) {
    int size = list.size();
    boolean isConsistent = true;
    for (int i = 0; i < size - 1; i++) {
      IMotion currentMotion = list.get(i);
      IMotion nextMotion = list.get(i + 1);

      if (currentMotion.getTEnd() != nextMotion.getTStart()
              || currentMotion.getXEnd() != nextMotion.getXStart()
              || currentMotion.getYEnd() != nextMotion.getYStart()) {
        isConsistent = false;
        break;
      }
    }

    return isConsistent;
  }
}
