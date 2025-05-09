package org.sifanur.library.libraryapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXML dosyasını yükle
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // CSS dosyasını sahneye ekle
        scene.getStylesheets().add(Application.class.getResource("styles.css").toExternalForm());

        // Başlık ve sahneyi ayarla
        stage.setTitle("Kütüphane Uygulaması");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
