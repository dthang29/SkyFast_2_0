package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Modifying
    @Query("insert into Passenger (title, fullName, nationality, phoneNumber, email) values (:title, :name, :nationality, :phoneNumber, :email)")
    void insert(@Param("title") String title,
                @Param("name") String name,
                @Param("nationality") String nationality,
                @Param("phoneNumber") String phoneNumber,
                @Param("email") String email);
    @Query("select p from Passenger p where p.id = (select max(p2.id) from Passenger p2)")
    Passenger finePassengerByMaxId();
}
