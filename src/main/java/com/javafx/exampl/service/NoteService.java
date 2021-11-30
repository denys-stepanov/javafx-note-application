package com.javafx.exampl.service;

import com.javafx.exampl.dao.DaoException;
import com.javafx.exampl.dao.NoteDao;
import com.javafx.exampl.entity.Note;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteService {

    private NoteDao noteDao = new NoteDao();

    public Note create(Note note) throws ServiceException {
        try {
            return noteDao.create(note);
        } catch (DaoException e) {
            throw new ServiceException("failed to save");
        }
    }

    public List<Note> findAll() {
        ResultSet resultSet = noteDao.findNote();
        List<Note> listNote = new ArrayList<>();



        try {
            while (resultSet.next()){
                Note note = new Note();
                note.setId(resultSet.getInt(1));
                note.setDescription(resultSet.getString(2));
                note.setCreatedTime(resultSet.getTimestamp(3).toLocalDateTime());
                listNote.add(note);

            }
        } catch (SQLException e) {
                e.printStackTrace();
        }


        return listNote;
    }

    public void delete(int id){
        noteDao.delete(id);
    }
}
