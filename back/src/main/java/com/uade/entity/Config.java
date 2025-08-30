package com.uade.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "Config")
public class Config {

    @Id
    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private int value;

    public Config(String key, int value){
        this.key = key;
        this.value = value;
    }

}