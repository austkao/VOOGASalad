module com.yeet.main {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;

    requires com.yeet.messenger;
    requires com.google.common;
    requires com.yeet.player;
    requires com.yeet.editor;
    requires com.yeet.console;

    exports main;
}