package cs3500.animator.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.motion.ShapeMotion;
import cs3500.animator.model.shape.AShape;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.model.shape.ShapeType;

public class AnimationBuilderImpl implements AnimationBuilder {
  IAnimatorModel model;
  int x;
  int y;
  int width;
  int height;
  ArrayList<ReadOnlyIMotion> listOfMotions;
  ArrayList<IShape> listOfShapes;


  @Override
  public Object build() {
    model = new AnimatorModelImpl();
    for (IShape shape : this.listOfShapes) {
      model.addShape(shape);
    }
  }

  @Override
  public AnimationBuilder setBounds(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    return this;
  }

  @Override
  public AnimationBuilder declareShape(String name, String type) {
    boolean doesShapeExist = false;
    for (ReadOnlyIShape shape : this.listOfShapes) {
      if (name.equals(shape.getShapeID())) {
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
  public AnimationBuilder addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1,
                                    int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
                                    int g2, int b2) {
    boolean doesShapeExist = false;
    IShape currentShape = null;
    for (IShape shape : this.listOfShapes) {
      if (name.equals(shape.getShapeID())) {
        doesShapeExist = true;
        currentShape = shape;
        break;
      }
    }

    if (doesShapeExist) {
      model.addMotion(new ShapeMotion(currentShape, x1, y1, w1, h1, r1, g1, b1, x2, y2, w2, h2, r2,
              g2, b2, t1, t2));
    }

    return this;
  }

  @Override
  public AnimationBuilder addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g,
                                      int b) {
    return null;
  }
}
