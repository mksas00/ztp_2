## ZTP - Projekt 2 

Aplikacja została napisane w języku Java w wersji 17 z użyciem frameworku Spring.
Do przechowywania danych został wykorzystany silnik bazodanowy MongoDB. Do stworzenia testów wykorzysate 
zostałe narzędzie Cucumber.

W celu uruchomienia projektu wymagana jest wersja w wersji **17** lub wyższej.

Do działa aplikacji wymagany jest silnik bazodanowy MongoDB. Najlepiej w formie kontenera w programie
Docker

Testy należy uruchomić z poziomu klasy **ProductIntegrationTest** znajdującego się w module

``src\test\java\ztp\project2\``

W celu zmianu portu z którego aplikacja łączy się z silnikem bazą **MongoDB**
należy zmienić wartość ``mongo.port`` w pliku ``application.properties`` znajdującym się w folderze
``src\main\resources`` analogicznie dla portu testowej bazy danych w folderze
``src\test\resources``