module com.yeet.renderer {
    exports renderer.external;
    exports renderer.external.Structures;
    requires com.yeet.messenger;

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    //requires kotlin.stdlib;
    //requires junit;
    requires org.junit.jupiter.api;

}
