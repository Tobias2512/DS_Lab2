package com.webserver.Payroll.repositories;

import com.webserver.Payroll.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
