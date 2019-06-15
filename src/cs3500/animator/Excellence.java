package cs3500.animator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimatorView;

/**
 * Main class to run the animator through.
 */
public class Excellence {
  public static void main(String[] args) {
    StringReader in = null;
    String input = null;
    String out = null;
    Integer speed = null;
    IAnimatorView view = null;
    IAnimatorModel model = null;
    int index = 0;

    for (String arg : args) {
      switch (arg) {
        case "-in":
          index ++;
          try {
            Scanner sc = new Scanner(new File(args[index]));
            while(sc.hasNextLine()){
              input += sc.nextLine();
            }
            in = in.append(input);
          } catch (IOException e) {
            throw new IllegalArgumentException("Could not find given input file.");
          }



      }
    }

    if (in == null || view == null) {
      throw new IllegalArgumentException("The in file and view must be specified.");
    } else if (model == null) {
      throw new IllegalArgumentException("Our program could not parse your file, please provide a "
              + "file in the correct format.");
    } else {
      AnimationReader reader = new AnimationReader();
      reader.parseFile(in, new AnimatorModelImpl.Builder());
    }

  }
}
