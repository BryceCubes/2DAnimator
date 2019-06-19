package cs3500.animator.view.edit;

import javax.swing.*;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IAnimatorView;

public class EditFrame extends JFrame implements IAnimatorView {
  IAnimatorModel model;

  private JPanel mainPanel;
  private JScrollPane mainScroll;
  private JButton button;

  private JLabel animationPreview;
  private JLabel playbackControls;
  private JLabel editControls;



  private EditFrame() {
    super();

    //TODO: model?

    setTitle("Animation Editor");
    setSize(500, 500);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScroll = new JScrollPane(mainPanel);
    add(mainPanel);

  }

  @Override
  public void animate() {

  }

}
