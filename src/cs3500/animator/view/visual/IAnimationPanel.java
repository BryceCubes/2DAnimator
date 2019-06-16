package cs3500.animator.view.visual;

import java.util.List;

import cs3500.animator.model.shape.ReadOnlyIShape;

/**
 * Represents a general animation panel.
 */
public interface IAnimationPanel {
  void draw(List<ReadOnlyIShape> shapes);
}
