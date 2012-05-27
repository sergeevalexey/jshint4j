package org.beethoven.jshint;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Validates JS file using JS hint
 *
 * @author Alexey Sergeev
 */
public class JSHintValidator {
    // JSHINT function
    private Function jsHintFunction;

    public JSHintValidator() throws IOException {
        this(new JSHintCompiler().compile());
    }

    /**
     * Create new validator
     *
     * @param jsHintFunction - JSHINT function
     */
    public JSHintValidator(Function jsHintFunction) {
        this.jsHintFunction = jsHintFunction;
    }

    /**
     * Validates JS file using JS hint using default options.
     *
     * @param jsFile - JS file under validation
     * @return error list
     * @throws IOException
     */
    public List<JSHintError> validate(File jsFile) throws IOException {
        return validate(jsFile, new HashMap<String, String>());
    }

    /**
     * Validates JS file using JS hint.
     * By default less then 100 errors will be parsed, if another value is not specified via 'maxerr' option.
     *
     * @param jsFile  - JS file under validation
     * @param options - validation <a href="http://www.jshint.com/options/">options</a>
     * @return error list
     * @throws IOException in case if JS file can't be read
     */
    public List<JSHintError> validate(File jsFile, Map<String, String> options) throws IOException {
        List<JSHintError> result = Collections.emptyList();

        Context context = Context.enter();
        ScriptableObject scope = context.initStandardObjects();
        NativeObject functionOptions = new NativeObject();
        if (options != null) {
            for (Map.Entry<String, String> o : options.entrySet()) {
                functionOptions.defineProperty(o.getKey(), o.getValue(), 1);
            }
        }
        if (!functionOptions.containsKey("maxerr")) {
            functionOptions.defineProperty("maxerr", 100, 1);
        }
        Boolean status = (Boolean) jsHintFunction.call(context, scope, scope, new Object[]{IOUtils.toString(jsFile.toURI()), functionOptions});
        if (Boolean.FALSE.equals(status)) {
            NativeArray errors = (NativeArray) jsHintFunction.get("errors", scope);
            if (errors != null) {
                result = convertErrors(errors);
            }
        }
        Context.exit();

        return result;
    }

    private List<JSHintError> convertErrors(NativeArray errorsNativeArray) {
        List<JSHintError> JSHintErrors = new LinkedList<JSHintError>();
        for (Object errorObject : errorsNativeArray) {
            if (errorObject instanceof NativeObject) {
                JSHintError JSHintError = toError((NativeObject) errorObject);
                if (JSHintError != null) {
                    JSHintErrors.add(JSHintError);
                }
            }
        }
        return JSHintErrors;
    }

    private JSHintError toError(NativeObject nativeError) {
        int line = toInt(nativeError.get("line"));
        int character = toInt(nativeError.get("character"));
        if (line < 0 || character < 0) {
            return null;
        }
        Object reasonObject = nativeError.get("reason");
        if (reasonObject instanceof String) {
            return new JSHintError(line, character, (String) reasonObject);
        }
        return null;
    }

    private int toInt(Object o) {
        if (o instanceof Number)
            return ((Number) o).intValue();
        else
            return -1;
    }

}
