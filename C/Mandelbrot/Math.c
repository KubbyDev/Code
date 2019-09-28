#include "Color.h"
#include "Math.h"

//(x, y) is the position of the pixel in the screen plane
//(w, h) is the width/height of the screen (number of pixels)
//(startX, startY) is the position of the top left corner of the screen in the real plane
//(width, height) is the size of the screen in the real plane's coordinates
Color* calcPixel(int x, int y, 
                 int w, int h, 
                 double startX, double startY, 
                 double width, double height) {

    double pixelValue = getPixelValue(
        (double)x/w * width + startX,
        (double)-y/h * height + startY
    );
    return simpleColoration(pixelValue);
}

//(x0, y0) is the position of the pixel in the real plane (not the screen plane)
double getPixelValue(double x0, double y0) {

    //This value can be seen as the clearness of the drawing
    int max_i = 1000;

    double x = 0, y = 0;
    int i = 0;
    while (x*x + y*y < 4 && i < max_i) {
			
	double x_temp = x*x - y*y + x0;
	y = 2*x*y + y0;
	x = x_temp;
			
	i++;	
    }

    return i/max_i;
}

//----------
// Coloration functions:
// Converts the value of the pixel [0,1] to a Color to display
//----------

//This function just uses the value and plugs in the green value
Color* simpleColoration(double value) {
    return newColor(0, 255*value, 0);
}
