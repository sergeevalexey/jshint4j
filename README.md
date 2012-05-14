jshint4j
========

Provides possibility to validate JS files using JSHint through Java

## HowToBuild ##

Requirements: Maven 3 & JDK 6
Run from command line: maven install

## HowToRun ##

Requirements: JDK 6
Run from command line: java -r jshint4j-tool-0.1-SNAPSHOT-jar-with-dependencies.jar <path to your JS file (required)> <comma separated JSHint options, in the form like: noempty:true,jquery=trueident:4>

Report is a simple error output into console. It looks like this:

<pre><code>
Errors:
*******
at row '11' an column '38' - Unsafe character.
at row '18' an column '1' - Use the function form of "use strict".
</code></pre>