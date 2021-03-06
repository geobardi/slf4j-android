/*
 * Copyright (c) 2004-2013 QOS.ch
 * Copyright (c) 2016 ois-yokohama.co.jp
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import android.util.Log;

public class AndroidLoggerAdapter extends MarkerIgnoringBase {

    protected int level = -1;

    public AndroidLoggerAdapter(String name) {
        this.name = name;
    }

    public boolean isTraceEnabled() {
        return isLoggable(Log.VERBOSE);
    }

    public void trace(String msg) {
        log(Log.VERBOSE, msg, null);
    }

    public void trace(String format, Object arg) {
        formatAndLog(Log.VERBOSE, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        formatAndLog(Log.VERBOSE, format, arg1, arg2);
    }

    public void trace(String format, Object... args) {
        formatAndLog(Log.VERBOSE, format, args);
    }

    public void trace(String msg, Throwable t) {
        log(Log.VERBOSE, msg, t);
    }

    public boolean isDebugEnabled() {
        return isLoggable(Log.DEBUG);
    }

    public void debug(String msg) {
        log(Log.DEBUG, msg, null);
    }

    public void debug(String format, Object arg) {
        formatAndLog(Log.DEBUG, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        formatAndLog(Log.DEBUG, format, arg1, arg2);
    }

    public void debug(String format, Object... args) {
        formatAndLog(Log.DEBUG, format, args);
    }

    public void debug(String msg, Throwable t) {
        log(Log.VERBOSE, msg, t);
    }

    public boolean isInfoEnabled() {
        return isLoggable(Log.INFO);
    }

    public void info(String msg) {
        log(Log.INFO, msg, null);
    }

    public void info(String format, Object arg) {
        formatAndLog(Log.INFO, format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        formatAndLog(Log.INFO, format, arg1, arg2);
    }

    public void info(String format, Object... args) {
        formatAndLog(Log.INFO, format, args);
    }

    public void info(String msg, Throwable t) {
        log(Log.INFO, msg, t);
    }

    public boolean isWarnEnabled() {
        return isLoggable(Log.WARN);
    }

    public void warn(String msg) {
        log(Log.WARN, msg, null);
    }

    public void warn(String format, Object arg) {
        formatAndLog(Log.WARN, format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        formatAndLog(Log.WARN, format, arg1, arg2);
    }

    public void warn(String format, Object... args) {
        formatAndLog(Log.WARN, format, args);
    }

    public void warn(String msg, Throwable t) {
        log(Log.WARN, msg, t);
    }

    public boolean isErrorEnabled() {
        return isLoggable(Log.ERROR);
    }

    public void error(String msg) {
        log(Log.ERROR, msg, null);
    }

    public void error(String format, Object arg) {
        formatAndLog(Log.ERROR, format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        formatAndLog(Log.ERROR, format, arg1, arg2);
    }

    public void error(String format, Object... args) {
        formatAndLog(Log.ERROR, format, args);
    }

    public void error(String msg, Throwable t) {
        log(Log.ERROR, msg, t);
    }

    private void formatAndLog(int priority, String format, Object... args) {
        if (isLoggable(priority)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
            logInternal(priority, ft.getMessage(), ft.getThrowable());
        }
    }

    private void log(int priority, String message, Throwable throwable) {
        if (isLoggable(priority)) {
            logInternal(priority, message, throwable);
        }
    }

    protected boolean isLoggable(int priority) {
        if (level < 0) {
            level = AndroidLoggerConfig.getInstance().getLevel(name);
        }
        return (level <= priority);
    }

    protected void logInternal(int priority, String message, Throwable throwable) {
        String format = AndroidLoggerConfig.getInstance().getFormat();
        if (format != null) {
            message = format(format, message);
        }
        if (throwable != null) {
            message += '\n' + Log.getStackTraceString(throwable);
        }
        String tag = AndroidLoggerConfig.getInstance().getTag();
        tag = (tag != null)? tag: name;
        Log.println(priority, tag, message);
    }

    protected String format(String format, String message) {
        StackTraceElement elem = null;
        StringBuilder sb = new StringBuilder(80);
        int len = format.length();
        for (int ix = 0; ix < len; ix ++) {
        	char ch = format.charAt(ix);
        	if (ch == '%' && (ix + 1) < len) {
        		char tp = format.charAt(ix + 1);
        		ix ++;
        		switch (tp) {
        		case 'c':
        			sb.append(getSimpleName());
        			break;
        		case 'F':
        			elem = (elem == null)? getCallerElement(): elem;
        			sb.append(elem.getFileName());
        			break;
        		case 'L':
        			elem = (elem == null)? getCallerElement(): elem;
        			sb.append(elem.getLineNumber());
        			break;
        		case 'M':
        			elem = (elem == null)? getCallerElement(): elem;
        			sb.append(elem.getMethodName());
        			break;
        		case 'm':
        			sb.append(message);
        			break;
        		case '%':
        			sb.append(tp);
        			break;
        		default:
        			sb.append(ch);
        			sb.append(tp);
        		}
        	} else {
        		sb.append(ch);
        	}
        }
        return sb.toString();
    }

    final static int CALLER_NO = 5;
    private StackTraceElement getCallerElement() {
        StackTraceElement[] elems = new Throwable().getStackTrace();
        if (elems.length < CALLER_NO) {
            return new StackTraceElement("-", "-", "-", -1);
        }
        return elems[CALLER_NO];
    }

    protected String getSimpleName() {
    	int ix = name.lastIndexOf('.');
    	return (ix >= 0)? name.substring(ix + 1): name;
    }

}
