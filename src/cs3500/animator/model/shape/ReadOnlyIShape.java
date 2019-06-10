package cs3500.animator.model.shape;

public interface ReadOnlyIShape {
  /**
   * This method is used to return the name of the shape to be used by the controller to pass
   * to the view.
   *
   * @return shape name as a string
   */
  String getShapeID();

  /**
   * This method is used to get the shape type as the concrete type from the enum to be used for
   * easier error checking.
   *
   * @return a shapeType for the given shape.
   */
  ShapeType getShapeType();

  /**
   * Method used to get the x position to be passed to the controller.
   *
   * @return the x position for the given shape
   */
  double getXPos();

  /**
   * Method used to get the y position to be passed to the controller.
   *
   * @return the y position for the given shape
   */
  double getYPos();

  /**
   * Method used to get the width of the given shape to be passed to the controller.
   *
   * @return the width of the given shape
   */
  double getWidth();

  /**
   * Method used to get the height of the given shape to be passed to the controller.
   *
   * @return the height of the given shape
   */
  double getHeight();

  /**
   * Method used to get the red value of the given shape to be passed to the controller.
   *
   * @return the red value of the given shape
   */
  double getRed();

  /**
   * Method used to get the green value of the given shape to be passed to the controller.
   *
   * @return the green value of the given shape
   */
  double getGreen();

  /**
   * Used to get the blue value of the given shape to be passed to the controller.
   *
   * @return the blue value of the given shape
   */
  double getBlue();

  /**
   * Gives the shape type as a string.
   *
   * @return the shape type as a string
   */
  String getShapeTypeAsString();
}
