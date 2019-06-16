package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
  private int canvasX;
  private int canvasY;
  private int canvasW;
  private int canvasH;

  /**
   * Base constructor used to initialize the variables
   */
  private AnimatorModelImpl() {
    this.shapes = new ArrayList<>();
    this.sortedMoveList = new HashMap<>();
  }

  /**
   * Constructor used to create an animator model.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel> {
    AnimatorModelImpl model;
    int x;
    int y;
    int width;
    int height;
    ArrayList<IMotion> listOfMotions = new ArrayList<>();
    ArrayList<IShape> listOfShapes = new ArrayList<>();

    @Override
    public AnimatorModelImpl build() {
      model = new AnimatorModelImpl();

      model.setCanvasX(this.x);
      model.setCanvasY(this.y);
      model.setCanvasW(this.width);
      model.setCanvasH(this.height);

      for (IShape shape : this.listOfShapes) {
        model.addShape(shape);
      }

      for (IMotion motion: this.listOfMotions) {
        model.builderMotion(motion);
      }

      return this.model;
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
      IShape currentShape = null;
      for (IShape shape : this.listOfShapes) {
        if (shape == null) {
          break;
        } if (name.equals(shape.getShapeID())) {
          currentShape = shape;
          break;
        }
      }

      if (currentShape == null) {
        throw new IllegalArgumentException("Given shape does not exist.");
      } else {
        listOfMotions.add(new ShapeMotion(currentShape, x1, y1, w1, h1, r1, g1, b1, x2, y2, w2, h2,
                r2, g2, b2, t1, t2));
      }

      return this;
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
      for (ReadOnlyIMotion motion : sortedMoveList.get(shape)) {
        motions.get(shape).add(motion);
      }
    }

    return motions;
  }

  @Override
  public ArrayList<ReadOnlyIShape> returnShapesAtTick(int tick) {
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
    for (IShape shape : this.shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        this.sortedMoveList.remove(shape);
        doesShapeExist = true;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException(shapeID + " shape does not exist.");
    }
  }
  // Added so that a shape can be removed with ease.

  @Override
  public void builderMotion(IMotion motion) {
    boolean doesShapeExist = false;
    IShape currentShape = motion.getShape();
    String shapeName = currentShape.getShapeID();

    for (IShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        sortedMoveList.get(shape).add(motion);
        this.bubbleSort();
        doesShapeExist = true;
        break;
      }
    }

    if (!doesShapeExist) {
      throw new IllegalArgumentException("Shape given does not exist.");
    }

    this.bubbleSort();

    if (!this.isContinuous(sortedMoveList.get(currentShape))) {
      throw new IllegalArgumentException("Adding given motion causes motions to be noncontinuous.");
    }
  }
  // Added for additional functionality to commands when creating a model

  @Override
  public void deleteMotion(IMotion motion) {
    boolean doesShapeExist = false;
    IShape currentShape = motion.getShape();
    String shapeName = currentShape.getShapeID();

    for (IShape shape : this.shapes) {
      if (shapeName.equals(shape.getShapeID())) {
        doesShapeExist = true;
        int index = -1;
        IMotion possibility = null;

        for (IMotion mot : this.sortedMoveList.get(shape)) {
          index++;
          if (mot.equals(motion)) {
            possibility = motion;
            break;
          }
        }
        if (possibility == null) {
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
  // Added so that we could offer additional functionality to the commands

  @Override
  public ArrayList<ReadOnlyIShape> returnShapes() {
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
  //Added so that could access and iterate through all of the data in the view

  /**
   * Method bubble sort algorithm implemented normally used to sort the list based on start times.
   */
  private void bubbleSort() {
    for (IShape shape : this.shapes) {
      int size = sortedMoveList.get(shape).size();

      for (int i = 0; i < size; i++) {
        for (int j = i; j < size; j++) {
          IMotion currentMotion = sortedMoveList.get(shape).get(i);
          IMotion checkingMotion = sortedMoveList.get(shape).get(j);

          if (currentMotion.getTStart() > checkingMotion.getTStart()) {
            Collections.swap(sortedMoveList.get(shape), j, i);
          }
        }
      }
    }
  }

  /**
   * Used to return whether or not a list is in sequence given the start and stop times,
   * and the starting and ending x and y coordinates.
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
}
