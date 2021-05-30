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

    public void createNewIssue(String tittle, String content, State state, String assigne, String project,
                               String issue, String status, String priority, String type, String code) {
//        Issue issue = new Issue();
//        issue.setTittle(tittle);
//        issue.setContent(content);
//        issue.setState(state);
//        issue.setAssignee(assigne);
//        issue.setProject(project);
//        issue.setIssue(issue);
//        issue.setStatus(status);
//        issue.setPriority(priority);
//        issue.setType(type);
//        issue.setCode(code);
//        issueRepository.save(issue);
    }

    public void deleteIssue(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if(issue.isPresent()){
            issueRepository.delete(issue.get());
        }
    }

//    protected void saveIssue(Issue issue) {
//        issueRepository.save(issue);//dopisanie save


    protected Issue editIssue (Long id) {

        return issueRepository.findById(id).orElse(null);
    }

    List<Issue> findAllIssue() {

        return issueRepository.findAll();
    }
}