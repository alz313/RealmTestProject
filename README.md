# RealmTestProject
Simple project to test Realm DB

Тестовое задание

1. Создать UI экрана приложения;
2. Создать модель Booking в Realm
- userName: String
- date: Date
- acceptedOn: Date
- rejectedOn: Date
3. UI экрана состоит из двух секций:
- Верхняя - список карточек, который пользователь может свайпить в стороны (лево-право). Свайп влево - Reject Action, свайп вправо - Accept
- Нижняя - список карточек Accepted Booking.

Описание логики:
- При запуске автоматически создаем 10 bookings. 3 - accepted, 7 - новых.
- Когда пользователь принимает или отклоняет Booking - он удаляется с экрана и БД, или добавляется в список ниже
- Счетчик показывает количество Accepted Booking в списке
- Снизу нативный navbar (две неактивные вкладки игнорировать)
- Верхняя и нижняя часть скрина скролятся
