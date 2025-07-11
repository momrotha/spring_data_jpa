package com.example.datajpa.mapper;


import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

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
    Customer fromCreateCustomerRequest(CustomerRequest customerRequest);

}
