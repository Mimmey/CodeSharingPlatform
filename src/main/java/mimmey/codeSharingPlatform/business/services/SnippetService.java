package mimmey.codeSharingPlatform.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mimmey.codeSharingPlatform.business.entities.Snippet;
import mimmey.codeSharingPlatform.business.entities.wrappers.Code;
import mimmey.codeSharingPlatform.business.entities.wrappers.Id;
import mimmey.codeSharingPlatform.persistence.SnippetRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SnippetService {

    @Autowired
    private SnippetRepository snippetRepository;

    public Optional<Snippet> getSnippetById(String id) {
        return snippetRepository.findById(id);
    }

    public Optional<Snippet> getSnippetByIdWithRefreshing(String id) {
        Optional<Snippet> optionalSnippet = getSnippetById(id);

        if (optionalSnippet.isPresent()) {
            optionalSnippet = updateSnippetInDatabase(optionalSnippet.get());
        }

        return optionalSnippet;
    }

    public List<Snippet> getSnippetSortedPageWithRefreshing() {
        List<Snippet> allSnippets = snippetRepository.findAll();
        allSnippets.sort(Snippet.getComparator());

        List<Snippet> snippetPage = new LinkedList<>();

        while (snippetPage.size() < 10 && !allSnippets.isEmpty()) {
            Snippet snippet = allSnippets.get(0);
            Optional<Snippet> optionalSnippet = getSnippetById(snippet.getId());

            if (optionalSnippet.isPresent() && (optionalSnippet.get().isViewsEndless() && optionalSnippet.get().isTimeEndless())) {
                updateSnippetInDatabase(optionalSnippet.get());
                snippetPage.add(optionalSnippet.get());
            }

            allSnippets.remove(0);
        }

        return snippetPage;
    }

    public List<Snippet> getUnarySnippetListByIdWithRefreshing(String id) {
        List<Snippet> list = new ArrayList<>();

        getSnippetByIdWithRefreshing(id).ifPresent(
                list::add
        );

        return list;
    }

    private boolean isAllowed(Snippet snippet) {
        return (snippet.isTimeEndless() || snippet.getTime() > 0)
                && (snippet.isViewsEndless() || snippet.getViews() >= 0);
    }

    public Id saveSnippet(Code code) {
        Snippet snippet = new Snippet(code);
        return new Id(snippetRepository.save(snippet));
    }

    public Optional<Snippet> updateSnippetInDatabase(Snippet snippet) {
        snippet.refresh();

        if (isAllowed(snippet)) {
            snippetRepository.save(snippet);
            return Optional.of(snippet);
        }

        snippetRepository.deleteById(snippet.getId());
        return Optional.empty();
    }
}