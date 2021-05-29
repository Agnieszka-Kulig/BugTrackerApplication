package bug.com.issues;

//dorobienie
import bug.com.enums.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class IssueService {

    private final IssueRepository issueRepository;


    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public void createNewIssue(String tittle, String content, State state,
                               String code) {
        Issue issue = new Issue();
        issue.setTittle(tittle);
        issue.setContent(content);
        issue.setState(state);
        issue.setCode(code);
        issueRepository.save(issue);
    }

    public void deleteIssue(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if(issue.isPresent()){
            issueRepository.delete(issue.get());
        }
    }

    protected Issue editIssue (Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    List<Issue> findAllIssue() {

        return issueRepository.findAll();
    }
}