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

  /**
   * Constructor used to create a text view using a Builder to input the model and the output.
   */
  private TextView() {
  }

  /**
   * The Builder method used to set the model and the output for the TextView.
   */
  public static class Builder {
    private TextView textView = new TextView();
    private IAnimatorModel model = null;
    private String out = "System.out";

    /**
     * The method used to construct a valid text view that can output properly.
     *
     * @return a text view that can be animated
     */
    public TextView build() {
      if (this.model == null) {
        throw new IllegalArgumentException("Model must be set to a value.");
      }
      textView.setModel(this.model);
      this.textView.setOut(this.out);
      this.textView.setTextOutput();
      return textView;
    }

    /**
     * Method used to declare the model for the given view to animate.
     *
     * @param model the model that will be animated for the given view
     * @return a Builder to continue to be built on
     */
    public Builder declareModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    /**
     * Method used to declare the output for where the view will produce its animation.
     *
     * @param out the output where the view will produce the animation
     * @return the Builder to continue to be built on
     */
    public Builder declareOut(String out) {
      if (out == null) {
        throw new IllegalArgumentException("Out cannot be null.");
      } else if ((!out.contains(".txt") && !out.equals("System.out")) || out.length() < 5) {
        throw new IllegalArgumentException("Out must be formatted in the following manner: name.txt"
                + " or System.out");
      }
      this.out = out;
      return this;
    }
  }

  /**
   * Method used to initialize the text output stringbuilder.
   */
  private void setTextOutput() {
    this.textOutput = new StringBuilder();
  }

  /**
   * Method used to set the output to where the view should animate to.
   *
   * @param out the output to where the view will animate to
   * @throws IllegalArgumentException when out is null
   */
  private void setOut(String out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Out cannot be null.");
    }
    this.out = out;
  }

  /**
   * Method used to set the model to be animated on for the given view.
   *
   * @param model the model to be animated for the given view
   * @throws IllegalArgumentException when model is null
   */
  private void setModel(IAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public void animate() {
    this.textOutput.append("canvas ").append(model.getCanvasX()).append(" ")
            .append(model.getCanvasY()).append(" ").append(model.getCanvasW()).append(" ")
            .append(model.getCanvasH()).append("\n");

    for (ReadOnlyIShape shape : this.model.getShapes()) {
      this.textOutput.append("shape ").append(shape.getShapeID()).append(" ")
              .append(shape.getShapeTypeAsString()).append("\n");
      for (ReadOnlyIMotion motion : model.returnMotions().get(shape)) {
        this.textOutput.append(motion.getTextOutput());
      }
    }

    if (out.equals("System.out")) {
      System.out.print(textOutput);
    } else {
      try {
        PrintWriter writer = new PrintWriter(out, "UTF-8");
        writer.print(textOutput);
        writer.close();
      } catch (Exception e) {
        throw new IllegalStateException("Your computer was not able to write to file.");
      }
    }
  }
}
