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

    for (IMotion motion: this.moveList) {
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

    for (int motion = 0; motion < moveList.size(); motion++) {
      IMotion currentMove = moveList.get(motion);
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
    for (IMotion move: moveList) {
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
    for (IMotion motion: moveList) {
      IShape currentShape = motion.getShape();
      String key = currentShape.getShapeID();

      if (sortedMoveList.get(key) == null) {
        sortedMoveList.put(key, new ArrayList<IMotion>());
      }

      if (sortedMoveList.get(key).isEmpty()) {
        sortedMoveList.get(key).add(motion);
      }

      int size = sortedMoveList.size();
      for (int i = 0; i < size; i++) {
        IMotion originalMotion = sortedMoveList.get(key).get(i);
        if (motion.getTEnd() == originalMotion.getTStart()) {
          moveList.add(motion);
        }
      }
    }
  }
}
