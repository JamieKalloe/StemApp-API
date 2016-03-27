package com.stemapp;

/**
 * Created by Jamie on 27-3-2016.
 */
public class View {

    public static class Internal extends Private {}

    public static class Private extends Protected {}

    public static class Protected extends Public {}

    public static class Public {}
}
