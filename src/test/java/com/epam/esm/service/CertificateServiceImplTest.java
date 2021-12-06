package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.model.dto.CertificateDTO;;
import com.epam.esm.model.dto.PatchDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.exception.CertificateNotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateDAO certificateDAO;

    @Mock
    private MapperDTO mapperDTO;

    private static Certificate certificate;
    private static CertificateDTO certificateDTO;

    @BeforeEach
    public void initCertificate() {
        certificate = Certificate.builder()
            .name("for test")
            .description("some info")
            .price(new BigDecimal("100"))
            .duration(7)
            .tags(new HashSet<>())
            .isActive(true)
            .build();
        certificateDTO = CertificateDTO.builder()
            .id(1L)
            .name("for test")
            .description("some info")
            .price(new BigDecimal("100"))
            .duration(7)
            .tags(new HashSet<>())
            .build();
    }

    @Test
    void findCertificateById() {
        Optional<Certificate> optionalCertificate = Optional.of(certificate);
        Mockito.when(certificateDAO.findById(1L)).thenReturn(optionalCertificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.findById(1L);
        verify(certificateDAO).findById(1L);
        verifyNoMoreInteractions(certificateDAO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void findAllCertificates() {
        List<Certificate> certificatesList = new ArrayList<>();
        List<CertificateDTO> expectedList = new ArrayList<>();
        certificatesList.add(certificate);
        expectedList.add(certificateDTO);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(certificateDAO.findAll(new Page())).thenReturn(certificatesList);
        List<CertificateDTO> actual = certificateService.findAll(new Page());
        verify(certificateDAO).findAll(new Page());
        verifyNoMoreInteractions(certificateDAO);
        Assertions.assertEquals(expectedList, actual);
    }

    @Test
    void createCertificate() {
        Mockito.when(certificateDAO.create(certificate)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        Mockito.when(mapperDTO.convertDTOToCertificate(certificateDTO)).thenReturn(certificate);
        CertificateDTO actual = certificateService.create(certificateDTO);
        verify(certificateDAO).create(certificate);
        verifyNoMoreInteractions(certificateDAO);
        Assertions.assertEquals(certificateDTO, actual);
    }

    @Test
    void updateCertificate() {
        Long id = 1L;
        Certificate certificate = Certificate.builder().name("testName").build();
        Mockito.when(certificateDAO.findById(id))
            .thenReturn(Optional.of(CertificateServiceImplTest.certificate));
        Mockito.when(mapperDTO.convertDTOToCertificate(certificateDTO)).thenReturn(certificate);
        Mockito.when(certificateDAO.update(CertificateServiceImplTest.certificate))
            .thenReturn(CertificateServiceImplTest.certificate);
        Mockito.when(mapperDTO.convertCertificateToDTO(CertificateServiceImplTest.certificate))
            .thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.update(certificateDTO);
        verify(certificateDAO).update(CertificateServiceImplTest.certificate);
        verifyNoMoreInteractions(certificateDAO);
        Assertions.assertEquals(actual.getName(), certificateDTO.getName());
    }

    @Test
    void updateCertificateWithException() {
        Long id = 10L;
        certificateDTO.setId(id);
        certificate.setId(id);
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class,
            () -> certificateService.update(certificateDTO));
    }

    @Test
    void deleteCertificateException() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            certificateService.delete(id);
        });
    }

    @Test
    void applyPatchCertificate() {
        Long id = 1L;
        Certificate update = Certificate.builder().duration(100).build();
        PatchDTO patchDTO = PatchDTO.builder().duration(100).build();
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.of(certificate));
        Mockito.when(certificateDAO.applyPatch(certificate, update)).thenReturn(certificate);
        Mockito.when(mapperDTO.convertPatchDTOToCertificate(patchDTO)).thenReturn(update);
        Mockito.when(mapperDTO.convertCertificateToDTO(certificate)).thenReturn(certificateDTO);
        CertificateDTO actual = certificateService.applyPatch(id, patchDTO);
        verify(certificateDAO).applyPatch(certificate, update);
        verifyNoMoreInteractions(certificateDAO);
        Assertions.assertEquals(actual.getDuration(), certificateDTO.getDuration());
    }

}
