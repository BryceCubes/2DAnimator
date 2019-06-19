package cs3500.animator.view.edit;

import javax.swing.*;

import cs3500.animator.view.IAnimatorView;

public class EditFrame extends JFrame implements IAnimatorView {
  private JPanel mainPanel;
  private JScrollPane mainScroll;
  private JButton button;

  private JLabel animationPreview;



  private EditFrame() {
    super();
    setTitle("Animation Editor");
    setSize(500, 500);

  }

  @Override
  public void animate() {

  }

}
