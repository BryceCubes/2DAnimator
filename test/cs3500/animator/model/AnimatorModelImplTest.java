package cs3500.animator.model;

import cs3500.animator.model.keyframe.ReadOnlyIKeyFrame;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class used to test our animator model.
 */
public class AnimatorModelImplTest {

  private IAnimatorModel model;

  private void setTest() {

    // Making the model and adding all shapes and motions
    model = new AnimatorModelImpl.Builder().setBounds(0, 0, 100, 100)
            .declareShape("Fred", "RecTanGle")
            .declareShape("Amy", "ellipse")
            .declareShape("Ethan", "ELLIPSE")
            .addMotion("Ethan", 30, 25, 25, 15, 15, 120,
                    180, 95, 60, 40, 15, 20, 30,
                    180, 120, 230)
            .addMotion("Ethan", 0, 25, 25, 15, 15, 180,
                    120, 230, 30, 25, 25, 15, 15,
                    120, 180, 95)
            .addMotion("Amy", 125, 50, 50, 20, 30, 0,
                    100, 255, 150, 50, 50, 10, 20,
                    0, 100, 255)
            .addMotion("Amy", 100, 50, 50, 30, 30, 0,
                    100, 255, 125, 50, 50, 20, 30,
                    0, 100, 255)
            .addMotion("Amy", 75, 50, 50, 30, 35, 0,
                    100, 255, 100, 50, 50, 30, 30,
                    0, 100, 255)
            .addMotion("Amy", 50, 50, 50, 20, 25, 0,
                    100, 255, 75, 50, 50, 30, 35,
                    0, 100, 255)
            .addMotion("Amy", 25, 50, 50, 10, 25, 0,
                    100, 255, 50, 50, 50, 20, 25,
                    0, 100, 255)
            .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                    100, 255, 25, 50, 50, 10, 25,
                    0, 100, 255)
            .addMotion("Fred", 50, 15, 5, 5, 5, 255,
                    150, 10, 60, 10, 20, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 40, 5, 15, 5, 5, 255,
                    150, 10, 50, 15, 5, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 30, 5, 5, 5, 5, 255,
                    150, 10, 40, 5, 15, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 20, 5, 10, 5, 5, 255,
                    150, 10, 30, 5, 5, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 10, 15, 10, 5, 5, 255,
                    150, 10, 20, 5, 10, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 0, 10, 10, 5, 5, 255,
                    150, 10, 10, 15, 10, 5, 5,
                    255, 150, 10).build();

    IAnimatorModel mtModel = new AnimatorModelImpl.Builder().setBounds(0, 0, 100, 100).build();

  }

  // test that it prints correctly with correct moves out of order
  @Test
  public void tryPrint() {
    setTest();
    assertEquals("shape Fred rectangle\n" +
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
                    "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n" +
                    "shape Ethan ellipse\n" +
                    "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n" +
                    "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n",
            model.textViewMotions());
  }

  // test that an exception is thrown when the start tick overlaps
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapStart() {
    setTest();
    new AnimatorModelImpl.Builder().declareShape("Fred", "Rectangle")
            .addMotion("Fred", 0, 10, 10, 5, 5, 255,
                    150, 10, 10, 15, 10, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 9, 15, 10, 5, 5, 255,
                    150, 10, 20, 5, 10, 5, 5,
                    255, 150, 10).build();
  }

  // test that an exception is thrown when the end tick overlaps
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapEnd() {
    setTest();
    new AnimatorModelImpl.Builder().declareShape("Amy", "ellipse")
            .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                    100, 255, 25, 50, 50, 10, 25,
                    0, 100, 255)
            .addMotion("Amy", 25, 50, 50, 10, 25, 0,
                    100, 255, 51, 50, 50, 20, 25,
                    0, 100, 255)
            .addMotion("Amy", 50, 50, 50, 20, 25, 0,
                    100, 255, 75, 50, 50, 30, 35,
                    0, 100, 255).build();
  }

  // test that an exception is thrown when one motion encompasses another
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapAll() {
    setTest();
    new AnimatorModelImpl.Builder().declareShape("Fred", "Rectangle")
            .addMotion("Fred", 20, 5, 10, 5, 5, 255,
                    150, 10, 30, 5, 5, 5, 5,
                    255, 150, 10)
            .addMotion("Fred", 5, 15, 10, 5, 5, 255,
                    150, 10, 25, 5, 10, 5, 5,
                    255, 150, 10).build();
  }

  // test that an exception is thrown if the motions are not continuous
  @Test(expected = IllegalArgumentException.class)
  public void testDisjoint() {
    setTest();
    new AnimatorModelImpl.Builder().declareShape("Ethan", "ellipse")
            .addMotion("Ethan", 0, 25, 25, 15, 15, 180,
                    120, 230, 30, 25, 25, 15, 15,
                    120, 180, 95)
            .addMotion("Ethan", 31, 25, 25, 15, 15, 120,
                    180, 95, 60, 40, 15, 20, 30,
                    180, 120, 230).build();
  }

  @Test
  public void testIfKeyFramesAdded() {
    setTest();
    IAnimatorModel newModel = new AnimatorModelImpl.Builder().declareShape("Amy", "ellipse")
            .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                    100, 255, 25, 50, 50, 10, 25,
                    0, 100, 255)
            .addMotion("Amy", 25, 50, 50, 10, 25, 0,
                    100, 255, 50, 50, 50, 20, 25,
                    0, 100, 255)
            .addMotion("Amy", 50, 50, 50, 20, 25, 0,
                    100, 255, 75, 50, 50, 30, 35,
                    0, 100, 255).addKeyframe("Amy", 10, 10, 10,
                    10, 10, 19, 10, 10).setBounds(1, 1, 1, 1).build();
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> keyFrames = newModel.returnKeyFrames();
    ArrayList<ReadOnlyIShape> shapes = newModel.getShapes();
    assertEquals("Amy", shapes.get(0).getShapeID());
    assertEquals(1, keyFrames.get(shapes.get(0)).size());
  }

  @Test
  public void testDeleteShape() {
    setTest();
    model.deleteShape("Fred");
    assertFalse(model.textViewMotions().contains("Fred"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteBadShape() {
    setTest();
    model.deleteShape("Bob");
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionAlreadyExists() {
    setTest();
    model.declareMotion("Fred", 10, 10, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionInconsistent() {
    setTest();
    model.declareMotion("Fred", 20, 30, 5, 5, 255,
            150, 10, 15, 10, 5, 5, 255, 150, 10,
            0, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMotionDisjoint() {
    setTest();
    model.declareMotion("Fred", 20, 5, 5, 255,
            150, 10, 10, 5, 5, 255, 150, 10,
            61, 70, 10, 20);
  }

  @Test
  public void addMotionTest() {
    setTest();
    model.declareMotion("Fred", 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
    assertTrue(model.textViewMotions().contains("Fred 60 10 20 5 5 255 150 10    " +
            "70 10 20 5 5 255 150 10"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shapeForMotionDoesntExist() {
    setTest();
    model.declareMotion("George", 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
  }

  @Test
  public void testDeclareMotion() {
    setTest();
    model.declareMotion("Fred", 10, 20, 5, 5,
            255, 150, 10, 20, 10, 5, 5,
            255, 150, 10, 60, 70);
    assertTrue(model.textViewMotions().contains("Fred 60 10 20 5 5 255 150 10    " +
            "70 20 10 5 5 255 150 10"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void motionDoesntExist() {
    setTest();
    model.deleteMotion("Fred", 10, 20, 5, 5, 255,
            150, 10, 10, 20, 5, 5, 255, 150, 10,
            60, 70);
  }

  //Doesnt pass yet
  @Test
  public void deleteMotionTest() {
    setTest();
    model.deleteMotion("Fred", 15, 5, 5, 5, 255,
            150, 10, 10, 20, 5, 5,
            255, 150, 10, 50, 60);
    assertEquals("shape Fred rectangle\n" +
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
                    "motion Amy 125 50 50 20 30 0 100 255    150 50 50 10 20 0 100 255\n" +
                    "shape Ethan ellipse\n" +
                    "motion Ethan 0 25 25 15 15 180 120 230    30 25 25 15 15 120 180 95\n" +
                    "motion Ethan 30 25 25 15 15 120 180 95    60 40 15 20 30 180 120 230\n",
            model.textViewMotions());
  }

  @Test
  public void getMotionsTest() {
    setTest();
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> motions = model.returnMotions();
    ArrayList<ReadOnlyIShape> shapes = model.getShapes();
    boolean hasFred = false;
    boolean hasAmy = false;
    boolean hasEthan = false;
    for (ReadOnlyIShape shape : shapes) {
      int length = motions.get(shape).size();
      for (int i = 0; i < length; i++) {
        String textOut = motions.get(shape).get(i).getTextOutput();
        if (textOut.contains("Fred")) {
          hasFred = true;
        } else if (textOut.contains("Amy")) {
          hasAmy = true;
        } else if (textOut.contains("Ethan")) {
          hasEthan = true;
        }
      }
    }
    assertTrue(hasFred && hasAmy && hasEthan);
  }


  @Test(expected = IllegalArgumentException.class)
  public void negativeTickGetShapes() {
    setTest();
    model.getShapesAtTick(-1);
  }

  @Test
  public void returnShapesAtTickTestAt70() {
    setTest();
    ArrayList<ReadOnlyIShape> shapes = model.getShapesAtTick(70);
    boolean hasFred = false;
    boolean hasAmy = false;
    boolean hasEthan = false;
    for (ReadOnlyIShape shape : shapes) {
      String textOut = shape.getShapeID();
      if (textOut.contains("Fred")) {
        hasFred = true;
      } else if (textOut.contains("Amy")) {
        hasAmy = true;
      } else if (textOut.contains("Ethan")) {
        hasEthan = true;
      }
    }
    assertTrue(!hasFred && hasAmy && !hasEthan);
  }

  @Test
  public void testSetCanX() {
    setTest();
    model.setCanvasX(5);
    assertEquals(model.getCanvasX(), 5);
  }

  @Test
  public void testSetCanY() {
    setTest();
    model.setCanvasY(5);
    assertEquals(model.getCanvasY(), 5);
  }

  @Test
  public void testSetCanW() {
    setTest();
    model.setCanvasW(400);
    assertEquals(model.getCanvasW(), 400);
  }

  @Test
  public void testSetCanH() {
    setTest();
    model.setCanvasH(400);
    assertEquals(model.getCanvasH(), 400);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegXBuild() {
    new AnimatorModelImpl.Builder().setBounds(-1, 0, 1, 1)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegYBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, -1, 1, 1)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void test0WBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, 0, 0, 1)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void test0HBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, 0, 1, 0)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void noDoubleShapeBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, 0, 1, 0)
            .declareShape("Ethan", "rectangle")
            .declareShape("Ethan", "ellipse").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void noInvalidShapeBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, 0, 1, 0)
            .declareShape("Ethan", "hi :)").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void notAddedShapeBuild() {
    new AnimatorModelImpl.Builder().setBounds(0, 0, 1, 0)
            .declareShape("Ethan", "rectangle")
            .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                    100, 255, 25, 50, 50, 10, 25,
                    0, 100, 255).build();
  }

  @Test
  public void addKeyFrame() {
    setTest();
    int totalSize = 0;
    model.addKeyFrame("Fred", 70);
    for (ReadOnlyIShape shape : model.getShapes()) {
      totalSize += model.returnKeyFrames().get(shape).size();
    }
    assertEquals(18, totalSize);
  }

  @Test
  public void deleteKeyFrame() {
    setTest();
    int totalSize = 0;
    model.deleteKeyFrame("Fred", 60);
    for (ReadOnlyIShape shape : model.getShapes()) {
      totalSize += model.returnKeyFrames().get(shape).size();
    }
    assertEquals(16, totalSize);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNullName() {
    setTest();
    model.addKeyFrame(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void delNullName() {
    setTest();
    model.deleteKeyFrame(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoExistName() {
    setTest();
    model.addKeyFrame("Bob", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void delNoExistName() {
    setTest();
    model.deleteKeyFrame("George", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addInvalidTick() {
    setTest();
    model.addKeyFrame("Amy", -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void delInvalidTick() {
    setTest();
    model.deleteKeyFrame("Fred", -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMTTick() {
    setTest();
    model.addKeyFrame("", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void delMTTick() {
    setTest();
    model.deleteKeyFrame("", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DelNoExistTick() {
    setTest();
    model.deleteKeyFrame("Fred", 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameNullShape() {
    setTest();
    this.model.editKeyFrame(null, 5, "x", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addKeyFrameEmptyShapeName() {
    setTest();
    this.model.editKeyFrame("", 5, "x", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeDoesntExist() {
    setTest();
    this.model.editKeyFrame("Chris", 5, "x", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tickDoesntExist() {
    setTest();
    this.model.editKeyFrame("Ethan", 31, "x", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tickIsNegative() {
    setTest();
    this.model.editKeyFrame("Ethan", -1, "x", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fieldIsNull() {
    setTest();
    this.model.editKeyFrame("Ethan", 30, null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fieldDoesntExist() {
    setTest();
    this.model.editKeyFrame("Ethan", 30, "asdf", 5);
  }

  @Test
  public void editTest() {
    setTest();
    this.model.editKeyFrame("Ethan", 30, "x", 5);
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> keyFrames = this.model.returnKeyFrames();
    ArrayList<ReadOnlyIShape> shapes = this.model.getShapes();
    for (ReadOnlyIShape shape : shapes) {
      if (shape.getShapeID().equals("Ethan")) {
        for (ReadOnlyIKeyFrame keyFrame : keyFrames.get(shape)) {
          if (keyFrame.getT() == 30) {
            assertEquals(5.0, keyFrame.getX(), .0);
          }
        }
      }
    }
  }
}
