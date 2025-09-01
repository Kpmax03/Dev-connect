package com.dev.connect.repository;

import com.dev.connect.entity.User;

import com.dev.connect.enums.Domain;
import com.dev.connect.enums.Techs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
     Optional<User> findByEmail(String email);
     List<User> findByUserProfile_Domain(Domain domain);
     List<User> findByUserProfile_TechsIn(List<Techs> techs);
     List<User> findByUserProfile_DomainAndUserProfile_TechsIn(Domain domain, List<Techs> techsList);
}
