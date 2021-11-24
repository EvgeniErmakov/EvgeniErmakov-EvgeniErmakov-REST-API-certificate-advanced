package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@AllArgsConstructor
@Validated
public class TagController {

    private static final int MIN_ID = 1;
    private final TagService service;

    @GetMapping(value = "/{id}")
    public TagDTO findById(@PathVariable @Min(MIN_ID) Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<TagDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleTags(service.findAll(page));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO) {
        return ResponseAssembler.assembleTag(service.create(tagDTO));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(MIN_ID) Long id) {
        service.delete(id);
    }

    @GetMapping(value = "/mostPopularTag")
    public TagDTO findMostPopularTag() {
        return ResponseAssembler.assembleTag(service.findMostPopularTag());
    }
}
