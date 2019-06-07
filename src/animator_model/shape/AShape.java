package animator_model.shape;

import animator_model.ShapeType;
import animator_model.motion.IMotion;


/**
 * Class represents all base attributes that a 2d shape must have in a larger frame.
 */
public class AShape implements IShape {
  private String shapeID;
  private ShapeType shapeType;
  private int xPos;
  private int yPos;
  private int width;
  private int height;
  private int red;
  private int green;
  private int blue;
  private IMotion motion;

  /**
   * Base constructor that takes in all necessary values to create a shape.
   * @param x the x position in the frame
   * @param y the y position in the frame
   * @param w the general width of the shape
   * @param h the general height of the shape
   * @param r the red value for it's color 0 - 255
   * @param g the green value for it's color 0 - 255
   * @param b the blue value for it's color 0 - 255
   */
  public AShape(String shapeID, ShapeType shapeType, int x, int y, int r, int w, int h, int g,
                int b, IMotion motion) {
    if (w < 0 || h < 0 || r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Width and height and/or rgb must be positive.");
    }
    this.shapeID = shapeID;
    this.shapeType = shapeType;
    this.xPos = x;
    this.yPos = y;
    this.width = w;
    this.height = h;
    this.red = r;
    this.green = g;
    this.blue = b;
    this.motion = motion;
  }

  //TODO: probably a fuckton of getters and most of our methods should be in here
  @Override
  public String getShapeID() {
    return this.shapeID;
  }

  public ShapeType getShapeType() {
    return this.shapeType;
  }

  public int getxPos() {
    return this.xPos;
  }

  public int getyPos() {
    return this.yPos;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getRed() {
    return this.red;
  }

  public int getGreen() {
    return this.green;
  }

  public int getBlue() {
    return this.blue;
  }

  public IMotion getMotion() {
    return this.motion;
  }
}

