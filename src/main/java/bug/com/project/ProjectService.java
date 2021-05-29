package bug.com.project;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){

        return projectRepository.findAll();
   }
 }
