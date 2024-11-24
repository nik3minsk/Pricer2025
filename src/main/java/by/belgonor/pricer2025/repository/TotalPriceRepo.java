package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.entity.TotalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//@Repository
//public interface TotalPriceRepo extends JpaRepository<TotalPrice, Integer> {
//}

//public interface TotalPriceRepo extends JpaRepository<TotalPrice, Integer> { List<TotalPrice> findByDate(LocalDate date); }
//
//
//
//@Repository
//public interface TotalPriceRepo extends JpaRepository<TotalPrice, Integer> { List<TotalPrice> findByDateAndIdSaler(LocalDate date, Seller idSaler); findByDate(LocalDate date) }
//@Repository
//public interface TotalPriceRepo extends JpaRepository<TotalPrice, Integer> { List<TotalPrice> findByDate(LocalDate date); }

@Repository
public interface TotalPriceRepo extends JpaRepository<TotalPrice, Integer> {
    List<TotalPrice> findByDateAndIdSaler(LocalDate date, Seller idSaler);
    List<TotalPrice> findByDate(LocalDate date); }