package cs3500.animator.util;

import cs3500.animator.model.IAnimatorModel;

public class AnimationBuilderImpl implements AnimationBuilder {
  IAnimatorModel model;

  @Override
  public Object build() {
    return null;
  }

  @Override
  public AnimationBuilder setBounds(int x, int y, int width, int height) {
    return null;
  }

  @Override
  public AnimationBuilder declareShape(String name, String type) {
    return null;
  }

  @Override
  public AnimationBuilder addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    return null;
  }

  @Override
  public AnimationBuilder addKeyframe(String name, int t, int x, int y, int w, int h, int r, int g, int b) {
    return null;
  }
}
