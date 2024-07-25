package com.ibank.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ibank.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Procedure(procedureName = "spRegisterUser")
    Integer spRegisterUser(
        @Param("p_UserName") String userName,
        @Param("p_Password") String password,
        @Param("p_FirstName") String firstName,
        @Param("p_LastName") String lastName,
        @Param("p_Email") String email,
        @Param("p_Phone") String phone,
        @Param("p_Address") String address
        
    );
}
