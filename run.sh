#!/bin/bash

if [ "$1" == "grayscale" ] ; then
	./graphed.run grayscale ./images/grayscale.jpg ./images/output.jpg 1.6
elif [ "$1" == "contrast" ] ; then
	./graphed.run contrast ./images/contrast.jpg ./images/output.jpg 1.6
elif [ "$1" == "brightness" ] ; then
	./graphed.run brightness ./images/bright.jpg ./images/output.jpg 40
elif [ "$1" == "exposure" ] ; then
	./graphed.run exposure ./images/exposure.jpg ./images/output.jpg 1.5
elif [ "$1" == "gamma" ] ; then
	./graphed.run gamma ./images/gamma.jpg ./images/output.jpg 0.915
	./graphed.run brightness ./images/gamma.jpg ./images/output2.jpg 30
elif [ "$1" == "sepia" ] ; then
	./graphed.run sepia ./images/sepia.jpg ./images/output.jpg 38
elif [ "$1" == "histeq" ] ; then
	./graphed.run hequalization ./images/histeq.jpg ./images/output.jpg
elif [ "$1" == "hstretch" ] ; then
	./graphed.run hstretching ./images/hstretch.jpg ./images/output.jpg
elif [ "$1" == "divide" ] ; then
	./graphed.run dividergb ./images/divide/rgb.jpg ./images/divide/red.jpg ./images/divide/green.jpg ./images/divide/blue.jpg gray
elif [ "$1" == "divcolor" ] ; then
	./graphed.run dividergb ./images/divide/rgb.jpg ./images/divide/red.jpg ./images/divide/green.jpg ./images/divide/blue.jpg color
elif [ "$1" == "join" ] ; then
	./graphed.run joinrgb ./images/divide/red.jpg ./images/divide/green.jpg ./images/divide/blue.jpg ./images/divide/output.jpg
elif [ "$1" == "recolorize" ] ; then
	./graphed.run recolorize ./images/recolorize.jpg ./images/output.jpg 13 38 20
#	./graphed.run recolorize ./images/recolorize.jpg ./images/output.jpg 13 38 180
elif [ "$1" == "resize" ] ; then
	./graphed.run resize ./images/resize.jpg ./images/output.jpg ratio 0.3
elif [ "$1" == "newsize" ] ; then
	./graphed.run resize ./images/newsize.jpg ./images/output.jpg size 1100 1100
elif [ "$1" == "timewarp" ] ; then
	./graphed.run timewarp ./images/timewarp.jpg ./images/output.jpg 15
fi
