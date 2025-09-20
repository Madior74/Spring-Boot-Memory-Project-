    package com.example.schooladmin.admin;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.utilisateur.Utilisateur;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends Utilisateur {


    //Role de l'Admin
    @Override
    public Role getRole(){
        return Role.ROLE_ADMIN;
    }
}