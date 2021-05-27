# ImageClassMaker - Make java class that contains image
## Overview
This utillity creates a java class that contains an accessible image that can be implemented in an application. This can be useful if you need an image / logo etc that can not be on an accessible media.
## How to use the application
The application is started with the default parameter. However, there are standard input and output directories defined in the application. These are:<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;./images (that contains the image that should be converted)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;./data &nbsp;&nbsp;&nbsp;&nbsp;(that contains the java class of the converted image)<br><br>
The parameters of the application are:<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-i = Image type (can contain ".png", ".jpg" or ".jpeg")<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-c = Image and class name (without image extention).<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-p = Package name.<br>
## Implemented functions in the class
### getLogo()
This call returns an image in the format:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java.awt.image.BufferedImage

### getLogoBytes()
This call returns an image in the format:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;byte[ ]

## Included Java libraries
This application does not use any library without those standard in java.

## License
* Licensed under the Apache License, version 2.0*[her](https://www.apache.org/licenses/LICENSE-2.0)
