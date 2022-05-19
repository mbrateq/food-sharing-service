# food-sharing-service
Simple foodSharing Springboot app

#Done
1. Utworzenie Kontrolera, serwisu do obsługi ogłoszeń
2. Utworzenie encji użytkownika i ogłoszenia wraz z dto i mapperem
3. Implementacja metod CRUD w kontrolerze
4. Dodanie ról i powiązań z użytkownikami.
5. Dodanie warstwy security
6. Rozwiązanie problemu 403 z POST i PUT
7. Rozwiązanie problemu sekwencji i hashowania hasła
8. Dodanie walidacji na obiektach wejściowych
9. Dodanie mechanizmu usuwania przeterminowanych ogłoszeń
10. ~~4. Przesyłanie obrazków~~
11. ~~dodanie walidacji hasła i czyszczenia tablicy z hasłem~~
12. dodanie numeru telefonu użytkownika

#Todo
5. Wysyłanie linka z rejestracją (https://www.baeldung.com/registration-verify-user-by-email)
7. Testy integracyjne kontrolerów.
8. Dodanie linków

# in progress

# Security

## Konfiguracja domyślna:

### 1. Konfiguracja domyślna:

* dodanie do startera security do pliku _pom.xml_:
```<dependency>```
```<groupId>org.springframework.boot</groupId>```
```<artifactId>spring-boot-starter-security</artifactId>```
```</dependency>``` 
    
* logowanie za pomocą user:user i hasła _Using generated security password_ generowanego każdorazowo podczas startu aplikacji.

#### Rozszerzenie konfiguracji domyślnej:

* ustawienie dowolnej nazwy użytkownika i hasła w pliku properties: 
```spring.security.user.name=*username*```
```spring.security.user.password=*password*```

#### Wyłączenie domyślnej strony logowania w spring security:

* z poziomu application properties za pomocą przełącznika:
```spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration```

* za pomocą adnotacji :
```@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })```

** Nie ma potrzeby wyłączania domyślnej strony logowania podczas nadpisywania konfiguracji 

## 2. Konfiguracja zaawansowana:

* Autentykacja użytkownika w SpringSecurity wykonywana jest w klasie _AuthenticationManager_ w metodzie _authenticate()_. Aby mieć wpływ na przebieg autentykacji używamy budowniczego tej klasy _AuthenticationManagerBuilder_. Proces odbywa się w dwóch krokach. 
1. Przyjęcie instancji _AuthenticationManagerBuilder_
2. Ustawienie wybranej konfiguracji na tym obiekcie 

** nie tworzymy żadnego z w/w obieków. _AuthenticationManagerBuilder_ zwraca (tworzy?) obiekt _AuthenticationManager_ na podstawie wskazanej konfiguracji.

** Dostęp do instancji _AuthenticationManagerBuilder_ odbywa się poprzez specjalną klasę, która dostarcza chronioną metodę _configure(AuthenticationManagerBuilder)_ która przyjmuje w parametrze obiekt AuthenticationManagerBuilder. 
Spring security framework wywołuje tę metodę i przekazuje tam obiekt AuthenticationManagerBuilder. 
Implementacja zrealizowana jest w następujący sposób, aby umożliwić programiście rozszerzenie klasy/metody i utworzenie swojej własnej konfiguracji.

### Instrukcja nadpisywania domyślnej konfiguracji spring security:

1. Usunięcie domyślnych wartości logowania z application.properties.
2. Utworzenie nowej klasy dziedziczącej po klasie _WebSecurityConfigurerAdapter_
3. Nadpisanie w klasie metody _configure(AuthenticationManagerBuilder auth)_
4. Dodanie adnotacji ```@EnableWebSecurity``` (dziala bez?)

### Rodzaje konfigracji Konfiguracja AuthenticationManagerBuilder:

#### 1. InMemoryAuthentication
 
* Konfiguracja poprzez przekazanie danych logowania jako parametrów w łańcucha metod wywołanych na obiekcie _AuthenticationManagerBuilder_  
* Przechowuje zahardcodowane dane w pamięci
* Każdy użytkownik musi być wskazany osobno
* Wygodna podczas rozwoju aplikacji, nie ma zastosowania produkcyjnego.
```auth.inMemoryAuthentication().withUser("foo").password("bar").roles("USER");```

#### 2. JDBC Authentication

* Szybka konfiguracja z domyślnym źródłem danych
* Konieczność utworzenia struktur danych o ściśle określonych nazwach

#### 3. JPA Authentication

* Zadeklarowanie obiektu implementującego interfejs _UserDetailsService_ w _WebSecurityConfigurationAdapter_ pozwala na dodanie personalizowanej metody autentykacji za pomocą własnego serwisu
* Utworzenie implementacji interfejsu _UserDetails_ oraz metody mapującej encję reprezentującą użytkownika znajdującą się w źródle danych na obiekt _UserDetails_ 
* Konieczne jest nadpisanie implementacji metody _loadUserByUsername(String s){}_, która powinna wywołać pobranie użytkownika ze źródła oraz metodę mapującą.
** Z punktu widzenia używanych komponentów Nie ma znaczenia źródło danych - nie musi to być jpa, może być też AD lub jakakolwiek inna usługa.
** W wypadku większych systemów warto oddzielić autentykację od autoryzacji. Konfiguracja umożliwia ręczne konfigurowanie każdego z URL w klasie _WebSecurityConfigurationAdapter_ jednak przy większej liczbie end-pointów, jeśli tylko pozwalają na to wymagania biznesowe
warto dla czystości kodu utrzymać separację konfiguracji. 

## 3 Zarządzanie hasłami
* Przechowując hasła char[]
* Przechowując hasła zawsze należy korzystać z hashowania
* Spring security wymusza kodowanie hasła
* Ustawienie password encodera w springu polega na ekspozycji beana ```@Bean PasswordEncoder```
* Podczas startu aplikacji kontener springa wyszuka beana i użyje go do kodowania
* NoOpPasswordEncoder jest przeźroczysty i nie hashuje hasła. Można go wywołać poprzez ```NoOpPasswordEncoder.getInstance();```
* W aplikacji wykorzystywany jest BCryptPasswordEncoder

#Openapi

Opis, wizualizacja i testy api odbywają się za pomocą specyfikacji OpenApi 3.0 wcześniej znanej jako Swagger Specification. Specyfikacja ta pozwala na wygodne przeglądanie end-pointów aplikacji.

## Konfiguracja openapi w aplikacji:
1. Dodanie do projektu zależności ```        <dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-ui</artifactId>
   <version>1.5.2</version>
   </dependency>
   <dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-data-rest</artifactId>
   <version>1.5.2</version>
   </dependency>```
2. Utworzenie głównej klasy z konfiguracją _OpenApi30Config_ zawierającą nazwę API oraz context root.
3. Wyodrębnienie interfejsów Operations z metodami implementowanymi przez kontrolery webowe oraz opisanie ich za pomocą właściwych adnotacji
4. Interfejs graficzny dostępny jest pod adresem ```http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config```
5.dlaczego przechodzą tylko gety???


Konfiguracja docker: (problem z innymi obrazami)
docker run --name postgresql-container -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:14.2

Linki:
login: http://localhost:8080/fss/login#/
main: http://localhost:8080/fss/swagger-ui/index.html?configUrl=/fss/v3/api-docs/swagger-config
api-docs: http://localhost:8080/fss/v3/api-docs


