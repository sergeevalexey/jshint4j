package org.beethoven.jshint;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Checks validation function.
 *
 * @author Alexey Sergeev
 */
public class JSHintValidatorTest {
    @Test
    public void testMissingSemicolon() throws Exception {
        // create temp JS file with the test script
        File file = File.createTempFile(String.valueOf(this.hashCode()), ".js");
        FileUtils.write(file, "function test() {alert('ok!')}");
        List<JSHintError> errors = new JSHintValidator(new JSHintCompiler().compile()).validate(file);
        String message = "There should be one error, ';' is missed at line 1,  column 30";
        Assert.assertNotNull(message, errors);
        Assert.assertEquals(message, 1, errors.size());
        Assert.assertEquals(message, 1, errors.get(0).getRow());
        Assert.assertEquals(message, 30, errors.get(0).getColumn());
        // delete temporary JS file
        file.deleteOnExit();
    }
}
