package academy.hub.app.book.controllers;


import academy.hub.app.book.dtos.*;
import academy.hub.app.book.services.interfaces.BookCommandService;
import academy.hub.app.book.services.interfaces.BookQueryService;
import academy.hub.app.sistem.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ApiResponse(responseCode = "200", description = "Lista cartilor, eventual goala")
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(bookQueryService.getBooks());
    }

    @PostMapping
    @Operation(summary = "Creeaza o carte",
    description = "Raspunde cu 200")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartea a fost creata"),
            @ApiResponse(responseCode = "404", description = "Studentul nu a fost gasit, probabil ID gresit",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return ResponseEntity.ok(bookCommandService.createBook(bookCreateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Sterge o carte",
            description = "Raspunsul contine id-ul si numele cartii de dinainte de stergere, "+
                    "pentru ca apelantul sa poata afisa ce a sters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cartea a fost stearsa"),
            @ApiResponse(responseCode = "404", description = "Id-ul cartii nu a fost gasit",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
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
