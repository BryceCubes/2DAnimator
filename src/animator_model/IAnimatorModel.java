package animator_model;

import animator_model.motion.IMotion;
import animator_model.shape.IShape;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel {

  enum ShapeType {ELLIPSE, RECTANGLE};
  IMotion[] moveList = new IMotion[200]; //TODO: could be arrayList or choose upper bound
  IShape[] shapes = new IShape[200];


  public void add(ShapeType type);

  public IShape findShape(String shapeID);
}
