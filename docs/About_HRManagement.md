##Use instructions

При первом запуске создаеться пустая база данных кроме следующих данных:
(эти данные нельзя удалить или изменить через преложение)
1) User user:svitlana.anulich@gmail.com pass:22222222 role: ADMIN
2) roles: ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR.
3) status: WORK, VACATION, HOSPITAL, REMOTE_WORK, ABSENT.
4) event : Null event.


Некоторые особенности реализации и работы:
1) При добавлении нового сотрудника в систему указываеться его email который будет служит логином при входе.
Пароль ставиться стандарный (pass:11111)
Так же работнику отправляеться приветсвеный email следующего содержания

Dear, employee.
 Congratulations you with authorization in HRManager.
You can access the data by using your email as login.

We strongly recommend changing the password. To do this, follow the link http://localhost:8888/api/employees/user/pass


2) Изменить email или пароль к системе любой работник может только свой (не зависимо от того какая у него роль: ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR)
3) Если пользователь имеет роль ROLE_USER то он может видеть только ту информацию которая касаеться его и никого другого.
4) После того как сотрудник проработает год ему будут доступно то количество дней отпуска которое соотвествует его должности.
На следующий год не израсходываные дни будут просто плюсоваться к новым.
5) Для расчета отпускных береться средняя зарплата за последние 12 месяцев (причем та что была получена на руки)
6) Для расчета больничного береться средняя зарплата за посление 6 месяцев (причем та что была получена на руки)
    Плюс учитываеться сумарный опыт сотрудника (как в этой фирме так и в предыдущих)


