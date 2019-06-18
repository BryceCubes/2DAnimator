package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IShape;

public class KeyFrame implements IKeyFrame {
  IShape shape;
  private double x;
  private double y;
  private double w;
  private double h;
  private int r;
  private int g;
  private int b;
  private int t;

  public static class Builder {
    private IShape shape = null;
    private double x = 0;
    private double y = 0;
    private double w = 0;
    private double h = 0;
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int t = 0;

    public KeyFrame build() {
      if (this.shape == null || this.t == 0) {
        throw new IllegalArgumentException("Shape and tick must be set.");
      }
      KeyFrame keyFrame = new KeyFrame();
      keyFrame.setX(this.x);
      keyFrame.setY(this.y);
      keyFrame.setW(this.w);
      keyFrame.setH(this.h);
      keyFrame.setR(this.r);
      keyFrame.setG(this.g);
      keyFrame.setB(this.b);
      keyFrame.setT(this.t);
      keyFrame.setShape(this.shape);
      return keyFrame;
    }

    public Builder declareT(int tick) {
      this.t = tick;
      return this;
    }

    public Builder declareX(double x) {
      this.x = x;
      return this;
    }

    public Builder declareY(double y) {
      this.y = y;
      return this;
    }

    public Builder declareW(double w) {
      if (w < 1) {
        throw new IllegalArgumentException("Width cannot be less than 1.");
      }
      this.w = w;
      return this;
    }

    public Builder declareH(double h) {
      if (h < 1) {
        throw new IllegalArgumentException("Height cannot be less than 1.");
      }
      this.h = h;
      return this;
    }

    public Builder declareR(int r) {
      if (r < 0 || r > 255) {
        throw new IllegalArgumentException("Red cannot be less than 0 or greater than 255.");
      }
      this.r = r;
      return this;
    }

    public Builder declareG(int g) {
      if (g < 0 || g > 255) {
        throw new IllegalArgumentException("Green cannot be less than 0 or greater than 255.");
      }
      this.g = g;
      return this;
    }

    public Builder declareB(int b) {
      if (b < 0 || b > 255) {
        throw new IllegalArgumentException("Blue cannot be less than 0 or greater than 255.");
      }
      this.b = b;
      return this;
    }

    public Builder declareShape(IShape shape) {
      if (shape == null) {
        throw new IllegalArgumentException("Shape cannot be null.");
      }
      this.shape = shape;
      return this;
    }
  }

  @Override
  public void setX(double x) {
    this.x = x;
  }

  @Override
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public void setW(double w) {
    if (w < 1) {
      throw new IllegalArgumentException("Width cannot be less than 1.");
    }
    this.w = w;
  }

  @Override
  public void setH(double h) {
    if (h < 1) {
      throw new IllegalArgumentException("Height cannot be less than 1.");
    }
    this.h = h;
  }

  @Override
  public void setR(int r) {
    if (r < 0 || r > 255) {
      throw new IllegalArgumentException("Red value cannot be less than 0 or greater than 255");
    }
    this.r = r;
  }

  @Override
  public void setG(int g) {
    if (g < 0 || g > 255) {
      throw new IllegalArgumentException("Green value cannot be less than 0 or greater than 255");
    }
    this.g = g;
  }

  @Override
  public void setB(int b) {
    if (b < 0 || b > 255) {
      throw new IllegalArgumentException("Blue value cannot be less than 0 or greater than 255");
    }
    this.b = b;
  }

  @Override
  public void setT(int t) {
    this.t = t;
  }

  @Override
  public void setShape(IShape shape) {
    this.shape = shape;
  }

  @Override
  public void editKeyFrame() {

  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public double getW() {
    return this.w;
  }

  @Override
  public double getH() {
    return this.h;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }

  @Override
  public int getT() {
    return this.t;
  }

  @Override
  public IShape getShape() {
    return this.shape;
  }
}
