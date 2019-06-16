package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.visual.AnimationView;

/**
 * Main class to run the animator through.
 */
public class Excellence {
  public static void main(String[] args) {
    String in = null;
    String out = "System.out";
    Integer speed = 1;
    String view = null;
    IAnimatorModel model = null;
    int index = 0;

    for (String arg : args) {
      switch (arg) {
        case "-in":
          index ++;
          in = args[index];
          try {
            FileReader fileReader = new FileReader(in);
            AnimationReader reader = new AnimationReader();
            model = reader.parseFile(fileReader, new AnimatorModelImpl.Builder());
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find given input file.");
          }
          break;
        case "out":
          index ++;
          out = args[index];
          break;
        case "view":
          index++;
          view = args[index];
          break;
        case "speed":
          index++;
          speed = Integer.parseInt(args[index]);
          break;
        default:
          index++;
          break;
      }
    }

    if (in == null) {
      throw new IllegalArgumentException("The in file must be specified.");
    } else if (model == null) {
      throw new IllegalArgumentException("Our program could not parse your file, please provide a "
              + "file in the correct format.");
    } else if (view == null){
      throw new IllegalArgumentException("Must provide a valid view of type visual, svg, or text.");
    } else {
      switch (view) {
        case "text":
          IAnimatorView textView = new TextView.Builder().setModel(model).setOut(out).build();
          textView.animate();
          break;
        case "svg":
          IAnimatorView svgView = new SVGView.Builder().setModel(model).setOut(out).setSpeed(speed)
                  .build();
          svgView.animate();
          break;
        case "visual":
          IAnimatorView animationView = new AnimationView.Builder().setModel(model).setSpeed(speed)
                  .build();
          animationView.animate();
          break;
        default:
          throw new IllegalArgumentException("Must provide a valid view of type visual, svg, or "
                  + "text.");
      }
    }

  }
}
