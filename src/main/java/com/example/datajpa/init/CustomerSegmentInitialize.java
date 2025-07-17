package com.example.datajpa.init;

import com.example.datajpa.domain.CustomerSegment;
import com.example.datajpa.repository.CustomerSegmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {
    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    public void init(){
        if(customerSegmentRepository.count() == 0){
            CustomerSegment regular = new CustomerSegment();
            regular.setSegment("REGULAR");
            regular.set_deleted(false);
            regular.setDescription("Regular description");

            CustomerSegment silver = new CustomerSegment();
            silver.setSegment("SILVER");
            silver.set_deleted(false);
            silver.setDescription("Silver description");

            CustomerSegment gold = new CustomerSegment();
            gold.setSegment("GOLD");
            gold.set_deleted(false);
            gold.setDescription("Gold description");

            customerSegmentRepository.saveAll(List.of(regular,silver,gold));


        }
    }

}

