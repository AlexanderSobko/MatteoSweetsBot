package com.github.AlexanderSobko.MatteoSweetsBot.repositories;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Patisserie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatisserieRepository extends JpaRepository<Patisserie, Long> {
}
