package animator.model;

import animator.model.motion.IMotion;
import animator.model.motion.ShapeMotion;
import animator.model.shape.AShape;
import animator.model.shape.IShape;
import animator.model.shape.ShapeType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//TODO: error check invalid motions

/**
 * Class used to test our animator model.
 */
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
    fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    fredMoveUp = new ShapeMotion(frectangle, 5, 10, 5, 5, 255,
            150, 10, 5, 5, 5, 5, 255, 150, 10,
            20, 30);
    fredMoveDown = new ShapeMotion(frectangle, 5, 5, 5, 5, 255,
            150, 10, 5, 15, 5, 5, 255, 150, 10,
            30, 40);
    fredMoveUpRight = new ShapeMotion(frectangle, 5, 15, 5, 5, 255,
            150, 10, 15, 5, 5, 5, 255, 150, 10,
            40, 50);
    fredMoveDownLeft = new ShapeMotion(frectangle, 15, 5, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            50, 60);

    // Ellipse scaling
    // tall blue Ellipse
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50, 50, 10, 20, 0,
            100, 255);
    amyGrowTall = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 25, 0, 100, 255,
            0, 25);
    amyGrowWide = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 50);
    amyScaleUp = new ShapeMotion(amyOval, 50, 50, 20, 25, 0,
            100, 255, 50, 50, 30, 35, 0, 100, 255,
            50, 75);
    amyShrinkHeight = new ShapeMotion(amyOval, 50, 50, 30, 35, 0,
            100, 255, 50, 50, 30, 30, 0, 100, 255,
            75, 100);
    amyShrinkWidth = new ShapeMotion(amyOval, 50, 50, 30, 30, 0,
            100, 255, 50, 50, 20, 30, 0, 100, 255,
            100, 125);
    amyScaleDown = new ShapeMotion(amyOval, 50, 50, 20, 30, 0,
            100, 255, 50, 50, 10, 20, 0, 100, 255,
            125, 150);

    // Circle changing color
    // starting light purple circle
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25, 25, 15, 15, 180,
            120, 230);

    // purple to green circle
    ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);

    // green to purple circle with movement up and to the right as well as expanding unevenly
    ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);

    // Making the model
    moveList = new ArrayList<>();
    moveList.add(ethanColorChanges);
    moveList.add(fredMoveLeft);
    moveList.add(fredMoveDownLeft);
    moveList.add(fredMoveDown);
    moveList.add(fredMoveUpRight);
    moveList.add(fredMoveUp);


    // Add Amy
    moveList.add(amyGrowTall);
    moveList.add(fredMoveDown);
    moveList.add(amyShrinkWidth);
    moveList.add(amyGrowWide);
    moveList.add(fredMoveUpRight);
    moveList.add(amyShrinkHeight);
    moveList.add(fredMoveDownLeft);
    moveList.add(amyScaleDown);
    moveList.add(amyScaleUp);
    moveList.add(fredMoveUp);
    moveList.add(ethanAllChanges);
    model = new AnimatorModelImpl(moveList);
  }

  // test that Fred can be found in the list of motions once added
  @Test
  public void testFindFred() {
    setTest();
    assertEquals(frectangle.getShapeID(), model.findShape("Fred").getShapeID());
  }

  // test that Fred can be found in the list of motions once added
  @Test
  public void testFindAmy() {
    setTest();
    assertEquals(amyOval.getShapeID(), model.findShape("Amy").getShapeID());
  }

  // test that Fred can be found in the list of motions once added
  @Test
  public void testFindEthan() {
    setTest();
    assertEquals(ethanCircle.getShapeID(), model.findShape("Ethan").getShapeID());
  }

  // test that an exception is thrown when there is no name match
  @Test(expected = IllegalArgumentException.class)
  public void testNoBob() {
    setTest();
    model.findShape("Bob");
  }

  @Test
  public void tryPrint() {
    setTest();
    assertEquals("shape Fred rectangle\n" +
                    "motion Fred 0 10 10 5 5 255 150 10    10 15 10 5 5 255 150 10\n" +
                    "motion Fred 10 15 10 5 5 255 150 10    20 5 10 5 5 255 150 10\n" +
                    "shape Amy ellipse\n" +
                    "motion Amy 0 50 50 10 20 0 100 255    25 50 50 10 25 0 100 255\n" +
                    "motion Amy 25 50 50 10 25 0 100 255    50 50 50 20 25 0 100 255\n" +
                    "motion Amy 50 50 50 20 25 0 100 255    75 50 50 30 35 0 100 255\n" +
                    "motion Amy 75 50 50 30 35 0 100 255    100 50 50 30 30 0 100 255\n" +
                    "motion Amy 100 50 50 30 30 0 100 255    125 50 50 20 30 0 100 255\n" +
                    "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n" +
                    "shape Ethan ellipse\n" +
                    "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n" +
                    "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n",
            model.textViewMotions());
  }


}
