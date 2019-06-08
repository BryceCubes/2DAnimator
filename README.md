# 2DAnimator

IANIMATORMODEL AND READONLY
We have an IAnimatorModel and a ReadOnlyIAnimatorModel. The
purpose of the readonly being to stop the view/controller
from accessing future setter methods. If the controller takes
a read only interface it will be unable to mutate directly.

ANIMATORMODELIMPL
Our model operates on the assumption that it will only be
passed an Arraylist of motions. These motions will be passed
in by the user through the controller. We then implement the
methods of find shape, a method that takes a shape's name and
returns the shape associated with that name, return shapes at 
tick, a method that will be able to pass the state of all the
shapes at tick t such that a controller can pass these shapes
to a view that could then display them, and text view motions,
a method that returns a text view of the motions provided by
the user. We have the variables of sortedMoveList, a hashmap
of keys relating to the name of the shape and a list of sorted
motions associated with that shape, movelist, an Arraylist of 
IMotions initialized at the start in order to allow the user
to input the motions in any way they would like, keys, an 
ArrayList of the shape names used as keys in our sorted move
list, and shapes, an ArrayList of shapes that is filled in by
shapes exist at a certain tick when return shapes at tick 
method is run. The purpose of the interface associated wth 
this class is to allow the controller to access any of the
public methods, and to allow for models that differ from ours
to be implemented.

SHAPETYPE ENUM
We use the enum of shapeType to provide concrete objects for
allowing specific shapes. We use an enum so that if we want to
allow for different shapes, all we have to do is add another 
field to the enum.

ISHAPE INTERFACE
We use the IShape interface to allow both the Animator model
to get access to the fields of AShape, and allow for those
fields to be passed to a view. We implemented shape to only
store the values associated with the shape itself, x, y, w,
h, r, g, b, shape type, and shapeid such that the shape can
know exactly where it is and its own state at all times.

IMOTION INTERFACE
IMotion interface was implemented with the 6 getters it
provides so that the model can check if any two motions
are overlapping. It also has interpolate, which will be
implemented in the next assignment to allow mutation of the
shape associated with the current motion. Get text output is
implemented so that the model can continuously call that command
on all of the motions and get a correct output. Get shape allows
our model to get access to the shape involved in the motion
because otherwise, shapes could not be accessed.
