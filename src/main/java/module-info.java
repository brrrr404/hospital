module com.example.hospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires java.sql;
    requires mysql.connector.java;

    exports com.example.hospital;
    exports Controller;

    opens com.example.hospital to
            javafx.fxml, javafx.base;
    opens Controller to
            javafx.fxml;
    opens Entity
            to javafx.base;
}