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
        model.builderShape(shape);
      }

      for (IMotion motion : this.listOfMotions) {
        model.builderMotion(motion);
      }

      // Added this into the builder so that if a user chooses to input the keyframes instead, our
      // builder will add them into the model.
      for (IKeyFrame keyFrame : this.listOfKeyFrames) {
        model.builderKeyFrame(keyFrame);
      }

      // If the keyframes were never added by the user, then the model will instead make the
      // key frames from the motions.
      if (this.listOfKeyFrames == null) {
        model.makeKeyFrames();
      }

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
      } else if (type == null) {
        throw new IllegalArgumentException("The shape type cannot be null");
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
  public void addKeyFrame(String shapeID, int tick)
          throws IllegalArgumentException {
    if (shapeID == null || shapeID.equals("")) {
      throw new IllegalArgumentException("ShapeID cannot be null or an empty string.");
    }

    boolean doesShapeExist = false;

    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        doesShapeExist = true;
        int size = this.keyFrames.get(shape).size();
        IKeyFrame keyFrameBefore;
        IKeyFrame keyFrameAfter;
        int index = 0;
        if (this.keyFrames.get(shape).isEmpty()) {
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .build());
        } else if (tick > this.keyFrames.get(shape).get(size - 1).getT()) {
          IKeyFrame lastKeyFrame = this.keyFrames.get(shape).get(size - 1);
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .declareX(lastKeyFrame.getX()).declareY(lastKeyFrame.getY())
                  .declareW(lastKeyFrame.getW()).declareH(lastKeyFrame.getH())
                  .declareR(lastKeyFrame.getR()).declareG(lastKeyFrame.getB())
                  .declareB(lastKeyFrame.getB()).build());
        } else if (tick < this.keyFrames.get(shape).get(0).getT()) {
          IKeyFrame firstKeyFrame = this.keyFrames.get(shape).get(0);
          this.keyFrames.get(shape).add(new KeyFrame.Builder().declareShape(shape).declareT(tick)
                  .declareX(firstKeyFrame.getX()).declareY(firstKeyFrame.getY())
                  .declareW(firstKeyFrame.getW()).declareH(firstKeyFrame.getH())
                  .declareR(firstKeyFrame.getR()).declareG(firstKeyFrame.getG())
                  .declareB(firstKeyFrame.getB()).build());
        } else {
          for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
            index++;
            if (keyFrame.getT() == tick) {
              throw new IllegalArgumentException("KeyFrame already exists at given tick.");
            } else if (tick > keyFrame.getT() && tick < this.keyFrames.get(shape).get(index)
                    .getT()) {
              keyFrameBefore = keyFrame;
              keyFrameAfter = this.keyFrames.get(shape).get(index);
              this.keyFrames.get(shape).add(this.interpolateKeyFrame(shape, tick, keyFrameBefore,
                      keyFrameAfter));
              break;
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
  // Added this functionality so that a user could add a keyframe anywhere for a shape and have it
  // either appear blank if added at the beginning or end, or appear interpolated in the middle of
  // two other key frames.

  @Override
  public void editKeyFrame(String shapeID, int tick, String field, int change)
          throws IllegalArgumentException {
    boolean doesShapeExist = false;
    boolean doesTickExist = false;
    if (shapeID == null) {
      throw new IllegalArgumentException("Shape id cannot be null.");
    } else if (field == null) {
      throw new IllegalArgumentException("field cannot be null.");
    }

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
            break;
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
  // Added this functionality so that a user could easily edit a key frame using the editor.

  @Override
  public void deleteKeyFrame(String shapeID, int tick) throws IllegalArgumentException {
    if (shapeID == null) {
      throw new IllegalArgumentException("ShapeID cannot be null.");
    }
    boolean doesShapeExist = false;
    boolean doesTickExist = false;
    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        doesShapeExist = true;
        for (IKeyFrame keyFrame : this.keyFrames.get(shape)) {
          if (keyFrame.getT() == tick) {
            doesTickExist = true;
            this.keyFrames.get(shape).remove(keyFrame);
            break;
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
  // Added this functionality so that could easily allow a user to delete a keyframe from the
  // editor.

  @Override
  public void deleteShape(String shapeID) throws IllegalArgumentException {
    if (shapeID == null || shapeID.equals("")) {
      throw new IllegalArgumentException("ShapeID cannot be null or an empty string.");
    }
    boolean doesShapeExist = false;
    int length = this.getShapes().size();
    for (int i = 0; i < length; i++) {
      if (shapes.get(i).getShapeID().equals(shapeID)) {
        this.sortedMoveList.remove(shapes.get(i));
        this.keyFrames.remove(shapes.get(i));
        this.shapes.remove(i);
        // Added in the removal of the shape from the keyframes too.
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException(shapeID + " shape does not exist.");
    }
  }

  @Override
  public void addShape(String shapeID, String type) {
    if (shapeID == null || type == null) {
      throw new IllegalArgumentException("ShapeID and shape type cannot be null.");
    }

    boolean doesShapeExist = false;

    for (IShape shape : this.shapes) {
      if (shapeID.equals(shape.getShapeID())) {
        doesShapeExist = true;
        break;
      }
    }

    if (doesShapeExist) {
      throw new IllegalArgumentException("Shape with given shapeID already exists.");
    }

    switch (type.toLowerCase()) {
      case "ellipse":
        IShape newEllipse = new AShape(shapeID, ShapeType.ELLIPSE);
        this.builderShape(newEllipse);
        break;
      case "rectangle":
        IShape newRectangle = new AShape(shapeID, ShapeType.RECTANGLE);
        this.builderShape(newRectangle);
        break;
      default:
        throw new IllegalArgumentException("Shape type must be either ellipse or rectangle.");
    }
  }
  // Added so that a user could easily add a shape using our editor view.

  @Override
  public ArrayList<ReadOnlyIShape> getShapesAtTick(int tick) {
    ArrayList<ReadOnlyIShape> shapesAtTick = new ArrayList<>();
    if (tick < 0) {
      throw new IllegalArgumentException("Tick must be a positive integer.");
    }

    for (IShape shape : this.shapes) {
      int index = this.keyFrames.get(shape).size();
      for (int i = 0; i < index - 1; i++) {
        IKeyFrame currentKeyFrame = this.keyFrames.get(shape).get(i);
        IKeyFrame nextKeyFrame = this.keyFrames.get(shape).get(i + 1);
        if (currentKeyFrame.getT() <= tick && nextKeyFrame.getT() >= tick) {
          shapesAtTick.add(this.interpolateKeyFrame(shape, tick, currentKeyFrame, nextKeyFrame)
                  .getShape());
          break;
        }
      }
    }

    return shapesAtTick;
  }
  // We assume here that to make an animation, a keyframe must have at least two keyframes in order
  // for it to be animated.

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
  // Added this functionality so it would be easy to retrieve all the keyframes if needed.

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
  public void deleteMotion(String shapeID, int xStart, int yStart, int wStart, int hStart,
                           int rStart, int gStart, int bStart, int toX, int toY, int toW, int toH,
                           int toR, int toG, int toB, int tStart, int tEnd)
          throws IllegalArgumentException {
    if (shapeID == null) {
      throw new IllegalArgumentException("ShapeID cannot be null.");
    }
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
        sortMoveList();
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
  public void setCanvasX(int canvasX) throws IllegalArgumentException {
    if (canvasX < 0) {
      throw new IllegalArgumentException("Canvas x cannot b negative.");
    }
    this.canvasX = canvasX;
  }

  @Override
  public void setCanvasY(int canvasY) throws IllegalArgumentException {
    if (canvasY < 0) {
      throw new IllegalArgumentException("Canvas y cannot be negative.");
    }
    this.canvasY = canvasY;
  }

  @Override
  public void setCanvasW(int canvasW) throws IllegalArgumentException {
    if (canvasW < 1) {
      throw new IllegalArgumentException("Canvas width cannot be less than 1.");
    }
    this.canvasW = canvasW;
  }

  @Override
  public void setCanvasH(int canvasH) throws IllegalArgumentException {
    if (canvasH < 1) {
      throw new IllegalArgumentException("Canvas height cannot be less than 1.");
    }
    this.canvasH = canvasH;
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

  /**
   * Method bubble sortMoveList algorithm implemented normally used to sortMoveList the list based
   * on start times.
   */
  private void sortMoveList() {
    for (IShape shape : this.shapes) {
      this.sortedMoveList.get(shape).sort(Comparator.comparingInt(IMotion::getTStart));
    }
  }

  /**
   * Method bubble sortMoveList algorithm implemented normally used to sortMoveList the list based
   * on start times.
   */
  private void sortKeyFrames() {
    for (IShape shape : this.shapes) {
      this.keyFrames.get(shape).sort(Comparator.comparingInt(IKeyFrame::getT));
    }
  }
  // Added this functionality so that we could easily sortMoveList the keyframes based on time when
  // they are added.

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
  // Added this functionality so that if only motions were added in the builder for the model, then
  // the key frames could be generated in this way.

  private IKeyFrame interpolateKeyFrame(IShape shape, int tick, IKeyFrame firstKeyFrame,
                                        IKeyFrame secondKeyFrame) {
    double deltaX = secondKeyFrame.getX() - firstKeyFrame.getX();
    double deltaY = secondKeyFrame.getY() - firstKeyFrame.getY();
    double deltaW = secondKeyFrame.getW() - firstKeyFrame.getW();
    double deltaH = secondKeyFrame.getH() - firstKeyFrame.getH();
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
    shape.setX(newX);
    shape.setY(newY);
    shape.setW(newW);
    shape.setH(newH);
    shape.setR(newR);
    shape.setG(newG);
    shape.setB(newB);
    return new KeyFrame.Builder().declareShape(shape).declareT(tick).declareX(newX).declareY(newY)
            .declareW(newW).declareH(newH).declareR(newR).declareG(newG).declareB(newB).build();
  }
  // Added the interpolation functionality to the model so that when adding a new key frame, the
  // new key frame will be initialized as an interpolation between two other key frames.

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
        this.sortMoveList();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }

    this.sortMoveList();

    if (!this.isContinuous(sortedMoveList.get(currentShape))) {
      throw new IllegalArgumentException("Adding given motion causes motions to be noncontinuous.");
    }
  }
  // Made this method private as it was only needed in this class for the builder specifically.

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
          }
        }
        this.keyFrames.get(shape).add(keyFrame);
        this.sortKeyFrames();
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }
  }
  // Added this method so that the model could easily add keyframes without interpolating between
  // two first because this is used in the builder where someone is just inputting a bunch of
  // keyframes. We would not want to just set the keyframes to basic values at that point as the
  // builder requires them to put in multiple values.

  /**
   * Adds a new shape to the hashmap without any motions attached.
   *
   * @param shape the given shape to be added to the hashmap
   * @throws IllegalArgumentException if shape already exists
   */
  private void builderShape(IShape shape) {
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

    this.sortedMoveList.put(shape, new ArrayList<>());
    this.keyFrames.put(shape, new ArrayList<>());
    this.shapes.add(shape);
  }
  // Fixed from last time so it is easier to add shapes to our hashmap.
  // Was made private as only needed it for the builder specifically.

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
}
