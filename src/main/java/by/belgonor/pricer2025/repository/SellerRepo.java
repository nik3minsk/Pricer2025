package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Integer> {
}
