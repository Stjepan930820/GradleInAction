package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryToDoRepositoryTest {

    private ToDoRepository inMemoryToDoRepository;
    @Before
    public void setUp() throws Exception {
        inMemoryToDoRepository=new InMemoryToDoRepository();
    }

    @Test
    public void insertToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write unit tests");
        Long newId = inMemoryToDoRepository.insert(toDoItem);
        assertNotNull(newId);

        ToDoItem persistedToDoItem =inMemoryToDoRepository.findById(newId);
        assertNotNull(persistedToDoItem);
        assertEquals(toDoItem,persistedToDoItem);

    }
}