package com.book.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK", schema = "BOOK_CATALOGUE")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column
    private String author;

    @Column(nullable = false)
    private BigInteger ISBN;

    @Column
    private Date publishdate;
}
