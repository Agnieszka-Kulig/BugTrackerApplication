package bug.com.project;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
//    @Mock
//    Project project;
    @InjectMocks
    private ProjectService projectService;
    @Test
    public void shouldDeleteProject(){
        //given
        Long id = 1L;
        Project project = new Project();
        project.setId(id);
        project.setName("name");
        project.setContent("content");
        //when
        when(projectRepository.findById(id)).thenReturn(java.util.Optional.of(project));
        projectService.deleteProject(id);
        //then
        verify(projectRepository,times(1)).delete(project);
    }

}