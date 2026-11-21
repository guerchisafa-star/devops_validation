package org.example.projetperfectionnement.repository;

import org.example.projetperfectionnement.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Admin--> c'est l'entite (table admins dans la base)
//JpaRepository interface fournie par spring elle contient des méthodes pretes 
//Long type de clé primaire 
public interface AdminRepository extends JpaRepository <Admin, Long>{
    //Optional permet d'eviter les errurs si la donnée n'existe pas
    Optional<Admin> findByEmail(String email);
}
//fichier repositiory --> commmunique avce la base de données 
//findByEmail  methode sans implémentation mais elle fonctionne  car il traduire automatiquement en sql(magie de spring data jpa)