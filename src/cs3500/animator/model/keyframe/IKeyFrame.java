package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IShape;

public interface IKeyFrame extends ReadOnlyIKeyFrame {
  void setX(double x);
  void setY(double y);
  void setW(double w) throws IllegalArgumentException;
  void setH(double h) throws IllegalArgumentException;
  void setR(int r) throws IllegalArgumentException;
  void setG(int g) throws IllegalArgumentException;
  void setB(int b) throws IllegalArgumentException;
  void setT(int t) throws IllegalArgumentException;
  void setShape(IShape shape);
  IShape getShape();
}
