package academy.hub.app.book.services.interfaces;

import academy.hub.app.book.dtos.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface BookCommandService {

    BookCreateResponse createBook(@Valid BookCreateRequest bookCreateRequest);
    BookDeleteResponse deleteBook(@Valid UUID id);
    BookUpdateResponse updateBook(@Valid UUID id, @Valid BookUpdateRequest bookUpdateRequest);
}
