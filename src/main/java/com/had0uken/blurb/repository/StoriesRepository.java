package com.had0uken.blurb.repository;

import com.had0uken.blurb.model.post.Stories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoriesRepository extends JpaRepository<Stories,Long> {
    @Query("SELECT s FROM Stories s JOIN s.tags t WHERE t.name = :tagName")
    List<Stories> findByTagName(String tagName);
}
