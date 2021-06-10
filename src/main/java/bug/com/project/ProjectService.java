package bug.com.project;

import bug.com.issues.Issue;
import bug.com.issues.IssueService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final IssueService issueService;

    public void createNewProject(Project project) {

        projectRepository.save(project);

    }

    public void deleteProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            projectRepository.delete(project.get());//usuwanie
        }
    }

    protected Project editProject (Project project) {

        return projectRepository.save(project);//edycja
    }

    public List<Project> getAllProjects() {

        return projectRepository.findAll();//wyszukiwanie
    }

    public List<Project> getAllEnabledProjects() {

        return projectRepository.findAllByEnabled(true);//wyszukiwanie
    }


    public Project findProject (long id) {

        return projectRepository.findById(id).orElse(null);
    }

    public boolean deleteEnable(long projectId) {
        Project project = findProject(projectId);
        if(project != null) {
            List<Issue> issueList = issueService.getAllIssuesByProject(project);
            return issueList.isEmpty();
        }
        return false;
    }
}