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

  private ArrayList<IMotion> badStartList;
  private ArrayList<IMotion> badEndList;
  private ArrayList<IMotion> overlapList;
  private ArrayList<IMotion> disjointList;

  private AnimatorModelImpl model;

  private void setTest() {
    // Rectangle movement
    // orange square
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10.0, 10.0, 5.0, 5.0,
            255, 150, 10);
    IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    IMotion fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    IMotion fredMoveUp = new ShapeMotion(frectangle, 5, 10, 5, 5, 255,
            150, 10, 5, 5, 5, 5, 255, 150, 10,
            20, 30);
    IMotion fredMoveDown = new ShapeMotion(frectangle, 5, 5, 5, 5, 255,
            150, 10, 5, 15, 5, 5, 255, 150, 10,
            30, 40);
    IMotion fredMoveUpRight = new ShapeMotion(frectangle, 5, 15, 5, 5, 255,
            150, 10, 15, 5, 5, 5, 255, 150, 10,
            40, 50);
    IMotion fredMoveDownLeft = new ShapeMotion(frectangle, 15, 5, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            50, 60);

    // Ellipse scaling
    // tall blue Ellipse
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50.0, 50.0, 10.0, 20.0, 0,
            100, 255);
    IMotion amyGrowTall = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 25, 0, 100, 255,
            0, 25);
    IMotion amyGrowWide = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 50);
    IMotion amyScaleUp = new ShapeMotion(amyOval, 50, 50, 20, 25, 0,
            100, 255, 50, 50, 30, 35, 0, 100, 255,
            50, 75);
    IMotion amyShrinkHeight = new ShapeMotion(amyOval, 50, 50, 30, 35, 0,
            100, 255, 50, 50, 30, 30, 0, 100, 255,
            75, 100);
    IMotion amyShrinkWidth = new ShapeMotion(amyOval, 50, 50, 30, 30, 0,
            100, 255, 50, 50, 20, 30, 0, 100, 255,
            100, 125);
    IMotion amyScaleDown = new ShapeMotion(amyOval, 50, 50, 20, 30, 0,
            100, 255, 50, 50, 10, 20, 0, 100, 255,
            125, 150);

    // Circle changing color
    // starting light purple circle
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25.0, 25.0, 15.0, 15.0, 180,
            120, 230);

    // purple to green circle
    IMotion ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);

    // green to purple circle with movement up and to the right as well as expanding unevenly
    IMotion ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);

    // ERROR CHECKING MOVES

    IMotion fredBadStart = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            9, 20);

    IMotion amyBadEnd = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 51);

    IMotion fredOverlap = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            5, 25);

    IMotion ethanDisjoint = new ShapeMotion(ethanCircle, 26, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);

    // Making the model
    ArrayList<IMotion> moveList = new ArrayList<>();
    moveList.add(ethanColorChanges);
    moveList.add(fredMoveLeft);
    moveList.add(fredMoveDown);
    moveList.add(amyGrowTall);
    moveList.add(amyShrinkWidth);
    moveList.add(fredMoveRight);
    moveList.add(amyGrowWide);
    moveList.add(fredMoveUpRight);
    moveList.add(amyShrinkHeight);
    moveList.add(fredMoveDownLeft);
    moveList.add(amyScaleDown);
    moveList.add(amyScaleUp);
    moveList.add(fredMoveUp);
    moveList.add(ethanAllChanges);
    model = new AnimatorModelImpl(moveList);

    ArrayList<IMotion> badStartList = new ArrayList<>();
    badStartList.add(fredMoveRight);
    badStartList.add(fredBadStart); // starts before previous motion finishes


    ArrayList<IMotion> badEndList = new ArrayList<>();
    badEndList.add(amyGrowTall);
    badEndList.add(amyBadEnd); // ends after the following motion
    badEndList.add(amyScaleUp);

    ArrayList<IMotion> overlapList = new ArrayList<>();
    overlapList.add(fredMoveLeft);
    overlapList.add(fredOverlap); // starts before and ends after previous


    ArrayList<IMotion> disjointList = new ArrayList<>();
    disjointList.add(ethanColorChanges);
    disjointList.add(ethanDisjoint); // ends 1 after the previous


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
    new AnimatorModelImpl(badStartList);
  }

  // test that an exception is thrown when the end tick overlaps
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapEnd() {
    setTest();
    new AnimatorModelImpl(badEndList);
  }

  // test that an exception is thrown when one motion encompasses another
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapAll() {
    setTest();
    new AnimatorModelImpl(overlapList);
  }

  // test that an exception is thrown when one motion encompasses another
  @Test(expected = IllegalArgumentException.class)
  public void testDisjoint() {
    setTest();
    new AnimatorModelImpl(disjointList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new AnimatorModelImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyMotions() {
    new AnimatorModelImpl(new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addAlreadyExistingShape() {
    setTest();
    model.addShape(new AShape("Fred", ShapeType.RECTANGLE, 15.0, 100.0, 5.0, 5.0,
            255, 150, 10));
  }

  @Test
  public void addShapeTest() {
    setTest();
    IShape george = new AShape("George", ShapeType.RECTANGLE, 15.0, 100.0, 5.0, 5.0,
            255, 150, 10);
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
    IShape george = new AShape("George", ShapeType.RECTANGLE, 15.0, 100.0, 5.0, 5.0,
            255, 150, 10);
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
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10.0, 10.0, 5.0, 5.0,
            255, 150, 10);
    IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    IMotion fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    ArrayList<IMotion> testList = new ArrayList<>();
    testList.add(fredMoveLeft);
    testList.add(fredMoveRight);
    IAnimatorModel testModel = new AnimatorModelImpl(testList);
    testModel.deleteMotion(fredMoveLeft);
    assertEquals(testModel.returnMotions().get("Fred").size(), 1);
  }

  @Test
  public void getMotionsTest() {
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10.0, 10.0, 5.0, 5.0,
            255, 150, 10);
    IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    IMotion fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    ArrayList<IMotion> testListMotions = new ArrayList<>();
    testListMotions.add(fredMoveRight);
    testListMotions.add(fredMoveLeft);
    IAnimatorModel testModel = new AnimatorModelImpl(testListMotions);
    HashMap<String, ArrayList<IMotion>> testHash = new HashMap<>();
    testHash.put("Fred", testListMotions);
    assertEquals(testHash, testModel.returnMotions());
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativeTickGetShapes() {
    setTest();
    model.returnShapesAtTick(-1);
  }

  @Test
  public void returnShapesAtTickTestAt0() {
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10.0, 10.0, 5.0, 5.0,
            255, 150, 10);
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50.0, 50.0, 10.0, 20.0, 0,
            100, 255);
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25.0, 25.0, 15.0, 15.0, 180,
            120, 230);
    IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    IMotion fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    IMotion amyGrowTall = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 25, 0, 100, 255,
            0, 25);
    IMotion amyGrowWide = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 50);
    IMotion ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);
    IMotion ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);
    ArrayList<IMotion> setOfMotions = new ArrayList<>();
    setOfMotions.add(ethanColorChanges);
    setOfMotions.add(fredMoveLeft);
    setOfMotions.add(amyGrowWide);
    setOfMotions.add(fredMoveRight);
    setOfMotions.add(amyGrowTall);
    setOfMotions.add(ethanAllChanges);
    IAnimatorModel testModel = new AnimatorModelImpl(setOfMotions);
    ArrayList<IShape> setOfShapes = new ArrayList<>();
    setOfShapes.add(ethanCircle);
    setOfShapes.add(frectangle);
    setOfShapes.add(amyOval);
    assertEquals(setOfShapes, testModel.returnShapesAtTick(0));
  }

  @Test
  public void returnShapesAtTickTestAt10() {
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 5.0, 10.0, 5.0, 22.0,
            255, 150, 10);
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50.0, 50.0, 10.0, 20.0, 0,
            100, 255);
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25.0, 25.0, 15.0, 15.0, 160,
            140, 185);
    IMotion fredMoveRight = new ShapeMotion(frectangle, 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
    IMotion fredMoveLeft = new ShapeMotion(frectangle, 15, 10, 5, 5, 255,
            150, 10, 5, 10, 5, 5, 255, 150, 10,
            10, 20);
    IMotion amyGrowTall = new ShapeMotion(amyOval, 50, 50, 10, 20, 0,
            100, 255, 50, 50, 10, 25, 0, 100, 255,
            0, 25);
    IMotion amyGrowWide = new ShapeMotion(amyOval, 50, 50, 10, 25, 0,
            100, 255, 50, 50, 20, 25, 0, 100, 255,
            25, 50);
    IMotion ethanColorChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 180,
            120, 230, 25, 25, 15, 15, 120, 180, 95,
            0, 30);
    IMotion ethanAllChanges = new ShapeMotion(ethanCircle, 25, 25, 15, 15, 120,
            180, 95, 40, 15, 20, 30, 180, 120, 230,
            30, 60);
    ArrayList<IMotion> setOfMotions = new ArrayList<>();
    setOfMotions.add(ethanColorChanges);
    setOfMotions.add(fredMoveLeft);
    setOfMotions.add(amyGrowWide);
    setOfMotions.add(fredMoveRight);
    setOfMotions.add(amyGrowTall);
    setOfMotions.add(ethanAllChanges);
    IAnimatorModel testModel = new AnimatorModelImpl(setOfMotions);
    ArrayList<IShape> setOfShapes = new ArrayList<>();
    setOfShapes.add(ethanCircle);
    setOfShapes.add(frectangle);
    setOfShapes.add(amyOval);
    assertEquals(setOfShapes, testModel.returnShapesAtTick(10));
  }
}
