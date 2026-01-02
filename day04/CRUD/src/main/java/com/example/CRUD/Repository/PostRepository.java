package com.example.CRUD.Repository;


    import com.example.CRUD.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface PostRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

}
