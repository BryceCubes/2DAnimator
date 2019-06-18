package cs3500.animator.model.keyframe;

public interface ReadOnlyIKeyFrame {
  void editKeyFrame();

  double getX();

  double getY();

  double getW();

  double getH();

  double getR();

  double getG();

  double getB();

  double getT();
}
