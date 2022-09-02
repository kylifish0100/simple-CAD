# CG CAD Individual Assignment Start Repo

This is a simple drawing software implemented features like 
#### 1. drawing simple shapes<br/>
  Fox Line/Box/Circle(Oval), drag mouse to draw; for triangle/polygon/curve, **left click** on the canvas to draw control point.<br/>
  - Triangle automatically draws after 3 control points be set<br/>
  - Curve is implemented with Cubic Bezier Curve algorithm so it has 4 control point as default -> Left click 4 control point and curve will be automatically drawn.<br/>
  - Polygon can have multiple customized control points so click as much as you want and later **right click** click OK on the pop up menu to finalize the polygon.<br/>
#### 2. edit shapes
  - Select Edit in the right menu to edit element shape with its control points.<br/>
  - Noticed that selected element will have red control point instead of bule.<br/>
  - Right click on a shape's control point and click "cancel select" on the pop up menu to cancel select. 
#### 3. remove shapes
  Right click to view the pop up menu and click on delete to remove unwanted element under edit mode.
#### 4. filling closed shape with color
  Select Fill in the right menu, **right click** on any control point of the shape you want to fill and select "Fill color" in the pop up menu with chosen color.
#### 5. get shape measurement
  It is available to get automatic measurement of rectangle(box), triangle and circle(oval) by selecting an element under edit mode and then click get measurement button.
#### 6. zoom in to view
  Select Zoom in the right menu and mouse left click on the canvas to set zoom origin, then normal scroll mouse wheel to gain zoon in/out effect.<br/>
  Noticed that zoom view only possible under zoom mode(selecting Zoom button)
  P.S. I have encountered a weird thing that on Mac particularly(haven't tried on Windows os) the original zoom scale should be set to 2 if required a correct zoom. I assume we use Linux as default environment so I set original zoom scaler to 1. 
#### 7. add label
  Select Add Label in the right menu, and click anywhere to create a empty label.<br/>
  If you want to edit label like input label text, select edit and select the label you want to edit, and then click Input Label button on the top menu bar to input label text.
#### 8. color chooser
  Click on choose a color on the top left menu bar to choose a desired color to apply on the shape to be drawn.




# Statement of originality 

This is an individual assignment, except for the list below this assignment has been entirely done by myself.

**[Yuhan Zhang]**
 
Below is the complete list of ideas, help, and source code I obtained form others in completing this assignment:
+ Eric McCreath (who wrote the starting point CAD code),  used with permission. https://gitlab.cecs.anu.edu.au/u4033585/cgcadassignment2020 
+ Refernece for the zoom transformation https://stackoverflow.com/questions/30792089/java-graphics2d-translate-and-scale
+ Changeable color icon in button implementation references to https://stackoverflow.com/questions/26565166/how-to-display-a-color-selector-when-clicking-a-button
+ Cubic Bezier Curve algorithm look up- http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/