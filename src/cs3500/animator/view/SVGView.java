package cs3500.animator.view;

import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Class used to construct a valid SVGview that can be animated to construct a valid formatting
 * for an svg file. These can be viewed either through System.out or in a given output file.
 */
public class SVGView implements IAnimatorView {
  private IAnimatorModel model;
  private String out;
  private int speed;

  /**
   * The base constructor for an SVGView that just makes an empty view.
   */
  private SVGView() {
  }

  /**
   * The Builder for the svg class that provides for the user to input the model, speed, and output
   * source, and, if they don't, it provides the standard values for them. This view runs at 10
   * ticks per second.
   */
  public static class Builder {
    private SVGView svgView = new SVGView();
    private IAnimatorModel model = null;
    private String out = "System.out";
    private int speed = 1;

    /**
     * Method used to build the SVGView with the correct parameters such that no errors should be
     * thrown.
     *
     * @return A valid SVGView
     */
    public SVGView build() {
      if (this.model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      svgView.setModel(this.model);
      svgView.setOut(this.out);
      svgView.setSpeed(this.speed);
      return svgView;
    }

    /**
     * Method used to declare a model to be input into the SVGView.
     *
     * @param model the given model to be used for the view
     * @return the Builder such that it can continue being built
     */
    public Builder declareModel(IAnimatorModel model) {
      this.model = model;
      return this;
    }

    /**
     * Method used to input the desired ouput for where the view should animate to.
     *
     * @param out the given output the view will animate to
     * @return the Builder such that it can continue to be built on
     */
    public Builder declareOut(String out) {
      if (out == null) {
        throw new IllegalArgumentException("Out cannot be null.");
      } else if ((!out.contains(".svg") && !out.equals("System.out")) || out.length() < 5) {
        throw new IllegalArgumentException("Out must be formatted in the following manner: name.svg"
                + " or System.out");
      }
      this.out = out;
      return this;
    }

    /**
     * Method used to declare the speed for the given view so the animation will be run at speed.
     *
     * @param speed the given speed for the view to animate at
     * @return a builder so it can continue to be built on
     */
    public Builder declareSpeed(int speed) {
      if (speed < 1) {
        throw new IllegalArgumentException("Speed cannot be less than 1.");
      } else {
        this.speed = speed;
      }

      return this;
    }
  }

  /**
   * Method used to set the output of the given view.
   *
   * @param out the output for the given view
   */
  private void setOut(String out) {
    this.out = out;
  }

  /**
   * The method used to set the model for the given view.
   *
   * @param model the model for the given view
   */
  private void setModel(IAnimatorModel model) {
    this.model = model;
  }

  /**
   * The method used to set the speed for the given view.
   *
   * @param speed the speed for the view to run at
   */
  private void setSpeed(int speed) {
    this.speed = speed;
  }

  /**
   * Method used to animate the given motions and format the svg properly.
   */
  @Override
  public void animate() {
    StringBuilder svgOutput = new StringBuilder("<svg width=\""
            + (this.model.getCanvasW() + this.model.getCanvasX()) + "\" " + "height=\""
            + (this.model.getCanvasH() + this.model.getCanvasY()) + "\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n");
    ArrayList<ReadOnlyIShape> shapes = this.model.getShapes();

    for (ReadOnlyIShape shape : shapes) {
      ArrayList<ReadOnlyIMotion> currentShapeMotions = this.model.returnMotions().get(shape);
      ReadOnlyIMotion firstMotion = currentShapeMotions.get(0);
      ReadOnlyIMotion lastMotion = currentShapeMotions.get(currentShapeMotions.size() - 1);

      if (firstMotion != null) {
        switch (shape.getShapeType()) {
          case RECTANGLE:
            svgOutput.append("<rect id=\"").append(shape.getShapeID()).append("\" x=\"")
                    .append(firstMotion.getXStart()).append("\" y=\"")
                    .append(firstMotion.getYStart()).append("\" width=\"")
                    .append(firstMotion.getWStart()).append("\" height=\"")
                    .append(firstMotion.getHStart()).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"hidden\" >\n");
            for (ReadOnlyIMotion motion : currentShapeMotions) {
              svgOutput.append(this.addVector(motion, "x"))
                      .append(this.addVector(motion, "y"))
                      .append(this.addVector(motion, "w"))
                      .append(this.addVector(motion, "h"))
                      .append(this.addVector(motion, "rgb"));
            }
            svgOutput.append("    <set attributeType=\"xml\" begin=\"").append((firstMotion
                    .getTStart() / this.speed) * (1000)).append("ms\" dur=\"")
                    .append((lastMotion.getTEnd() - firstMotion.getTStart() / this.speed)
                            * (1000))
                    .append("ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" "
                            + "/>\n");
            svgOutput.append("</rect>\n");
            break;

          case ELLIPSE:
            svgOutput.append("<ellipse id=\"").append(shape.getShapeID()).append("\" cx=\"")
                    .append(firstMotion.getXStart()).append("\" cy=\"")
                    .append(firstMotion.getYStart()).append("\" rx=\"")
                    .append(firstMotion.getWStart() / 2).append("\" ry=\"")
                    .append(firstMotion.getHStart() / 2).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"hidden\" >\n");
            for (ReadOnlyIMotion motion : currentShapeMotions) {
              svgOutput.append(this.addVector(motion, "cx"))
                      .append(this.addVector(motion, "cy"))
                      .append(this.addVector(motion, "rx"))
                      .append(this.addVector(motion, "ry"))
                      .append(this.addVector(motion, "rgb"));
            }
            svgOutput.append("    <set attributeType=\"xml\" begin=\"").append((firstMotion
                    .getTStart() / this.speed) * (1000)).append("ms\" dur=\"")
                    .append((lastMotion.getTEnd() - firstMotion.getTStart() / this.speed)
                            * (1000))
                    .append("ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" "
                            + "/>\n").append("</ellipse>\n");
            break;

          default:
            throw new IllegalStateException("You should'nt be seeing this, something went wrong.");
        }
      }
    }

    svgOutput.append("</svg>");

    if (out.equals("System.out")) {
      System.out.print(svgOutput);
    } else {
      try {
        PrintWriter writer = new PrintWriter(out, "UTF-8");
        writer.print(svgOutput);
        writer.close();
      } catch (Exception e) {
        throw new IllegalStateException("Your computer was not able to write to an output file.");
      }
    }
  }

  /**
   * Method used to add a vector to the svg object for a specific shape so that the individual parts
   * of the shape could be animated.
   *
   * @param motion   the given motion to be animated in the svg format
   * @param property the given property to be animated
   * @return a string formatted in the proper way for svg from given motion
   */
  private String addVector(ReadOnlyIMotion motion, String property) {
    StringBuilder returnString = new StringBuilder();
    int deltaProperty = 0;
    switch (property) {
      case "x":
      case "cx":
        deltaProperty = motion.getXStart() - motion.getXEnd();
        break;
      case "y":
      case "cy":
        deltaProperty = motion.getYStart() - motion.getYEnd();
        break;
      case "w":
      case "rx":
        deltaProperty = motion.getWStart() - motion.getWEnd();
        break;
      case "h":
      case "ry":
        deltaProperty = motion.getHStart() - motion.getHEnd();
        break;
      case "rgb":
        if (motion.getRStart() != motion.getREnd()) {
          deltaProperty = motion.getRStart() - motion.getREnd();
        } else if (motion.getGStart() != motion.getGEnd()) {
          deltaProperty = motion.getGStart() - motion.getGEnd();
        } else if (motion.getBStart() != motion.getBEnd()) {
          deltaProperty = motion.getBStart() - motion.getBEnd();
        }
        break;
      default:
        throw new IllegalStateException("If you're seeing this error, something is wrong.");
    }

    if (deltaProperty != 0) {
      int duration = (motion.getTEnd() - motion.getTStart()) * (1000 / this.speed);
      String dur = " ";
      if (duration != 0) {
        dur = " dur=\"" + duration + "ms\" ";
      }
      switch (property) {
        case "x":
        case "cx":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getXStart()).append("\" to=\"").append(motion.getXEnd())
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "y":
        case "cy":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getYStart()).append("\" to=\"").append(motion.getYEnd())
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "w":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append("width").append("\" from=\"")
                  .append(motion.getWStart()).append("\" to=\"").append(motion.getWEnd())
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "rx":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getWStart() / 2).append("\" to=\"").append(motion.getWEnd() / 2)
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "h":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append("height").append("\" from=\"")
                  .append(motion.getHStart()).append("\" to=\"").append(motion.getHEnd())
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "ry":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getHStart() / 2).append("\" to=\"").append(motion.getHEnd() / 2)
                  .append("\" fill=\"freeze\" />\n");
          break;
        case "rgb":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.speed) * (1000)).append("ms\"").append(dur)
                  .append("attributeName=\"fill\" from=\"rgb(").append(motion.getRStart())
                  .append(",").append(motion.getGStart()).append(",").append(motion.getBStart())
                  .append(")\" to=\"rgb(").append(motion.getREnd()).append(",")
                  .append(motion.getGEnd()).append(",").append(motion.getBEnd())
                  .append(")\" fill=\"freeze\" />\n");
          break;
        default:
          throw new IllegalArgumentException("You shouldn't be seeing this.");
      }
    }

    return returnString.toString();
  }
}
