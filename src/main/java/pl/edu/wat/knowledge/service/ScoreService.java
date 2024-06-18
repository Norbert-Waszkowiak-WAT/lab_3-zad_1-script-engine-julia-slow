package pl.edu.wat.knowledge.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.entity.Book;
import pl.edu.wat.knowledge.entity.Chapter;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class ScoreService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    public AuthorRepository authorRepository;
    
    public Integer getScore(Author author, Integer year) {
        List<Integer> allScores = new ArrayList<>();

        // punkty z artykułów autora w danym roku
        List<Article> articles = articleRepository.findAll();
        List<Integer> articleScores = articles
                .stream()
                .filter(article -> article.getAuthors().contains(author) && article.getYear() == year)
                .map(article -> calculateScoreForArticle(article, author))
                .collect(Collectors.toList());    
        allScores.addAll(articleScores);

        //  punkty z rozdziałów autora w danym roku
        List<Chapter> chapters = chapterRepository.findAll();
        List<Integer> chapterScores = chapters
                .stream()
                .filter(chapter -> chapter.getAuthors().contains(author) && chapter.getBook().getYear() == year)
                .map(chapter -> calculateScoreForChapter(chapter, author))
                .collect(Collectors.toList());
        allScores.addAll(chapterScores);

        allScores.sort(Comparator.reverseOrder()); //malejąco

        // Wybierz 4 najlepsze wyniki
        int totalScore = allScores
                .stream()
                .limit(4)
                .mapToInt(Integer::intValue)
                .sum();

        return totalScore;
    }

    private int calculateScoreForArticle(Article article, Author author) {
        int score = 0;
        int numAuthors = article.getAuthors().size();

        if (numAuthors == 0) {
            return score;
        }

        int individualScore = article.getScore() / numAuthors;

        if (article.getAuthors().contains(author)) {
            score += individualScore;
        }

        return score;
    }

    private int calculateScoreForChapter(Chapter chapter, Author author) {
        int score = 0;
        int numAuthors = chapter.getAuthors().size();

        if (numAuthors == 0) {
            return score;
        }

        int individualScore = chapter.getScore() / numAuthors;

        if (chapter.getAuthors().contains(author)) {
            score += individualScore;
        }

        return score;
    }
}
