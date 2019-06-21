package cs3500.animator.view.edit;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.model.shape.ReadOnlyIShape;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.visual.AnimationPanel;

public class EditFrame extends JFrame implements IAnimatorView, ActionListener {
  private ReadOnlyIAnimatorModel model;
  private int speed;
  private Timer timer;
  private int tick;
  private ArrayList<ReadOnlyIShape> shapesToRender;

  private AnimationPanel aPanel;

  public EditFrame(ReadOnlyIAnimatorModel model, int speed) {
    super();
    this.model = model;
    this.speed = speed;
    setTitle("Animation Editor");
    // these values are somewhat arbitrary based on the layout. Scales well with animation window
    setSize(model.getCanvasW() + 22, model.getCanvasH() + 184);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // main panel and scroll
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScroll = new JScrollPane(mainPanel);
    add(mainScroll);

    // animation panel
    aPanel = new AnimationPanel();
    //aPanel.setBorder(BorderFactory.createTitledBorder("Animation Preview"));
    aPanel.setMaximumSize(new Dimension(model.getCanvasW(), model.getCanvasH()));
    aPanel.setBackground(Color.white);
    aPanel.setPreferredSize(new Dimension(model.getCanvasW(), model.getCanvasH()));

    // Animation scroll panel
    JScrollPane aScroll = new JScrollPane(aPanel);
    aScroll.setPreferredSize(new Dimension(model.getCanvasW(), model.getCanvasH()));
    mainPanel.add(aScroll);

    // Playback button panel
    JPanel playbackButtonPanel = new JPanel();
    playbackButtonPanel.setBorder(BorderFactory.createTitledBorder("Playback Buttons"));
    playbackButtonPanel.setLayout(new FlowLayout());
    playbackButtonPanel.setMaximumSize(new Dimension(model.getCanvasW(), 100));
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

    // edit motion panel
    JPanel editMotionPanel = new JPanel();
    editMotionPanel.setBorder(BorderFactory.createTitledBorder("Edit Keyframe Animations"));
    editMotionPanel.setLayout(new FlowLayout());
    editMotionPanel.setMaximumSize(new Dimension(model.getCanvasW(), 100));
    mainPanel.add(editMotionPanel);

    // add keyframe
    JPanel addFramePanel = new JPanel();
    editMotionPanel.add(addFramePanel);
    JButton addFrameButton = new JButton("Add");
    addFrameButton.setActionCommand("add");
    addFrameButton.addActionListener(this);
    addFramePanel.add(addFrameButton);

    // edit keyframe
    JPanel editFramePanel = new JPanel();
    editMotionPanel.add(editFramePanel);
    JButton editFrameButton = new JButton("Edit");
    editFrameButton.setActionCommand("edit");
    editFrameButton.addActionListener(this);
    editFramePanel.add(editFrameButton);

    // delete keyframe
    JPanel deleteFramePanel = new JPanel();
    editMotionPanel.add(deleteFramePanel);
    JButton deleteFrameButton = new JButton("Delete");
    deleteFrameButton.setActionCommand("delete");
    deleteFrameButton.addActionListener(this);
    deleteFramePanel.add(deleteFrameButton);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    setVisible(true);
  }

  @Override
  public void animate() {
    this.timer = new Timer(1000 / this.speed, e -> {
      shapesToRender = model.getShapesAtTick(tick++);
      aPanel.draw(shapesToRender);
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "edit":
        // dialogue box for changing keyframes
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
        break;
      case "add":

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
        }

        break;

      case "delete":
        //TODO: decide if this can be abstracted somehow
        JPanel deleteOptionsPanel = new JPanel();
        deleteOptionsPanel.setLayout(new BoxLayout(deleteOptionsPanel, BoxLayout.PAGE_AXIS));

        JTextField delShapeName = new JTextField();
        JTextField delTick = new JTextField();
        Object[] dels = {
                "Shape Name:", delShapeName,
                "Keyframe Tick:", delTick,
        };
        int del = JOptionPane.showConfirmDialog(deleteOptionsPanel, dels,
                "Delete KeyFrame Specifications", JOptionPane.OK_CANCEL_OPTION);
        if (del == JOptionPane.OK_OPTION) {
          String addName = delShapeName.getText();
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
        break;

      case "speed":
        JPanel changeSpeedPanel = new JPanel();
        changeSpeedPanel.setLayout(new BoxLayout(changeSpeedPanel, BoxLayout.PAGE_AXIS));

        JTextField newSpeed = new JTextField();
        Object[] speedIn = {
                "New Speed:", newSpeed
        };
        int speed = JOptionPane.showConfirmDialog(changeSpeedPanel, speedIn,
                "Choose New Speed", JOptionPane.OK_CANCEL_OPTION);
        if (speed == JOptionPane.OK_OPTION) {
          String changedSpeed = newSpeed.getText();
          int speedNum;
          // check that it's an integer
          try {
            speedNum = Integer.parseInt(changedSpeed);
          } catch (NumberFormatException num) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please enter a valid number", "Invalid speed warning",
                    JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Our program could not parse your file, please provide a "
                    + "file in the correct format.");
          }
          if (speedNum > 0) {
            this.speed = speedNum;
            animate();
          }
        }
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
}
