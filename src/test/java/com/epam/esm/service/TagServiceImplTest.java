package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.util.MapperDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    public MapperDTO mapperDTO;

    @Mock
    public TagDAO tagDAO;

    private Tag tag;

    private TagDTO tagDTO;

    @BeforeEach
    public void initTag() {
        tag = Tag.builder().name("Name").build();
    }

    @Test
    void findTagById() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.of(tag));
        TagDTO expected = mapperDTO.convertTagToDTO(tag);
        TagDTO actual = tagService.findById(id);
        verify(tagDAO).findById(id);
        verifyNoMoreInteractions(tagDAO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTags() {
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDAO.findAll(new Page())).thenReturn(tags);
        tags.add(tag);
        List<TagDTO> expected = tags.stream()
            .map(mapperDTO::convertTagToDTO)
            .collect(Collectors.toList());
        List<TagDTO> actual = tagService.findAll(new Page());
        verify(tagDAO).findAll(new Page());
        verifyNoMoreInteractions(tagDAO);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createTag() {
        Mockito.when(mapperDTO.convertTagToDTO(tag)).thenReturn(tagDTO);
        Mockito.when(mapperDTO.convertDTOToTag(tagDTO)).thenReturn(tag);
        Mockito.when(tagDAO.findOrCreate(tag)).thenReturn(tag);
        TagDTO actual = tagService.create(tagDTO);
        verify(tagDAO).findOrCreate(tag);
        verifyNoMoreInteractions(tagDAO);
        Assertions.assertEquals(tagDTO, actual);
    }

    @Test
    void findMostPopularTag() {
        Mockito.when(tagDAO.findMostPopularTag()).thenReturn(tag);
        Mockito.when(mapperDTO.convertTagToDTO(tag)).thenReturn(tagDTO);
        TagDTO actual = tagService.findMostPopularTag();
        verify(tagDAO).findMostPopularTag();
        verifyNoMoreInteractions(tagDAO);
        Assertions.assertEquals(tagDTO, actual);
    }
}
