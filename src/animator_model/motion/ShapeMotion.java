package animator_model.motion;

import animator_model.shape.IShape;

/**
 * Class represents a motion of a shape in a 2D space
 */
public class ShapeMotion {
  IShape shape;
  int toX, toY;
  int toR, toG, toB;
  int tStart, tEnd;

  public ShapeMotion(String shapeID, int x, int y, int r, int g, int b, int tStart, int tEnd) {
    this.toX = x;
    this.toY = y;
    this.toR = r;
    this.toG = g;
    this.toB = b;
    this.tStart = tStart;
    this.tEnd = tEnd;
    //TODO: be able to find a shape from its ID
  }
}
