# language: ru
  @editUserTest
Функция: Редактирование пользователя
  Сервер должен побавить тестового пользователя, а затем изменить каждый из его параметров.
  Изменения должны быть отражены в UI, а также в DB.

  Предыстория:
    Допустим мы авторизовались с ролью админа
    И добавили пользователя с именем "Тестирование", фамилией "Редактирования", логином "Петрович", паролем "МуПас@*()!", телефоном "103", ролью "user".

  @correct
  Сценарий: Успешное редактирование пользователя
    Если на сервере имеется запись с логином "Петрович"
    То вносим следующие изменения в запись с логином "Петрович": имя "Тест", фамилия "Валид", логином "Тест", паролем "Валид", телефоном "000", ролью "admin"
    Тогда в UI и DB будет отображены изменения: имя "Тест", фамилия "Валид", логином "Тест", паролем "Валид", телефоном "000", ролью "admin"
#
#  @fail
#  Сценарий: Ошибочное редактирование пользователя из-за существующего в DB логина
#    Если на сервере имеется запись с логином "Петрович"
#    То вносим следующие изменения: имя "Тест", фамилия "Валид", логином "admin", паролем "Валид", телефоном "000", ролью "admin"
#    Тогда должно появиться сообщение об ошибке из-за наличия в DB аналогичного логина
#
#  @fail
#  Сценарий: Ошибочное редактирование пользователя из-за неверного номера телефона
#    Если на сервере имеется запись с логином "Петрович"
#    То вносим следующие изменения: имя "Тест", фамилия "Валид", логином "Тест", паролем "Валид", телефоном "фывыфв", ролью "admin"
#    Тогда должно появиться сообщение об ошибке из-за некоректного номера телефона
#
#  @fail
#  Сценарий: Ошибочное редактирование пользователя из-за пустого логина или пароля
#    Если на сервере имеется запись с логином "Петрович"
#    То вносим следующие изменения: имя "Тест", фамилия "Валид", логином "", паролем "Валид", телефоном "000", ролью "admin"
#    Тогда должно появиться сообщение об ошибке пустого логина
#    Если на сервере имеется запись с логином "Петрович"
#    То вносим следующие изменения: имя "Тест", фамилия "Валид", логином "Тест", паролем "", телефоном "000", ролью "admin"
#    Тогда должно появиться сообщение об ошибке пустого пароля
#
