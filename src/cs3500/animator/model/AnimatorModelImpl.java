package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.keyframe.KeyFrame;
import cs3500.animator.model.keyframe.ReadOnlyIKeyFrame;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.motion.ShapeMotion;
import cs3500.animator.model.shape.AShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.util.AnimationBuilder;

/**
 * Animator model implementation that contains the representations of shapes and their animation
 * instructions.
 */
public class AnimatorModelImpl implements IAnimatorModel {

  private ArrayList<IShape> shapes;
  private HashMap<IShape, ArrayList<IMotion>> sortedMoveList;
  private HashMap<IShape, ArrayList<IKeyFrame>> keyFrames;
  private int canvasX;
  private int canvasY;
  private int canvasW;
  private int canvasH;

  /**
   * Base constructor used to initialize the variables.
   */
  private AnimatorModelImpl() {
    this.shapes = new ArrayList<>();
    this.sortedMoveList = new HashMap<>();
    this.keyFrames = new HashMap<>();
  }

  /**
   * Constructor used to create an animator model.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel> {
    private Integer x = null;
    private Integer y = null;
    private Integer width = null;
    private Integer height = null;
    private ArrayList<IMotion> listOfMotions = new ArrayList<>();
    private ArrayList<IShape> listOfShapes = new ArrayList<>();
    private ArrayList<IKeyFrame> listOfKeyFrames = new ArrayList<>();

    @Override
    public AnimatorModelImpl build() {
      AnimatorModelImpl model = new AnimatorModelImpl();
      if (this.x == null || this.y == null || this.width == null || this.height == null) {
        throw new IllegalArgumentException("The bounds of the view must be set.");
      }
      model.setCanvasX(this.x);
      model.setCanvasY(this.y);
      model.setCanvasW(this.width);
      model.setCanvasH(this.height);

      for (IShape shape : this.listOfShapes) {
        model.addShape(shape);
      }

      for (IMotion motion : this.listOfMotions) {
        model.builderMotion(motion);
      }

      for (IKeyFrame keyFrame : this.listOfKeyFrames) {
        model.builderKeyFrame(keyFrame);
      }

      model.makeKeyFrames();

      return model;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> setBounds(int x, int y, int width, int height) {
      if (x < 0 || y < 0) {
        throw new IllegalArgumentException("The xy coordinate of the frame cannot be negative.");
      } else if (width < 1 || height < 1) {
        throw new IllegalArgumentException("The width and height cannot be less than 1.");
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;

      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> declareShape(String name, String type) {
      boolean doesShapeExist = false;
      for (IShape shape : this.listOfShapes) {
        if (shape == null) {
          break;
        } else if (name.equals(shape.getShapeID())) {
          doesShapeExist = true;
          break;
        }
      }

      IShape newShape;

      if (doesShapeExist) {
        throw new IllegalArgumentException("Shape with name already exists.");
      } else if (type.equalsIgnoreCase("rectangle")) {
        newShape = new AShape(name, ShapeType.RECTANGLE);
      } else if (type.equalsIgnoreCase("ellipse")) {
        newShape = new AShape(name, ShapeType.ELLIPSE);
      } else {
        throw new IllegalArgumentException("Not a valid shape type.");
      }

      this.listOfShapes.add(newShape);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                      int h1, int r1, int g1, int b1, int t2,
                                                      int x2, int y2, int w2, int h2, int r2,
                                                      int g2, int b2) {
      IShape currentShape = this.getShape(name);

      listOfMotions.add(new ShapeMotion(currentShape, x1, y1, w1, h1, r1, g1, b1, x2, y2, w2, h2,
              r2, g2, b2, t1, t2));

      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
                                                        int h, int r, int g, int b) {
      IShape currentShape = this.getShape(name);

      listOfKeyFrames.add(new KeyFrame.Builder().declareShape(currentShape).declareT(t).declareX(x)
              .declareY(y).declareW(w).declareH(h).declareR(r).declareG(g).declareB(b).build());
      return this;
    }

    private IShape getShape(String name) {
      IShape currentShape = null;
      for (IShape shape : this.listOfShapes) {
        if (shape == null) {
          break;
        }
        if (name.equals(shape.getShapeID())) {
          currentShape = shape;
          break;
        }
      }

      if (currentShape == null) {
        throw new IllegalArgumentException("Given shape does not exist.");
      } else {
        return currentShape;
      }
    }
  }

  @Override
  public ReadOnlyIShape findShape(String shapeID) {
    ReadOnlyIShape returnShape = null;

    for (IShape shape : this.shapes) {
      if (shapeID.equals(shape.getShapeID())) {
        returnShape = shape;
      }
    }

    if (returnShape == null) {
      throw new IllegalArgumentException("A shape with input shapeID does not exist.");
    }

    return returnShape;
  }

  @Override
  public HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> returnMotions() {
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIMotion>> motions = new HashMap<>();
    for (IShape shape : this.shapes) {
      motions.put(shape, new ArrayList<>());
      for (IMotion motion : this.sortedMoveList.get(shape)) {
        motions.get(shape).add(motion);
      }
    }

    return motions;
  }

  @Override
  public HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> returnKeyFrames() {
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> keyFrames = new HashMap<>();
    for (IShape shape : this.shapes) {
      keyFrames.put(shape, new ArrayList<>());
      for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
        keyFrames.get(shape).add(keyFrame);
      }
    }

    return keyFrames;
  }

  @Override
  public ArrayList<ReadOnlyIShape> getShapesAtTick(int tick) {
    ArrayList<ReadOnlyIShape> shapesAtTick = new ArrayList<>();
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (IShape shape : this.shapes) {
      for (IMotion motion : this.sortedMoveList.get(shape)) {
        if (motion.getTStart() <= tick && motion.getTEnd() >= tick) {
          shapesAtTick.add(motion.interpolate(tick));
          break;
        }
      }
    }

    return shapesAtTick;
  }

  @Override
  public String textViewMotions() {
    StringBuilder textView = new StringBuilder();

    for (IShape shape : this.shapes) {
      textView.append("shape ").append(shape.getShapeID()).append(" ").append(shape
              .getShapeTypeAsString()).append("\n");
      for (IMotion motion : this.sortedMoveList.get(shape)) {
        textView.append(motion.getTextOutput());
      }
    }

    return textView.toString();
  }

  @Override
  public void addShape(IShape shape) {
    boolean doesShapeExist = false;
    String shapeName = null;

    for (IShape key : this.shapes) {
      if (shape.getShapeID().equals(key.getShapeID())) {
        doesShapeExist = true;
        shapeName = key.getShapeID();
        break;
      }
    }

    if (doesShapeExist) {
      throw new IllegalArgumentException(shapeName + " shape already exists.");
    }

    sortedMoveList.put(shape, new ArrayList<>());
    this.shapes.add(shape);
  }
  // Fixed from last time so it is easier to add shapes to our hashmap

  @Override
  public void deleteShape(String shapeID) {
    boolean doesShapeExist = false;
    int length = this.getShapes().size();
    int index = 0;
    for (int i = 0; i < length; i++) {
      if (shapes.get(i).getShapeID().equals(shapeID)) {
        this.sortedMoveList.remove(shapes.get(i));
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException(shapeID + " shape does not exist.");
    } else {
      shapes.remove(index);
    }
  }

  // deletes the motion with the given values
  @Override
  public void deleteMotion(String shapeID, int xStart, int yStart, int wStart, int hStart,
                           int rStart, int gStart, int bStart, int toX, int toY, int toW, int toH,
                           int toR, int toG, int toB, int tStart, int tEnd)
          throws IllegalArgumentException {
    boolean doesShapeExist = false;
    boolean doesMotionExist = false;

    for (IShape shape : this.shapes) {
      if (shapeID.equals(shape.getShapeID())) {
        doesShapeExist = true;

        int index = 0;

        for (IMotion mot : this.sortedMoveList.get(shape)) {
          if (equalMotions(mot, shapeID, xStart, yStart, wStart, hStart,
                  rStart, gStart, bStart, toX, toY, toW, toH,
                  toR, toG, toB, tStart, tEnd)) {
            doesMotionExist = true;
            break;
          }
          index++;
        }
        if (!doesMotionExist) {
          throw new IllegalArgumentException("Given motion for given shape does not exist.");
        } else {
          sortedMoveList.get(shape).remove(index);
        }

        if (!this.isContinuous(sortedMoveList.get(shape))) {
          throw new IllegalArgumentException("Deleting given motion causes motions to be "
                  + "noncontinuous.");
        }
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }

  /**
   * Checks if the given motion matches all the given fields.
   *
   * @param mot     the given motion from the model
   * @param shapeID the shapeID as a string to compare to mot
   * @param xStart  the starting x location to compare to mot
   * @param yStart  the starting y location to compare to mot
   * @param wStart  the starting width to compare to mot
   * @param hStart  the starting height to compare to mot
   * @param rStart  the starting red value to compare to mot
   * @param gStart  the starting green value to compare to mot
   * @param bStart  the starting blue value to compare to mot
   * @param toX     the destination x value to compare to mot
   * @param toY     the destination y value to compare to mot
   * @param toW     the destination width to compare to mot
   * @param toH     the destination height to compare to mot
   * @param toR     the destination red value to compare to mot
   * @param toG     the destination green value to compare to mot
   * @param toB     the destination blue value to compare to mot
   * @param tStart  the starting tick value to compare to mot
   * @param tEnd    the destination tick value to compare to mot
   * @return whether all the given values match the value in the given IMotion
   */
  private boolean equalMotions(IMotion mot, String shapeID, int xStart, int yStart, int wStart,
                               int hStart, int rStart, int gStart, int bStart, int toX, int toY,
                               int toW, int toH, int toR, int toG, int toB, int tStart, int tEnd) {
    return mot.getShape().getShapeID().equals(shapeID)
            && mot.getTStart() == tStart
            && mot.getTEnd() == tEnd
            && mot.getXStart() == xStart
            && mot.getYStart() == yStart
            && mot.getWStart() == wStart
            && mot.getHStart() == hStart
            && mot.getRStart() == rStart
            && mot.getGStart() == gStart
            && mot.getBStart() == bStart
            && mot.getXEnd() == toX
            && mot.getYEnd() == toY
            && mot.getWEnd() == toW
            && mot.getHEnd() == toH
            && mot.getREnd() == toR
            && mot.getGEnd() == toG
            && mot.getBEnd() == toB;
  }


  @Override
  public ArrayList<ReadOnlyIShape> getShapes() {
    IShape currentShape;
    ArrayList<ReadOnlyIShape> newShapes = new ArrayList<>();
    for (IShape shape : this.shapes) {
      currentShape = shape;
      newShapes.add(currentShape);
    }

    return newShapes;
  }

  @Override
  public int getCanvasX() {
    return this.canvasX;
  }

  @Override
  public int getCanvasY() {
    return this.canvasY;
  }

  @Override
  public int getCanvasW() {
    return this.canvasW;
  }

  @Override
  public int getCanvasH() {
    return this.canvasH;
  }

  @Override
  public void declareMotion(String shapeID, int xStart, int yStart, int wStart, int hStart,
                            int rStart, int gStart, int bStart, int toX, int toY, int toW, int toH,
                            int toR, int toG, int toB, int tStart, int tEnd)
          throws IllegalArgumentException {

    boolean shapeFound = false;

    for (IShape shape : this.shapes) {
      if (shapeID.equals(shape.getShapeID())) {
        shapeFound = true;
        sortedMoveList.get(shape).add(new ShapeMotion(shape, xStart, yStart, wStart, hStart,
                rStart, gStart, bStart, toX, toY, toW, toH,
                toR, toG, toB, tStart, tEnd));
        sort();
        if (!this.isContinuous(sortedMoveList.get(shape))) {
          throw new IllegalArgumentException("Deleting given motion causes motions to be "
                  + "noncontinuous.");
        }
        break;
      }
    }
    if (!shapeFound) {
      throw new IllegalArgumentException(shapeID + " not found in Animator");
    }
  }

  @Override
  public void setCanvasX(int canvasX) {
    this.canvasX = canvasX;
  }

  @Override
  public void setCanvasY(int canvasY) {
    this.canvasY = canvasY;
  }

  @Override
  public void setCanvasW(int canvasW) {
    this.canvasW = canvasW;
  }

  @Override
  public void setCanvasH(int canvasH) {
    this.canvasH = canvasH;
  }

  @Override
  public void addKeyFrame(String shapeID, int tick)
          throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick cannot be negative.");
    }

    boolean doesShapeExist = false;

    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        doesShapeExist = true;
        IKeyFrame keyFrameBefore;
        IKeyFrame keyFrameAfter;
        int index = 0;
        if (this.keyFrames.get(shape).isEmpty()) {
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .build());
        } else if (tick > this.keyFrames.get(shape)
                .get(this.keyFrames.get(shape).size()).getT()) {
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .build());
        } else if (tick < this.keyFrames.get(shape).get(0).getT()) {
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .build());
        } else {
          for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
            index++;
            if (keyFrame.getT() == tick) {
              throw new IllegalArgumentException("KeyFrame already exists at given tick.");
            } else if (tick > keyFrame.getT() && tick < this.keyFrames.get(shape).get(index)
                    .getT()) {
              keyFrameBefore = keyFrame;
              keyFrameAfter = keyFrame;
              this.keyFrames.get(shape).add(this.interpolateNewKeyFrame(shape, tick, keyFrameBefore,
                      keyFrameAfter));
            }
          }
        }
        this.sortKeyFrames();
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape with given shapeID does not exist.");
    }
  }

  @Override
  public void editKeyFrame(String shapeID, int tick, String field, int change)
          throws IllegalArgumentException {
    boolean doesShapeExist = false;
    boolean doesTickExist = false;
    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        doesShapeExist = true;
        for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
          if (keyFrame.getT() == tick) {
            doesTickExist = true;
            switch (field.toLowerCase()) {
              case "x":
                keyFrame.setX(change);
                break;
              case "y":
                keyFrame.setY(change);
                break;
              case "width":
                keyFrame.setW(change);
                break;
              case "height":
                keyFrame.setH(change);
                break;
              case "red":
                keyFrame.setR(change);
                break;
              case "green":
                keyFrame.setG(change);
                break;
              case "blue":
                keyFrame.setB(change);
                break;
              default:
                throw new IllegalArgumentException("The field referenced does not exist. Field must"
                        + " be of type x, y, width, height, red, green or blue.");
            }
          }
        }
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape with given shape id does not exist.");
    }
    if (!doesTickExist) {
      throw new IllegalArgumentException("KeyFrame with given tick does not exist.");
    }
  }

  @Override
  public void deleteKeyFrame(String shapeID, int tick) throws IllegalArgumentException {
    boolean doesShapeExist = false;
    boolean doesTickExist = false;
    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        doesShapeExist = true;
        for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
          if (keyFrame.getT() == tick) {
            doesTickExist = true;
            this.keyFrames.get(shape).remove(keyFrame);
          }
        }
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape with given shape id does not exist.");
    } else if (!doesTickExist) {
      throw new IllegalArgumentException("KeyFrame with given tick does not exist.");
    }
  }

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   */
  private void sort() {
    for (IShape shape : this.shapes) {
      this.sortedMoveList.get(shape).sort(Comparator.comparingInt(IMotion::getTStart));
    }
  }

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   */
  private void sortKeyFrames() {
    for (IShape shape : this.shapes) {
      this.keyFrames.get(shape).sort(Comparator.comparingInt(IKeyFrame::getT));
    }
  }

  /**
   * Used to return whether or not a list is in sequence given the start and stop times, and the
   * starting and ending x and y coordinates.
   *
   * @param list list given from the hashmap associated with a certain key
   * @return a boolean regarding whether or not the list is in sequence
   */
  private Boolean isContinuous(ArrayList<IMotion> list) {
    int size = list.size();
    boolean isConsistent = true;

    for (int i = 1; i < size; i++) {
      IMotion currentMotion = list.get(i - 1);
      IMotion nextMotion = list.get(i);

      if (currentMotion.getTEnd() != nextMotion.getTStart()
              || currentMotion.getXEnd() != nextMotion.getXStart()
              || currentMotion.getYEnd() != nextMotion.getYStart()
              || currentMotion.getWEnd() != nextMotion.getWStart()
              || currentMotion.getHEnd() != nextMotion.getHStart()
              || currentMotion.getREnd() != nextMotion.getRStart()
              || currentMotion.getGEnd() != nextMotion.getGStart()
              || currentMotion.getBEnd() != nextMotion.getBStart()) {
        isConsistent = false;
        break;
      }
    }

    return isConsistent;
  }

  private void makeKeyFrames() {
    for (IShape shape : this.shapes) {
      this.keyFrames.put(shape, new ArrayList<>());
      IMotion firstMotion = this.sortedMoveList.get(shape).get(0);
      if (firstMotion != null) {
        IKeyFrame firstKeyFrame = new KeyFrame.Builder().declareShape(shape)
                .declareX(firstMotion.getXStart()).declareY(firstMotion.getYStart())
                .declareW(firstMotion.getWStart()).declareH(firstMotion.getHStart())
                .declareR(firstMotion.getRStart()).declareG(firstMotion.getGStart())
                .declareB(firstMotion.getBStart()).declareT(firstMotion.getTStart()).build();
        this.keyFrames.get(shape).add(firstKeyFrame);
      }

      for (IMotion motion : this.sortedMoveList.get(shape)) {
        IKeyFrame keyFrame = new KeyFrame.Builder().declareShape(shape).declareX(motion.getXEnd())
                .declareY(motion.getYEnd()).declareW(motion.getWEnd()).declareH(motion.getHEnd())
                .declareR(motion.getREnd()).declareG(motion.getGEnd()).declareB(motion.getBEnd())
                .declareT(motion.getTEnd()).build();
        this.keyFrames.get(shape).add(keyFrame);
      }
    }
  }

  private IKeyFrame interpolateNewKeyFrame(IShape shape, int tick, IKeyFrame firstKeyFrame,
                                           IKeyFrame secondKeyFrame) {
    double deltaX = secondKeyFrame.getX() - firstKeyFrame.getX();
    double deltaY = secondKeyFrame.getY() - firstKeyFrame.getY();
    double deltaW = secondKeyFrame.getW() - firstKeyFrame.getW();
    double deltaH = secondKeyFrame.getH() - firstKeyFrame.getW();
    int deltaR = secondKeyFrame.getR() - firstKeyFrame.getR();
    int deltaG = secondKeyFrame.getG() - firstKeyFrame.getG();
    int deltaB = secondKeyFrame.getB() - firstKeyFrame.getB();
    double deltaT = secondKeyFrame.getT() - firstKeyFrame.getT();
    double currTick = tick - firstKeyFrame.getT();
    double newX = (currTick / deltaT) * deltaX + firstKeyFrame.getX();
    double newY = (currTick / deltaT) * deltaY + firstKeyFrame.getY();
    double newW = (currTick / deltaT) * deltaW + firstKeyFrame.getW();
    double newH = (currTick / deltaT) * deltaH + firstKeyFrame.getH();
    int newR = (int) ((currTick / deltaT) * deltaR) + firstKeyFrame.getR();
    int newG = (int) ((currTick / deltaT) * deltaG) + firstKeyFrame.getG();
    int newB = (int) ((currTick / deltaT) * deltaB) + firstKeyFrame.getB();

    return new KeyFrame.Builder().declareShape(shape).declareT(tick).declareX(newX).declareY(newY)
            .declareW(newW).declareH(newH).declareR(newR).declareG(newG).declareB(newB).build();
  }

  /**
   * Adds motion to the animation for an already existing shape used for the builder.
   *
   * @param motion motion to be added to a shapes arraylist of motions
   * @throws IllegalArgumentException when motion already exists for given time, if motion is
   *                                  inconsistent, or disjoint
   */
  private void builderMotion(IMotion motion) {
    boolean doesShapeExist = false;
    IShape currentShape = motion.getShape();
    String shapeName = currentShape.getShapeID();

    for (IShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        this.sortedMoveList.get(shape).add(motion);
        this.sort();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }

    this.sort();

    if (!this.isContinuous(sortedMoveList.get(currentShape))) {
      throw new IllegalArgumentException("Adding given motion causes motions to be noncontinuous.");
    }
  }

  /**
   * Method used to assist the builder in just adding a keyFrame without already having a model
   * built.
   *
   * @param keyFrame given KeyFrame to add to the model
   */
  private void builderKeyFrame(IKeyFrame keyFrame) {
    boolean doesShapeExist = false;
    IShape currentShape = keyFrame.getShape();
    String shapeName = currentShape.getShapeID();

    for (IShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        doesShapeExist = true;
        for (IKeyFrame frame : this.keyFrames.get(shape)) {
          if (keyFrame.getT() == frame.getT()) {
            throw new IllegalArgumentException("Cannot add a keyframe with same tick as another.");
          } else {
            this.keyFrames.get(shape).add(keyFrame);
            this.sortKeyFrames();
          }
        }
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }
}
