package animator_model.motion;

import animator_model.shape.IShape;

/**
 * Interface represents a motion of a shape.
 */
public interface IMotion {
  String getTextOutput();

  int getTStart();

  int getTEnd();

  IShape getShape();

  void interpolate();
}

