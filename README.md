jshint4j
========

Provides possibility to validate JS files using [JSHint](http://www.jshint.com/) through Java.

## Requirements 
[Maven 3](http://maven.apache.org) and [JDK 6](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## HowToBuild

Run from command line: 
<pre><code>maven package</code></pre> 

In target directory will get jshint4j-tool-&lt;current project version&gt;-jar-with-dependencies.jar ready to use

## HowToRun

Run from command line:
<pre><code>java -r jshint4j-tool-0.1-SNAPSHOT-jar-with-dependencies.jar  
     &lt;path to your JS file (required)> 
     &lt;comma separated JSHint options, in the form like: noempty:true,jquery=trueident:4 (without spaces)>
</code></pre>

## Report

Report is a simple error line by line output into console. It looks like this:

<pre><code>Errors:
*******
at row '11' an column '38' - Unsafe character.
at row '18' an column '1' - Use the function form of "use strict".
</code></pre>