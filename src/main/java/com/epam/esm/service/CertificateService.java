package com.epam.esm.service;

import com.epam.esm.model.dto.CertificateDTO;
import com.epam.esm.model.dto.ParametersSpecificationDTO;
import com.epam.esm.model.dto.PatchDTO;
import com.epam.esm.model.entity.Page;

import java.math.BigInteger;
import java.util.List;

public interface CertificateService extends CommonService<CertificateDTO, Long> {

    List<CertificateDTO> findAll(ParametersSpecificationDTO querySpecificationDTO, Page page);

    CertificateDTO update(CertificateDTO certificateDTO);

    CertificateDTO applyPatch(Long id, PatchDTO patchDTO);

    List<CertificateDTO> findAllByOrderId(Long id, Page page);

    BigInteger getCountOfCertificate();

    Long getCountOfCertificateWithQuery(ParametersSpecificationDTO parameters);
}
