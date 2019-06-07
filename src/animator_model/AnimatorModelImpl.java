package animator_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animator_model.motion.IMotion;
import animator_model.shape.IShape;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private ArrayList<IMotion> moveList;
  private ArrayList<IShape> shapes;
  private Map<String, ArrayList<IMotion>> sortedMoveList;

  /**
   * Constructor used to create an animator model.
   *
   * @param moveList
   */
  public AnimatorModelImpl(ArrayList moveList) {
    if (moveList == null) {
      throw new IllegalArgumentException("Move list cannot be null and/or tick must be positive.");
    }
    this.moveList = moveList;
    this.shapes = new ArrayList<>();
    this.sortedMoveList = new HashMap<>();
    this.sortMoveList();
  }

  @Override
  public IShape findShape(String shapeID) {
    IShape returnShape = null;

    for (IMotion motion : this.moveList) {
      IShape currentShape = motion.getShape();
      if (currentShape.getShapeID() == shapeID) {
        returnShape = currentShape;
        break;
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

  // The current method would not sort by shape, so if motions are input out of order for different
  // shapes, then it will reprint the header of the shape. We also did not sort the time values
  // as we were not sure whether this was in the scope of the assignment. We will probably implement
  // the list of motions as a hash map on the shape name, and add to the hashmap based on time.
  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();
    String shapeName = null;
    for (IMotion move : moveList) {
      IShape currentShape = move.getShape();
      String newShapeName = currentShape.getShapeID();
      if (newShapeName != shapeName) {
        shapeName = newShapeName;
        textView = textView.append("shape " + currentShape.getShapeID() + " "
                + currentShape.getShapeTypeAsString() + "\n");
      }
      textView = textView.append(move.getTextOutput());
    }

    return textView.toString();
  }

  private void sortMoveList() {
    ArrayList<String> keys = new ArrayList<>();
    for (IMotion motion : moveList) {
      IShape currentShape = motion.getShape();
      String key = currentShape.getShapeID();

      // This is to add a new key to the hashmap
      if (sortedMoveList.get(key) == null) {
        sortedMoveList.put(key, new ArrayList<IMotion>());
        keys.add(key);
      }

      // This is to add a starting motion to a specific key
      if (sortedMoveList.get(key).isEmpty()) {
        sortedMoveList.get(key).add(motion);
      } else {
        int size = sortedMoveList.get(key).size();

        // Are the motions overlapping?
        for (int i = 0; i < size; i++) {
          int motionStartTime = motion.getTStart();
          int motionEndTime = motion.getTEnd();
          int indexedMotionStart = sortedMoveList.get(key).get(i).getTStart();
          int indexedMotionEnd = sortedMoveList.get(key).get(i).getTEnd();
          if ((motionStartTime >= indexedMotionStart && motionStartTime < indexedMotionEnd)
                  || (motionEndTime > indexedMotionStart && motionEndTime <= indexedMotionEnd)
                  || (motionStartTime <= indexedMotionStart && motionEndTime >= indexedMotionEnd)) {
            throw new IllegalArgumentException("Overlapping moves for same shape");
          }

          sortedMoveList.get(key).add(motion);
        }
      }
    }

    for (String key : keys) {
      sortedMoveList.put(key, this.bubbleSort(sortedMoveList.get(key)));
      if (!this.isInSequence(sortedMoveList.get(key))) {
        throw new IllegalArgumentException("Motions are not continuous.");
      }
    }
  }

  private ArrayList<IMotion> bubbleSort(ArrayList<IMotion> list) {
    int size = list.size();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
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

  private Boolean isInSequence(ArrayList<IMotion> list) {
    int size = list.size();
    boolean isConsistent = true;
    for (int i = 0; i < size - 1; i++) {
      IMotion currentMotion = list.get(i);
      IMotion nextMotion = list.get(i + 1);

      if (currentMotion.getTEnd() != nextMotion.getTStart()) {
        isConsistent = false;
        break;
      }
    }

    return isConsistent;
  }
}
