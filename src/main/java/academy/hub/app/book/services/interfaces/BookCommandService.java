package academy.hub.app.book.services.interfaces;

import academy.hub.app.book.dtos.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface BookCommandService {

    BookCreateResponse createBook(@Valid BookCreateRequest bookCreateRequest);
    BookDeleteResponse deletebook(@Valid UUID id);
    BookUpdateResponse updatebook(@Valid UUID id, @Valid BookUpdateRequest bookUpdateRequest);
}
