package cs3500.animator.view.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import cs3500.animator.model.ReadOnlyIAnimatorModel;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.visual.AnimationPanel;

public class EditFrame extends JFrame implements IAnimatorView, ActionListener, ItemListener {
  ReadOnlyIAnimatorModel model;
  Timer timer;
  int tick;

  private JPanel mainPanel;
  private JScrollPane mainScroll;
  private JButton button;

  private JLabel animationPreview;
  private JLabel playbackControls;
  private JLabel editControls;


  private EditFrame(ReadOnlyIAnimatorModel model) {
    super();

    this.model = model;
    setTitle("Animation Editor");
    setSize(500, 500);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScroll = new JScrollPane(mainPanel);
    add(mainPanel);
    mainPanel.add(mainScroll);

    // The panel containing the actual animation
    int width = model.getCanvasX() + model.getCanvasW();
    int height = model.getCanvasY() + model.getCanvasH();

    AnimationPanel aPanel = new AnimationPanel();
    aPanel.setMinimumSize(new Dimension(width, height));
    aPanel.setBackground(Color.white);
    aPanel.setBorder(BorderFactory.createTitledBorder("Animation Preview"));
    mainPanel.add(aPanel);


    // Playback button panel
    JPanel playbackButtonPanel = new JPanel();
    playbackButtonPanel.setBorder(BorderFactory.createTitledBorder("Playback Buttons"));
    playbackButtonPanel.setLayout(new FlowLayout());

    //new BoxLayout(playbackButtonPanel, BoxLayout.LINE_AXIS)

    // pause button
    JPanel pausePanel = new JPanel();
    playbackButtonPanel.add(pausePanel);
    JButton pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("pause");
    pauseButton.addActionListener(this);
    pausePanel.add(pauseButton);

    // play button
    JPanel playPanel = new JPanel();
    playbackButtonPanel.add(playPanel);
    JButton playButton = new JButton("Play");
    playButton.setActionCommand("play");
    playButton.addActionListener(this);
    playPanel.add(playButton);

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

    // add keyframe
    JPanel addFramePanel = new JPanel();
    editMotionPanel.add(addFramePanel);
    JButton addFrameButton = new JButton("Add");
    addFrameButton.setActionCommand("add");
    addFrameButton.addActionListener(this);
    addFramePanel.add(addFrameButton);

    // add keyframe
    JPanel editFramePanel = new JPanel();
    editMotionPanel.add(editFramePanel);
    JButton editFrameButton = new JButton("Edit");
    editFrameButton.setActionCommand("edit");
    editFrameButton.addActionListener(this);
    editFramePanel.add(editFrameButton);

    // add keyframe
    JPanel deleteFramePanel = new JPanel();
    editMotionPanel.add(deleteFramePanel);
    JButton deleteFrameButton = new JButton("Delete");
    deleteFrameButton.setActionCommand("delete");
    deleteFrameButton.addActionListener(this);
    deleteFramePanel.add(addFrameButton);

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);
  }

  @Override
  public void animate() {

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
        Object[] message = {
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
        int option = JOptionPane.showConfirmDialog(editFrameOptionsPanel, message,
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

    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }
}
