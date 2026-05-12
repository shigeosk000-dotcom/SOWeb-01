package com.aromatripnippon.repository;

import com.aromatripnippon.entity.ExperienceProgram;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceProgramRepository extends JpaRepository<ExperienceProgram, Long> {
  Optional<ExperienceProgram> findFirstByDeletedAtIsNullAndActiveTrueOrderById();
}
