package com.epam.esm.controller;

import com.epam.esm.model.dto.CertificateDTO;
import com.epam.esm.model.dto.ParametersSpecificationDTO;
import com.epam.esm.model.dto.PatchDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.service.CertificateService;
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
@RequestMapping(value = "/certificates")
@AllArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService certificateService;
    private final TagService tagService;

    private static final int MIN_ID = 1;

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public CertificateDTO findById(@PathVariable @Min(MIN_ID) Long id) {
        return ResponseAssembler.assembleCertificate(certificateService.findById(id));
    }

    /**
     * @param parameters
     * @param page
     * @return
     */
    @GetMapping
    public List<CertificateDTO> findAllByFilter(ParametersSpecificationDTO parameters, @Valid Page page) {
        return ResponseAssembler.assembleCertificates(certificateService.findAll(parameters, page));
    }

    /**
     * @param certificateDTO
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@Valid @RequestBody CertificateDTO certificateDTO) {
        return ResponseAssembler.assembleCertificate(certificateService.create(certificateDTO));
    }

    /**
     * @param id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(MIN_ID) Long id) {
        certificateService.delete(id);
    }

    /**
     * @param id
     * @param patchDTO
     * @return
     */
    @PatchMapping(value = "/{id}")
    public CertificateDTO patch(@PathVariable @Min(MIN_ID) Long id, @Valid @RequestBody PatchDTO patchDTO) {
        certificateService.applyPatch(id, patchDTO);
        return ResponseAssembler.assembleCertificate(certificateService.findById(id));
    }

    /**
     * @param id
     * @param certificateDTO
     * @return
     */
    @PutMapping(value = "/{id}")
    public CertificateDTO update(@PathVariable @Min(MIN_ID) Long id, @Valid @RequestBody CertificateDTO certificateDTO) {
        certificateDTO.setId(id);
        certificateService.update(certificateDTO);
        return ResponseAssembler.assembleCertificate(certificateService.findById(id));
    }

    /**
     * @param id
     * @param page
     * @return
     */
    @GetMapping(value = "/{id}/tags")
    public List<TagDTO> findTagsByCertificateId(@PathVariable @Min(MIN_ID) Long id, @Valid Page page) {
        return ResponseAssembler.assembleTags(tagService.findAllByCertificateId(id, page));
    }
}
