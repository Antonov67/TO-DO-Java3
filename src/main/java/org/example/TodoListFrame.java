package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TodoListFrame extends JFrame {
    private final TodoTableModel tableModel;
    private final TodoService service;
    private JTable todoTable;

    public TodoListFrame() {
        this.service = new TodoService();
        this.tableModel = new TodoTableModel();

        initializeUI();
        loadTodos();
    }

    private void initializeUI() {
        setTitle("Список задач");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //таблица задач
        todoTable = new JTable(tableModel);
        add(new JScrollPane(todoTable), BorderLayout.CENTER);
        //панель кнопок
        JPanel buttonPanel = new JPanel();
        //
        JButton addButton = new JButton("Add ToDo");
        addButton.addActionListener(e -> addTodo());

        JButton completeButton = new JButton("Toggle Complete");
        completeButton.addActionListener(e -> updateTodo());

        JButton deleteButton = new JButton("Delete ToDo");
        deleteButton.addActionListener(e -> deleteTodo());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadTodos());

        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTodos() {
        service.getTodos(new SimpleCallback<ResponseTodo>() {
            @Override
            public void load(ResponseTodo data) {
                tableModel.setTodos(data.getTodoList());
            }
        });
    }

    private void addTodo() {
        String title = JOptionPane.showInputDialog(
                this,
                "Напишите задачу");
        if (!title.trim().isEmpty()){
            Todo todo = new Todo(title, false);
            service.createTodo(todo, new SimpleCallback<Todo>() {
                @Override
                public void load(Todo data) {
                   if (data != null){
                       loadTodos();
                   }
                }
            });
        }
    }

    private void deleteTodo(){
        int selectedRow = todoTable.getSelectedRow();
        if (selectedRow >= 0){
            Todo todo = tableModel.getTodoAt(selectedRow);
            service.deleteTodo(todo.getId(), new SimpleCallback<String>() {
                @Override
                public void load(String data) {
                    if (data != null){
                        loadTodos();
                    }
                }
            });
        }
    }

    private void updateTodo(){
        int selectedRow = todoTable.getSelectedRow();
        if (selectedRow >= 0) {
            Todo todo = tableModel.getTodoAt(selectedRow);
            todo.setCompleted(!todo.isCompleted());
            service.updateTodo(todo.getId(), todo, new SimpleCallback<Todo>() {
                @Override
                public void load(Todo data) {
                    if (data != null){
                        loadTodos();
                    }
                }
            });
        }

    }

}
