package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    //Получаем всех юзеров и передаем это на отображение во view "all_users"
    //Это метод GET/users
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers()); //здесь пара ключ-значение
        return "users/all";
    }

    // Получаем одного юзера по id и передаем его на отображение во view "user_by_id"
    //Это метод GET/users/id
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/showUser";
    }


    //Это метод GET/users/new, при котором мы получаем форму для заполнения полей
    // д/создания нового юзера (изначально он здесь пустой)
    // Аннотация @ModelAttribute делает: создание нового объекта, добавление значений
    // в поля этого объекта, которые берет из http-запроса и затем добавление этого объекта в модель
    // @GetMapping, потому что при запросе мы получим html-форму
    // Этот метод возвращает html-форму для создания нового человека
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    //Это метод POST/users - создаем нового юзера
    //Аннотация @ModelAttribute создаст юзера с теми значениями, которые придут из формы. Мы
    // здесь принимает в аргумент юзера, который пришел из заполненной формы
    //Метод, который принимает post-запрос, берет данные из html-формы и добавляет нового юзера в БД
    //@PostMapping, потому что при запросе будут отправлены параметры-значения д/нового юзера, поэтому это post-запрос
    //В скобках нет url-адреса, потому что мы попадаем сюда из "/new"
    //User user - это созданный юзер по html-форме, которая реализована во view "new"
    //Здесь использован механизм redirect, который говорит браузеру перейти на какую-то другую страницу.
    //Д/этого пишем "redirect:" и после двоеточия пишем url-адрес той страницы, на которую нужно перенаправить
    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user); // Добавляем этого юзера в БД
        return "redirect:/users";
    }

    // Находим одного юзера по id, удаляем его и передаем это на отображение во view "remove_user"
    //Метод возвращает форму для редактирования юзера
    //Помещаем в модель юзера с переданным id, потом передаем этого юзера на отображение,
    // где будет форма по его редактированию
    @GetMapping("/{id}/edit")
    public String edit (Model model, @PathVariable("id") long id ){
        model.addAttribute("user",userService.getUserById(id));
        return "users/edit";
    }

    //Метод, который принимает html-запрос на адрес "/users/id", т.е. во view "edit" форму заполнили,
    // нажали кнопку "Внести изменения..." и на перебросит на url-адрес "/users/id"
    //@PatchMapping, потому что мы вносим при этом изменения
    @PatchMapping("/{id}")
    public String update (@ModelAttribute ("user") User updateUser, @PathVariable ("id") long id) {
        userService.updateUser(id, updateUser); //Находим по id того юзера, которого надо изменить
        return "redirect:/users";
    }


    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users"; // После удаления делаем редирект на /users
    }
}