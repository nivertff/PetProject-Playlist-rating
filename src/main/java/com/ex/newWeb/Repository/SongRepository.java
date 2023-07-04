package com.ex.newWeb.Repository;

import com.ex.newWeb.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
