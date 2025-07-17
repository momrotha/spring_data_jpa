package com.example.datajpa.mapper;


import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCustomertoCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
            @MappingTarget Customer customer
    );
//    DTO -> Model
//    Model(source) -> DTO(response)
//    What is source data? (parameter) (customer)
//    What is target data? (return_type) (response)

    CustomerResponse mapCustomerToCustomerResponse(Customer customer);

    @Mapping(target = "customerSegment", ignore = true)
    Customer fromCreateCustomerRequest(CustomerRequest customerRequest);
}
