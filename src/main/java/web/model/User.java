package web.model;

import javax.persistence.*;

@Entity //Т.е. этот класс будет отображаться в БД в виде таблицы
@Table(name = "users")//указываем, к какой именно таблице мы привязываем класс
public class User {

    @Id // Этой аннотацией помечаем, что поле Id - primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // описывает стратегию по генерации значений д/столбца Id (автоматическое увеличение в этом случае)
    private Long id;
    private String name;
    private String surname;
    private Byte age;

    public User() {
    }
    public User(String name, String surname, Byte age) {
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
