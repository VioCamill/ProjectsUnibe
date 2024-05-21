## Use Case 
### Use Case 1 
Name: Entry erstellen 
Kurzbeschreibung : Der Entry soll in Deutscher, Englischer und Französischer Version erstellt werden
Beteiligte : Akteur, 
              System: Craft CMS mit Plugin Ellinika 
Vorbedingugnen: Craft CMS ist installiert mit dem Plugin Ellinika, Ellinika Tabelle ist leer  

Ablauf: 1. Akteur erstellt Entry 
        2. Akteur klickt im Dashboard auf Ellinika 
        3. Akteur sieht Tabelle mit geänderter Sprache sowie die üngeänderten Sprachen deutsch und Französisch 
        4. Akteur wählt die Sprache de aus
        5. Akteur klickt auf Edit 
        6. Akteur ändert die deutsche Version im Popup fenster 
        7. Akteur klickt auf Save 
Alternativablauf: 
        4a) Akteur klickt auf den Link bei der de Version 
        5a & 6a) Akteur ändert den Eintrag in der de sprache direkt über die Entries in Craft CMS 
        7a) Akteur speichert die änderung über die Entries in Craft 
        8) Akteur klickt im auf Dashboard Ellinika

Nachbedingungen: 
  Die Ellinika Tabellen enthält nun den Eintrag für den Entry. Die englische und deutsche Version sind aufgelistet in der Spalte Changed, die französische Version nach wie vor bei unchanged 

### Use Case 2 

Name: Settings einstellen von Ellinika 
