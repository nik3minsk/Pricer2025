package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Bean
@Repository
public interface CatRepo extends JpaRepository<Cat, Integer> {

}
