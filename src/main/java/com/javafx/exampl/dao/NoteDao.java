package com.javafx.exampl.dao;

import com.javafx.exampl.entity.Note;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

public class NoteDao {

    private static final String URL = "jdbc:mysql://localhost:3306/note_store";
    private static final String USER = "kiska";
    private static final String PASSWORD = "secret";

    public static final String INSERT_QUERY = "INSERT INTO note(DESCRIPTION, CREATED_TIME) VALUES (?, ?)";

    public static final String FIND_NOTE = "SELECT * FROM note_store.note";

    public static final String DELETE_NOTE = "DELETE FROM note WHERE id=?";

    public Note create(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            Timestamp timestamp = Timestamp.valueOf(note.getCreatedTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            note.setId(id);
            return note;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public ResultSet findNote(){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(FIND_NOTE);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id){
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTE);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
