package org.beethoven.jshint;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;

/**
 * Compiles 'jshint.js' file and retrieve JSHINT function from it.
 *
 * @author Alexey Sergeev
 */
public class JSHintCompiler {
    private static final String JSHINT_FUNCTION = "JSHINT";

    public Function compile() throws IOException {
        // compile JSHint file
        Context context = Context.enter();
        String scriptSource = IOUtils.toString(this.getClass().getResource("/org/beethoven/jshint/jshint-r07.js"));
        Script jsHintScript = context.compileString(scriptSource, "<" + JSHINT_FUNCTION + " script>", 1, null);
        Context.exit();
        // retrieve function
        context = Context.enter();
        ScriptableObject scope = context.initStandardObjects();
        jsHintScript.exec(context, scope);
        Function jsHintFunction = (Function) scope.get(JSHINT_FUNCTION, scope);
        Context.exit();

        return jsHintFunction;
    }
}
