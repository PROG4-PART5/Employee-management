package com.example.prog4.repository.CnapsEmployee;

import com.example.prog4.repository.CnapsEmployee.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnapsPositionRepository extends JpaRepository<Position, String> {
}
