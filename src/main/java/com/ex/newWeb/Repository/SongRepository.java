package com.ex.newWeb.Repository;

import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByName(String url);
    @Query("SELECT c FROM songs c where c.name like CONCAT('%', :query, '%')")
    List<Song> searchSong(String query);

    List<Song> findByCreatedBy(UserEntity user);
}
