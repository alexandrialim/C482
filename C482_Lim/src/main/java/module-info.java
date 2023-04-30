module c482.c482_lim {
    requires javafx.controls;
    requires javafx.fxml;


    opens c482.c482_lim to javafx.fxml;
    exports c482.c482_lim;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}