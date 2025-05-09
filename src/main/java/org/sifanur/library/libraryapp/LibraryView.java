package org.sifanur.library.libraryapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryView {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private ListView<String> bookListView;

    @FXML
    private void handleAddBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            showAlert("Lütfen tüm alanları doldurun.");
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            String entry = title + " - " + author + " - " + year;
            bookListView.getItems().add(entry);

            String fileName = "books_" + UserSession.getCurrentUserTC() + ".txt";
            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(entry + System.lineSeparator());
            }

            titleField.clear();
            authorField.clear();
            yearField.clear();

        } catch (NumberFormatException e) {
            showAlert("Yıl alanına sadece sayı girilmelidir.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteBook() {
        String selected = bookListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lütfen silmek için bir kitap seçin.");
            return;
        }

        bookListView.getItems().remove(selected);

        String fileName = "books_" + UserSession.getCurrentUserTC() + ".txt";
        try {
            List<String> updatedBooks = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals(selected)) {
                        updatedBooks.add(line);
                    }
                }
            }

            try (FileWriter writer = new FileWriter(fileName, false)) {
                for (String book : updatedBooks) {
                    writer.write(book + System.lineSeparator());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateBook() {
        String selected = bookListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Lütfen güncellemek için bir kitap seçin.");
            return;
        }

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            showAlert("Lütfen tüm alanları doldurun.");
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            String updatedEntry = title + " - " + author + " - " + year;

            int selectedIndex = bookListView.getSelectionModel().getSelectedIndex();
            bookListView.getItems().set(selectedIndex, updatedEntry);

            String fileName = "books_" + UserSession.getCurrentUserTC() + ".txt";
            List<String> updatedBooks = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(selected)) {
                        updatedBooks.add(updatedEntry);
                    } else {
                        updatedBooks.add(line);
                    }
                }
            }

            try (FileWriter writer = new FileWriter(fileName, false)) {
                for (String book : updatedBooks) {
                    writer.write(book + System.lineSeparator());
                }
            }

            titleField.clear();
            authorField.clear();
            yearField.clear();

        } catch (NumberFormatException e) {
            showAlert("Yıl alanı sayı olmalıdır.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleListBook() {
        bookListView.getItems().clear();

        String fileName = "books_" + UserSession.getCurrentUserTC() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookListView.getItems().add(line);
            }
        } catch (IOException e) {
            // Dosya yoksa sessizce geç
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
