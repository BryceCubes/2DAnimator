package animator_model;

import animator_model.motion.IMotion;
import animator_model.motion.ShapeMotion;
import animator_model.shape.AShape;
import animator_model.shape.IShape;
import animator_model.shape.ShapeType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//TODO: error check invalid motions

public class AnimatorModelImplTest {
  // rectangle with basic movements
  private IShape frectangle;
  private IMotion fredMoveRight;
  private IMotion fredMoveLeft;
  private IMotion fredMoveUp;
  private IMotion fredMoveDown;
  private IMotion fredMoveUpRight;
  private IMotion fredMoveDownLeft;

  // ellipse with size changes
  private IShape amyOval;
  private IMotion amyGrowTall;
  private IMotion amyGrowWide;
  private IMotion amyScaleUp;
  private IMotion amyShrinkHeight;
  private IMotion amyShrinkWidth;
  private IMotion amyScaleDown;

  // circle with color changes combined with other types
  private IShape ethanCircle;
  private IMotion ethanColorChanges;
  private IMotion ethanAllChanges;

  // Animator Model
  private ArrayList<IMotion> moveList;
  private AnimatorModelImpl model;

  private void setTest() {
    // Rectangle movement
    // orange square
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

    // Ellipse scaling
    // tall blue Ellipse
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50, 50, 10, 20, 0,
            100, 255);
    amyGrowTall = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 25, 0, 100, 255,
            0, 25);
    amyGrowWide = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 20, 20, 0, 100, 255,
            0, 25);
    amyScaleUp = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 20, 30, 0, 100, 255,
            0, 25);
    amyShrinkHeight = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 10, 0, 100, 255,
            0, 25);
    amyShrinkWidth = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 5, 20, 0, 100, 255,
            0, 25);
    amyScaleDown = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 5, 10, 0, 100, 255,
            0, 25);

    // Circle changing color
    // starting light purple circle
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25, 25, 15, 15,180,
            120, 230);

    // purple to green circle
    ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);

    // purple to green circle with movement up and to the right as well as expanding unevenly
    ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 40, 15, 20, 30, 120, 180, 95,
            0, 30);

    // Making the model
    moveList = new ArrayList<>();
    moveList.add(fredMoveUpRight);
    moveList.add(amyScaleUp);
    moveList.add(ethanAllChanges);
    model = new AnimatorModelImpl(moveList);
  }

  @Test
  public void testFindFred() {
    setTest();
    assertEquals(frectangle.getShapeID(), model.findShape("Fred").getShapeID());
  }

  @Test
  public void printShapes() {
    setTest();
    for(int i = 0; i < 3; i++) {
      System.out.println(moveList.get(i).getShape().getShapeID());
    }
  }



}