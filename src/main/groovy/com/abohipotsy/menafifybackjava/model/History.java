package com.abohipotsy.menafifybackjava.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History {
    private String title;
    private String description;
    private LocalDate updatedAt;

}
