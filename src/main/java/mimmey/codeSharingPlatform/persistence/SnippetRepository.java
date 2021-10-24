package mimmey.codeSharingPlatform.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mimmey.codeSharingPlatform.business.entities.Snippet;

import java.util.List;
import java.util.Optional;

@Repository
public interface SnippetRepository extends CrudRepository<Snippet, String> {
    Optional<Snippet> findById(String id);
    List<Snippet> findAll();
    Snippet save(Snippet snippet);
    void deleteById(String id);
}
