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
import java.io.FileWriter;
import java.io.*;

public class RegisterController {
    @FXML
    private TextField tcField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister(){
        String tc=tcField.getText().trim();
        String password=passwordField.getText().trim();

        if (tc.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Bos alan bırakmayınız!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts[0].equals(tc)){
                    showAlert(Alert.AlertType.ERROR, "Bu TC ile zaten kayıt olunmuş.");
                    return;
                }
            }
        }catch (IOException ignored) {}

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt",true))){
            writer.write(tc+","+password);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Kayıt başarılı! Giriş ekranına yönlendiriliyorsunuz.");

            goToLogin();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) tcField.getScene().getWindow();
        stage.setScene(scene);
    }
    private void showAlert(Alert.AlertType type , String message){
        Alert alert = new Alert(type);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

