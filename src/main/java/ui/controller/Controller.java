package ui.controller;

import message.Message;
import message.MessageService;
import student.Student;

import java.util.Vector;

// There is a public static controller in the Window class
// Use the below code to access it in your UI classes
//
// import ui.general.Window
//
// Window.controller.(method call)

public class Controller {
    MessageService mService = new MessageService();

    public void createMessage(String text, Student author,
                              String courseNumber, Integer board, Message message) {
        mService.createMessage(text, author, courseNumber, board, message);
    }

    public void deleteMessage(String id) {
        mService.deleteMessage(id);
    }

    public void editRepostMessage(String id, String text) {
        mService.editRepostMessage(id, text);
    }

    public Vector<Object> fetchBoard(Integer messageBoard) {
        return mService.fetchBoard(messageBoard);
    }
}
