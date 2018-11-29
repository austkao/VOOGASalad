module com.yeet.player {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.yeet.audio;
    requires com.yeet.input;
    requires com.yeet.messenger;
    requires com.yeet.physics;
    requires com.yeet.renderer;
    requires com.google.common;
    exports player.external;
}