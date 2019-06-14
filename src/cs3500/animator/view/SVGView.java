package cs3500.animator.view;

import java.io.PrintWriter;
import java.util.ArrayList;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.motion.IMotion;
import cs3500.animator.model.shape.IShape;

public class SVGView implements IAnimatorView {
  private final int speed;
  private StringBuilder svgOutput;
  IAnimatorModel model;

  public SVGView(IAnimatorModel model, int width, int height) {
    this(model, 1, "System.out", width, height);
  }

  public SVGView(IAnimatorModel model, int speed, int width, int height) {
    this(model, speed, "System.out", width, height);
  }

  public SVGView(IAnimatorModel model, String out, int width, int height) {
    this(model, 1, out, width, height);
  }

  public SVGView(IAnimatorModel model, int speed, String out, int width, int height) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model and out input cannot be null.");
    } else if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height cannot be 0 or less.");
    } else if ((!out.contains(".svg") && !out.equals("System.out")) || out.length() < 5) {
      throw new IllegalArgumentException("Out must be formatted in the following manner: name.svg "
              + "or System.out");
    }

    this.model = model;
    this.speed = speed;
    this.svgOutput = new StringBuilder("<svg width=\"" + width + "\" height=\""
            + height + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    this.animate();
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
   * Method used to animate the given motions and format the svg properly.
   */
  private void animate() {
    ArrayList<String> keys = this.model.returnKeys();

    for (String key : keys) {
      ArrayList<IMotion> currentShapeMotions = this.model.returnMotions().get(key);
      IShape currentShape = this.model.findShape(key);
      IMotion firstMotion = currentShapeMotions.get(0);
      if (firstMotion != null) {
        switch (currentShape.getShapeType()) {
          case RECTANGLE:
            this.svgOutput.append("<rect id=\"").append(key).append("\" x=\"")
                    .append(firstMotion.getXStart()).append("\" y=\"")
                    .append(firstMotion.getYStart()).append("\" width=\"")
                    .append(firstMotion.getWStart()).append("\" height=\"")
                    .append(firstMotion.getHStart()).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"visible\" >\n");
            for (IMotion motion : currentShapeMotions) {
              this.svgOutput.append(this.addVector(motion, "x", motion.getXStart(), motion.getXEnd()))
                      .append(this.addVector(motion, "y", motion.getYStart(), motion.getYEnd()))
                      .append(this.addVector(motion, "w", motion.getWStart(), motion.getWEnd()))
                      .append(this.addVector(motion, "h", motion.getHStart(), motion.getHEnd()))
                      .append(this.addVector(motion, "r", motion.getRStart(), motion.getREnd()))
                      .append(this.addVector(motion, "g", motion.getGStart(), motion.getGEnd()))
                      .append(this.addVector(motion, "b", motion.getBStart(), motion.getBEnd()));
            }

            this.svgOutput.append("\n</rect>");
            break;

          case ELLIPSE:
            this.svgOutput.append("<ellipse id=\"").append(key).append("\" cx=\"")
                    .append(firstMotion.getXStart()).append("\" cy=\"")
                    .append(firstMotion.getYStart()).append("\" rx=\"")
                    .append(firstMotion.getWStart()).append("\" ry=\"")
                    .append(firstMotion.getHStart()).append("\" fill=\"rgb(")
                    .append(firstMotion.getRStart()).append(",")
                    .append(firstMotion.getGStart()).append(",")
                    .append(firstMotion.getBStart()).append(")\" visibility=\"visible\" >\n");
            for (IMotion motion : currentShapeMotions) {
              this.svgOutput.append(this.addVector(motion, "cx", motion.getXStart(), motion.getXEnd()))
                      .append(this.addVector(motion, "cy", motion.getYStart(), motion.getYEnd()))
                      .append(this.addVector(motion, "rx", motion.getWStart(), motion.getWEnd()))
                      .append(this.addVector(motion, "ry", motion.getHStart(), motion.getHEnd()))
                      .append(this.addVector(motion, "r", motion.getRStart(), motion.getREnd()))
                      .append(this.addVector(motion, "g", motion.getGStart(), motion.getGEnd()))
                      .append(this.addVector(motion, "b", motion.getBStart(), motion.getBEnd()));
            }

            this.svgOutput.append("\n</ellipse>");
            break;
        }
      }
    }
  }

  private String addVector(IMotion motion, String property, int startProperty, int stopProperty) {
    if (!((stopProperty - startProperty) == 0)) {
      switch (property) {
        case "x":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.getTStart() / this.speed
                  + "ms\" dur=\"" + (motion.getTEnd() - motion.getTStart()) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\""
                  + motion.startShape.x + "\" to=\"" + motion.stopShape.x
                  + "\" fill=\"freeze\" />\n";
        case "cx":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\""
                  + motion.startShape.x + "\" to=\"" + motion.stopShape.x
                  + "\" fill=\"freeze\" />\n";
        case "y":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\"" + motion.startShape.y
                  + "\" to=\"" + motion.stopShape.y + "\" fill=\"freeze\" />\n";
        case "cy":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\""
                  + motion.startShape.y + "\" to=\"" + motion.stopShape.y
                  + "\" fill=\"freeze\" />\n";
        case "w":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + "width" + "\" from=\"" + motion.startShape.w
                  + "\" to=\"" + motion.stopShape.w + "\" fill=\"freeze\" />\n";
        case "rx":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\"" + motion.startShape.w
                  + "\" to=\"" + motion.stopShape.w + "\" fill=\"freeze\" />\n";
        case "h":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + "height" + "\" from=\""
                  + motion.startShape.h + "\" to=\"" + motion.stopShape.h
                  + "\" fill=\"freeze\" />\n";
        case "ry":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"" + property + "\" from=\"" + motion.startShape.h
                  + "\" to=\"" + motion.stopShape.h + "\" fill=\"freeze\" />\n";
        case "r":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"fill\" from=\"rgb("
                  + motion.startShape.r + "," + motion.startShape.g + "," + motion.startShape.b
                  + ")\" to=\"rgb(" + motion.stopShape.r + "," + motion.stopShape.g + ","
                  + motion.stopShape.b + ")\" fill=\"freeze\" />\n";
        case "g":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"fill\" from=\"rgb("
                  + motion.startShape.r + "," + motion.startShape.g + "," + motion.startShape.b
                  + ")\" to=\"rgb(" + motion.stopShape.r + "," + motion.stopShape.g + ","
                  + motion.stopShape.b + ")\" fill=\"freeze\" />\n";
        case "b":
          return "    <animate attributeType=\"xml\" begin=\"" + motion.startTime / this.speed
                  + "ms\" dur=\"" + (this.motion.stopTime - this.motion.startTime) / this.speed
                  + "ms\" attributeName=\"fill\" from=\"rgb("
                  + motion.startShape.r + "," + motion.startShape.g + "," + motion.startShape.b
                  + ")\" to=\"rgb(" + motion.stopShape.r + "," + motion.stopShape.g + ","
                  + motion.stopShape.b + ")\" fill=\"freeze\" />\n";
        default:
          return "";
      }
    }
    return "";
  }
}
