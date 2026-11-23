# Лабораторна робота 2
 
## Завдання
 
Створити консольний додаток, що відповідає наступним вимогам:
1. Створити XML-файл та відповідну XSD-схему: прості й комплексні типи, переліки, граничні значення, обов'язкові атрибути, тип `ID`.
2. Створити Java-класи, що відповідають цьому опису.
3. Розібрати XML парсерами SAX, DOM та StAX і заповнити колекцію об'єктів.
4. Для сортування використати інтерфейс `Comparator`.
5. Провалідувати XML за XSD.
6. За допомогою XSL розбити список на підсписки за характеристикою (тут `Origin`).
7. Уникати дублювання коду.
8. Весь код повинен бути покритий Unit-тестами.
9. Код розміщений на віддаленому репозиторії, логічні commit-повідомлення, правильний `.gitignore`.
10. Повідомлення виводяться через Logger.
**Варіант: Комп'ютери.**
Комплектуючі: `Name`, `Origin`, `Price`, `Type` (периферійне, енергоспоживання, кулер, група, порти), `Critical`. Кореневий елемент: `Device`.
 
## Реалізація
 
- **Модель:**
  - `Device`: id, назва, країна, ціна, характеристики, критичність.
  - `Characteristics`: блок `Type` (периферійне, енергоспоживання, кулер, група, порти).
  - `Group`, `Port`: enum-и, що відповідають переліком у XSD.
- **`DeviceBuilder`:** збирає `Device`, перевіряє обов'язкові поля.
- **Парсери:**
  - `DeviceParser`: інтерфейс (`parse(xmlPath)` повертає `List<Device>`).
  - `AbstractDeviceParser`: спільна обробка винятків і логування.
  - `SaxDeviceParser`, `DomDeviceParser`, `StaxDeviceParser`: лише власний спосіб читання XML.
  - `DeviceHandler`: єдине місце перетворення «тег → поле». Для SAX це обробник подій, DOM і StAX викликають його методи `startDevice`, `setField`, `endDevice`.
- **`DeviceLoader`:** валідація за XSD, парсинг, сортування.
- **Утиліти:**
  - `XmlValidator`: перевірка XML за XSD;
  - `XslTransformer`: застосування XSL до XML;
  - `DeviceComparators`: сортування за назвою, країною, ціною, споживанням;
  - `XmlTags`: назви тегів;
  - `DeviceParseException`: єдиний виняток обробки XML.
- **`Main`:** проганяє три парсери, порівнює результати, виводить список за ціною, запускає XSL.
- **Логування:** Log4j2, вивід у консоль (`log4j2.xml`).
### Дані
 
`src/main/resources/data/devices.xml`, схема `devices.xsd`.
 
Обмеження в XSD:
- `id` обов'язковий, тип `ID`, шаблон `dev-NNN`;
- `Name`, `Origin`: рядок 1–100 символів;
- `Price`: число ≥ 0 з обов'язковим атрибутом `currency` (`UAH`);
- `PowerConsumption`: ціле 1–1500;
- `Group`: `IO` або `Multimedia`;
- `Port`: `COM`, `USB`, `LPT`, від 1 до 3.
### XSL-перетворення
 
`src/main/resources/xsl/group-by-origin.xsl` групує пристрої за країною:
 
```
<Origins>
    <Origin name="China"> <Device>…</Device> <Device>…</Device> </Origin>
    <Origin name="USA">   <Device>…</Device> </Origin>
</Origins>
```
 
Результат: `output/grouped-by-origin.xml`.
 
## Тести
 
JUnit 5.
 
- `DeviceBuilderTest`: збірка, обов'язкові поля, порти.
- `DeviceHandlerTest`: перетворення тегів, ігнорування полів поза пристроєм, некоректний порт.
- `DomDeviceParserTest`, `SaxDeviceParserTest`, `StaxDeviceParserTest`: спільний набір перевірок з `DeviceParserTestBase`.
- `ParserEquivalenceTest`: усі парсери повертають однаковий результат.
- `XmlValidatorTest`: валідний, невалідний, відсутній файл.
- `DeviceComparatorsTest`: порядок сортування.
- `XslTransformerTest`: групування за країною, помилка при відсутньому файлі.
- `DeviceLoaderTest`: сортування та помилка на невалідному XML.
## Структура проєкту
 
```
lab2_xml
├── build.gradle
└── src
    ├── main
    │   ├── java/com/lab2
    │   │   ├── Main.java
    │   │   ├── builder/DeviceBuilder.java
    │   │   ├── loader/DeviceLoader.java
    │   │   ├── model/{Device, Characteristics, Group, Port}.java
    │   │   ├── parser/{DeviceParser, AbstractDeviceParser, DeviceHandler,
    │   │   │           SaxDeviceParser, DomDeviceParser, StaxDeviceParser}.java
    │   │   └── util/{DeviceComparators, DeviceParseException, XmlTags,
    │   │             XmlValidator, XslTransformer}.java
    │   └── resources
    │       ├── data/{devices.xml, devices.xsd}
    │       ├── xsl/group-by-origin.xsl
    │       └── log4j2.xml
    └── test
        ├── java/com/lab2/{builder, loader, parser, util}
        └── resources/{valid-devices.xml, invalid-devices.xml}
```
 
## Запуск програми та тестів
```
.\gradlew run
.\gradlew test
```
## Стек технологій
- Java 17+
- Gradle
- JUnit 5
- Log4j2
- Git
 
