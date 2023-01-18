# Kastro_learn_qa_javaAppiumAutomation_hw5

Ex8: Рефакторинг тестов Отрефакторить тесты, написанные в предыдущих занятиях (Ex3, Ex5, Ex6) под текущую структуру
тестов.

Ex9*: Рефакторинг темплейта В одном из занятий четвертого урока упоминается о методе темплейтов. Там рассказано, как
работать с локаторами, которые зависят от подстроки SUBSTRING. В примере из теста у нас всего одна подстрока. Но
подобные локаторы можно строить с любым количеством подстрок.

В приложении Wikipedia результатом поиска является набор ссылок на статьи, и каждая ссылка содержит как заголовок
статьи, так и краткое описание. Например, для запроса “Java” одним из результатов выдачи будет “Java (Programming
language)” и описание “Object-oriented programming language”.

Задача:
1 - Подобрать локатор, который находит результат поиска одновременно по заголовку и описанию (если заголовок или
описание отличается - элемент не находится).

2 - Добавить соответствующий метод в секцию TEMPLATES METHODS класса SearchPageObject.

3 - В этот же класс добавить метод waitForElementByTitleAndDescription(String title, String description). Он должен
дожидаться результата поиска по двум строкам - по заголовку и описанию. Если такой элемент не появляется, тест должен
упасть с читаемой и понятной ошибкой.

4 - Написать тест, который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как
минимум 3 результата). Далее тест должен убеждаться, что первых три результата присутствуют в результате поиска.

Результатом выполнения задания должен быть дифф к коду, который был написан на четвертом занятии. В этом диффе должны
быть вспомогательные методы, лежащие в соответствующих классах и код теста, лежащего в соответствующем классе. Тест
должен работать (т.е. проходить при верном результате поиска и обязательно падать, если результат поиска изменился).


Ex11: Для тех, кто проходит курс без макбук, надо прислать список файлов, в которые Вам пришлось бы вносить изменения,
чтобы адаптировать под iOS тест на удаление одной сохраненной статьи из двух.

Ответ:

1-	Изменять класс CoreTestCase, писать метод для выбора платформа запуска теста.

2-	В папке  lib создать класс  Platform  и переместить туда метод DesireCapabilities и  все нужные поля.

3-	В папке ui создать папки android  и ios.

4-	В папка  android  создать абстрактные классы androidSearchPageObject и iOSSearchPageObject, перемещать туда все константы.

5-	В папке  lib создать пакет factories и тут создать класс SearchPageObjectFactory.

6-	В классе Platform создать статическое поле  instance, приватный конструктор и метод getInstance.

7-	 В классе CoreTestCase изменять способ передачи значения для переменой driver.

8-	А классе SearchPageObjectFactory написать метод get.

9-	 В классе MyListTests поменять создание объектов SearchObject через  SearchPageObjectFactory.

10-	В классе ArticlePageObject поменять метод getArticleTitle. чтобы искать элемент по name.

11-	В классе MyListTests добавить условия (android, ios) для тех методов которые отличаются в работе в разных платформах.

12-	В классе ArticlePageObject создать метод addArticlesToMySaved для ios.

13-	 Превращать классе  NavigationUI в абстрактный.

14-	В пакете ui.android создать класс AndroidNavigationUI.

15-	В пакете ui.ios создать класс iOSNavigationUI

16-	В классе класс iOSNavigationUI написать селектор MY_LIST_LINK.

17-	В пакете factories создать класс NavigationUIFactory.

18-	В классе MyListTests используем класс NavigationUIFactory для создания объект типа NavigationUI.

19-	Поменять класс MyListPageObject.

20-	Создать классы AndroidMyListPageObject и iOSMyListPageObject.

21-	Создать класс MyListPageObjectFactory.

22-	В классе MyListTests используем класс MyListPageObjectFactory для создания объекта типа MyListPageObject.

23-	В классе iOSMyListPageObject изменяем локатор для ARTICLE_BY_TILTE_TPL.

24-	В классе MyListPageObject поменять метод swipeElementToLeft, Чтобы работал в зависимости от платформы.

25-	В классе MainPageOBject написать метод clickElementToTheUpperCorner, чтобы кликнуть по кнопке удаления стати.

26-	В кдассе MyListPageObject изменять метод swipeByArticleToDelete.