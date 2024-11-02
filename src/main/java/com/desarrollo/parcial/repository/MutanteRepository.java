package com.desarrollo.parcial.repository;

import com.desarrollo.parcial.model.Mutante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MutanteRepository extends JpaRepository<Mutante, Long> {
    Optional<Mutante> findByAdn(String adn);
}
