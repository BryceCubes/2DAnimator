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
   * Constructor creates a base shape with dummy values so the user can simply enter a name and type
   * and choose its properties through adding motions.
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
      this.xPos = 0;
      this.yPos = 0;
      this.width = 0;
      this.height = 0;
      this.red = 0;
      this.green = 0;
      this.blue = 0;
    }
  }


  @Override
  public String getShapeID() {
    return this.shapeID;
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
  public void setW(double w) throws IllegalArgumentException {
    if (w < 0) {
      throw new IllegalArgumentException("Width cannot be negative.");
    }
    this.width = w;
  }

  @Override
  public void setH(double h) throws IllegalArgumentException {
    if (h < 0) {
      throw new IllegalArgumentException("height cannot be negative.");
    }
    this.height = h;
  }

  @Override
  public void setR(int r) throws IllegalArgumentException {
    if (r < 0 || r > 255) {
      throw new IllegalArgumentException("Red cannot be negative or greater than 255.");
    }
    this.red = r;
  }

  @Override
  public void setG(int g) throws IllegalArgumentException {
    if (g < 0 || g > 255) {
      throw new IllegalArgumentException("Green cannot be negative or greater than 255.");
    }
    this.green = g;
  }

  @Override
  public void setB(int b) throws IllegalArgumentException {
    if (b < 0 || b > 255) {
      throw new IllegalArgumentException("Blue cannot be negative or greater than 255.");
    }
    this.blue = b;
  }
}
