package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.model.dto.ParametersSpecificationDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import com.epam.esm.model.entity.Tag;
import java.math.BigInteger;
import javax.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CertificateDAOImpl implements CertificateDAO {

    private final EntityManager entityManager;

    private static final String SELECT_ALL_CERTIFICATES = "SELECT certificate FROM gift_certificate certificate";
    private static final String SELECT_COUNT_OF_CERTIFICATES = "SELECT count(*) FROM module_4.gift_certificate WHERE is_active = true";
    private static final String CERTIFICATE_TAGS_ATTRIBUTE_NAME = "tags";
    private static final String NAME_COLUMN = "name";
    private static final String ID_COLUMN = "id";
    private static final String PRICE_COLUMN = "price";
    private static final String DURATION_COLUMN = "duration";
    private static final String LIKE_OPERATOR_FORMAT = "%%%s%%";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String SQL_ASC = "ASC";
    private static final String IS_ACTIVE_ATTRIBUTE = "isActive";
    private static final boolean IS_ACTIVE_VALUE = true;

    @Override
    public BigInteger getCountOfTest() {
        Query nativeQuery = entityManager.createNativeQuery(SELECT_COUNT_OF_CERTIFICATES);
        List resultList = nativeQuery.getResultList();
        return (BigInteger) resultList.get(0);
    }

    @Override
    public List<Certificate> findAll(ParametersSpecificationDTO querySpecification, Page page) {
        CriteriaQuery<Certificate> criteriaQuery = createCriteriaQuery(
            querySpecification);
        return entityManager.createQuery(criteriaQuery)
            .setFirstResult((page.getPage() * page.getSize()) - page.getSize())
            .setMaxResults(page.getSize())
            .getResultList();
    }

    @Override
    public Certificate create(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public List<Certificate> findAll(Page page) {
        return entityManager.createQuery(SELECT_ALL_CERTIFICATES, Certificate.class)
            .setFirstResult(page.getPage() * page.getSize())
            .setMaxResults(page.getSize())
            .getResultList();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public Certificate update(Certificate certificate) {
        return entityManager.merge(certificate);
    }

    @Override
    public Certificate applyPatch(Certificate certificate, Certificate update) {
        certificate.setPrice(
            ObjectUtils.isEmpty(update.getPrice()) ? certificate.getPrice()
                : update.getPrice());
        certificate.setDuration(
            (ObjectUtils.isEmpty(update.getDuration()) || (update.getDuration() == 0))
                ? certificate.getDuration()
                : update.getDuration());
        return certificate;
    }

    @Override
    public void delete(Certificate certificate) {
        certificate.setActive(false);
    }

    private CriteriaQuery<Certificate> createCriteriaQuery(
        ParametersSpecificationDTO specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);

        List<Predicate> list = new ArrayList<>();
        list.add(criteriaBuilder.equal(root.get(IS_ACTIVE_ATTRIBUTE), IS_ACTIVE_VALUE));

        if (!ObjectUtils.isEmpty(specification.getText())) {
            list.add(criteriaBuilder.or(criteriaBuilder.like(root.get(NAME_COLUMN),
                    String.format(LIKE_OPERATOR_FORMAT, specification.getText())),
                criteriaBuilder.like(root.get(DESCRIPTION_ATTRIBUTE),
                    String.format(LIKE_OPERATOR_FORMAT, specification.getText()))));
        }

        if (!ObjectUtils.isEmpty(specification.getPrice())) {
            list.add(criteriaBuilder.equal(root.get(PRICE_COLUMN), specification.getPrice()));
        }

        if (!ObjectUtils.isEmpty(specification.getDuration())) {
            list.add(criteriaBuilder.equal(root.get(DURATION_COLUMN), specification.getDuration()));
        }

        if (!ObjectUtils.isEmpty(specification.getTags())) {
            Join<Certificate, Tag> join = root.join(CERTIFICATE_TAGS_ATTRIBUTE_NAME,
                JoinType.INNER);
            list.add(
                criteriaBuilder.in(join.get(NAME_COLUMN)).value(specification.getTags()));
            criteriaQuery.groupBy(root);
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(root),
                specification.getTags().size()));
        }

        if (!ObjectUtils.isEmpty(specification.getOrder())) {
            List<Order> ordersList = new ArrayList<>();
            specification.getOrder()
                .forEach(s -> ordersList.add((ObjectUtils.isEmpty(specification.getSort())
                    || specification.getSort().remove(0).toUpperCase(Locale.ROOT)
                    .equals(SQL_ASC))
                    ? criteriaBuilder.asc(root.get(s)) : criteriaBuilder.desc(root.get(s))));
            Order[] orders = new Order[ordersList.size()];
            criteriaQuery.orderBy(ordersList.toArray(orders));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ID_COLUMN)));
        }
        Predicate[] predicates = new Predicate[list.size()];

        return criteriaQuery.select(root).where(list.toArray(predicates));
    }
}
