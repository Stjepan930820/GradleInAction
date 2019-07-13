package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ：stje
 * @date ：Created in 2019-07-13 19:22
 * @description：
 * @modified By：
 */
public class InMemoryToDoRepository implements ToDoRepository {
    //线程安全的标识符序列号
    private AtomicLong currentId = new AtomicLong();
    //用了保护to-do项目的有效内存结构
    private ConcurrentMap<Long, ToDoItem> toDos = new ConcurrentHashMap<Long, ToDoItem>();

    //通过标识符对to-do项目进行排序
    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<ToDoItem>(toDos.values());
        Collections.sort(toDoItems);
        return toDoItems;
    }

    //
    @Override
    public ToDoItem findById(Long id) {
        return toDos.get(id);
    }
    //如果to-do项目还不存在，就只将它放到Map中
    @Override
    public Long insert(ToDoItem toDoItem) {
        Long id = currentId.incrementAndGet();
        toDoItem.setId(id);
        toDos.putIfAbsent(id, toDoItem);
        return id;
    }

    //如果存在于Map中，就替换掉该项目
    @Override
    public void update(ToDoItem toDoItem) {
        toDos.replace(toDoItem.getId(), toDoItem);
    }
    //如果存在于Map中。就移除该项目
    @Override
    public void delete(ToDoItem toDoItem) {
        toDos.remove(toDoItem.getId());
    }
}
