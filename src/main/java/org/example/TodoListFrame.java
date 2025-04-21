package org.example;

import javax.swing.*;
import java.awt.*;

public class TodoListFrame extends JFrame {
    private final TodoTableModel tableModel;
    private final TodoService service;
    private JTable todoTable;

    public TodoListFrame(){
        this.service = new TodoService();
        this.tableModel = new TodoTableModel();

        initializeUI();
        loadTodos();
    }

    private void initializeUI(){
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
        JButton completeButton = new JButton("Toggle Complete");
        JButton deleteButton = new JButton("Delete ToDo");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTodos(){
        service.getTodos(new SimpleCallback<ResponseTodo>() {
            @Override
            public void load(ResponseTodo data) {
                tableModel.setTodos(data.getTodoList());
            }
        });
    }

}
