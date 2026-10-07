package academy.hub.app.book.services;


import academy.hub.app.book.dtos.BookCreateRequest;
import academy.hub.app.book.dtos.BookCreateResponse;
import academy.hub.app.book.dtos.BookDeleteResponse;
import academy.hub.app.book.exceptions.BookAlreadyAssignedToThisStudent;
import academy.hub.app.book.exceptions.BookExceptionConstants;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.repositories.BookRepository;
import academy.hub.app.student.exceptions.StudentExceptionConstants;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repositories.StudentRepository;
import academy.hub.app.support.BookFixtures;
import academy.hub.app.support.StudentFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookCommandServiceImpl (unit test, mocked repository")

public class BookCommandServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    BookCommandServiceImpl bookCommandService;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    @Nested
    @DisplayName("Create Book")
    class CreateBook
    {

        @Test
        @DisplayName("Creates book and returns requested values")
        public void createBook()
        {
            Student student = StudentFixtures.persisted();

            BookCreateRequest request = new BookCreateRequest(
                    "Java For Beginners",
                    student.getId()
            );

            Book savedBook = BookFixtures.persisted(request.name());



            when(studentRepository
                    .findById(student.getId()))
                    .thenReturn(Optional.of(student));

            when(bookRepository.
                    existsByStudentIdAndName(student.getId(), request.name()))
                    .thenReturn(false);

            when(bookRepository.save(any(Book.class)))
                    .thenReturn(savedBook);

            BookCreateResponse response =
                    bookCommandService.createBook(request);

            assertThat(response.id()).isEqualTo(savedBook.getId());
            assertThat(response.name()).isEqualTo(request.name());

            verify(bookRepository)
                    .save(bookArgumentCaptor.capture());

            Book bookCaptured =
                    bookArgumentCaptor.getValue();

            assertThat(bookCaptured.getName()).isEqualTo(request.name());
            assertThat(bookCaptured.getStudent()).isEqualTo(student);

        }

        @Test
        @DisplayName("throws StudentNotFound if StudentId cannot be found")
        void createBook_throwsStudentNotFound()
        {

            UUID studentId = StudentFixtures.OTHER_ID;

            BookCreateRequest request = new BookCreateRequest(
                    "Java For Beginners",
                    studentId
            );

            when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

            assertThatThrownBy(()->
                    bookCommandService.createBook(request))
                    .isInstanceOf(StudentNotFound.class)
                    .hasMessage(StudentExceptionConstants.STUDENT_NOT_FOUND);

            verify(bookRepository, never())
                    .existsByStudentIdAndName(any(), any());
            verify(bookRepository, never())
                    .save(any(Book.class));

        }

        @Test
        @DisplayName("throws BookAlreadyAssignedToThisStudent")
        void createBook_throwsBookAlreadyAssignedToThisStudent()
        {
            Student student = StudentFixtures.persisted();

            BookCreateRequest request = new BookCreateRequest(
                    "Java For Beginners",
                    student.getId()
            );

            when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

            when(bookRepository.existsByStudentIdAndName(student.getId(), request.name()))
                    .thenReturn(true);

            assertThatThrownBy(() ->
                    bookCommandService.createBook(request))
                    .isInstanceOf(BookAlreadyAssignedToThisStudent.class)
                    .hasMessage(BookExceptionConstants.BOOK_ALREADY_ASSIGNED_TO_THIS_STUDENT);

            verify(bookRepository, never()).save(any(Book.class));

        }

    }

    @Nested
    @DisplayName("Delete Book")
    class DeleteBook
    {

        @Test
        @DisplayName("delete Book")
        void deleteBook()
        {

            Book book = BookFixtures.persisted();

            when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

            BookDeleteResponse response =
                    bookCommandService.deleteBook(book.getId());

            assertThat(response.id()).isEqualTo(book.getId());
            assertThat(response.name()).isEqualTo(book.getName());

            verify(bookRepository).delete(book);


        }

    }


}
