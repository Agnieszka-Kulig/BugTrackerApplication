package bug.com.project;

import bug.com.issues.Issue;
import lombok.Data;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

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
   public Project findProject (long id) {

        return projectRepository.findById(id).orElse(null);
   }
 }
