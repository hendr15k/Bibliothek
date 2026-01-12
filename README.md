# Bibliothek

Eine kleine, rein clientseitige Bibliotheks-App zum Suchen, Speichern und Organisieren von Büchern. Die Anwendung läuft ohne Build-Setup direkt als statische HTML-Datei.

## Online testen

Die App ist hier verfügbar: https://hendr15k.github.io/Bibliothek/

## Funktionen

- Bücher über die Google Books API suchen
- Eigene Bibliothek mit Lesestatus, Bewertung und Notizen
- Dark Mode
- KI-gestützte Empfehlungen und Cover (API-Key erforderlich)

## Lokal starten

Da es sich um eine reine HTML-Datei handelt, genügt ein statischer Server oder ein direktes Öffnen im Browser.

```bash
# Im Projektverzeichnis
python3 -m http.server 8000
```

Anschließend `http://localhost:8000` im Browser öffnen.

## Hinweise

- Für KI-Funktionen wird ein eigener Gemini API-Key benötigt und in den Einstellungen gespeichert.
- Daten werden im Browser (localStorage) und in Firestore (bei aktivierter Firebase-Konfiguration) gespeichert.
