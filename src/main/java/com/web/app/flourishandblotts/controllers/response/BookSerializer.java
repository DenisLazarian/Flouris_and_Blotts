package com.web.app.flourishandblotts.controllers.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.web.app.flourishandblotts.models.Author;
import com.web.app.flourishandblotts.models.BookEntity;
import com.web.app.flourishandblotts.models.Category;

import java.io.IOException;

public class BookSerializer extends JsonSerializer<BookEntity> {
    @Override
    public void serialize(BookEntity bookEntity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("title", bookEntity.getTitle());
        jsonGenerator.writeStringField("id", bookEntity.getId().toString());
        jsonGenerator.writeStringField("subtitle", bookEntity.getSubtitle());
        jsonGenerator.writeStringField("datePublished", bookEntity.getDatePublished());
        jsonGenerator.writeStringField("pageNumber", bookEntity.getPageNumber().toString());
        jsonGenerator.writeStringField("description", bookEntity.getDescription());
        jsonGenerator.writeStringField("thumbnail", bookEntity.getThumbnail());
        jsonGenerator.writeObjectField("language", bookEntity.getLanguage().getName());
        jsonGenerator.writeObjectField("editorial", bookEntity.getEditorial().getName());

        // List of categories
        jsonGenerator.writeFieldName("categories");
        jsonGenerator.writeStartArray();
        for(Category category: bookEntity.getCategories()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", category.getId());
            jsonGenerator.writeStringField("name", category.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

   jsonGenerator.writeFieldName("authors");
        jsonGenerator.writeStartArray();
        for(Author author: bookEntity.getAuthors()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", author.getId());
            jsonGenerator.writeStringField("name", author.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();


        jsonGenerator.writeEndObject();
    }
}
