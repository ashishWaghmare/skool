package domainapp.modules.simple.dom.impl;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import java.util.List;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.TeacherMenu",
        repositoryFor = Teacher.class
)
@DomainServiceLayout(
        named = "Teachers",
        menuOrder = "10"
)
public class TeacherMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Teacher> listAll() {
        return TeacherRepository.listAll();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Teacher> findByUid(
            @ParameterLayout(named = "Uid") final String uid
    ) {
        return TeacherRepository.findByUid(uid);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<Teacher> findByName(
            @ParameterLayout(named = "Name") final String name
    ) {
        return TeacherRepository.findByName(name);
    }


    public static class CreateDomainEvent extends ActionDomainEvent<TeacherMenu> {
    }

    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "4")
    public Teacher create(
            @ParameterLayout(named = "Uid") final String uid,
            @ParameterLayout(named = "Name") final String name
    ) {
        return TeacherRepository.create(uid, name);
    }


    @javax.inject.Inject
    TeacherRepository TeacherRepository;

}

