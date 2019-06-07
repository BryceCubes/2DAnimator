package animator_model.shape;

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

  /**
   * Constructor to create a shape. Invariants are w and h cannot be less than 0, rgb cannot
   * be less than 0 or greater than 255, and shapeID and shapeType cannot be null, and shapeID
   * cannot be empty string.
   * @param shapeID name of shape
   * @param shapeType shape type
   * @param x x value of center of shape
   * @param y y value of center of shape
   * @param w width of shape
   * @param h height of shape
   * @param r red value of shape
   * @param g green value of shape
   * @param b blue value of shape
   */
  public AShape(String shapeID, ShapeType shapeType, int x, int y, int w, int h, int r, int g,
                int b) {
    if (w < 0 || h < 0 || r < 0 || g < 0 || b < 0 || shapeID == null || shapeType == null
            || r > 255 || g > 255 || b > 255 || shapeID == "") {
      throw new IllegalArgumentException("Width, height and/or rgb must be positive and less than"
              + " 256. Shape type and shapeID cannot be null and shapeID cannot be empty string.");
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
  }

  @Override
  public String getShapeID() {
    return this.shapeID;
  }

  @Override
  public ShapeType getShapeType() {
    return this.shapeType;
  }

  @Override
  public int getXPos() {
    return this.xPos;
  }

  @Override
  public int getYPos() {
    return this.yPos;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public String getShapeTypeAsString() {
    switch (this.shapeType) {
      case RECTANGLE:
        return "rectangle";
      case ELLIPSE:
        return "ellipse";
      default:
        throw new IllegalStateException("If you see this error, something has gone very wrong.");
    }
  }
}
