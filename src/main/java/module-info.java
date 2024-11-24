module prj.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens prj.library to javafx.fxml;
    exports prj.library;
    exports prj.library.models;
    opens prj.library.models to javafx.fxml;
    exports prj.library.networking;
    opens prj.library.networking to javafx.fxml;
    exports prj.library.networking.NI;
    opens prj.library.networking.NI to javafx.fxml;
}