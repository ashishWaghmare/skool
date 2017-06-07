package domainapp.modules.simple.dom.impl;

import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

/**
 * Created by ashis on 6/7/2017.
 */

@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "simple"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByUid",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.impl.Teacher "
                        + "WHERE uid.indexOf(:uid) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Teacher_id_UNQ", members = {"uid"})
@DomainObject() // objectType inferred from @PersistenceCapable#schema
public class Teacher implements Comparable<Student> {

    public Teacher(final String uid) {
        setUid(uid);
    }

    public Teacher(final String uid,final String name) {
        setUid(uid);
        setName(name);
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property() // editing disabled by default, see isis.properties
    @Getter
    @Setter
    @Title(prepend = "Object: ")
    private String uid;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String name;

    @Override
    public int compareTo(Student o) {
        return 0;
    }
}
