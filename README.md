# CodeToPic

School project. Code smells visualizer.

## Visualization example

This is the generated code smell visualization for this projet.

<a href="https://raw.githubusercontent.com/DelvalletQuentin/CodeToPic/master/readme/CodeToPic.svg">
<img src="https://raw.githubusercontent.com/DelvalletQuentin/CodeToPic/master/readme/CodeToPic.png" alt="Visualization example" class="image mod-full-width" />
</a>

This is a png, you can click on the picture to access the svg file.

## How does it work?

Given a project path, our code in hierarchyView.visitors takes advantage of <a href="https://github.com/javaparser/javaparser">Javaparser</a> to analyse java code. We search for some code smells and compile the metrics into objects representing classes and methods.

From that, we use the <a href="https://xmlgraphics.apache.org/batik/">Apache Batik library</a> 
to create svg elements for each classes and methods. We put then in place so it represents the project. 

## Circles, colors, what does it means?

The generated circles are simple.

- White-filled circles with black stroke are the package in your project.
- Colored circles within packages are classes.
- Colored circles within classes are constructors and methods.

About the color, green means good, red is bad smell.

For methods, the fill color represents the Cyclomatic Complexity and the stroke color the parameter list. For classes, it's an average of methods' data.

The size for everything represents the number of line of code, the bigger the circle, the bigger the package/class/method.