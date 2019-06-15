package cs3500.animator.model;

import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.ShapeMotion;
import cs3500.animator.model.shape.AShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ShapeType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Class used to test our animator model.
 */
public class AnimatorModelImplTest {
  // rectangle with basic movements
  private IShape frectangle;

  // ellipse with size changes
  private IShape amyOval;

  // circle with color changes combined with other types
  private IShape ethanCircle;

  private IMotion fredMoveRight;
  private IMotion fredMoveLeft;
  private IMotion fredMoveUp;
  private IMotion fredMoveDown;
  private IMotion fredMoveUpRight;
  private IMotion fredMoveDownLeft;

  private IMotion amyGrowTall;
  private IMotion amyGrowWide;
  private IMotion amyScaleUp;
  private IMotion amyShrinkHeight;
  private IMotion amyShrinkWidth;
  private IMotion amyScaleDown;

  private IMotion ethanColorChanges;
  private IMotion ethanAllChanges;

  private IMotion fredBadStart;
  private IMotion amyBadEnd;
  private IMotion fredOverlap;
  private IMotion ethanDisjoint;

  private AnimatorModelImpl model;


  private void setTest() {
    // Rectangle movement
    // orange square
    frectangle = new AShape("Fred", ShapeType.RECTANGLE);
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
    amyOval = new AShape("Amy", ShapeType.ELLIPSE);
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
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE);

    // purple to green circle
    ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);

    // green to purple circle with movement up and to the right as well as expanding unevenly
    ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);

    // ERROR CHECKING MOVES

    fredBadStart = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            9, 20);

    amyBadEnd = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 51);

    fredOverlap = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            5, 25);

    ethanDisjoint = new ShapeMotion(ethanCircle, 26, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);


    // Making the model and adding all shapes and motions
    model = new AnimatorModelImpl();

    model.addShape(frectangle);
    model.addShape(amyOval);
    model.addShape(ethanCircle);

    model.addMotion(ethanColorChanges);
    model.addMotion(fredMoveLeft);
    model.addMotion(fredMoveDown);
    model.addMotion(amyGrowTall);
    model.addMotion(amyShrinkWidth);
    model.addMotion(fredMoveRight);
    model.addMotion(amyGrowWide);
    model.addMotion(fredMoveUpRight);
    model.addMotion(amyShrinkHeight);
    model.addMotion(fredMoveDownLeft);
    model.addMotion(amyScaleDown);
    model.addMotion(amyScaleUp);
    model.addMotion(fredMoveUp);
    model.addMotion(ethanAllChanges);

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

  // test that it prints correctly with correct moves out of order
  @Test
  public void tryPrint() {
    setTest();
    assertEquals("shape Ethan ellipse\n" +
                    "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n" +
                    "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n" +
                    "shape Fred rectangle\n" +
                    "motion Fred 0 10 10 5 5 255 150 10    10 15 10 5 5 255 150 10\n" +
                    "motion Fred 10 15 10 5 5 255 150 10    20 5 10 5 5 255 150 10\n" +
                    "motion Fred 20 5 10 5 5 255 150 10    30 5 5 5 5 255 150 10\n" +
                    "motion Fred 30 5 5 5 5 255 150 10    40 5 15 5 5 255 150 10\n" +
                    "motion Fred 40 5 15 5 5 255 150 10    50 15 5 5 5 255 150 10\n" +
                    "motion Fred 50 15 5 5 5 255 150 10    60 10 20 5 5 255 150 10\n" +
                    "shape Amy ellipse\n" +
                    "motion Amy 0 50 50 10 20 0 100 255    25 50 50 10 25 0 100 255\n" +
                    "motion Amy 25 50 50 10 25 0 100 255    50 50 50 20 25 0 100 255\n" +
                    "motion Amy 50 50 50 20 25 0 100 255    75 50 50 30 35 0 100 255\n" +
                    "motion Amy 75 50 50 30 35 0 100 255    100 50 50 30 30 0 100 255\n" +
                    "motion Amy 100 50 50 30 30 0 100 255    125 50 50 20 30 0 100 255\n" +
                    "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n",
            model.textViewMotions());
  }

  // test that an exception is thrown when the start tick overlaps
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapStart() {
    setTest();
    IAnimatorModel badModel = new AnimatorModelImpl();
    badModel.addShape(frectangle);
    badModel.addMotion(fredMoveRight);
    badModel.addMotion(fredBadStart); // starts before previous motion finishes
  }

  // test that an exception is thrown when the end tick overlaps
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapEnd() {
    setTest();
    IAnimatorModel badModel = new AnimatorModelImpl();
    badModel.addShape(amyOval);
    badModel.addMotion(amyGrowTall);
    badModel.addMotion(amyBadEnd); // ends after the following motion
    badModel.addMotion(amyScaleUp);
  }

  // test that an exception is thrown when one motion encompasses another
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapAll() {
    setTest();
    IAnimatorModel badModel = new AnimatorModelImpl();
    badModel.addShape(frectangle);
    badModel.addMotion(fredMoveLeft);
    badModel.addMotion(fredOverlap); // starts before and ends after previous
  }

  // test that an exception is thrown when one motion encompasses another
  @Test(expected = IllegalArgumentException.class)
  public void testDisjoint() {
    setTest();
    IAnimatorModel badModel = new AnimatorModelImpl();
    badModel.addShape(ethanCircle);
    badModel.addMotion(ethanColorChanges);
    badModel.addMotion(ethanDisjoint); // ends 1 after the previous
  }

  @Test(expected = IllegalArgumentException.class)
  public void addAlreadyExistingShape() {
    setTest();
    model.addShape(new AShape("Fred", ShapeType.ELLIPSE));
  }

  @Test
  public void addShapeTest() {
    setTest();
    IShape george = new AShape("George", ShapeType.RECTANGLE);
    model.addShape(george);
    assertEquals(model.returnMotions().get("George"), model.returnMotions().get(george
            .getShapeID()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionAlreadyExists() {
    setTest();
    model.addMotion(new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionInconsistent() {
    setTest();
    model.addMotion(new ShapeMotion(frectangle, 20, 30, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionDisjoint() {
    setTest();
    model.addMotion(new ShapeMotion(frectangle, 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            61, 70));
  }

  @Test
  public void addMotionTest() {
    setTest();
    IMotion newFred = new ShapeMotion(frectangle, 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
    model.addMotion(newFred);
    assertEquals(model.returnMotions().get("Fred").get(6), newFred);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shapeForMotionDoesntExist() {
    setTest();
    IShape george = new AShape("George", ShapeType.RECTANGLE);
    IMotion georgeMoves = new ShapeMotion(george, 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
    model.deleteMotion(georgeMoves);
  }

  @Test(expected = IllegalArgumentException.class)
  public void motionDoesntExist() {
    setTest();
    IMotion newFred = new ShapeMotion(frectangle, 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
    model.deleteMotion(newFred);
  }

  //Doesnt pass yet
  @Test
  public void deleteMotionTest() {
    setTest();
    model.deleteMotion(fredMoveDownLeft);
    assertEquals("shape Ethan ellipse\n" +
            "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n" +
            "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n" +
            "shape Fred rectangle\n" +
            "motion Fred 0 10 10 5 5 255 150 10    10 15 10 5 5 255 150 10\n" +
            "motion Fred 10 15 10 5 5 255 150 10    20 5 10 5 5 255 150 10\n" +
            "motion Fred 20 5 10 5 5 255 150 10    30 5 5 5 5 255 150 10\n" +
            "motion Fred 30 5 5 5 5 255 150 10    40 5 15 5 5 255 150 10\n" +
            "motion Fred 40 5 15 5 5 255 150 10    50 15 5 5 5 255 150 10\n" +
            "shape Amy ellipse\n" +
            "motion Amy 0 50 50 10 20 0 100 255    25 50 50 10 25 0 100 255\n" +
            "motion Amy 25 50 50 10 25 0 100 255    50 50 50 20 25 0 100 255\n" +
            "motion Amy 50 50 50 20 25 0 100 255    75 50 50 30 35 0 100 255\n" +
            "motion Amy 75 50 50 30 35 0 100 255    100 50 50 30 30 0 100 255\n" +
            "motion Amy 100 50 50 30 30 0 100 255    125 50 50 20 30 0 100 255\n" +
            "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n" ,
            model.textViewMotions());
  }

  @Test
  public void getMotionsTest() {
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativeTickGetShapes() {
    setTest();
    model.returnShapesAtTick(-1);
  }

  @Test
  public void returnShapesAtTickTestAt0() {

  }

  @Test
  public void returnShapesAtTickTestAt10() {

  }
}
