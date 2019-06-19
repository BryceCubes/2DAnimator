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
    addFrameButton.setActionCommand("add frame");
    addFrameButton.addActionListener(this);
    addFramePanel.add(addFrameButton);

    // add keyframe
    JPanel editFramePanel = new JPanel();
    editMotionPanel.add(editFramePanel);
    JButton editFrameButton = new JButton("Edit");
    editFrameButton.setActionCommand("edit frame");
    editFrameButton.addActionListener(this);
    editFramePanel.add(editFrameButton);

    // add keyframe
    JPanel deleteFramePanel = new JPanel();
    editMotionPanel.add(deleteFramePanel);
    JButton deleteFrameButton = new JButton("Delete");
    deleteFrameButton.setActionCommand("delete frame");
    deleteFrameButton.addActionListener(this);
    deleteFramePanel.add(addFrameButton);


  }

  @Override
  public void animate() {

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }
}
