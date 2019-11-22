module ifsp.poo {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires commons.lang3;
    requires commons.collections4;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires mysql.connector.java;
    requires java.sql;
    requires java.persistence;
    requires java.naming;
    requires java.xml.bind;

    opens ifsp.poo to javafx.fxml;
    opens ifsp.poo.controller to javafx.fxml;
    opens ifsp.poo.model to org.hibernate.orm.core;
    exports ifsp.poo.controller;
    exports ifsp.poo.model;
    exports ifsp.poo;
}