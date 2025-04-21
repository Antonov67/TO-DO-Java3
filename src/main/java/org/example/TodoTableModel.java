package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TodoTableModel extends AbstractTableModel {
    private final List<Todo> todoList;
    private final String[] columnsNames = {"ID", "Title", "Completed", "Created"};

    public TodoTableModel(){
        this.todoList = new ArrayList<>();
    }
    @Override
    public int getRowCount() {
        return todoList.size();
    }
    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Todo todo = todoList.get(rowIndex);
        return switch (columnIndex){
            case 0 -> todo.getId();
            case 1 -> todo.getTitle();
            case 2 -> todo.isCompleted() ? "Yes" : "No";
            case 3 -> todo.getCreated();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnsNames[column];
    }

    public void setTodos(List<Todo> todoList){
        this.todoList.clear();
        this.todoList.addAll(todoList);
        fireTableDataChanged();
    }
}
