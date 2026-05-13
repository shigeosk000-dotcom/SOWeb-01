package com.aromatripnippon.service;

import com.aromatripnippon.entity.ExperienceProgram;
import com.aromatripnippon.repository.ExperienceProgramRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ExperienceProgramService {
  private final ExperienceProgramRepository programs;

  public ExperienceProgramService(ExperienceProgramRepository programs) {
    this.programs = programs;
  }

  public List<ExperienceProgram> findActivePrograms() {
    return programs.findByDeletedAtIsNullOrderById();
  }

  public ExperienceProgram findActive(Long id) {
    return programs.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  public ExperienceProgram findFirstBookableProgram() {
    return programs.findFirstByDeletedAtIsNullAndActiveTrueOrderById().orElseThrow();
  }

  @Transactional
  public ExperienceProgram save(@Valid ExperienceProgram program) {
    return programs.save(program);
  }

  @Transactional
  public void softDelete(Long id) {
    ExperienceProgram program = findActive(id);
    program.softDelete();
    programs.save(program);
  }
}
