package com.had0uken.blurb.repository;

import com.had0uken.blurb.model.post.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaFile,Long> {
}
