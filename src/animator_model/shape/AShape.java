package animator_model.shape;

/**
 * Class represents all base attributes that a 2d shape must have in a larger frame.
 */
public abstract class AShape implements IShape {
  private int xPos;
  private int yPos;
  private int width;
  private int height;
  private int red;
  private int green;
  private int blue;

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
  public AShape(int x, int y, int r, int w, int h, int g, int b) {
    this.xPos = x;
    this.yPos = y;
    this.width = w;
    this.height = h;
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  //TODO: probably a fuckton of getters and most of our methods should be in here
}
