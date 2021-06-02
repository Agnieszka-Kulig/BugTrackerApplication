package bug.com.issues;

//dorobienie
import bug.com.enums.Status;
import bug.com.project.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class IssueService {

    private final IssueRepository issueRepository;


    public IssueService(IssueRepository issueRepository) {

        this.issueRepository = issueRepository;
    }
    public void createNewIssue(Issue issue) {

        issueRepository.save(issue);

    }

//    public void createNewIssue(String tittle, String content, Status state, String assigne, String project,
//                               String issue, String status, String priority, String type, String code) {
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

    public void deleteIssue(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if(issue.isPresent()){
            issueRepository.delete(issue.get());
        }
    }

//    protected void saveIssue(Issue issue) {
//        issueRepository.save(issue);//dopisanie save

//    List<Issue> findAllIssue() {
//
//        return issueRepository.findAll();
//    }
//}

    protected Issue editIssue (Issue issue) {

        return issueRepository.save(issue);//edycja
    }

    public List<Issue> getAllIssue() {

        return issueRepository.findAll();//wyszukiwanie
    }
    public Issue findIssue (long id) {

        return issueRepository.findById(id).orElse(null);
    }
    }

