module org.sifanur.library.libraryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.sifanur.library.libraryapp to javafx.fxml;
    exports org.sifanur.library.libraryapp;
}