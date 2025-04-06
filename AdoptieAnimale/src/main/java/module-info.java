module com.onofreiflavius.adoptieanimale {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.compiler;
    requires java.net.http;
    requires javafx.swing;
    requires itextpdf;
    requires jbcrypt;

    opens com.onofreiflavius.adoptieanimale to javafx.fxml;
    exports com.onofreiflavius.adoptieanimale;
    exports com.onofreiflavius.adoptieanimale.controller.authentication;
    exports com.onofreiflavius.adoptieanimale.controller.database;
    exports com.onofreiflavius.adoptieanimale.model;
    exports com.onofreiflavius.adoptieanimale.controller.adoption;
    exports com.onofreiflavius.adoptieanimale.controller.scenes;
    exports com.onofreiflavius.adoptieanimale.view.authentication;
    exports com.onofreiflavius.adoptieanimale.view.menu;
}