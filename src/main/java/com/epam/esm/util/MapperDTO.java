package com.epam.esm.util;

import com.epam.esm.model.dto.*;
import com.epam.esm.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperDTO {

    private final ModelMapper mapper;

    public CertificateDTO convertCertificateToDTO(Certificate certificate) {
        return mapper.map(certificate, CertificateDTO.class);
    }

    public Certificate convertDTOToCertificate(CertificateDTO certificateDTO) {
        return mapper.map(certificateDTO, Certificate.class);
    }

    public ParametersSpecificationDTO convertDTOToQuery(
        ParametersSpecificationDTO querySpecificationDTO) {
        return mapper.map(querySpecificationDTO, ParametersSpecificationDTO.class);
    }

    public TagDTO convertTagToDTO(Tag tag) {
        return mapper.map(tag, TagDTO.class);
    }

    public Tag convertDTOToTag(TagDTO tagDTO) {
        return mapper.map(tagDTO, Tag.class);
    }

    public UserDTO convertUserToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public OrderDTO convertOrderToDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }

    public Order convertDTOToOrder(OrderDTO orderDTO) {
        return mapper.map(orderDTO, Order.class);
    }

    public Certificate convertPatchDTOToCertificate(PatchDTO patchDTO) {
        return mapper.map(patchDTO, Certificate.class);
    }
}
