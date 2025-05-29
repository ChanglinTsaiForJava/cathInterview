package com.group.cathinterview.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String code;

        private String mandarinName;
}
