package com.ex.newWeb.Repository;

import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList,Long> {
    Optional<PlayList> findByName(String url);
    @Query("SELECT c from playLists c WHERE c.name LIKE CONCAT('%', :query, '%')")
    List<PlayList> searchClubs(String query);

    List<PlayList> findByCreatedBy(UserEntity user);

}
