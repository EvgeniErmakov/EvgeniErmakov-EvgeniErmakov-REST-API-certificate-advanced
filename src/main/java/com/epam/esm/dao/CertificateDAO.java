package com.epam.esm.dao;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.QuerySpecification;

import java.util.List;

public interface CertificateDAO extends CommonDAO<Certificate, Long> {

    List<Certificate> findAll(QuerySpecification querySpecification, Page page);

    Certificate update(Certificate certificate);

    Certificate applyPatch(Certificate certificate, Certificate update);
}
