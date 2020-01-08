package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.edit.EditFrame;
import cs3500.animator.view.visual.AnimationView;

/**
 * Main class to run the animator through.
 */
public class Excellence {

  /**
   * Runs the Animation based on the given commands in the below format.
   * -in "name-of-animation-file".
   * -view "type-of-view".
   * -out "where-output-show-go".
   * -speed "integer-ticks-per-second".
   *
   * <p>This has 3 different views: SVG, Visual, and Text.
   * Speed is measured in 10's of ms per frame.
   *
   * @param args The input for the program
   */
  public static void main(String[] args) {
    String in = null;
    String out = "System.out";
    int speed = 1;
    String view = null;
    IAnimatorModel model = null;
    int index = 0;

    for (String arg : args) {
      switch (arg) {
        case "-in":
          index++;
          in = args[index];
          try {
            FileReader fileReader = new FileReader(in);
            model = AnimationReader.parseFile(fileReader, new AnimatorModelImpl.Builder());
            break;
          } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Could not find given input file.", "No file warning",
                    JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Could not find given input file.");
          }
        case "-out":
          index++;
          out = args[index];
          break;
        case "-view":
          index++;
          view = args[index];
          break;
        case "-speed":
          index++;
          speed = Integer.parseInt(args[index]);
          break;
        default:
          index++;
          break;
      }
    }

    if (in == null) {
      JOptionPane.showMessageDialog(new JFrame(),
              "The in file must be specified.", "No file specified warning",
              JOptionPane.WARNING_MESSAGE);
      throw new IllegalArgumentException("The in file must be specified.");
    } else if (model == null) {
      JOptionPane.showMessageDialog(new JFrame(),
              "Cannot parse the given file.", "Invalid file warning",
              JOptionPane.WARNING_MESSAGE);
      throw new IllegalArgumentException("Our program could not parse your file, please provide a "
              + "file in the correct format.");
    } else if (view == null) {
      JOptionPane.showMessageDialog(new JFrame(),
              "Must provide a valid view of type visual, svg, or text.",
              "Invalid view type warning",
              JOptionPane.WARNING_MESSAGE);
      throw new IllegalArgumentException("Must provide a valid view of type visual, svg, or text.");
    } else {
      switch (view) {
        case "text":
          IAnimatorView textView = new TextView.Builder().declareModel(model).declareOut(out)
                  .build();
          textView.animate();
          break;
        case "svg":
          IAnimatorView svgView = new SVGView.Builder().declareModel(model).declareOut(out)
                  .declareSpeed(speed).build();
          svgView.animate();
          break;
        case "visual":
          IAnimatorView animationView = new AnimationView.Builder().declareModel(model)
                  .declareSpeed(speed).build();
          animationView.animate();
          break;
        case "edit":
          IAnimatorView editView = new EditFrame(model, speed);
          editView.animate();
          break;
        default:
          JOptionPane.showMessageDialog(new JFrame(),
                  "View must either be of type text, svg, visual, or edit.",
                  "Invalid View",
                  JOptionPane.WARNING_MESSAGE);
          throw new IllegalArgumentException("View must either be of type text, svg, visual, or "
                  + "edit.");
      }
    }

  }
}
