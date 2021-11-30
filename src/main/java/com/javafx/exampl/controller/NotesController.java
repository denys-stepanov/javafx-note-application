package com.javafx.exampl.controller;

import com.javafx.exampl.entity.Note;
import com.javafx.exampl.service.NoteService;
import com.javafx.exampl.service.ServiceException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NotesController implements Initializable {

    private NoteService noteService = new NoteService();

    @FXML
    private TextArea noteDescription;
    @FXML
    private ListView<Note> noteList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCreatedNote();
    }

    public void createNote() throws ServiceException {
        String noteDescriptionText = noteDescription.getText();

        Note note = new Note();
        note.setCreatedTime(LocalDateTime.now());
        note.setDescription(noteDescriptionText);

        Note createNoteId = noteService.create(note);
        noteList.getItems().add(createNoteId);
    }
    public void addCreatedNote(){
        noteList.getItems().addAll(noteService.findAll());
    }
    public void deleteNote() {
        Note selectedItem = noteList.getSelectionModel().getSelectedItem();
        noteService.delete(selectedItem.getId());
        noteList.getItems().remove(selectedItem);
    }
}
