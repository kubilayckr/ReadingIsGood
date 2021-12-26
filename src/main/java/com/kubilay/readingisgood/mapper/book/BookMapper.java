package com.kubilay.readingisgood.mapper.book;

import com.kubilay.readingisgood.entity.book.Book;
import com.kubilay.readingisgood.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class BookMapper {

    public abstract BookDTO toDTO(Book book);

    public abstract Book toModel(BookDTO bookDTO);
}
