package cs3500.animator.view;

import java.io.PrintWriter;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Textual view for viewing an animation.
 */
public class TextView implements IAnimatorView {
  private final IAnimatorModel model;
  private StringBuilder textOutput;

  /**
   * Constructor used to construct a textual animation.
   *
   * @param model   given animation model to get the data from
   * @param out     given output file path
   * @param canvasX x location for top left corner of canvas
   * @param canvasY y location for top left corner of canvase
   * @param width   width of canvas
   * @param height  height of canvas
   */
  public TextView(IAnimatorModel model, String out, int canvasX, int canvasY, int width,
                  int height) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model and out input cannot be null.");
    } else if (canvasX < 0 || canvasY < 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Canvas xy coordinate must not be negative. Width and " +
              "height cannot be 0 or less.");
    } else if ((!out.contains(".txt") && !out.equals("System.out")) || out.length() < 5) {
      throw new IllegalArgumentException("Out must be formatted in the following manner: name.txt "
              + "or System.out");
    }

    this.textOutput = new StringBuilder();
    this.model = model;
    this.textOutput.append("canvas ").append(canvasX).append(" ").append(canvasY).append(" ")
            .append(width).append(" ").append(height).append("\n");
    this.animate();

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

  /**
   * Adds all of the motion data to the output string.
   */
  private void animate() {
    for (String key : this.model.returnKeys()) {
      ReadOnlyIShape currentShape = this.model.findShape(key);
      this.textOutput.append("shape ").append(currentShape.getShapeID()).append(" ")
              .append(currentShape.getShapeTypeAsString()).append("\n");
      for (ReadOnlyIMotion motion : model.returnMotions().get(key)) {
        this.textOutput.append(motion.getTextOutput());
      }
    }
  }
}
