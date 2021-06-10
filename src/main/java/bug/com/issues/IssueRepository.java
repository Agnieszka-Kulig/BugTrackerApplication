package bug.com.issues;


import bug.com.auth.Person;
import bug.com.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    @Override
    Optional<Issue> findById(Long aLong);

    List<Issue> findAllByProject(Project project);

    List<Issue> findAllByAssignee(Person assignee);
}