package pl.edu.wat.knowledge.service;


import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;
import pl.edu.wat.knowledge.repository.AffiliationRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;
import pl.edu.wat.knowledge.repository.EntityRepository;
import pl.edu.wat.knowledge.repository.JournalRepository;
import pl.edu.wat.knowledge.repository.PublisherRepository;


// Uzupełnij ScriptService zgodnie z nowym schematem encji oraz uwzględnij w nim korzystanie z serwisu ScoreService.
@Service
@Slf4j
public class ScriptService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final AffiliationRepository affiliationRepository;
    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final EntityRepository entityRepository;
    private final JournalRepository journalRepository;
    private final PublisherRepository publisherRepository;
    private final ScoreService scoreService;


    @Autowired
    public ScriptService(ArticleRepository articleRepository, AuthorRepository authorRepository, AffiliationRepository affiliationRepository, BookRepository bookRepository, ChapterRepository chapterRepository, EntityRepository entityRepository, JournalRepository journalRepository, PublisherRepository publisherRepository, ScoreService scoreService) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.affiliationRepository = affiliationRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
        this.entityRepository = entityRepository;
        this.journalRepository = journalRepository;
        this.publisherRepository = publisherRepository;
        this.scoreService = scoreService;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", articleRepository);
            bindings.putMember("authorRepository", authorRepository);
            bindings.putMember("affiliationRepository", affiliationRepository);
            bindings.putMember("bookRepository", bookRepository);
            bindings.putMember("chapterRepository", chapterRepository);
            bindings.putMember("entityRepository", entityRepository);
            bindings.putMember("journalRepository", journalRepository);
            bindings.putMember("publisherRepository", publisherRepository);
            bindings.putMember("scoreService", scoreService);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}
