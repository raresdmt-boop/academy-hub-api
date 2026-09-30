package academy.hub.app.book.controllers;


import academy.hub.app.book.dtos.*;
import academy.hub.app.book.models.Book;
import academy.hub.app.book.services.interfaces.BookCommandService;
import academy.hub.app.book.services.interfaces.BookQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    private final BookQueryService bookQueryService;
    private final BookCommandService bookCommandService;

    public BookController(BookQueryService bookQueryService, BookCommandService bookCommandService) {
        this.bookQueryService = bookQueryService;
        this.bookCommandService = bookCommandService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookQueryService.getBookById(id));
    }

    @GetMapping
    @Operation(summary = "Lista cartilor",
    description = "Pe baza goala, raspunde cu 200")
    @ApiResponse(responseCode = "200", description = "bla bla bla")
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(bookQueryService.getBooks());
    }

    @PostMapping
    public ResponseEntity<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return ResponseEntity.ok(bookCommandService.createBook(bookCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDeleteResponse> deleteBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCommandService.deletebook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookUpdateResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        return ResponseEntity.ok(bookCommandService.updatebook(id, bookUpdateRequest));
    }

}
