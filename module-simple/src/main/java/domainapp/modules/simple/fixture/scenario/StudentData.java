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

import domainapp.modules.simple.dom.impl.Student;
import domainapp.modules.simple.dom.impl.StudentMenu;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StudentData {

    FOO("123","Foo"),
    BAR("124","Bar"),
    BAZ("125","Baz"),
    FRODO("163","Frodo"),
    FROYO("165","Froyo"),
    FIZZ("125","Fizz"),
    BIP("127","Bip"),
    BOP("129","Bop"),
    BANG("1233","Bang"),
    BOO("1232","Boo");

    private final String uid;
    private final String name;

    public Student createWith(final StudentMenu menu) {
        return menu.create(uid,name);
    }

    public Student findWith(final StudentMenu menu) {
        return menu.findByUid(uid).get(0);
    }
}
