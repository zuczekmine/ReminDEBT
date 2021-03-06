Dokumentacja projektu
Autorzy:
Anna Żukowska, 188897
Wojciech Wisła, 188665
Grupa: KrDZIs3012Io
Cel projektu:
Celem projektu było stworzenie działającej aplikacji mobilnej, która stanowiłaby pomoc dla
użytkowników w zakresie spłacania zobowiązań oraz otrzymywania należności.
Opis:
Aplikacja została napisana z wykorzystaniem klas zewnętrznych oraz fragmentów, w tym
klas i interfejsów niezbędnych do ich obsługi.
Opis klas:
MainActivity
Główna aktywność aplikacji rozszerzająca klasę AppCompatActivity oraz implementująca
interfejsy OnFragmentInteractionListener obsługujące fragmenty aplikacji.
Pola:
podmiotyMain - tablica typu ArrayList, składająca się z elementów klasy Podmiot,
przechowywane są w niej obiekty klasy Podmiot.
podmiotyPrzekazane - tablica typu ArrayList, składająca się z elementów klasy Podmiot,
przechowywane są w niej obiekty klasy Podmiot.
naleznosciMain - tablica typu ArrayList, składająca się z elementów klasy Naleznosc,
przechowywane są w niej obiekty klasy Naleznosc.
naleznosciPrzekazane - tablica typu ArrayList, składająca się z elementów klasy Naleznosc,
przechowywane są w niej obiekty klasy Naleznosc.
zobowiazaniaMain - tablica typu ArrayList, składająca się z elementów klasy Zobowiazanie,
przechowywane są w niej obiekty klasy Zobowiazanie.
zobowiazaniaPrzekazane - tablica typu ArrayList, składająca się z elementów klasy
Zobowiazanie, przechowywane są w niej obiekty klasy Zobowiazanie.
nazwaPlikuPodm - zmienna typu String przechowująca ciąg znaków “podmioty”, który
określa nazwę pliku do przechowywania utworzonych przez użytkownika obiektów klasy
Podmiot.
nazwaPlikuNal - zmienna typu String przechowująca ciąg znaków “naleznosci”, który określa
nazwę pliku do przechowywania utworzonych przez użytkownika obiektów klasy Naleznosc.
nazwaPlikuZob - zmienna typu String przechowująca ciąg znaków “zobowiazania”, który
określa nazwę pliku do przechowywania utworzonych przez użytkownika obiektów klasy
Zobowiazanie.
czyJestPodm - zmienna typu boolean zwracająca wartość true, w momencie kiedy plik
“podmioty” nie jest pusty i zawiera chociaż jeden obiekt.
czyJestNal - zmienna typu boolean zwracająca wartość true, w momencie kiedy plik
“naleznosci” nie jest pusty i zawiera chociaż jeden obiekt.

czyJestZob - zmienna typu boolean zwracająca wartość true, w momencie kiedy plik
“zobowiazania” nie jest pusty i zawiera chociaż jeden obiekt.
czyJestService - zmienna typu boolean zwracająca wartość true, w momencie kiedy usługa
klasy Service (związana z aplikacją) zostanie wykryta jako uruchomiona.
klucz - zmienna typu String przechowująca klucz przekazany z fragmentu.
Metody:
onCreate() - metoda określająca działania mającę się wykonać w momencie uruchomienia
aplikacji.
onBackPressed() - nadpisana metoda określająca działania mające się wykonać w
momencie wciśnięcia domyślnego przycisku powrotu na urządzeniu mobilnym.
onNavigationItemSelected() - nadpisana metoda określająca działania mające się wykonać
w momencie wybrania jednego z elementów zawartych na bocznym pasku nawigacyjnym.
startService() - metoda uruchamiająca usługę typu Service.
isMyServiceRunning() - metoda zwracająca wartość true, w momencie kiedy usługa typu
Service (związana z aplikacja) zostanie wykryta jako uruchomiona.
onFragmentInteraction() - nadpisana metoda określająca działania mające się wykonać w
momencie otrzymania danych od poszczególnych fragmentów aplikacji.
Podmiot
Klasa określająca strukturę obiektów Podmiot. Wykorzystuje interfejs Serializable.
Pola:
serialVersionUID - statyczna i finalna zmienna typu long.
nazwa - zmienna typu String przyjmująca ciąg znaków opisujący nazwę danego podmiotu.
Metody:
Podmiot() - konstruktor klasy Podmiot.
getNazwa() - metoda zwracająca nazwę danego obiektu klasy Podmiot.
toString() - nadpisana metoda zwracająca nazwę danego obiektu klasy Podmiot.
OsobaPrywatna
Klasa rozszerzająca klasę Podmiot. Określa strukturę obiektów OsobaPrywatna.
Metody:
OsobaPrywatna() - konstruktor klasy OsobaPrywatna.
Przedsiebiorstwo
Klasa rozszerzająca klasę Podmiot. Określa strukturę obiektów Przedsiebiorstwo.
Metody:
Przedsiebiorstwo() - konstruktor klasy Przedsiebiorstwo.
Naleznosc
Klasa określająca strukturę obiektów Naleznosc. Wykorzystuje interfejs Serializable.

Pola:
nazwa - zmienna typu String przechowująca podaną nazwę danej należności.
waluta - zmienna typu String przechowująca wybraną walutę danej należności.
podmiot - obiekt klasy Podmiot przechowujący podmiot wybrany przez użytkownika dla
danej należności.
data - obiekt klasy Date przechowujący datę wybraną przez użytkownika dla danej
należności.
kwota - zmienna typu double przechowująca wprowadzoną przez użytkownika datę dla
danej należności.
Metody:
Naleznosc() - konstruktor klasy Naleznosc.
getNazwa() - metoda zwracająca zmienną nazwa.
getKwota() - metoda zwracająca zmienną kwota.
getData() - metoda zwracająca obiekt data.
getWaluta() - metoda zwracająca zmienną waluta.
toString() - nadpisana metoda zwracająca zmienną nazwa.
Zobowiazanie
Klasa określająca strukturę obiektów Zobowiazanie. Wykorzystuje interfejs Serializable.
Pola:
nazwa - zmienna typu String przechowująca podaną nazwę danego zobowiązania.
waluta - zmienna typu String przechowująca wybraną walutę danego zobowiązania.
podmiot - obiekt klasy Podmiot przechowujący podmiot wybrany przez użytkownika dla
danego zobowiązania.
data - obiekt klasy Date przechowujący datę wybraną przez użytkownika dla danego
zobowiązania.
kwota - zmienna typu double przechowująca wprowadzoną przez użytkownika datę dla
danego zobowiązania.
Metody:
Zobowiazanie() - konstruktor klasy Zobowiazanie.
getNazwa() - metoda zwracająca zmienną nazwa.
getKwota() - metoda zwracająca zmienną kwota.
getData() - metoda zwracająca obiekt data.
getWaluta() - metoda zwracająca zmienną waluta.
toString() - nadpisana metoda zwracająca zmienną nazwa.
FragmentDodajPodm
Klasa rozszerzająca klasę Fragment, opisująca fragment odpowiedzialny za dodanie
podmiotu przez użytkownika.
Pola:
podmioty - Lista typu ArrayList przechowująca obiekty typu Podmiot.

nazwaPodmiotu - zmienna typu String przechowująca przekazaną przez użytkownika nazwę
podmiotu.
KLUCZ - finalna zmienna przechowująca klucz do identyfikacji danych pochodzących z
fragmentu.
czyPrzedsiebiorstwo - zmienna typu boolean przyjmująca wartość true w momencie
zaznaczenia odpowiedniego pola w formularzu, określa czy wprowadzony podmiot jest
przedsiębiorstwem czy osobą prywatną.
mListener - obiekt typu OnFragmentInteractionListener, wykorzystywany do komunikacji
pomiędzy fragmentem, a aktywnością.
Metody:
FragmentDodajPodm() - pusty konstruktor klasy FragmentDodajPodm.
onCreateView() - nadpisana metoda określająca działania mające wykonać się podczas
otwarcia fragmentu.
onButtonPressed() - nadpisana metoda służąca do komunikacji.
onAttach() - nadpisana metoda służąca do komunikacji.
onDetach() - nadpisana metoda służąca do komunikacji.
OnFragmentInteractionListener - zdefiniowanie interfejsu.
FragmentNaleznosci
Klasa rozszerzająca klasę Fragment, opisująca fragment odpowiedzialny za dodanie
należności przez użytkownika.
Pola:
KLUCZ - finalna zmienna przechowująca klucz do identyfikacji danych pochodzących z
fragmentu.
mListener - obiekt typu OnFragmentInteractionListener, wykorzystywany do komunikacji
pomiędzy fragmentem, a aktywnością.
podmiotyDoWyboru - lista typu ArrayList przechowująca obiekty typu Podmiot.
naleznosci - lista typu ArrayList przechowująca obiekty typu Naleznosc.
waluty - lista typu ArrayList przechowująca obiekty typu String.
nazwaPlikuPodm - zmienna typu String przechowująca ciąg znaków określający nazwę
pliku.
walutaWybranaNal - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika walutę.
dataWybranaNal - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika datę.
nazwaNaleznosci - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika nazwę należności.
kwotaWybranaNal - zmienna typu String przechowująca ciąg znaków określający wpisaną
przez użytkownika kwotę należności.
fabNaleznosci - obiekt typu FloatingActionButton odpowiedzialny za pojawienie się przycisku
o określonym działaniu.
etNazwaNaleznosci - obiekt typu EditText.
podmiotWybrany - obiekt typu Podmiot, do którego zostaje przypisany obiekt typu Podmiot
wybrany przez użytkownika po wybraniu przez użytkownika.

data - obiekt typu Date przechowujący datę wybraną przez użytkownika.
kwotaNal - obiekt typu double przechowujący kwotę wpisaną przez użytkownika.
etWybierzDateNal - obiekt typu EditText.
etKwotaNaleznosci - obiekt typu EditText.
tytulNaleznosci - obiekt typu EditText.
myCalendar - obiekt typu Calendar przechowujący kalendarz.
sdf - obiekt typu SimpleDateFormat przechowujący informacje o formacie wyświetlania daty.
date - obiekt typu DatePicker pozwalający na komunikację.
Metody:
FragmentNaleznosci() - pusty konstruktor klasy FragmentNaleznosci.
onCreateView() - nadpisana metoda określająca działania mające wykonać się podczas
otwarcia fragmentu.
onButtonPressed() - nadpisana metoda służąca do komunikacji.
onAttach() - nadpisana metoda służąca do komunikacji.
onDetach() - nadpisana metoda służąca do komunikacji.
OnFragmentInteractionListener - zdefiniowanie interfejsu.
FragmentZobowiazania
Pola:
KLUCZ - finalna zmienna przechowująca klucz do identyfikacji danych pochodzących z
fragmentu.
mListener - obiekt typu OnFragmentInteractionListener, wykorzystywany do komunikacji
pomiędzy fragmentem, a aktywnością.
podmiotyDoWyboru - lista typu ArrayList przechowująca obiekty typu Podmiot.
zobowiazania - lista typu ArrayList przechowująca obiekty typu Zobowiazanie.
waluty - lista typu ArrayList przechowująca obiekty typu String.
nazwaPlikuPodm - zmienna typu String przechowująca ciąg znaków określający nazwę
pliku.
walutaWybranaZob - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika walutę.
dataWybranaZob - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika datę.
nazwaZobowiazania - zmienna typu String przechowująca ciąg znaków określający wybraną
przez użytkownika nazwę zobowiązania.
kwotaWybranaZobl - zmienna typu String przechowująca ciąg znaków określający wpisaną
przez użytkownika kwotę zobowiązania.
fabZobowiazania - obiekt typu FloatingActionButton odpowiedzialny za pojawienie się
przycisku o określonym działaniu.
etNazwaZobowiazania - obiekt typu EditText.
podmiotWybrany - obiekt typu Podmiot, do którego zostaje przypisany obiekt typu Podmiot
wybrany przez użytkownika po wybraniu przez użytkownika.
data - obiekt typu Date przechowujący datę wybraną przez użytkownika.
kwotaZob - obiekt typu double przechowujący kwotę wpisaną przez użytkownika.
etWybierzDateZob - obiekt typu EditText.

etKwotaZobowiazania - obiekt typu EditText.
tytulZobowiazania - obiekt typu EditText.
myCalendar - obiekt typu Calendar przechowujący kalendarz.
sdf - obiekt typu SimpleDateFormat przechowujący informacje o formacie wyświetlania daty.
date - obiekt typu DatePicker pozwalający na komunikację.
Metody:
FragmentZobowiazania() - pusty konstruktor klasy FragmentZobowiazania.
onCreateView() - nadpisana metoda określająca działania mające wykonać się podczas
otwarcia fragmentu.
onButtonPressed() - nadpisana metoda służąca do komunikacji.
onAttach() - nadpisana metoda służąca do komunikacji.
onDetach() - nadpisana metoda służąca do komunikacji.
OnFragmentInteractionListener - zdefiniowanie interfejsu.
FragmentUstawienia
Pola:
mListener - obiekt typu OnFragmentInteractionListener wykorzystywany do komunikacji
pomiędzy fragmentem, a aktywnością.
btnUsunPodmiot - obiekt typu Button.
btnUsunNal - obiekt typu Button.
btnUsunZob - obiekt typu Button.
spinnerUsunPodm - obiekt typu Spinner.
spinnerUsunNal - obiekt typu Spinner.
spinnerUsunZob - obiekt typu Spinner.
KLUCZ_PODM - finalna zmienna przechowująca klucz do identyfikacji danych
pochodzących z fragmentu.
KLUCZ_NAL - finalna zmienna przechowująca klucz do identyfikacji danych pochodzących z
fragmentu.
KLUCZ_ZOB - finalna zmienna przechowująca klucz do identyfikacji danych pochodzących
z fragmentu.
podmioty - lista typu ArrayList przechowująca obiekty typu Podmiot.
naleznosci - lista typu ArrayList przechowująca obiekty typu Naleznosc.
zobowiazania - lista typu ArrayList przechowująca obiekty typu Zobowiazania.
nazwaPlikuPodm - zmienna typu String przechowująca ciąg znaków określający nazwę
pliku.
nazwaPlikuNal - zmienna typu String przechowująca ciąg znaków określający nazwę pliku.
nazwaPlikuZob - zmienna typu String przechowująca ciąg znaków określający nazwę pliku.
pozycjaUsunPodm - zmienna typu int przechowująca numer pozycji wybranej przez
użytkownika z listy.
pozycjaUsunNal - zmienna typu int przechowująca numer pozycji wybranej przez
użytkownika z listy.
pozycjaUsunZob - zmienna typu int przechowująca numer pozycji wybranej przez
użytkownika z listy.

Metody:
FragmentUstawienia() - pusty konstruktor klasy FragmentUstawienia.
onCreateView() - nadpisana metoda określająca działania mające wykonać się podczas
otwarcia fragmentu.
onButtonPressed() - nadpisana metoda służąca do komunikacji.
onAttach() - nadpisana metoda służąca do komunikacji.
onDetach() - nadpisana metoda służąca do komunikacji.
OnFragmentInteractionListener - zdefiniowanie interfejsu.
