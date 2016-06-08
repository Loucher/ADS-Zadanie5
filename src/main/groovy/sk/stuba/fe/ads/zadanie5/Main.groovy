/*
Zadanie č.5

Napíšte program, ktorý načíta slovník (slovnik.txt, textový súbor, na každom riadku jedno slovo)
a textový súbor (textx.txt), v súbore textx.txt opraví “preklepy” a opravený súbor zapíše do výstupného textového súboru.

Ako vstupný textový súbor vám budú dodané 3 textové súbory: text1.txt, text2.txt a text3.txt. Každý
z nich bude mať rovnaký počet slov, ako pôvodné súbory bez preklepov (tie však k dispozícii nemáte),
budú v nich zachované medzery medzi slovami, avšak bude v nich vnesených 5% (text1.txt), 10% (text2.txt),
15% (text3.txt) chýb. Chyby môžu byť: zmena znaku, zmazanie znaku, vloženie znaku.

Pridanie/zmazanie/zmena znaku môže byť spravené kdekoľvek v texte (náhodne).
Slovník môžete použiť vlastný (nejaký základný vám pošleme mailom).

Odovzdať je potrebné:
    • Súbory s opravenými chybami: text1o.txt, text2o.txt, text3o.txt – nezipovať, len odovzdať.
    • Zazipované zdrojové kódy.
Slovník neodovzdávajte!!!

Hodnotenie:
    Text1 - max.8 bodov
    Text2 - max.10 bodov
    Text3 - max.12 bodov

Podľa úspešnosti opráv. Hodnotí sa počet slov, ktoré sú zhodné s pôvodným korektným textom (na príslušných pozíciách).

Termín odovzdania: do 2.6.2016 do 23:59 hod.
Hodnotia sa len zadania odovzdané do AIS!!!
*/
package sk.stuba.fe.ads.zadanie5

import groovy.transform.CompileStatic

@CompileStatic
class Main {
    static void main(String... args) {
        SpellChecker sc = new SpellChecker("/slovnik.txt")
        sc.checkFile("/text1.txt")
        sc.checkFile("/text2.txt")
        sc.checkFile("/text3.txt")
    }

}
