package com.lab.jc.chat.service.models.documents;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name= "users")
public class UserChat implements Serializable {

    private static final long serialVersionUID = -6761366143607157167L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private Boolean status;
    private String color;
    private String email;
    private String picture;

}
