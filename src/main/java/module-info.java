module org.example.generator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;

    opens org.example.generator to javafx.fxml;
    exports org.example.generator;
    exports org.example.generator.Controller;
    opens org.example.generator.Controller to javafx.fxml;
}