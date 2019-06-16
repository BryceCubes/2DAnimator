package cs3500.animator.view;

import java.io.PrintWriter;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;
import javafx.util.Builder;

/**
 * Textual view for viewing an animation.
 */
public class TextView implements IAnimatorView {
  private final IAnimatorModel model;
  private StringBuilder textOutput;
  private final String out;

  /**
   * Constructor used to create a text view using a Builder to input the model and the output.
   * @param builder The Builder given to the textView containing the model and output
   */
  private TextView() {
  }

  /**
   * The Builder method used to set the model and the output for the TextView
   */
  public static class TextBuilder {
    private TextView = new;
    private IAnimatorModel model = null;
    private String out = "System.out";

    public TextView build() {
      ;
    }

    public TextBuilder setModel(IAnimatorModel model) {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      this.model = model;
      return this;
    }

    public TextBuilder setOut(String out) {
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
   * Adds all of the motion data to the output string.
   */
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
