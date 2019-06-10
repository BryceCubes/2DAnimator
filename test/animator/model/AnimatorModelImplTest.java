package animator.model;

import animator.model.motion.IMotion;
import animator.model.motion.ShapeMotion;
import animator.model.shape.AShape;
import animator.model.shape.IShape;
import animator.model.shape.ShapeType;

import org.junit.Test;

import java.util.ArrayList;

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
    frectangle = new AShape("Fred", ShapeType.RECTANGLE, 10, 10, 5, 5,
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
    amyOval = new AShape("Amy", ShapeType.ELLIPSE, 50, 50, 10, 20, 0,
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
    ethanCircle = new AShape("Ethan", ShapeType.ELLIPSE, 25, 25, 15, 15, 180,
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

  //TODO add tests for addShape, addMotion, and deleteMotion

}
