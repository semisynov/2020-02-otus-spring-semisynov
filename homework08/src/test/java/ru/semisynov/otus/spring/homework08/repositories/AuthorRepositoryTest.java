package ru.semisynov.otus.spring.homework08.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами книг ")
@DataMongoTest
public class AuthorRepositoryTest {

   private static final int EXPECTED_AUTHORS_COUNT = 3;
////    private static final String EXPECTED_AUTHOR_ID = "12345";
////    private static final String EXPECTED_AUTHOR_NAME = "Author20";
////
////    private Author testAuthor;
//
    @Autowired
    private AuthorRepository authorRepository;

////    @BeforeEach
////    void setUp() {
////        testAuthor = new Author(EXPECTED_AUTHOR_ID, EXPECTED_AUTHOR_NAME);
////    }
//
    @DisplayName("возвращает общее количество всех авторов")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long count = authorRepository.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

//
//    @DisplayName("given object to save"
//            + " when save object using MongoDB template"
//            + " then object is saved")
//    @Test
//    public void test(@Autowired MongoTemplate mongoTemplate) {
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");
//    }
}