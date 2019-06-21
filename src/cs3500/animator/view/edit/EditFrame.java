package cs3500.animator.view.edit;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.model.keyframe.ReadOnlyIKeyFrame;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.visual.AnimationPanel;

/**
 * EditFrame class plays an animation from the file provided in main. It also offers the ability to
 * edit the animation by adding, editing, and removing shapes and keyframes. It also has the options
 * to pause, restart, and loop the animation.
 */
public class EditFrame extends JFrame implements IAnimatorView, ActionListener {
  private ReadOnlyIAnimatorModel model;
  private int speed;
  private Timer timer;
  private int tick;
  private ArrayList<ReadOnlyIShape> shapesToRender;
  private JCheckBox loopBox;
  private int lastTick;
  private boolean loop;
  private AnimationPanel aPanel;

  /**
   * Constructs the animation player and all of its buttons and panels.
   * @param model the Animator model that represents the shape motions and keyframes
   * @param speed the speed of the animation in frames per second
   */
  public EditFrame(ReadOnlyIAnimatorModel model, int speed) {
    super();
    this.model = model;
    this.speed = speed;
    this.loop = false;
    getLastTick();
    setTitle("Animation Editor");
    // these values are somewhat arbitrary based on the layout. Scales best with animation window
    setMinimumSize(new Dimension(model.getCanvasW(), model.getCanvasH() + 200));
    setSize(model.getCanvasW(), model.getCanvasH() + 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // main panel and scroll
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScroll = new JScrollPane(mainPanel);
    add(mainScroll);

    // animation panel
    aPanel = new AnimationPanel();
    aPanel.setMaximumSize(new Dimension(model.getCanvasW(), model.getCanvasH()));
    aPanel.setBackground(Color.white);

    // Animation scroll panel
    JScrollPane aScroll = new JScrollPane(aPanel);
    aScroll.setPreferredSize(new Dimension(model.getCanvasW(), model.getCanvasH()));
    mainPanel.add(aScroll);

    // Playback button panel
    JPanel playbackButtonPanel = new JPanel();
    playbackButtonPanel.setBorder(BorderFactory.createTitledBorder("Playback Buttons"));
    playbackButtonPanel.setLayout(new FlowLayout());
    playbackButtonPanel.setMaximumSize(new Dimension(800, 75));
    mainPanel.add(playbackButtonPanel);

    // play button
    JPanel playPanel = new JPanel();
    playbackButtonPanel.add(playPanel);
    JButton playButton = new JButton("Play");
    playButton.setActionCommand("play");
    playButton.addActionListener(this);
    playPanel.add(playButton);

    // pause button
    JPanel pausePanel = new JPanel();
    playbackButtonPanel.add(pausePanel);
    JButton pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("pause");
    pauseButton.addActionListener(this);
    pausePanel.add(pauseButton);

    // restart button
    JPanel restartPanel = new JPanel();
    playbackButtonPanel.add(restartPanel);
    JButton restartButton = new JButton("Restart");
    restartButton.setActionCommand("restart");
    restartButton.addActionListener(this);
    restartPanel.add(restartButton);

    // change speed button
    JPanel speedPanel = new JPanel();
    playbackButtonPanel.add(speedPanel);
    JButton speedButton = new JButton("Change Speed");
    speedButton.setActionCommand("speed");
    speedButton.addActionListener(this);
    speedPanel.add(speedButton);

    // loop checkbox
    JPanel checkBoxPanel = new JPanel();
    playbackButtonPanel.add(checkBoxPanel);
    loopBox = new JCheckBox("Loop");
    checkBoxPanel.add(loopBox);
    loopBox.setSelected(false);
    loopBox.setActionCommand("loop");
    loopBox.addActionListener(this);

    // edit motion panel
    JPanel editMotionPanel = new JPanel();
    editMotionPanel.setBorder(BorderFactory.createTitledBorder("Edit Keyframe Animations"));
    editMotionPanel.setLayout(new FlowLayout());
    editMotionPanel.setMaximumSize(new Dimension(800, 75));
    mainPanel.add(editMotionPanel);

    // add keyframe button
    JPanel addFramePanel = new JPanel();
    editMotionPanel.add(addFramePanel);
    JButton addFrameButton = new JButton("Add KeyFrame");
    addFrameButton.setActionCommand("add");
    addFrameButton.addActionListener(this);
    addFramePanel.add(addFrameButton);

    // edit keyframe button
    JPanel editFramePanel = new JPanel();
    editMotionPanel.add(editFramePanel);
    JButton editFrameButton = new JButton("Edit Keyframe");
    editFrameButton.setActionCommand("edit");
    editFrameButton.addActionListener(this);
    editFramePanel.add(editFrameButton);

    // delete keyframe button
    JPanel deleteFramePanel = new JPanel();
    editMotionPanel.add(deleteFramePanel);
    JButton deleteFrameButton = new JButton("Delete Keyframe");
    deleteFrameButton.setActionCommand("delete");
    deleteFrameButton.addActionListener(this);
    deleteFramePanel.add(deleteFrameButton);

    // add shape button
    JPanel addShapePanel = new JPanel();
    editMotionPanel.add(addShapePanel);
    JButton addShapeButton = new JButton("Add Shape");
    addShapeButton.setActionCommand("add shape");
    addShapeButton.addActionListener(this);
    addShapePanel.add(addShapeButton);

    // delete shape button
    JPanel deleteShapePanel = new JPanel();
    editMotionPanel.add(deleteShapePanel);
    JButton deleteShapeButton = new JButton("Delete Shape");
    deleteShapeButton.setActionCommand("delete shape");
    deleteShapeButton.addActionListener(this);
    deleteShapePanel.add(deleteShapeButton);

    setVisible(true);
  }

  /**
   * The main method that starts the animation timer and resets to the beginning of the animation
   * when looping.
   */
  @Override
  public void animate() {
    this.timer = new Timer(1000 / this.speed, e -> {
      shapesToRender = model.getShapesAtTick(tick++);
      aPanel.draw(shapesToRender);
      if (loop && tick >= lastTick) {
        tick = 0;
      }
    });
  }

  /**
   * Handles the actions performed based on which button was pressed.
   *
   * @param e the event triggered by the button.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "edit":
        // panel for changing keyframes
        JPanel editFrameOptionsPanel = new JPanel();
        editFrameOptionsPanel.setLayout(new BoxLayout(editFrameOptionsPanel, BoxLayout.PAGE_AXIS));

        JTextField shapeName = new JTextField();
        JTextField tick = new JTextField();
        JTextField xVal = new JTextField();
        JTextField yVal = new JTextField();
        JTextField wVal = new JTextField();
        JTextField hVal = new JTextField();
        JTextField rVal = new JTextField();
        JTextField gVal = new JTextField();
        JTextField bVal = new JTextField();
        Object[] edits = {
                "Shape Name:", shapeName,
                "Keyframe Tick:", tick,
                "Shape X Value", xVal,
                "Shape Y Value", yVal,
                "Shape Width", wVal,
                "Shape Height", hVal,
                "Red Value", rVal,
                "Green Value", gVal,
                "Blue Value", bVal
        };
        int option = JOptionPane.showConfirmDialog(editFrameOptionsPanel, edits,
                "Edit KeyFrame Specifications", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          String name = shapeName.getText();
          String refTick = tick.getText();
          String x = xVal.getText();
          String y = yVal.getText();
          String w = wVal.getText();
          String h = hVal.getText();
          String r = rVal.getText();
          String g = gVal.getText();
          String b = bVal.getText();
          ArrayList<String> values = new ArrayList<>();
          values.add("x");
          values.add("y");
          values.add("w");
          values.add("h");
          values.add("r");
          values.add("g");
          values.add("b");
          for (String value : values) {
            try {
              int keyT = Integer.parseInt(refTick);
              switch (value) {
                case "x":
                  try {
                    if (x.equals("")) {
                      break;
                    }
                    int keyX = Integer.parseInt(x);
                    this.model.editKeyFrame(name, keyT, "x", keyX);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your x input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "y":
                  if (y.equals("")) {
                    break;
                  }
                  try {
                    int keyY = Integer.parseInt(y);
                    this.model.editKeyFrame(name, keyT, "y", keyY);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your y input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "w":
                  if (w.equals("")) {
                    break;
                  }
                  try {
                    int keyW = Integer.parseInt(w);
                    this.model.editKeyFrame(name, keyT, "width", keyW);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your width input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "h":
                  if (h.equals("")) {
                    break;
                  }
                  try {
                    int keyH = Integer.parseInt(h);
                    this.model.editKeyFrame(name, keyT, "height", keyH);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your height input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "r":
                  if (r.equals("")) {
                    break;
                  }
                  try {
                    int keyR = Integer.parseInt(r);
                    this.model.editKeyFrame(name, keyT, "red", keyR);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your red input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "g":
                  if (g.equals("")) {
                    break;
                  }
                  try {
                    int keyG = Integer.parseInt(g);
                    this.model.editKeyFrame(name, keyT, "green", keyG);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your green input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                case "b":
                  if (b.equals("")) {
                    break;
                  }
                  try {
                    int keyB = Integer.parseInt(b);
                    this.model.editKeyFrame(name, keyT, "blue", keyB);
                  } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Your blue input could not be parsed to "
                            + "an integer, please input a number without any decimal places.");
                  }
                  break;
                default:
                  throw new IllegalArgumentException("You shouldn't be seeing this, we did" +
                          " something wrong.");
              }
            } catch (NumberFormatException tException) {
              throw new IllegalArgumentException("Your tick input could not be parsed to an integer"
                      + ", please input a number without any decimal places.");
            }
          }
        }
        getLastTick();
        break;
      case "add":

        // panel for adding keyframes
        JPanel addOptionsPanel = new JPanel();
        addOptionsPanel.setLayout(new BoxLayout(addOptionsPanel, BoxLayout.PAGE_AXIS));

        JTextField addShapeName = new JTextField();
        JTextField addTick = new JTextField();
        Object[] adds = {
                "Shape Name:", addShapeName,
                "Keyframe Tick:", addTick,
        };
        int add = JOptionPane.showConfirmDialog(addOptionsPanel, adds,
                "Add KeyFrame Specifications", JOptionPane.OK_CANCEL_OPTION);
        if (add == JOptionPane.OK_OPTION) {
          String addName = addShapeName.getText();
          String newTick = addTick.getText();
          if (newTick.equals("") || addName.equals("")) {
            throw new IllegalArgumentException("Tick and shape name must be input.");
          }
          try {
            int keyTick = Integer.parseInt(newTick);
            this.model.addKeyFrame(addName, keyTick);
          } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Tick must be a positive integer.");
          }
          getLastTick();
        }

        break;

      case "delete":
        JPanel deleteOptionsPanel = new JPanel();
        deleteOptionsPanel.setLayout(new BoxLayout(deleteOptionsPanel, BoxLayout.PAGE_AXIS));

        JTextField delFrameName = new JTextField();
        JTextField delTick = new JTextField();
        Object[] dels = {
                "Shape Name:", delFrameName,
                "Keyframe Tick:", delTick,
        };
        int del = JOptionPane.showConfirmDialog(deleteOptionsPanel, dels,
                "Delete KeyFrame Specifications", JOptionPane.OK_CANCEL_OPTION);
        if (del == JOptionPane.OK_OPTION) {
          String addName = delFrameName.getText();
          String newTick = delTick.getText();
          if (newTick.equals("") || addName.equals("")) {
            throw new IllegalArgumentException("Tick and shape name must be input.");
          }
          try {
            int keyTick = Integer.parseInt(newTick);
            this.model.deleteKeyFrame(addName, keyTick);
          } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Tick must be a positive integer.");
          }
        }
        getLastTick();
        break;

      case "speed":
        // stop the animation while in the menu
        timer.stop();

        // the panel that contains the options
        JPanel changeSpeedPanel = new JPanel();
        changeSpeedPanel.setLayout(new BoxLayout(changeSpeedPanel, BoxLayout.PAGE_AXIS));

        // field for new speed
        JTextField newSpeed = new JTextField();
        Object[] speedIn = {
                "New Speed:", newSpeed
        };
        int speed = JOptionPane.showConfirmDialog(changeSpeedPanel, speedIn,
                "Choose New Speed", JOptionPane.OK_CANCEL_OPTION);
        if (speed == JOptionPane.OK_OPTION) {
          // get the entered value
          String changedSpeed = newSpeed.getText();
          int speedNum;
          // check that it's an integer
          try {
            speedNum = Integer.parseInt(changedSpeed);
          } catch (NumberFormatException num) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please enter a valid number", "Invalid speed warning",
                    JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid input");
          }
          if (speedNum > 0) {
            this.speed = speedNum;
            animate();
            timer.start();
          } else {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please enter a valid number", "Invalid speed warning",
                    JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid number");
          }
        }
        break;
      case "add shape":
        JPanel addShapePanel = new JPanel();
        addShapePanel.setLayout(new BoxLayout(addShapePanel, BoxLayout.PAGE_AXIS));

        // inputs for the new shape
        JTextField newShapeName = new JTextField();
        JComboBox<String> newShapeType = new JComboBox<>();
        newShapeType.addItem("Rectangle");
        newShapeType.addItem("Ellipse");
        Object[] shapes = {
                "Shape Name:", newShapeName,
                "Shape Type", newShapeType,
        };

        // shows options and takes input when user confirms
        int shape = JOptionPane.showConfirmDialog(addShapePanel, shapes,
                "Add new shape", JOptionPane.OK_CANCEL_OPTION);
        if (shape == JOptionPane.OK_OPTION) {
          // new name and shape type
          String addName = newShapeName.getText();
          String addShapeType = (String) newShapeType.getSelectedItem();
          assert addShapeType != null;
          if (addName.equals("") || addShapeType.equals("")) {
            throw new IllegalArgumentException("New shape must have name and type declared");
          }
          model.addShape(addName, addShapeType);
          //TODO: make sure this works^^^
        }
        break;
      case "delete shape":
        //TODO: decide if this can be abstracted somehow
        // the panel for the new shape specifications
        JPanel deleteShapePanel = new JPanel();
        deleteShapePanel.setLayout(new BoxLayout(deleteShapePanel, BoxLayout.PAGE_AXIS));

        // inputs for the new shape
        JTextField delShapeName = new JTextField();

        Object[] delShapes = {
                "Shape Name:", delShapeName,
        };

        // shows options and takes input when user confirms
        int delShape = JOptionPane.showConfirmDialog(deleteShapePanel, delShapes,
                "Delete shape", JOptionPane.OK_CANCEL_OPTION);
        if (delShape == JOptionPane.OK_OPTION) {
          // new name and shape type
          String addName = delShapeName.getText();
          try {
            model.deleteShape(addName);
          } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception);
          }
          //TODO: make sure this works^^^
        }
        break;
      case "loop":
        loop = loopBox.isSelected();
        //TODO: how to determine the end of an animation???
        break;
      case "pause":
        timer.stop();
        break;
      case "play":
        timer.start();
        break;
      case "restart":
        this.tick = 0;
        timer.restart();
        break;
    }
  }

  /**
   * Used to find the last tick of an animation.
   */
  private void getLastTick() {
    HashMap<ReadOnlyIShape, ArrayList<ReadOnlyIKeyFrame>> keyFrames = this.model.returnKeyFrames();
    ArrayList<ReadOnlyIShape> shapes = this.model.getShapes();
    int last = 0;
    for (ReadOnlyIShape shape : shapes) {
      for (ReadOnlyIKeyFrame keyFrame : keyFrames.get(shape)) {
        if (keyFrame.getT() > last) {
          last = keyFrame.getT();
        }
      }
    }
    this.lastTick = last;
  }
}
