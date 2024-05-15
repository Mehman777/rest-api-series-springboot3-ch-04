package com.tiagoamp.booksapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_token")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "seq_user_token", sequenceName = "seq_user_token_id", allocationSize = 1, initialValue = 1)
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_token")

    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "token_user_managed")
    private AppUser user;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "last_used")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;
}