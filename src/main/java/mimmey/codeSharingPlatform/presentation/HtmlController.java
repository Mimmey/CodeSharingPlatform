package mimmey.codeSharingPlatform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import mimmey.codeSharingPlatform.business.entities.Snippet;
import mimmey.codeSharingPlatform.business.services.SnippetService;

import java.util.List;

@Controller
public class HtmlController {

    @Autowired
    private SnippetService snippetService;

    @GetMapping(value = "/code/{id}")
    public String getHtmlSnippet(@PathVariable String id, Model model) {
        List<Snippet> snippet = snippetService.getUnarySnippetListByIdWithRefreshing(id);

        if (snippet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("title", "Code");
        model.addAttribute("snippets", snippet);
        return "codeSnippet";
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getNewSnippetCreationPage() {
        return "newSnippetCreation";
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestHtmlSnippets(Model model) {
        List<Snippet> snippetSortedPage = snippetService.getSnippetSortedPageWithRefreshing();
        model.addAttribute("title", "Latest");
        model.addAttribute("snippets", snippetSortedPage);
        return "codeSnippet";
    }
}
