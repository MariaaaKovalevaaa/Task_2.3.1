package model;

import jakarta.persistence.*;

@Entity //Т.е. этот класс будет отображаться в БД в виде таблицы
@Table (name = "users")//указываем, к какой именно таблице мы привязываем класс
public class User {

    @Id // Этой аннотацией помечаем, что поле Id - primary key
    @GeneratedValue (strategy = GenerationType.IDENTITY) // описывает стратегию по генерации значений д/столбца Id (автоматическое увеличение в этом случае)
    @Column (name = "id") // указываем, с каким столбцом в таблице users нашей БД связано это поле
    private Long id;

    @Column (name = "name")
    private String name;

    @Column (name = "lastName")
    private String lastName;

    @Column (name = "age")
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}

