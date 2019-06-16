package cs3500.animator.view;

/**
 * Interface used to produce an animation using a model with provided shapes and motions involved.
 */
public interface IAnimatorView {
  /**
   * Method used to animate from a given view that takes a model as input so that the given shapes
   * can be outputted correctly.
   */
  void animate();
}
