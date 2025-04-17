package org.example;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTodo {

    @SerializedName("items")
    private List<Todo> todoList;

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }
}
