package bug.com.issues;

import bug.com.auth.Person;
import bug.com.enums.Status;
import bug.com.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@NoArgsConstructor
public class IssueFilter {

    Status status;
    Project project;
    Person assignee;
    String title;
    String priority;
    String type;
    String globalSearch;

    private Specification<Issue> hasPriority() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("priority"), priority);
    }

    private Specification<Issue> hasType() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("type"), type);
    }

    private Specification<Issue> hasStatus() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("status"), status);
    }

    private Specification<Issue> hasProject() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("project"), project);
    }

    private Specification<Issue> hasAssignee() {
        return (issueRoot, query, builder) -> builder.equal(issueRoot.get("assignee"), assignee);
    }

    private Specification<Issue> hasTitle() {
        return (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("title")), "%" + title.toLowerCase() + "%");
    }

    private Specification<Issue> globalSearching() {

        Specification<Issue> hasTitle = (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("title")), "%" + globalSearch.toLowerCase() + "%");
        Specification<Issue> hasContent = (issueRoot, query, builder) -> builder.like(builder.lower(issueRoot.get("content")), "%" + globalSearch.toLowerCase() + "%");

        return hasTitle.or(hasContent);
    }

    public Specification<Issue> buildQuery() {
        Specification<Issue> spec = Specification.where(null);

        if (project != null) {
            spec = spec.and(hasProject());
        }

        if (assignee != null) {
            spec = spec.and(hasAssignee());
        }

        if (status != null) {
            spec = spec.and(hasStatus());
        }

        if (title != null) {
            spec = spec.and(hasTitle());
        }

        if (globalSearch != null) {
            spec = spec.and(globalSearching());
        }

        return spec;
    }

}

