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


    // Edit Motion Panel
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
    editFramePanel.add(deleteFrameButton);

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
          //TODO: do something with these values^^^
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
          //TODO: do something with these values^^^
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
          //TODO: do something with these values^^^
        }
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
}
