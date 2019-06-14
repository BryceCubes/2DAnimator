package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class AnimationView extends JFrame implements IAnimatorView {
  Timer timer;
  private JPanel shapePlane;
  private JScrollPane scrollPane;

  public AnimationView() {
    super();
    timer = new Timer(5, this);
    this.setTitle("2D Animation :)");
    this.setSize(360, 360);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    shapePlane = new JPanel();
    shapePlane.setPreferredSize(new Dimension(360, 360));
    scrollPane = new JScrollPane(shapePlane);
    this.add(scrollPane, BorderLayout.CENTER);

    this.pack();
  }

  public void animate() {

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
