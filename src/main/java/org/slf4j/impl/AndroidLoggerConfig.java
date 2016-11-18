/**
 * Copyright (c) 2016 ois-yokohama.co.jp
 * All rights reserved.
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package org.slf4j.impl;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class AndroidLoggerConfig {

    static boolean DEBUG = false;

    private static AndroidLoggerConfig _instance = new AndroidLoggerConfig();

    public static AndroidLoggerConfig getInstance() {
        return _instance;
    }

    String tag;

    String Head = "(%F:%L) %m";

    int rootLevel;

    Map<String, Integer> categoryMap = new HashMap<>();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String Head) {
        this.Head = Head;
    }

    public int getRootLevel() {
        return rootLevel;
    }

    public void setRootLevel(int rootLevel) {
        this.rootLevel = rootLevel;
    }

    public void setLevel(String category, int level) {
        categoryMap.put(category, level);
    }

    public int getLevel(String category) {
        if (DEBUG) {
            Log.i("_debug_", "getLevel() " + category);
        }
        if (category.isEmpty()) {
            return rootLevel;
        }
        Integer level = categoryMap.get(category);
        if (level != null) {
            return level;
        }
        int ix = category.lastIndexOf('.');
        if (ix > 0) {
            return getLevel(category.substring(0, ix));
        }
        return getLevel("");
    }
}
