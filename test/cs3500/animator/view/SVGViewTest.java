package cs3500.animator.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Class used to ensure that the svg view outputs, formats code, and constructs correctly.
 */
public class SVGViewTest {
  private IAnimatorModel model = new AnimatorModelImpl.Builder().declareShape("Fred", "RecTanGle")
          .declareShape("Amy", "ellipse")
          .declareShape("Ethan", "ELLIPSE")
          .addMotion("Ethan", 30, 25, 25, 15, 15, 120,
                  180, 95, 60, 40, 15, 20, 30,
                  180, 120, 230)
          .addMotion("Ethan", 0, 25, 25, 15, 15, 180,
                  120, 230, 30, 25, 25, 15, 15,
                  120, 180, 95)
          .addMotion("Amy", 125, 50, 50, 20, 30, 0,
                  100, 255, 150, 50, 50, 10, 20,
                  0, 100, 255)
          .addMotion("Amy", 100, 50, 50, 30, 30, 0,
                  100, 255, 125, 50, 50, 20, 30, 0, 100, 255)
          .addMotion("Amy", 75, 50, 50, 30, 35, 0,
                  100, 255, 100, 50, 50, 30, 30,
                  0, 100, 255)
          .addMotion("Amy", 50, 50, 50, 20, 25, 0,
                  100, 255, 75, 50, 50, 30, 35,
                  0, 100, 255)
          .addMotion("Amy", 25, 50, 50, 10, 25, 0,
                  100, 255, 50, 50, 50, 20, 25,
                  0, 100, 255)
          .addMotion("Amy", 0, 50, 50, 10, 20, 0,
                  100, 255, 25, 50, 50, 10, 25,
                  0, 100, 255)
          .addMotion("Fred", 50, 15, 5, 5, 5, 255,
                  150, 10, 60, 10, 20, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 40, 5, 15, 5, 5, 255,
                  150, 10, 50, 15, 5, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 30, 5, 5, 5, 5, 255,
                  150, 10, 40, 5, 15, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 20, 5, 10, 5, 5, 255,
                  150, 10, 30, 5, 5, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 10, 15, 10, 5, 5, 255,
                  150, 10, 20, 5, 10, 5, 5,
                  255, 150, 10)
          .addMotion("Fred", 0, 10, 10, 5, 5, 255,
                  150, 10, 10, 15, 10, 5, 5,
                  255, 150, 10).setBounds(1, 2, 3, 4).build();
  private IAnimatorView modelSystemView = new SVGView.Builder().declareModel(model).build();
  private IAnimatorView modelFileView = new SVGView.Builder().declareModel(model)
          .declareOut("test.svg").build();
  private final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStream() {
    System.setOut(new PrintStream(newOut));
  }

  @After
  public void restoreStream() {
    System.setOut(originalOut);
  }

  @Test(expected = IllegalArgumentException.class)
  public void modelNotSet() {
    new SVGView.Builder().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void modelSetToNull() {
    new SVGView.Builder().declareModel(null).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void outLessThan5Chars() {
    new SVGView.Builder().declareModel(model).declareOut("ab").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void outDoesntContainSVG() {
    new SVGView.Builder().declareModel(model).declareOut("abcdefg").build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void outIsNull() {
    new SVGView.Builder().declareModel(model).declareOut(null).build();
  }

  @Test
  public void testSystemOut() {
    modelSystemView.animate();
    assertEquals("<svg width=\"4\" height=\"6\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n<rect id=\"Fred\" x=\"10\" y=\"10\" "
            + "width=\"5\" height=\"5\" fill=\"rgb(255,150,10)\" visibility=\"hidden\" >\n    "
            + "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10000ms\" attributeName=\"x\" "
            + "from=\"10\" to=\"15\" fill=\"freeze\" />\n    <animate attributeType=\"xml\" "
            + "begin=\"10000ms\" dur=\"10000ms\" attributeName=\"x\" from=\"15\" to=\"5\" "
            + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"20000ms\" "
            + "dur=\"10000ms\" attributeName=\"y\" from=\"10\" to=\"5\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"30000ms\" dur=\"10000ms\" "
            + "attributeName=\"y\" from=\"5\" to=\"15\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" "
            + "begin=\"40000ms\" dur=\"10000ms\" attributeName=\"x\" from=\"5\" to=\"15\" "
            + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"40000ms\" "
            + "dur=\"10000ms\" attributeName=\"y\" from=\"15\" to=\"5\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"50000ms\" dur=\"10000ms\" "
            + "attributeName=\"x\" from=\"15\" to=\"10\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" "
            + "begin=\"50000ms\" dur=\"10000ms\" attributeName=\"y\" from=\"5\" to=\"20\" "
            + "fill=\"freeze\" />\n    <set attributeType=\"xml\" begin=\"0ms\" dur=\"60000ms\" "
            + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" />\n</rect>\n"
            + "<ellipse id=\"Amy\" cx=\"50\" cy=\"50\" rx=\"5\" ry=\"10\" fill=\"rgb(0,100,255)\" "
            + "visibility=\"hidden\" >\n    <animate attributeType=\"xml\" begin=\"0ms\" "
            + "dur=\"25000ms\" attributeName=\"ry\" from=\"10\" to=\"12\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"25000ms\" dur=\"25000ms\" "
            + "attributeName=\"rx\" from=\"5\" to=\"10\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" "
            + "begin=\"50000ms\" dur=\"25000ms\" attributeName=\"rx\" from=\"10\" to=\"15\" "
            + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"50000ms\" "
            + "dur=\"25000ms\" attributeName=\"ry\" from=\"12\" to=\"17\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"75000ms\" dur=\"25000ms\" "
            + "attributeName=\"ry\" from=\"17\" to=\"15\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" "
            + "begin=\"100000ms\" dur=\"25000ms\" attributeName=\"rx\" from=\"15\" to=\"10\" "
            + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"125000ms\" "
            + "dur=\"25000ms\" attributeName=\"rx\" from=\"10\" to=\"5\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"125000ms\" dur=\"25000ms\" "
            + "attributeName=\"ry\" from=\"15\" to=\"10\" fill=\"freeze\" />\n    "
            + "<set attributeType=\"xml\" "
            + "begin=\"0ms\" dur=\"150000ms\" attributeName=\"visibility\" from=\"hidden\" "
            + "to=\"visible\" />\n</ellipse>\n<ellipse id=\"Ethan\" cx=\"25\" cy=\"25\" rx=\"7\" "
            + "ry=\"7\" fill=\"rgb(180,120,230)\" visibility=\"hidden\" >\n    <animate "
            + "attributeType=\"xml\" begin=\"0ms\" dur=\"30000ms\" attributeName=\"fill\" "
            + "from=\"rgb(180,120,230)\" to=\"rgb(120,180,95)\" fill=\"freeze\" />\n    <animate "
            + "attributeType=\"xml\" begin=\"30000ms\" dur=\"30000ms\" attributeName=\"cx\" "
            + "from=\"25\" to=\"40\" fill=\"freeze\" />\n    <animate attributeType=\"xml\" "
            + "begin=\"30000ms\" dur=\"30000ms\" attributeName=\"cy\" from=\"25\" to=\"15\" "
            + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"30000ms\" "
            + "dur=\"30000ms\" attributeName=\"rx\" from=\"7\" to=\"10\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" begin=\"30000ms\" dur=\"30000ms\" "
            + "attributeName=\"ry\" from=\"7\" to=\"15\" fill=\"freeze\" />\n    "
            + "<animate attributeType=\"xml\" "
            + "begin=\"30000ms\" dur=\"30000ms\" attributeName=\"fill\" from=\"rgb(120,180,95)\" "
            + "to=\"rgb(180,120,230)\" fill=\"freeze\" />\n    <set attributeType=\"xml\" "
            + "begin=\"0ms\" dur=\"60000ms\" attributeName=\"visibility\" from=\"hidden\" "
            + "to=\"visible\" />\n</ellipse>\n</svg>", newOut.toString());
  }

  @Test
  public void fileWrite() {
    modelFileView.animate();
    try {
      InputStream stream = new FileInputStream("test.svg");
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

      String line = reader.readLine();
      StringBuilder sb = new StringBuilder();

      while (line != null) {
        sb.append(line).append("\n");
        line = reader.readLine();
      }

      String fileAsString = sb.toString();
      assertEquals("<svg width=\"4\" height=\"6\" version=\"1.1\" "
              + "xmlns=\"http://www.w3.org/2000/svg\">\n<rect id=\"Fred\" x=\"10\" y=\"10\" "
              + "width=\"5\" height=\"5\" fill=\"rgb(255,150,10)\" visibility=\"hidden\" >\n    "
              + "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"10000ms\" attributeName=\"x\" "
              + "from=\"10\" to=\"15\" fill=\"freeze\" />\n    <animate attributeType=\"xml\" "
              + "begin=\"10000ms\" dur=\"10000ms\" attributeName=\"x\" from=\"15\" to=\"5\" "
              + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"20000ms\" "
              + "dur=\"10000ms\" attributeName=\"y\" from=\"10\" to=\"5\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"30000ms\" dur=\"10000ms\" "
              + "attributeName=\"y\" from=\"5\" to=\"15\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" "
              + "begin=\"40000ms\" dur=\"10000ms\" attributeName=\"x\" from=\"5\" to=\"15\" "
              + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"40000ms\" "
              + "dur=\"10000ms\" attributeName=\"y\" from=\"15\" to=\"5\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"50000ms\" dur=\"10000ms\" "
              + "attributeName=\"x\" from=\"15\" to=\"10\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" "
              + "begin=\"50000ms\" dur=\"10000ms\" attributeName=\"y\" from=\"5\" to=\"20\" "
              + "fill=\"freeze\" />\n    <set attributeType=\"xml\" begin=\"0ms\" dur=\"60000ms\" "
              + "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" />\n</rect>\n"
              + "<ellipse id=\"Amy\" cx=\"50\" cy=\"50\" rx=\"5\" ry=\"10\" fill=\"rgb(0,100,255)\" "
              + "visibility=\"hidden\" >\n    <animate attributeType=\"xml\" begin=\"0ms\" "
              + "dur=\"25000ms\" attributeName=\"ry\" from=\"10\" to=\"12\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"25000ms\" dur=\"25000ms\" "
              + "attributeName=\"rx\" from=\"5\" to=\"10\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" "
              + "begin=\"50000ms\" dur=\"25000ms\" attributeName=\"rx\" from=\"10\" to=\"15\" "
              + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"50000ms\" "
              + "dur=\"25000ms\" attributeName=\"ry\" from=\"12\" to=\"17\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"75000ms\" dur=\"25000ms\" "
              + "attributeName=\"ry\" from=\"17\" to=\"15\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" "
              + "begin=\"100000ms\" dur=\"25000ms\" attributeName=\"rx\" from=\"15\" to=\"10\" "
              + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"125000ms\" "
              + "dur=\"25000ms\" attributeName=\"rx\" from=\"10\" to=\"5\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"125000ms\" dur=\"25000ms\" "
              + "attributeName=\"ry\" from=\"15\" to=\"10\" fill=\"freeze\" />\n    "
              + "<set attributeType=\"xml\" "
              + "begin=\"0ms\" dur=\"150000ms\" attributeName=\"visibility\" from=\"hidden\" "
              + "to=\"visible\" />\n</ellipse>\n<ellipse id=\"Ethan\" cx=\"25\" cy=\"25\" rx=\"7\" "
              + "ry=\"7\" fill=\"rgb(180,120,230)\" visibility=\"hidden\" >\n    <animate "
              + "attributeType=\"xml\" begin=\"0ms\" dur=\"30000ms\" attributeName=\"fill\" "
              + "from=\"rgb(180,120,230)\" to=\"rgb(120,180,95)\" fill=\"freeze\" />\n    <animate "
              + "attributeType=\"xml\" begin=\"30000ms\" dur=\"30000ms\" attributeName=\"cx\" "
              + "from=\"25\" to=\"40\" fill=\"freeze\" />\n    <animate attributeType=\"xml\" "
              + "begin=\"30000ms\" dur=\"30000ms\" attributeName=\"cy\" from=\"25\" to=\"15\" "
              + "fill=\"freeze\" />\n    <animate attributeType=\"xml\" begin=\"30000ms\" "
              + "dur=\"30000ms\" attributeName=\"rx\" from=\"7\" to=\"10\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" begin=\"30000ms\" dur=\"30000ms\" "
              + "attributeName=\"ry\" from=\"7\" to=\"15\" fill=\"freeze\" />\n    "
              + "<animate attributeType=\"xml\" "
              + "begin=\"30000ms\" dur=\"30000ms\" attributeName=\"fill\" from=\"rgb(120,180,95)\" "
              + "to=\"rgb(180,120,230)\" fill=\"freeze\" />\n    <set attributeType=\"xml\" "
              + "begin=\"0ms\" dur=\"60000ms\" attributeName=\"visibility\" from=\"hidden\" "
              + "to=\"visible\" />\n</ellipse>\n</svg>\n", fileAsString);

    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read from file.");
    }
  }
}
