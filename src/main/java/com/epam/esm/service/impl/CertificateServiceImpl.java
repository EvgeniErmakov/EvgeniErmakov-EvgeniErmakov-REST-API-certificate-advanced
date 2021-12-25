package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.dto.CertificateDTO;

import com.epam.esm.model.dto.ParametersSpecificationDTO;
import com.epam.esm.model.dto.PatchDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.exception.EntityNotFoundException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final OrderDAO orderDAO;
    private final MapperDTO mapperDTO;

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = mapperDTO.convertDTOToCertificate(certificateDTO);
        certificate.setTags(new HashSet<>());
        attachTags(certificate, certificateDTO.getTags());
        certificate = certificateDAO.create(certificate);
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAll(Page page) {
        return certificateDAO.findAll(page)
            .stream()
            .map(mapperDTO::convertCertificateToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO findById(Long id) {
        Certificate certificate = certificateDAO.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (!certificate.isActive()) {
            throw new EntityNotFoundException(id.toString());
        }
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAll(ParametersSpecificationDTO querySpecificationDTO,
                                        Page page) {
        ParametersSpecificationDTO querySpecification = mapperDTO.convertDTOToQuery(querySpecificationDTO);
        return certificateDAO.findAll(querySpecification, page)
            .stream()
            .map(mapperDTO::convertCertificateToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO updateDTO) {
        Optional<Certificate> optional = certificateDAO.findById(updateDTO.getId());
        if (optional.isEmpty() || !optional.get().isActive()) {
            throw new EntityNotFoundException(updateDTO.getId().toString());
        }
        Certificate certificate = optional.get();
        Certificate update = mapperDTO.convertDTOToCertificate(updateDTO);
        certificate.setPrice(update.getPrice());
        certificate.setDuration(update.getDuration());
        attachTags(certificate, updateDTO.getTags());
        certificateDAO.update(certificate);
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    @Transactional
    public CertificateDTO applyPatch(Long id, PatchDTO patchDTO) {
        Certificate certificate = certificateDAO.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (!certificate.isActive()) {
            throw new EntityNotFoundException(id.toString());
        }
        Certificate update = mapperDTO.convertPatchDTOToCertificate(patchDTO);
        certificate = certificateDAO.applyPatch(certificate, update);
        return mapperDTO.convertCertificateToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> findAllByOrderId(Long id, Page page) {
        return orderDAO
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString()))
            .getCertificates()
            .stream()
            .distinct()
            .skip(((long) page.getPage() * page.getSize()) - page.getSize())
            .limit(page.getSize())
            .map(mapperDTO::convertCertificateToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        certificateDAO.delete(certificateDAO.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    private void attachTags(Certificate certificate, Set<TagDTO> tags) {
        if (!ObjectUtils.isEmpty(tags)) {
            tags.forEach(
                tag -> certificate.getTags().add(tagDAO.find(mapperDTO.convertDTOToTag(tag))));
        }
    }
}
