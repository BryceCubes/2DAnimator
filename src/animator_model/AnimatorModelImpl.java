package animator_model;

import java.util.ArrayList;

import animator_model.motion.IMotion;
import animator_model.shape.IShape;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private IMotion[] moveList;
  private ArrayList<IShape> shapes;
  private int tick;

  public AnimatorModelImpl(IMotion[] moveList, int tick) {
    if (moveList == null || tick < 0) {
      throw new IllegalArgumentException("Move list cannot be null and/or tick must be positive.");
    }

    this.moveList = moveList;
    this.tick = tick;
    this.shapes = new ArrayList<IShape>();
  }

  @Override
  public void add(ShapeType type) {

  }

  @Override
  public IShape findShape(String shapeID) {
    IShape returnShape = null;

    for (IShape shape: this.shapes) {
      if (shape.getShapeID() == shapeID) {
        returnShape = shape;
        break;
      }
    }

    return returnShape;
  }

  @Override
  public ArrayList<IShape> returnShapesAtTick() {
    for (int motion = 0; motion < moveList.length; motion++) {
      IMotion currentMove = moveList[motion];
      if (currentMove.getTStart() <= this.tick && currentMove.getTEnd() >= this.tick) {
        currentMove.interpolate();
        shapes.add(currentMove.getShape());
      }
    }

    return shapes;
  }

  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();
    for (IMotion move: moveList) {
      textView = textView.append(move.getTextOutput());
    }

    return textView.toString();
  }
}

