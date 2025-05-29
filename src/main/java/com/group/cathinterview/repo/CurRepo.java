package com.group.cathinterview.repo;

import com.group.cathinterview.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurRepo extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);

}
