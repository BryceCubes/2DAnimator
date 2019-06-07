package animator_model;

import animator_model.motion.IMotion;
import animator_model.motion.ShapeMotion;
import animator_model.shape.AShape;
import animator_model.shape.IShape;

import static org.junit.Assert.*;

//TODO: straight directions, diagonals, color change, dimension change, error check invalid motions

public class AnimatorModelImplTest {
  private IShape frectangle;
  private IMotion fredMoveRight;
  private IMotion fredMoveLeft;
  private IMotion fredMoveUp;
  private IMotion fredMoveDown;
  private IMotion fredMoveUpRight;
  private IMotion fredMoveDownLeft;

  private void setTest() {
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5,
            255, 150, 10);
    fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    fredMoveLeft = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            0, 10);
    fredMoveUp = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 10, 5, 5, 5, 255, 150, 10,
            0, 10);
    fredMoveDown = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 10, 15, 5, 5, 255, 150, 10,
            0, 10);
    fredMoveUpRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 5, 5, 5, 255, 150, 10,
            0, 10);
    fredMoveDownLeft = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 5, 5, 5, 255, 150, 10,
            0, 10);
  }

}