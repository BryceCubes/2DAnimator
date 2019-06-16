package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;

/**
 * Interface used to produce an animation using a model with provided shapes and motions involved.
 */
public interface IAnimatorView extends ReadOnlyIAnimatorView{
  /**
   * Sets the output of the view to the given output.
   *
   * @param out the ouput to be used
   */
  void setOut(String out);

  /**
   * Sets the model to the given input as long as it isn't null
   * @param model
   */
  void setModel(IAnimatorModel model);

  /**
   * Sets the speed of the animation to the given input.
   * @param speed the speed for the animation to run at
   */
  void setSpeed(int speed);
}
