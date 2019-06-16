package cs3500.animator.model.shape;

/**
 * Class represents all base attributes that a 2d shape must have in a larger frame.
 */
public class AShape implements IShape {
  private String shapeID;
  private ShapeType shapeType;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private int red;
  private int green;
  private int blue;
  // changed position and size to doubles so could have smooth animations

  /**
   * Constructor creates a base shape with dummy values so the user can simply enter a name and
   * type and choose its properties through adding motions.
   *
   * @param shapeID   name of shape
   * @param shapeType shape type (Rectangle, Ellipse)
   */
  public AShape(String shapeID, ShapeType shapeType) {
    if (shapeID == null || shapeType == null || shapeID.equals("")) {
      throw new IllegalArgumentException("Shape type and Shape ID cannot be null");
    } else {
      this.shapeID = shapeID;
      this.shapeType = shapeType;
      this.xPos = 1;
      this.yPos = 1;
      this.width = 1;
      this.height = 1;
      this.red = 1;
      this.green = 1;
      this.blue = 1;
    }
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
  public double getXPos() {
    return this.xPos;
  }

  @Override
  public double getYPos() {
    return this.yPos;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
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

  @Override
  public void setX(double x) {
    this.xPos = x;
  }

  @Override
  public void setY(double y) {
    this.yPos = y;
  }

  @Override
  public void setW(double w) {
    this.width = w;
  }

  @Override
  public void setH(double h) {
    this.height = h;
  }

  @Override
  public void setR(int r) {
    this.red = r;
  }

  @Override
  public void setG(int g) {
    this.green = g;
  }

  @Override
  public void setB(int b) {
    this.blue = b;
  }
}
