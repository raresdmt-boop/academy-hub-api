package academy.hub.app.book.controllers;


import academy.hub.app.book.dtos.*;
import academy.hub.app.book.services.interfaces.BookCommandService;
import academy.hub.app.book.services.interfaces.BookQueryService;
import academy.hub.app.system.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    @Operation(
            summary = "Get a book by ID",
            description = "Returns the book identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookQueryService.getBookById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Returns all books. If no books exist, returns an empty list."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Books retrieved successfully"
    )
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(bookQueryService.getBooks());
    }


    @PostMapping
    @Operation(
            summary = "Create a book",
            description = "Creates a new book and assigns it to the specified student. " +
                    "On success, returns 201 Created and the Location header contains " +
                    "the URL of the created book."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified student was not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The book is already assigned to this student",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {

        BookCreateResponse bookCreateResponse = bookCommandService.createBook(bookCreateRequest);

        URI location = URI.create("/api/books/" + bookCreateResponse.id());

        return ResponseEntity.created(location).body(bookCreateResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by its ID. The response contains the ID and name " +
                    "of the deleted book so the client can identify the removed resource."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<BookDeleteResponse> deleteBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCommandService.deleteBook(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a book",
            description = "Updates the book identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<BookUpdateResponse> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        return ResponseEntity.ok(bookCommandService.updateBook(id, bookUpdateRequest));
    }

}
