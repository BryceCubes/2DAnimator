package cs3500.animator.view;

import java.io.PrintWriter;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Textual view for viewing an animation.
 */
public class TextView implements IAnimatorView {
  private IAnimatorModel model;
  private StringBuilder textOutput;
  private String out;
  private int speed;


  /**
   * Constructor used to create a text view using a Builder to input the model and the output.
   */
  private TextView() {
  }

  /**
   * The Builder method used to set the model and the output for the TextView
   */
  public static class Builder {
    private TextView textView = new TextView();
    private IAnimatorModel model = null;
    private String out = "System.out";
    private int speed = 1;

    public TextView build() {
      if (this.model == null) {
        throw new IllegalArgumentException("Model must be set to a value.");
      }
      textView.setModel(this.model);

      this.textView.setOut(this.out);
      this.textView.setSpeed(this.speed);
      return textView;
    }

    public Builder setModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    public Builder setOut(String out) {
      if (out == null) {
        throw new IllegalArgumentException("Out cannot be null.");
      } else if ((!out.contains(".txt") && !out.equals("System.out")) || out.length() < 5) {
        throw new IllegalArgumentException("Out must be formatted in the following manner: name.txt"
                + " or System.out");
      }

      this.out = out;
      return this;
    }

    public Builder setSpeed(int speed) {
      if (speed < 1) {
        throw new IllegalArgumentException("Speed cannot be less than 1.");
      } else {
        this.speed = speed;
      }

      return this;
    }
  }

  @Override
  public void setOut(String out) {
    this.out = out;
  }

  @Override
  public void setModel(IAnimatorModel model) {
    this.model = model;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void animate() {
    this.textOutput.append("canvas ").append(model.getCanvasX()).append(" ")
            .append(model.getCanvasY()).append(" ").append(model.getCanvasW()).append(" ")
            .append(model.getCanvasH()).append("\n");

    for (ReadOnlyIShape shape : this.model.returnShapes()) {
      this.textOutput.append("shape ").append(shape.getShapeID()).append(" ")
              .append(shape.getShapeTypeAsString()).append("\n");
      for (ReadOnlyIMotion motion : model.returnMotions().get(shape)) {
        this.textOutput.append(motion.getTextOutput());
      }
    }

    if (out.equals("System.out")) {
      System.out.println(textOutput);
    } else {
      try {
        PrintWriter writer = new PrintWriter(out, "UTF-8");
        writer.println(textOutput);
        writer.close();
      } catch (Exception e) {
        throw new IllegalStateException("Your computer was not able to write to file.");
      }
    }
  }
}
