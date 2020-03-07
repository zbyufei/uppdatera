/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jhejderup.callgraph;

import java.io.Serializable;
import java.util.Objects;

public final class ResolvedCall implements Serializable {

    private final ResolvedMethod source;
    private final ResolvedMethod target;

    /**
     * Class representing a call from one resolved method to another.
     *
     * @param source the calling method (caller)
     * @param target the called method (callee)
     */
    public ResolvedCall(ResolvedMethod source, ResolvedMethod target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Returns the calling (source) method.
     *
     * @return the source method
     */
    public ResolvedMethod getSource() {
        return source;
    }

    /**
     * Returns the called (target) method.
     *
     * @return the target method
     */
    public ResolvedMethod getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getSource() + " -> " + getTarget();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvedCall that = (ResolvedCall) o;
        return Objects.equals(source, that.source) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
