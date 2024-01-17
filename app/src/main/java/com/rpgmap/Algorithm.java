package com.rpgmap;

import android.util.Log;

public final class Algorithm {
    private Algorithm() {}


    private static native String init0(String path) throws Exception;
    private static native String gen0(String json) throws Exception;
    private static native String view0(int x, int y) throws Exception;

    public static String initialize(String path) {
        try {
            System.loadLibrary("back");
            return init0(path);
        }
        catch(Exception ex) {
            Log.e("Algorithm", ex.toString());
            return ex.getMessage();
        }
    }

    public static String generate(String json) {
        try {
            return gen0(json);
        }
        catch(Exception ex) {
            Log.e("Algorithm", ex.toString());
            return ex.getMessage();
        }
    }


    public static String view(int x, int y) {
        try {
            return view0(x, y);
        }
        catch(Exception ex) {
            Log.e("Algorithm", ex.toString());
            return ex.getMessage();
        }
    }
}
