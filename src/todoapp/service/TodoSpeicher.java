package todoapp.service;

import todoapp.model.Todo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Kümmert sich darum die Aufgaben in eine Datei zu speichern und wieder zu laden.
 * Ich benutze Gson - das ist eine Library von Google die aus Objekten JSON macht
 * (und zurück). Im Prinzip wie JSON.stringify / JSON.parse aus JavaScript, nur dass
 * man Gson die Library erst als .jar dazupacken muss (liegt im Ordner lib/).
 */
class TodoSpeicher {

    // Name der Datei in der alles landet - liegt im Projektordner
    private static final String DATEI = "todos.json";

    // das Gson-Objekt das die ganze Umwandlung macht (einmal bauen, immer wiederverwenden)
    private final Gson gson;

    /**
     * Konstruktor - baut das Gson-Objekt zusammen.
     */
    TodoSpeicher() {
        this.gson = baueGson();
    }

    /**
     * Stellt das Gson-Objekt ein.
     * Knackpunkt: Gson weiß von sich aus nicht wie es ein LocalDate (Datum) speichern soll.
     * Deswegen sage ich ihm: schreib das Datum einfach als Text (z.B. "2026-05-22") und
     * lies es genauso wieder ein. LocalDate.toString() / LocalDate.parse() machen genau das.
     */
    private static Gson baueGson() {
        return new GsonBuilder()
                // beim Speichern: Datum -> Text
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (datum, typ, ctx) -> new JsonPrimitive(datum.toString()))
                // beim Laden: Text -> Datum
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, typ, ctx) -> LocalDate.parse(json.getAsString()))
                .setPrettyPrinting() // schön eingerückt damit man die Datei auch lesen kann
                .create();
    }

    /**
     * Schreibt alle Aufgaben in die JSON-Datei.
     * try-with-resources (die Klammern hinter try) schließt den Writer automatisch wieder,
     * egal ob's klappt oder ein Fehler kommt - ähnlich wie "with open(...)" in Python.
     */
    void speichern(List<Todo> todos) {
        try (Writer writer = Files.newBufferedWriter(Path.of(DATEI), StandardCharsets.UTF_8)) {
            gson.toJson(todos, writer);
        } catch (IOException e) {
            // wenn Speichern schiefgeht nur eine Meldung ausgeben, App soll nicht abstürzen
            System.err.println("Konnte nicht speichern: " + e.getMessage());
        }
    }

    /**
     * Liest die Aufgaben aus der JSON-Datei wieder ein.
     * Gibt eine leere Liste zurück wenn es noch keine Datei gibt (z.B. beim allerersten Start).
     */
    List<Todo> laden() {
        Path pfad = Path.of(DATEI);
        if (!Files.exists(pfad)) {
            return new ArrayList<>(); // noch nichts gespeichert -> leer anfangen
        }
        try (Reader reader = Files.newBufferedReader(pfad, StandardCharsets.UTF_8)) {
            // Gson und Java-Generics vertragen sich schlecht, deswegen lese ich ein
            // Array (Todo[]) statt direkt eine Liste - der Trick stand so im Netz
            Todo[] array = gson.fromJson(reader, Todo[].class);
            if (array == null) {
                return new ArrayList<>(); // Datei war leer
            }
            List<Todo> todos = new ArrayList<>(List.of(array));
            // dem ID-Zähler Bescheid sagen damit neue IDs nicht mit alten kollidieren
            for (Todo t : todos) {
                Todo.idZaehlerAnpassen(t.getId());
            }
            return todos;
        } catch (IOException e) {
            System.err.println("Konnte nicht laden: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
