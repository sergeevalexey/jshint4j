package org.beethoven.jshint;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs JSHint validation with default options.
 *
 * @author Alexey Sergeev
 */
public class RunValidation {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 0 || args.length > 2) {
            System.out.println("Incorrect parameters amount are passed: " + (args!=null?args.length:0) + "\n");
            System.out.println("Program accepts only two parameters");
            System.out.println("1) Path to JS file that you would like to validate");
            System.out.println("2) JSHint options string in the for like this 'forin:true,noarg:true,noempty:true,eqeqeq:true,bitwise:true,strict:true,undef:true,curly:true' (without quotes)");
            System.exit(1);
        }

        File jsFile = new File(args[0]);
        if (!jsFile.exists()) {
            System.out.println("JS file '" + jsFile.getAbsolutePath() + "' is not found!");
            System.exit(1);
        }
        Map<String, String> jsHintOptions = new HashMap<String, String>();
        if (args.length == 2) {
            Pattern pattern = Pattern.compile("(\\w+)\\=(\\w+)");
            for(String keyAndValue: StringUtils.split(args[1], ',')) {
                Matcher matcher = pattern.matcher(keyAndValue.trim());
                if (matcher.matches()) {
                    jsHintOptions.put(matcher.group(1), matcher.group(2));
                } else {
                    System.out.println("Option config '" + keyAndValue + "' is skipped and will not be used as JSHint option");
                }
            }
        }

        List<JSHintError> errors = new JSHintValidator(new JSHintCompiler().compile()).validate(new File(args[0]), jsHintOptions);
        if (errors != null && errors.size() > 0) {
            System.out.println();
            System.out.println("Errors:");
            System.out.println("*******");
            for (JSHintError error : errors) {
                System.out.println(error);
            }
        } else {
            System.out.println("No errors are found");
        }
    }
}
