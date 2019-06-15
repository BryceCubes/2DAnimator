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
  private final String out;

  /**
   * Constructor used to construct a textual animation.
   *
   * @param model   given animation model to get the data from
   * @param out     given output file path
   */
  public TextView(IAnimatorModel model, String out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model and out input cannot be null.");
    } else if ((!out.contains(".txt") && !out.equals("System.out")) || out.length() < 5) {
      throw new IllegalArgumentException("Out must be formatted in the following manner: name.txt "
              + "or System.out");
    }

    this.textOutput = new StringBuilder();
    this.model = model;
    this.out = out;


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
