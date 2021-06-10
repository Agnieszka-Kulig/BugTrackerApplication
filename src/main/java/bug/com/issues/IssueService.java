package bug.com.issues;

//dorobienie
import bug.com.auth.Person;
import bug.com.mail.MailService;
import bug.com.project.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final MailService mailService;

    public void createNewIssue(Issue issue) {
        Project project = issue.getProject();
        String projectCode = project.getCode();
        issue.setTitle(projectCode + "-" + issue.getTitle());
        Person assignee = issue.getAssignee();
        if (assignee != null && assignee.getEmail() != null) {
            String message = "Task was assigne to you. Title: " + issue.getTitle() + "Project name: " + project.getName();
            try {
                mailService.sendEmail(assignee.getEmail(), message);
            } catch (MessagingException e) {
                return;
            }
        }
        issueRepository.save(issue);
    }

    public void deleteIssue(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if(issue.isPresent()){
            issueRepository.delete(issue.get());
        }
    }

    public List<Issue> getAllIssue() {

        return issueRepository.findAll();//wyszukiwanie
    }
    public Issue findIssue (long id) {

        return issueRepository.findById(id).orElse(null);
    }

    public List<Issue> getAllIssuesByProject(Project project) {
        return issueRepository.findAllByProject(project);
    }
    public List<Issue> getAllIssuesByPerson(Person person) {
        return issueRepository.findAllByAssignee(person);
    }

}
