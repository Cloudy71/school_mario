/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 21:02
*/

package cz.cloudy.fxengine.io;

public class CodeAccessProtector {
    private static boolean ENABLED = false; // Might be performance demanding (Went from 760 fps to 430 fps).
    // TODO: May replace protector with multi-package accessors which will extend main accessors in another package.

    public static boolean isAccessedFromClass(Class<?>... clazz) {
        if (!ENABLED) return true;

        StackTraceElement[] stackTrace = Thread.currentThread()
                                               .getStackTrace();
        for (Class<?> aClass : clazz) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                try {
                    if (Class.forName(stackTraceElement.getClassName())
                             .equals(aClass)) {
                        return true;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isAccessedFromPackage(String... packages) {
        if (!ENABLED) return true;

        StackTraceElement[] stackTrace = Thread.currentThread()
                                               .getStackTrace();
        for (String pkg : packages) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (stackTraceElement.getClassName()
                                     .contains(pkg)) {
                    return true;
                }
            }
        }
        return false;
    }
}
