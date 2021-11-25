package com.epam.esm.dao;

import com.epam.esm.dao.OrderDAO;;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Sql(scripts = "classpath:/data_test_H2.sql")
class OrderDAOImplTest {

    @Autowired
    private OrderDAO orderDAO;

    @Test
    void findAllOrders() {
        Page page = new Page();
        List<Order> orderList = orderDAO.findAll(page);
        Assertions.assertEquals(2, orderList.size());
    }

    @Test
    void findOrderById() {
        Long id = 1L;
        Optional<Order> order = orderDAO.findById(id);
        Assertions.assertEquals(new BigDecimal("5.00"), order.get().getCost());
    }

    @Test
    @Transactional
    void createValid() {
        Order orderTest = Order.builder()
                .certificates(new ArrayList<>())
                .user(User.builder().id(4L).name("Dzmitry").build())
                .cost(new BigDecimal(500))
                .build();

        orderTest.getCertificates()
                .add(Certificate
                        .builder()
                        .name("for test")
                        .description("for test")
                        .price(new BigDecimal(500))
                        .duration(365).build());

        Order actual = orderDAO.create(orderTest);
        Assertions.assertEquals(orderTest.getCost(), actual.getCost());
    }
}
