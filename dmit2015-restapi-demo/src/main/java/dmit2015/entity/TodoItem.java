package dmit2015.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

/**
 * This Jakarta Persistence class is mapped to a relational database table with the same name.
 * If Java class name does not match database table name, you can use @Table annotation to specify the table name.
 * <p>
 * Each field in this class is mapped to a column with the same name in the mapped database table.
 * If the field name does not match database table column name, you can use the @Column annotation to specify the database table column name.
 * The @Transient annotation can be used on field that is not mapped to a database table column.
 */
@Entity
//@Table(schema = "CustomSchemaName", name="CustomTableName")
@Getter
@Setter
public class TodoItem implements Serializable {

    private static final Logger _logger = Logger.getLogger(TodoItem.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "ColumnName", nullable = false)
    private Long id;

    @NotBlank(message = "Task cannot be blank.")
    @Column(nullable = false, length = 64)
    private String task;

    private boolean done;


    public TodoItem() {

    }

    @Version
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    private void beforePersist() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        updateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        return (
                (obj instanceof TodoItem other) &&
                        Objects.equals(id, other.id)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Factory method to create a new TodoItem instance
//    public static TodoItem of(Faker faker) {
//        TodoItem currentTodoItem = new TodoItem();
//        // TODO: uncomment below set each property with fake data
//        // currentTodoItem.setProperty1(faker.provider().method());
//        // currentTodoItem.setProperty2(faker.provider().method());
//        // currentTodoItem.setProperty3(faker.provider().method());
//        return currentTodoItem;
//    }


}