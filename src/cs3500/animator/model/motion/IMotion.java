package cs3500.animator.model.motion;

import cs3500.animator.model.shape.IShape;

/**
 * Interface represents a motion of a shape.
 */
public interface IMotion extends ReadOnlyIMotion {
  /**
   * Mutates the shape associated with the given motion at the time t given by the tick.
   */
  void interpolate(int tick);
}
