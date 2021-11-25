package com.epam.esm;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
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
    void findAllCertificates() {
        Page page = new Page();
        List<Certificate> certificatesList = certificateDAO.findAll(page);
        Assertions.assertEquals(3, certificatesList.size());
    }

    @Test
    void findCertificateById() {
        Long id = 1L;
        Optional<Certificate> certificate = certificateDAO.findById(id);
        Assertions.assertEquals(31, certificate.get().getDuration());
    }

    @Test
    void updateTest() {
        Certificate certificate = Certificate.builder()
                .name("certificate")
                .price(new BigDecimal(100))
                .duration(10)
                .build();
        Certificate update = Certificate.builder()
                .name("update")
                .price(new BigDecimal(50))
                .duration(5)
                .build();
        Certificate updatedCertificate = certificateDAO.update(certificate, update);
        Assertions.assertEquals(5, updatedCertificate.getDuration());
    }
}
