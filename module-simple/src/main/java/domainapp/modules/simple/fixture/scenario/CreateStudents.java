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

package domainapp.modules.simple.fixture.scenario;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import domainapp.modules.simple.dom.impl.StudentMenu;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.dom.impl.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CreateStudents extends FixtureScript {

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    @Nullable
    @Getter @Setter
    private Integer number;

    /**
     * The objects created by this fixture (output).
     */
    @Getter
    private final List<Student> simpleObjects = Lists.newArrayList();

    @Override
    protected void execute(final ExecutionContext ec) {

        int max = StudentData.values().length;

        // defaults
        final int number = defaultParam("number", ec, 3);

        // validate
        if(number < 0 || number > max) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", max));
        }

        // execute
        for (int i = 0; i < number; i++) {
            final StudentData data = StudentData.values()[i];
            final Student student =  data.createWith(wrap(simpleObjectMenu));
            ec.addResult(this, student);
            simpleObjects.add(student);
        }
    }

    @javax.inject.Inject
    StudentMenu simpleObjectMenu;

}
