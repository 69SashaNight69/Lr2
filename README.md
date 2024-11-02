Колодько Олександр ТВ-21 Варіант 2
--
Мета: Ознайомлення з асинхронністю в Java.  
Завдання: Розробити рішення задач відповідно до варіанту з реалізацією
асинхронності.
Очікуваний результат: Код має бути написаний коректно (синтаксис,
ООП, обов’язкові конструкції), відповідати вимогам завдання та успішно
виконувати задачі.
--
Вимоги до кожного варіанту:
Напишіть програму, яка обробляє великі масиви різних чисел різними
потоками в асинхронному режимі.
Масиви потрібно заповнювати рандомними числами, згенерованими у
заданому діапазоні. Діапазон повинен задавати користувач. Кількість елементів
в масиві від 40 до 60.
Для обробки масиви потрібно розбивати на частини (наприклад, 1-100,
101-200 і т.д.), частини масиву потрібно обробляти в окремих потоках.
Використовуйте Future для збору та виводу результатів. Також останнім
рядком виводьте на екран час роботи програми.
Впишіть у логіку програми перевірки isDone() та isCancelled().
--
Напишіть Callable, який приймає число N і знаходить всі прості
числа до N. Створіть кілька потоків, кожен з яких обробляє свій
діапазон. Використовуйте Future, щоб зібрати результати обчислень з
усіх потоків.
Діапазон [0; 1000] – цілі числа, число N задає користувач.
Використати CopyOnWriteArrayList.
--
Короткий опиз коду
Цей код призначений для асинхронної обробки масиву чисел, розбитого на діапазони, з метою визначення простих чисел у кожному з них. Програма генерує масив випадкових чисел розміром від 40 до 60 у межах 1-1000, який ділиться на визначені діапазони (1-100, 101-200 тощо). Для кожного діапазону створюється окреме завдання `PrimeCheckerTask`, що реалізує `Callable` для перевірки чисел на простоту до значення N, заданого користувачем. Завдання виконуються асинхронно в пулі потоків `ExecutorService`, а результати збираються за допомогою `Future`. При зборі результатів перевіряються `isDone()` і `isCancelled()`, щоб контролювати завершення та скасування завдань. Для збереження чисел у кожному діапазоні використовується `CopyOnWriteArrayList`, що забезпечує безпеку потоків. У кінці програма виводить прості числа в кожному діапазоні та загальний час виконання, що вимірюється як різниця між початковим і кінцевим моментом виконання коду.
--
