package animator_model;

import java.util.ArrayList;

import animator_model.motion.IMotion;
import animator_model.shape.IShape;

/**
 * General Model to represent the shapes and motions for an animator application.
 */
public interface IAnimatorModel {

  public void add(ShapeType type);

  public IShape findShape(String shapeID);

  /**
   * Method to return all the shapes the view will need to display.
   * @return A list of shapes
   */
  public ArrayList<IShape> returnShapesAtTick();

  /**
   * Provides a text representation of the motions.
   * @return A string representing the motions
   */
  public String textViewMotions();
}
