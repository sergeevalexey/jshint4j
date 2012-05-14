jshint4j
========

Provides possibility to validate JS files using JSHint through Java

## HowToBuild

Requirements: Maven 3 & JDK 6
Run from command line: 
<pre><code>maven install</code></pre>

## HowToRun

Requirements: JDK 6
Run from command line:
<pre><code>
java -r jshint4j-tool-0.1-SNAPSHOT-jar-with-dependencies.jar  
     &lt;path to your JS file (required)> 
     &lt;comma separated JSHint options, in the form like: noempty:true,jquery=trueident:4 (without spaces)>
</code></pre>

Report is a simple error output into console. It looks like this:

<pre><code>
Errors:
*******
at row '11' an column '38' - Unsafe character.
at row '18' an column '1' - Use the function form of "use strict".
</code></pre>