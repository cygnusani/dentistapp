# dentistapp - Hambaarsti juurde registreerimise rakendus
## CGI prooviülesanne
Tegu on lihtsa arsti juurde registreerimise rakendusega.
### Käivitamine
Tuleb käivitada **Main** meetod **DentistAppApplication.java** alt ja ligi pääseb aadressilt **http://localhost:8080**.
### Visiidile registreerimine
Registreerimisel tuleb valida **arsti nimi**, **visiidi kuupäev** ja **kellaaeg**. Kui midagi ei sobi, näiteks valitud kuupäeva ja/või kellaaega on minevikus või kellegi poolt juba valitud, antakse sellest teada vastava veateatega.
### Kõik registreerimised ja nende muutmine
Kõiki registreeritud aegu saab näha **Kõik registreerimised** vaate alt, kus igat registreeringut saab **muuta** ja **kustutada**. Muutmisel kontrollitakse samuti, et sisestatud oleks valiidne info (eelmises punktis välja toodud kontrollid).
### Otsing
Otsingu lahter asub kõikide registreerimiste vaates ning otsida saab registreeringuid **arsti nime** järgi.
### Millal, mida, kuidas?
**05.01 - 06.01** - Ülesande baasiga tutvuse tegemine ja abimaterjalide otsimine.
**07.01 - 09.01** - Ülesande lahendamine. Poolik versioon sai 9. õhtuks valmis. Selleks ajaks oli enamus funktsionaalsusi olemas, aga vormi kontroll oli poolik, otsingu lahter katk, puudus teenuse poolne vormi kontroll ning UI oli kole. Sain aja pikendust.
**10.01 - 13.01** - Puuduste likvideerimine ja parandatud UI.
### Mis oli kerge?
Ülesande baasist ja struktuurist polnud väga raske aru saada. Samuti sai suurema osa lihtsa vaevaga valmis.
### Mis oli keeruline?
Peamiselt Thymeleaf ja bindingud. Thymeleaf-i kohtasin esimest korda ja bindingutega läks kaua aega. Dokumentatsioon ja internetist leitu ei lihtsustanud väga thymeleaf-i mõistmist.
### Probleemid ja lahendused
#### Date
Esialgses versioonis oli annotatsiooniga määratud kuupäeva pattern “dd.MM.yyyy”. Kui HTML-is kuupäeva välja muutsin text tüübist date-iks, siis see pattern enam ei sobinud (rakendus crashis). Sai muudetud “yyyy-MM-dd”.
#### Vormi kontrollid
Ei osanud BindingResult-i abiga tervet veahaldust realiseerida. Lahendasin probleemi, luues veatetaed käsitsi.
#### Kellaaja format
Visiidi kellaajad tulevad Dao-st List<String> kujul. Kui vorm esitatakse, liidetakse dentistVisitService-isse loodud abimeetodiga valitud kellaaja String visitTime-ile juurde. Sama kehtib ka vastupidi, ehk kui mingit registreeringut muuta, võetakse kellaaeg dateTime-ist ja luuakse sellest String. Probleemiks oli tundide ja minutite kättesaadavus. Lahendasin probleemi sellega, et kasutasin **deprecated** meetodeid.
