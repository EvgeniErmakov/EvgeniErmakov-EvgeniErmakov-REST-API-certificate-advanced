package com.epam.esm.controller;

import com.epam.esm.model.dto.CertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Page;
import com.epam.esm.security.jwt.JwtUser;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final CertificateService certificateService;
    private static final int MIN_ID = 1;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public OrderDTO findById(@PathVariable @Min(MIN_ID) Long id) {
        return ResponseAssembler.assembleOrder(orderService.findById(id));
    }

    /**
     *
     * @param page
     * @return
     */
    @GetMapping
    public List<OrderDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleOrders(orderService.findAll(page));
    }

    /**
     *
     * @param id
     * @param page
     * @return
     */
    @GetMapping(value = "/{id}/certificates")
    public List<CertificateDTO> findAllByOrderId(@PathVariable @Min(MIN_ID) Long id,
        @Valid Page page) {
        return ResponseAssembler.assembleCertificates(
            certificateService.findAllByOrderId(id, page));
    }

    /**
     *
     * @param orderDTO
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public OrderDTO create(@Valid @RequestBody OrderDTO orderDTO,  Authentication authentication) {
        JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
        return ResponseAssembler.assembleOrder(orderService.create(orderDTO, jwtUser));
    }
}
