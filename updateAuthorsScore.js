
var Article = Java.type('pl.edu.wat.knowledge.entity.Article');
var Author = Java.type('pl.edu.wat.knowledge.entity.Author');
var Book = Java.type('pl.edu.wat.knowledge.entity.Book');
var Chapter = Java.type('pl.edu.wat.knowledge.entity.Chapter');

var Set = Java.type('java.util.Set');

// Pobierz wszystkich autorów
var authors = authorRepository.findAll();
var book = bookRepository.findAll();
var chapter = chapterRepository.findAll();
var article = articleRepository.findAll();


// Iteruj przez wszystkich autorów
for (var i = 0; i < authors.size(); i++) {
    var author = authors.get(i);

    // Zmień liczbę punktów autora na losową wartość od 1 do 10
    var newScore = scoreService.getScore(author, 2024);
    author.setScore(newScore);

    // Zapisz autora z powrotem do bazy danych
    authorRepository.save(author);

    // Loguj informacje o autorze do konsoli
    console.log("Updated author " + author.getName() + " " + author.getSurname() + " with new score: " + newScore);
}
