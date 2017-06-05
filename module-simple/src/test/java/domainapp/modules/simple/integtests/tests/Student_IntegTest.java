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
package domainapp.modules.simple.integtests.tests;

import java.sql.Timestamp;

import javax.inject.Inject;

import domainapp.modules.simple.dom.impl.Student;
import domainapp.modules.simple.dom.impl.StudentMenu;
import domainapp.modules.simple.fixture.scenario.StudentData;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusIdLong;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusVersionTimestamp;

import domainapp.modules.simple.fixture.scenario.CreateStudents;
import domainapp.modules.simple.fixture.teardown.SimpleModuleTearDown;
import domainapp.modules.simple.integtests.SimpleModuleIntegTestAbstract;
import static org.assertj.core.api.Assertions.assertThat;

public class Student_IntegTest extends SimpleModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    StudentMenu simpleObjectMenu;
    @Inject
    TransactionService transactionService;

    Student student;

    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new SimpleModuleTearDown(), null);
        CreateStudents fs = new CreateStudents().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        student = StudentData.FOO.findWith(wrap(simpleObjectMenu));
        assertThat(student).isNotNull();
    }

    public static class Name extends Student_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = wrap(student).getName();

            // then
            assertThat(name).isEqualTo(student.getName());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(student).setUid("new name");
        }

    }

    public static class UpdateName extends Student_IntegTest {

        @Test
        public void can_be_updated_directly() throws Exception {

            // when
            wrap(student).updateUid("1236789");
            transactionService.nextTransaction();

            // then
            assertThat(wrap(student).getUid()).isEqualTo("1236789");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            wrap(student).updateUid("!!!");
        }
    }


    public static class Title extends Student_IntegTest {

        @Inject
        TitleService titleService;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = wrap(student).getUid();

            // when
            final String title = titleService.titleOf(student);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }

    public static class DataNucleusId extends Student_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long id = mixin(Persistable_datanucleusIdLong.class, student).exec();

            // then
            assertThat(id).isGreaterThanOrEqualTo(0);
        }
    }

    public static class DataNucleusVersionTimestamp extends Student_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Timestamp timestamp = mixin(Persistable_datanucleusVersionTimestamp.class, student).exec();
            // then
            assertThat(timestamp).isNotNull();
        }
    }


}