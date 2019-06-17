# 2DAnimator

++++++++++++++++++++ MODEL ++++++++++++++++++++
IANIMATORMODEL and READONLYIANIATORMODEL
We have an IAnimatorModel that is implemented in AnimatorModelImp.
We changed our readonly to implement setters such that our model
could easily use a builder.

ANIMATORMODELIMPL
Our model has changed in that it now operates from a builder.
The methods of our model are used in order to allow the builder
properly input all values. We also changed the signatures of
many of our methods to only output the read only interfaces.
We also changed them to ensure that the model gets to use the
non read only interfaces so it can more easily implement the
logic. We also changed the signature of deleteMotion in order
to just delete any motion given all the motions values. In order
to implement this, we had to make an equalsmotion method that
could compare them easier. We also changed our addMotion to be
called declareMotion, and is made more user friendly through
having the same signature as the builder method of the same name.
We also added a builderMotion that easily takes in the builders
inputs as IMotions so it would be easier to add that way.
We then implement the methods of find shape, a method that
takes a shape's name andreturns the shape associated with
that name, return shapes at tick, a method that will be able
to pass the state of all the shapes at tick t such that a
controller can pass these shapes to a view that could then
display them, and text view motions, a method that returns a
text view of the motions provided by the user. We have the
variables of sortedMoveList, a hashmap of keys relating to the
name of the shape and a list of sorted motions associated with
that shape, movelist, an Arraylist of IMotions initialized at
the start in order to allow the user to input the motions in any
way they would like, keys, an ArrayList of the shape names used
as keys in our sorted move list, and shapes, an ArrayList of
shapes that is filled in by shapes exist at a certain tick when
return shapes at tick method is run. The purpose of the
interface associated wth this class is to allow the controller
to access any of the public methods, and to allow for models
that differ from ours to be implemented.

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
know exactly where it is and its own state at all times. We
changed AShape to have an empty constructor so that all that
could change  it are the setters

IMOTION INTERFACE
IMotion interface was implemented with the 6 getters it
provides so that the model can check if any two motions
are overlapping. It also has interpolate, which will be
implemented in the next assignment to allow mutation of the
shape associated with the current motion. Get text output is
implemented so that the model can continuously call that command
on all of the motions and get a correct output. Get shape allows
our model to get access to the shape involved in the motion
because otherwise, shapes could not be accessed. We put a
standard 4 spaces in between the start values and stop values
because implementing the values to line up perfectly seemed
a bit out of scope. It was confusing seeing the different
space patterns on the example text output.

++++++++++++++++++++ VIEW ++++++++++++++++++++
IANIMATORVIEW
IAnimatorView simply has one method to animate. The idea being
that you can simply call the animate method once you've
constructed a view with a model.

TEXTVIEW
TextView class outputs the stored IMOtions in a readable format
to get an overview of the motions in the project. It prints in
order the starting and then ending time, x, y, width, height,
and rgb values as well as the canvas size at the top. This
contains a builder and setters for the builder to use, but
otherwise it simply implements the animate method that
outputs all the necessary data about the motions to the
output file given in main.

SVGVIEW
SVGView formats the motion into a readable format for the SVG
to interpret and animate in Google Chrome or Opera. This also
implements animate and contains a builder and basic setters
for the builder to use. The animate formats the motions and
shapes into a format that is readable to the SVG format and
can be loaded into a webpage. Animate also works to make the
motions only visible when the first motion starts through
using the set function in the svg.

ANIMATIONVIEW
AnimationView uses the java swing library to craft
animations in a pop-up window with an adjustable window
and scroll bar if the window is not at the full canvas size.
