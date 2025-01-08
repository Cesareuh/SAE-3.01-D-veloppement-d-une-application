module javafx {
    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires java.compiler;
    exports app;
    exports app.control;
    exports app.vue;
    exports app.classes;
}