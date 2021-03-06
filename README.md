# Strings Translator
Сливает файлы текстовых ресурсов в CSV таблицу и обратно, имеет интуитивно понятный интерфейс, красивые кнопочки.
Проект на 87.5% (7 из 8 классов) написан на языке Kotlin.
Сторонних third-party библиотек не использовано.


Приложение работает в двух режимах: консольном и визуальном.

###Консольный режим

Следующий консольный код запустит программу, после чего файлы strings.xml и en.strings будут переведены в таблицу myCoolTable.csv, при этом UI программы не будет запущен и она завершится после выполнения слияния файлов. Можно указать больше одного подряд файла xml или strings - главное, чтобы они имели правильное расширение, чтобы парсер мог понять, как именно обработать текущий файл.
```
java -jar StringsTranslator.jar "tocsv myCoolTable.csv strings.xml en.strings"
```


Следующий код позволяет запустить программу для перевода CSV таблицы обратно в файлы xml и strings. Можно указать несколько файлов strings и xml.
```
java -jar StringsTranslator.jar "fromcsv myCoolTable.csv ru.strings values-ru/strings.xml"
```


###Визуальный режим
Если запустить программу без каких-либо аргументов командной строки, то откроется окно с UI, позволяющее продолжить работу по сливанию\разливанию ресурсов.


###Особенности
* Вы можете создавать CSV таблицы только из XML файлов, либо только из STRINGS файлов.
* Вы можете создавать только XML или STRINGS файлы из таблиц CSV
* Программа работает в "быстром" консольном режиме ~~(не нравится UI - допили его сам)~~


Чтобы скачать только исполняемый JAR файл нажми [сюда](https://github.com/StrongholdOfAdequacy/KODE-Strings-Translator/blob/master/out/artifacts/StringsTranslator_jar/StringsTranslator.jar)
