/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
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
                        + "FROM domainapp.modules.simple.dom.impl.Student "
                        + "WHERE uid.indexOf(:uid) >= 0 ")
})

@javax.jdo.annotations.Unique(name="Student_id_UNQ", members = {"uid"})
@DomainObject() // objectType inferred from @PersistenceCapable#schema
public class Student implements Comparable<Student> {

    public Student(final String uid) {
        setUid(uid);
    }

    public Student(final String uid,final String name) {
        setUid(uid);
        setName(name);
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property() // editing disabled by default, see isis.properties
    @Getter @Setter
    @Title(prepend = "Object: ")
    private String uid;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String name;


    //region > updateName (action)
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Student updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Name")
            final String name) {
        setName(name);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Student updateUid(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Uid")
            final String uid) {
        setUid(uid);
        return this;
    }



    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validate0UpdateName(final String name) {
        return name != null && name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

    public String default0UpdateUid() {
        return getUid();
    }

    public TranslatableString validate0UpdateUid(final String uid) {
        return uid != null && uid.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }
    //endregion

    //region > delete (action)
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }
    //endregion


    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "uid");
    }

    @Override
    public int compareTo(final Student other) {
        return ObjectContracts.compare(this, other, "uid");
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    //endregion

}