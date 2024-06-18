package pl.edu.wat.knowledge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.entity.Affiliation;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.entity.Book;
import pl.edu.wat.knowledge.entity.Chapter;
import pl.edu.wat.knowledge.entity.Journal;
import pl.edu.wat.knowledge.entity.Publisher;

import pl.edu.wat.knowledge.repository.AuthorRepository;
import pl.edu.wat.knowledge.repository.AffiliationRepository;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;
import pl.edu.wat.knowledge.repository.JournalRepository;
import pl.edu.wat.knowledge.repository.PublisherRepository;
import pl.edu.wat.knowledge.service.ScoreService;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {
    private final Random random = new Random();

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected JournalRepository journalRepository;

    @Autowired
    protected PublisherRepository publisherRepository;

    @Autowired
    protected AffiliationRepository affiliationRepository;

    @Autowired
    protected ChapterRepository chapterRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    public void setUpDatabase() {
       /*  var author = new Author();
        author.setName("Jan");
        author.setSurname("Kowalski");
        authorRepository.save(
               author
        );*/

        chapterRepository.deleteAll();
        articleRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        journalRepository.deleteAll();
        publisherRepository.deleteAll();
        affiliationRepository.deleteAll();
        insertSamplePublishers(9); //done
        insertSampleAffiliations(9); //done
        insertSampleAuthors(); //podane 5 autorów
        insertSampleBooks(); //podane 5
        insertSampleJournals(11); //done
        insertSampleArticles(); //podane 7
        insertSampleChapters(); //podane 3 
    }
    private void insertSampleArticles() {
        List<Journal> journals = journalRepository.findAll();
       // List<Author> authors = authorRepository.findAll();
       List<Author> authors = insertSampleAuthors();

        Article article1 = new Article();
        article1.setTitle("ABC");
        article1.setCollection(random_string(8));
        article1.setScore(12);
        article1.setVol(random.nextInt(10));
        article1.setNo(random.nextInt(5));
        article1.setArticleNo(random.nextInt(20));
        article1.setJournal(journals.get(random.nextInt(journals.size()))); 
        article1.setAuthors(authors.subList(0, 1));
        article1.setYear(2015);
        articleRepository.save(article1);

        Article article2 = new Article();
        article2.setTitle("BBB");
        article2.setCollection(random_string(8));
        article2.setScore(10);
        article2.setVol(random.nextInt(10));
        article2.setNo(random.nextInt(5));
        article2.setArticleNo(random.nextInt(20));
        article2.setJournal(journals.get(random.nextInt(journals.size()))); 
        article2.setAuthors(authors.subList(0, 2));
        article2.setYear(2015);
        articleRepository.save(article2);

        Article article3 = new Article();
        article3.setTitle("CCC");
        article3.setCollection(random_string(8));
        article3.setScore(17);
        article3.setVol(random.nextInt(10));
        article3.setNo(random.nextInt(5));
        article3.setArticleNo(random.nextInt(20));
        article3.setJournal(journals.get(random.nextInt(journals.size()))); 
        article3.setAuthors(authors.subList(1, 2));
        article3.setYear(2016);
        articleRepository.save(article3);

        Article article4 = new Article();
        article4.setTitle("DDD");
        article4.setCollection(random_string(8));
        article4.setScore(13);
        article4.setVol(random.nextInt(10));
        article4.setNo(random.nextInt(5));
        article4.setArticleNo(random.nextInt(20));
        article4.setJournal(journals.get(random.nextInt(journals.size()))); 
        article4.setAuthors(authors.subList(0, 2));
        article4.setYear(2016);
        articleRepository.save(article4);

        Article article5 = new Article();
        article5.setTitle("EEE");
        article5.setCollection(random_string(8));
        article5.setScore(13);
        article5.setVol(random.nextInt(10));
        article5.setNo(random.nextInt(5));
        article5.setArticleNo(random.nextInt(20));
        article5.setJournal(journals.get(random.nextInt(journals.size()))); 
        article5.setAuthors(authors.subList(0, 1));
        article5.setYear(2017);
        articleRepository.save(article5);

        Article article6 = new Article();
        article6.setTitle("FFF");
        article6.setCollection(random_string(8));
        article6.setScore(11);
        article6.setVol(random.nextInt(10));
        article6.setNo(random.nextInt(5));
        article6.setArticleNo(random.nextInt(20));
        article6.setJournal(journals.get(random.nextInt(journals.size()))); 
        article6.setAuthors(authors.subList(1, 2));
        article6.setYear(2016);
        articleRepository.save(article6);

        Article article7 = new Article();
        article7.setTitle("GGG");
        article7.setCollection(random_string(8));
        article7.setScore(14);
        article7.setVol(random.nextInt(10));
        article7.setNo(random.nextInt(5));
        article7.setArticleNo(random.nextInt(20));
        article7.setJournal(journals.get(random.nextInt(journals.size()))); 
        article7.setAuthors(authors.subList(2,3));
        article7.setYear(2014);
        articleRepository.save(article7);
    }



























    private void insertSampleChapters() {
        // List<Author> authors = authorRepository.findAll();
         List<Author> authors = insertSampleAuthors();
         List<Book> books = bookRepository.findAll();
 
         Chapter chapter1 = new Chapter();
         chapter1.setTitle(random_string(15));
         chapter1.setScore(12);
         chapter1.setCollection(random_string(8));
         chapter1.setAuthors(authors.subList(0, 1));
         chapter1.setBook(books.get(0));
         chapterRepository.save(chapter1);
 
         Chapter chapter2 = new Chapter();
         chapter2.setTitle(random_string(15));
         chapter2.setScore(10);
         chapter2.setCollection(random_string(8));
         chapter2.setAuthors(authors.subList(0, 1));
         chapter2.setBook(books.get(0));
         chapterRepository.save(chapter2);
 
         Chapter chapter3 = new Chapter();
         chapter3.setTitle(random_string(15));
         chapter3.setScore(12);
         chapter3.setCollection(random_string(8));
         chapter3.setAuthors(authors.subList(1, 2));
         chapter3.setBook(books.get(1));
         chapterRepository.save(chapter3);
     }
    private List<Author> insertSampleAuthors() {
        List<Affiliation> affiliations = affiliationRepository.findAll();
        List<Author> authors = new ArrayList<>();

        Author author1 = new Author();
        author1.setName("Adam");
        author1.setSurname("Nowak");
        author1.setAffiliation(affiliations.get(random.nextInt(affiliations.size()))); 
        authorRepository.save(author1);
        authors.add(author1);
      
        Author author2 = new Author();
        author2.setName("Ewa");
        author2.setSurname("Kowalska");
        author2.setAffiliation(affiliations.get(random.nextInt(affiliations.size()))); 
        authorRepository.save(author2);
        authors.add(author2);

        Author author3 = new Author();
        author3.setName("Jan");
        author3.setSurname("Wiśniewski");
        author3.setAffiliation(affiliations.get(random.nextInt(affiliations.size()))); 
        authorRepository.save(author3);
        authors.add(author3);

        Author author4 = new Author();
        author4.setName("Anna");
        author4.setSurname("Zielińska");
        author4.setAffiliation(affiliations.get(random.nextInt(affiliations.size()))); 
        authorRepository.save(author4);
        authors.add(author4);

        Author author5 = new Author();
        author5.setName("Marek");
        author5.setSurname("Lewandowski");
        author5.setAffiliation(affiliations.get(random.nextInt(affiliations.size()))); 
        authorRepository.save(author5);
        authors.add(author5);

        return authors;
    }
    private void insertSampleBooks() {
        List<Publisher> publishers = publisherRepository.findAll();
        List<Author> authors = authorRepository.findAll();

        Book book1 = new Book();
        book1.setIsbn(random_string(4));
        book1.setYear(2019); 
        book1.setBaseScore(10);
        book1.setTitle("Biblia");
        book1.setEditor(authors.get(random.nextInt(authors.size())));
        book1.setPublisher(publishers.get(random.nextInt(publishers.size()))); 
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setIsbn(random_string(4));
        book2.setYear(2018); 
        book2.setBaseScore(9);
        book2.setTitle("BBB");
        book2.setEditor(authors.get(random.nextInt(authors.size())));
        book2.setPublisher(publishers.get(random.nextInt(publishers.size()))); 
        bookRepository.save(book2);
    
        Book book3 = new Book();
        book3.setIsbn(random_string(4));
        book3.setYear(2017); 
        book3.setBaseScore(8);
        book3.setTitle("CCC");
        book3.setEditor(authors.get(random.nextInt(authors.size())));
        book3.setPublisher(publishers.get(random.nextInt(publishers.size()))); 
        bookRepository.save(book3);
    
        Book book4 = new Book();
        book4.setIsbn(random_string(4));
        book4.setYear(2016); 
        book4.setBaseScore(7);
        book4.setTitle("DDD");
        book4.setEditor(authors.get(random.nextInt(authors.size())));
        book4.setPublisher(publishers.get(random.nextInt(publishers.size()))); 
        bookRepository.save(book4);
    
        Book book5 = new Book();
        book5.setIsbn(random_string(4));
        book5.setYear(2015); 
        book5.setBaseScore(6);
        book5.setTitle("ABC");
        book5.setEditor(authors.get(random.nextInt(authors.size())));
        book5.setPublisher(publishers.get(random.nextInt(publishers.size()))); 
        bookRepository.save(book5);
    }
    private String random_string(int length) {
        StringBuilder sb = new StringBuilder(length);

        String characters = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(Character.toUpperCase(randomChar));
        }

        return sb.toString();
    }
    private void insertSamplePublishers(int count) {
        List<Publisher> publishers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Publisher publisher = new Publisher();
            publisher.setName(random_string(10));
            publisher.setLocation(random_string(8));
            publisherRepository.save(publisher);
            publishers.add(publisher);
        }
    }
    private List<Affiliation> insertSampleAffiliations(int count) {
        List<Affiliation> affiliations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Affiliation affiliation = new Affiliation();
            affiliation.setName(random_string(12));
            affiliation.setParent(null);
            affiliationRepository.save(affiliation);
            affiliations.add(affiliation);
        }
        return affiliations;
    }
    private void insertSampleJournals(int count) {
        List<Publisher> publishers = publisherRepository.findAll();

        for (int i = 0; i < count; i++) {
            Journal journal = new Journal();
            journal.setBaseScore(random.nextInt(100));
            journal.setTitle(random_string(10));
            journal.setIssn(random_string(4));
            journal.setPublisher(publishers.get(random.nextInt(publishers.size())));
            journalRepository.save(journal);
        }
    }



}