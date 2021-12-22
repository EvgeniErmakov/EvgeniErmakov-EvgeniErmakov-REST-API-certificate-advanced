package com.epam.esm.dao;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Sql(scripts = "classpath:/data_test_H2.sql")
class CertificateDAOImplTest {

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    @Transactional
    void findAllCertificates() {
        Page page = new Page();
        List<Certificate> certificatesList = certificateDAO.findAll(page);
        Assertions.assertEquals(3, certificatesList.size());
    }

    @Test
    @Transactional
    void findCertificateById() {
        Long id = 1L;
        Optional<Certificate> certificate = certificateDAO.findById(id);
        Assertions.assertEquals(31, certificate.get().getDuration());
    }

    @Test
    @Transactional
    void updateTest() {
        Certificate certificate = Certificate.builder()
            .name("certificate")
            .description("description")
            .price(new BigDecimal(100))
            .duration(10)
            .build();
        Certificate updatedCertificate = certificateDAO.update(certificate);
        Assertions.assertEquals(10, updatedCertificate.getDuration());
    }
}
