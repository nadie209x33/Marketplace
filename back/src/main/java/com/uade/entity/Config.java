package com.uade.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@DaTa
@Entity
@Table(name = "Config")
public class Config {

public Config(){}
public Config(Int value){
this.value = value;
}

@Id
private String key;

@Column
private Int value;

}