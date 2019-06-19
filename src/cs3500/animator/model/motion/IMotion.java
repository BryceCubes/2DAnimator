package cs3500.animator.model.motion;

import cs3500.animator.model.shape.IShape;

/**
 * Interface represents a motion of a shape in 2D plane for an Animation model.
 */
public interface IMotion extends ReadOnlyIMotion {
  /**
   * Method used to return a shape object associated with a given motion.
   *
   * @return an IShape associated with the given motion
   */
  IShape getShape();
  // Added this to IMotion because it gives and IShape which can be mutated.
}
