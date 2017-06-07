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

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Teacher.class
)
public class TeacherRepository {

    public List<Teacher> listAll() {
        return repositoryService.allInstances(Teacher.class);
    }


    public List<Teacher> findByUid(final String uid) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Teacher.class,
                        "findByUid",
                        "uid", uid));
    }

    public List<Teacher> findByName(final String name) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Teacher.class,
                        "findByName",
                        "name", name));
    }

    public Teacher create(final String uid) {
        final Teacher object = new Teacher(uid);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    public Teacher create(final String uid,final String name) {
        final Teacher object = new Teacher(uid,name);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
