package cs3500.animator.view;

import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.ReadOnlyIMotion;
import cs3500.animator.model.shape.ReadOnlyIShape;

public class SVGView implements IAnimatorView {
  private StringBuilder svgOutput;
  private IAnimatorModel model;
  private String out;

  /**
   * The base constructor for an SVGView that just makes an empty view.
   */
  private SVGView() {
  }

  /**
   * The Builder for the svg class that provides for the user to input the model, speed, and output
   * source, and, if they don't, it provides the standard values for them.
   */
  public static class Builder {
    SVGView svgView = new SVGView();
    IAnimatorModel model = null;
    String out = "System.out";

    public SVGView build() {
      if (this.model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      svgView.setModel(this.model);
      svgView.setOut(this.out);
      return svgView;
    }

    public Builder setModel(IAnimatorModel model) {
      this.model = model;
      return this;
    }

    public Builder setOut(String out) {
      if (out == null) {
        throw new IllegalArgumentException("Out cannot be null.");
      } else if ((!this.out.contains(".svg") && !this.out.equals("System.out"))
              || this.out.length() < 5) {
        throw new IllegalArgumentException("Out must be formatted in the following manner: name.svg"
                + " or System.out");
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

  /**
   * Method used to animate the given motions and format the svg properly.
   */
  @Override
  public void animate() {
    this.svgOutput = new StringBuilder("<svg width=\"" + this.model.getCanvasW() + "\" height=\""
            + this.model.getCanvasH() + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    ArrayList<ReadOnlyIShape> shapes = this.model.returnShapes();

    for (ReadOnlyIShape shape : shapes) {
      ArrayList<ReadOnlyIMotion> currentShapeMotions = this.model.returnMotions().get(shape);
      ReadOnlyIMotion firstMotion = currentShapeMotions.get(0);
      if (firstMotion != null) {
        switch (shape.getShapeType()) {
          case RECTANGLE:
            this.svgOutput.append("<rect id=\"").append(shape.getShapeID()).append("\" x=\"")
                    .append(firstMotion.getXStart()).append("\" y=\"")
                    .append(firstMotion.getYStart()).append("\" width=\"")
                    .append(firstMotion.getWStart()).append("\" height=\"")
                    .append(firstMotion.getHStart()).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"hidden\" >\n");
            for (ReadOnlyIMotion motion : currentShapeMotions) {
              this.svgOutput.append(this.addVector(motion, "x"))
                      .append(this.addVector(motion, "y"))
                      .append(this.addVector(motion, "w"))
                      .append(this.addVector(motion, "h"))
                      .append(this.addVector(motion, "rgb"));
            }

            this.svgOutput.append("\n</rect>");
            break;

          case ELLIPSE:
            this.svgOutput.append("<ellipse id=\"").append(shape.getShapeID()).append("\" cx=\"")
                    .append(firstMotion.getXStart()).append("\" cy=\"")
                    .append(firstMotion.getYStart()).append("\" rx=\"")
                    .append(firstMotion.getWStart()).append("\" ry=\"")
                    .append(firstMotion.getHStart()).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"hidden\" >\n");
            for (ReadOnlyIMotion motion : currentShapeMotions) {
              this.svgOutput.append(this.addVector(motion, "cx"))
                      .append(this.addVector(motion, "cy"))
                      .append(this.addVector(motion, "rx"))
                      .append(this.addVector(motion, "ry"))
                      .append(this.addVector(motion, "rgb"));
            }

            this.svgOutput.append("\n</ellipse>");
            break;

          default:
            throw new IllegalStateException("You should'nt be seeing this, something went wrong.");
        }
      }
    }

    this.svgOutput.append("</svg>");

    if (out.equals("System.out")) {
      System.out.println(svgOutput);
    } else {
      try {
        PrintWriter writer = new PrintWriter(out, "UTF-8");
        writer.println(svgOutput);
        writer.close();
      } catch (Exception e) {
        throw new IllegalStateException("Your computer was not able to write to an output file.");
      }
    }
  }

  /**
   * Method used to add a vector to the svg object for a specific shape so that the individual
   * parts of the shape could be animated.
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
      switch (property) {
        case "x":
        case "cx":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getXStart()).append("\" to=\"").append(motion.getXEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "y":
        case "cy":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getYStart()).append("\" to=\"").append(motion.getYEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "w":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append("width").append("\" from=\"")
                  .append(motion.getWStart()).append("\" to=\"").append(motion.getWEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "rx":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getWStart()).append("\" to=\"").append(motion.getWEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "h":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append("height").append("\" from=\"")
                  .append(motion.getHStart()).append("\" to=\"").append(motion.getHEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "ry":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"").append(property).append("\" from=\"")
                  .append(motion.getHStart()).append("\" to=\"").append(motion.getHEnd())
                  .append("\" fill=\"freeze\" />\n");
        case "rgb":
          returnString.append("    <animate attributeType=\"xml\" begin=\"")
                  .append((motion.getTStart() / this.model.getSpeed()) * 1000).append("ms\" dur=\"")
                  .append(((motion.getTEnd() - motion.getTStart()) / this.model.getSpeed()) * 1000)
                  .append("ms\" attributeName=\"fill\" from=\"rgb(").append(motion.getRStart())
                  .append(",").append(motion.getGStart()).append(",").append(motion.getBStart())
                  .append(")\" to=\"rgb(").append(motion.getREnd()).append(",")
                  .append(motion.getGEnd()).append(",").append(motion.getBEnd())
                  .append(")\" fill=\"freeze\" />\n");
      }
    }

    return returnString.toString();
  }
}
