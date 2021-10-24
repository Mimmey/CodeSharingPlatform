package mimmey.codeSharingPlatform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import mimmey.codeSharingPlatform.business.entities.Snippet;
import mimmey.codeSharingPlatform.business.services.SnippetService;
import mimmey.codeSharingPlatform.business.entities.wrappers.Code;
import mimmey.codeSharingPlatform.business.entities.wrappers.Id;
import java.util.List;
import java.util.Optional;

@Controller
public class ApiController {

    @Autowired
    private SnippetService snippetService;

    @GetMapping(value = "/api/code/{id}")
    @ResponseBody
    public Snippet getJsonSnippet(@PathVariable String id) {
        Optional<Snippet> optionalSnippet = snippetService.getSnippetByIdWithRefreshing(id);

        if (optionalSnippet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return optionalSnippet.get();
    }

    @PostMapping(value = "/api/code/new")
    @ResponseBody
    public Id postSnippet(@RequestBody Code code) {
        return snippetService.saveSnippet(code);
    }

    @GetMapping(value = "api/code/latest")
    @ResponseBody
    public List<Snippet> getLatestJsonSnippets() {
        return snippetService.getSnippetSortedPageWithRefreshing();
    }
}