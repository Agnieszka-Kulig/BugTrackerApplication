package bug.com.project;

import bug.com.auth.Person;
import bug.com.issues.Issue;
import bug.com.issues.IssueService;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private IssueService issueService;

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
        project.setCode("code");
        project.setDateCreated(new Date());
        project.setEnabled(true);
        //when
        when(projectRepository.findById(id)).thenReturn(java.util.Optional.of(project));
        projectService.deleteProject(id);
        //then
        verify(projectRepository,times(1)).delete(project);
    }
    @Test
    public void deleteEnableShouldReturnTrueWhenThereIsNoRelatedIssue(){
        //given
        Long id = 1L;
        Project project = new Project();
        project.setId(id);
        project.setName("name");
        project.setContent("content");
        project.setCode("code");
        project.setDateCreated(new Date());
        project.setEnabled(true);
        //when
        when(projectRepository.findById(id)).thenReturn(java.util.Optional.of(project));
        when(issueService.getAllIssuesByProject(project)).thenReturn(new ArrayList<>());
        boolean deleteEnable = projectService.deleteEnable(id);
        //then
        Assertions.assertTrue(deleteEnable);
    }
    @Test
    public void deleteEnableShouldReturnTrueWhenThereIsRelatedIssue(){
        //given
        Long id = 1L;
        Project project = new Project();
        project.setId(id);
        project.setName("name");
        project.setContent("content");
        project.setCode("code");
        project.setDateCreated(new Date());
        project.setEnabled(true);
        List<Issue> list = new ArrayList<>();
        Issue issue = new Issue();
        issue.setId(id);
        issue.setTitle("title");
        issue.setAssignee(new Person());
        issue.setProject(project);
        list.add(issue);
        //when
        when(projectRepository.findById(id)).thenReturn(java.util.Optional.of(project));
        when(issueService.getAllIssuesByProject(project)).thenReturn(list);
        boolean deleteEnable = projectService.deleteEnable(id);
        //then
        Assertions.assertFalse(deleteEnable);
    }
}
