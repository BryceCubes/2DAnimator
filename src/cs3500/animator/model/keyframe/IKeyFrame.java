package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IShape;

public interface IKeyFrame extends ReadOnlyIKeyFrame {
  void setX(int x);
  void setY(int y);
  void setW(int w) throws IllegalArgumentException;
  void setH(int h) throws IllegalArgumentException;
  void setR(int r) throws IllegalArgumentException;
  void setG(int g) throws IllegalArgumentException;
  void setB(int b) throws IllegalArgumentException;
  void setT(int t) throws IllegalArgumentException;
  void setShape(IShape shape);
}
