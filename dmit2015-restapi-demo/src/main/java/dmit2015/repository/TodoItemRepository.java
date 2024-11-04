package dmit2015.repository;

import dmit2015.entity.TodoItem;
import jakarta.data.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * This Jakarta Data interface contains methods for performing CRUD operations on a Jakarta Data entity.
 * */

@Repository
public interface TodoItemRepository {
    @Insert
    void add(TodoItem newTodoItem);

    @Find
    Optional<TodoItem> findById(Long id);

    @Find
    List<TodoItem> findAll();

    @Update
    TodoItem update(TodoItem updatedTodoItem);

    @Delete
    void delete(TodoItem existingTodoItem);

    @Query("select count(o) from TodoItem o")
    long count();
}