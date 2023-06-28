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

    @Autowired //Внедрили зависимость в UsersController
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    //Получаем всех юзеров и передаем это на отображение во view "all"
    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers()); //это пара ключ-значение. Ключ "users" будет во вьюшке all
        return "users/all";
    }

    // Получаем одного юзера по id и передаем его на отображение во view "showUser"
    //@PathVariable("id") long id - это взятое из url-адреса введенное значение id

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));//это пара ключ-значение. Ключ "user" будет во вьюшке showUser
        return "users/showUser";
    }


    //Это метод, при котором мы получаем форму для создания нового юзера, форму д/заполнения полей
    //Объект класса User здесь создается, но он пустой
    //@GetMapping, потому что этот метод возвращает html-форму для создания нового человека
    //после того, как в new мы нажали на "Создать нового пользователя", нас перенаправит на th:method="POST" th:action="@{/users}", т.е.
    // на метод addUser с аннотацией @PostMapping (это и есть POST-метод) и url которого - "/users"
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }



    //Это метод, который принимает post-запрос, берет данные из html-формы вьюшки new, заполняет
    // этими данными поля пустого юзера и добавляет этого юзера в БД
    //@PostMapping, потому что при запросе будут отправлены в БД параметры-значения д/нового юзера,
    // поэтому это post-запрос, потому что мы ЗАПОСТИМ в БД нового юзера

    //Аннотация @ModelAttribute создаст юзера с теми значениями, которые придут из формы из вьюшки new.
    // Мы здесь принимает в аргумент юзера, который пришел из заполненной формы
    // Аннотация @ModelAttribute делает: создание нового объекта, добавление значений
    // в поля этого объекта и затем добавление этого объекта в модель

    //В скобках нет url-адреса, метод принимает url контроллера, т.е. "/users"
    //User user - это созданный юзер по html-форме, которая реализована во view "new"
    //Здесь использован механизм redirect, который говорит браузеру перейти на какую-то другую страницу.
    //Д/этого пишем "redirect:" и после двоеточия пишем url-адрес той страницы, на которую нужно перенаправить
    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user); // Добавляем этого юзера в БД
        return "redirect:/users";
    }

    //Метод возвращает форму для редактирования юзера, а после заполнения форм, нажав кнопку "Внести изменения в данные пользователя",
    //вьюшка перебросит на th:method="PATCH" th:action="@{/users/{id}, т.е. на метод update, аннотированный @PatchMapping и
    //по адресу "/users/{id}"
    //А здесь в методе edit помещаем в модель юзера с переданным из url-адреса id
    @GetMapping("/{id}/edit")
    public String edit (Model model, @PathVariable("id") long id ){
        model.addAttribute("user",userService.getUserById(id));
        return "users/edit";
    }

    //Метод, который принимает patch-запрос от вьюшки edit - атрибуты th:method="PATCH" th:action="@{/users/{id} указывают на метод update,
    // т.е. когда во view "edit" форму заполнили,
    // нажали кнопку "Внести изменения...", нас перебросит на url-адрес "/users/id" на PATCH-метод
    //@PatchMapping, потому что мы вносим при этом изменения
    //Сюда придут новые данные юзера, которые хотят изменить
    @PatchMapping("/{id}")
    public String update (@ModelAttribute ("user") User updateUser, @PathVariable ("id") long id) {
        userService.updateUser(id, updateUser); //Находим по id того юзера, которого надо изменить
        return "redirect:/users";
    }

// К этому методу нас перебрасывает из вьюшки showUser. Там есть указание на этот метод - th:method="DELETE" th:action="@{/users/{id}
    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users"; // После удаления делаем редирект на /users
    }
}