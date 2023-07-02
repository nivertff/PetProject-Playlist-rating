package com.ex.newWeb.Repository;

import com.ex.newWeb.models.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList,Long> {
    Optional<PlayList> findByName(String url);

    void delete(Long playListId);
}
