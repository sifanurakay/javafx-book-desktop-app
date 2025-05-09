package org.sifanur.library.libraryapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField tcField;
    @FXML
    private PasswordField passwordField;

    //giriş yap butonu
    @FXML
    private void handleLogin(){
        String enteredTc = tcField.getText().trim();
        String enteredPassword = passwordField.getText().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(enteredTc) && parts[1].equals(enteredPassword)){
                   //kullanıcıyı oturuma al
                    UserSession.setCurrentUserTC(enteredTc);
                    // Giriş başarılı → Ana ekranı yükle
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("library-view.fxml"));
                    Scene scene=new Scene(loader.load());
                    Stage stage=(Stage) tcField.getScene().getWindow();
                    stage.setScene(scene);
                    return;
                }
            }
            showAlert("Hatalı TC veya Sifre Girildi.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Scene scene=new Scene(loader.load());
        Stage stage = (Stage) tcField.getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gires Hatası");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
