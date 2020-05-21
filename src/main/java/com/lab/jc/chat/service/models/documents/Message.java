package com.lab.jc.chat.service.models.documents;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name= "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private Long date;
    private String username;
    private String typeMessage;
    private String color;
    private String roomId;

    private static final long serialVersionUID = 9082250369122447856L;



}
