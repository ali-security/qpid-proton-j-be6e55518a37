/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.qpid.proton.codec;

import java.nio.ByteBuffer;

import org.junit.Before;

/**
 * Support class for tests of the type decoders
 */
public class CodecTestSupport {

    public static final int DEFAULT_MAX_BUFFER = 256 * 1024;

    protected DecoderImpl decoder;
    protected EncoderImpl encoder;

    protected ByteBuffer buffer;

    @Before
    public void setUp() {
        decoder = new DecoderImpl();
        // Sealed non-breaking backport: the shipped default zeroWidthElementLimit was raised from 0
        // to 65535, but these codec tests validate the limit *mechanism* (that a count over the limit
        // is rejected), which is cleanest to exercise at 0 — the value they were written against.
        // Pin the test decoder to 0 so the negative cases still reject; positive cases set their own.
        decoder.setZeroWidthArrayElementLimit(0);
        encoder = new EncoderImpl(decoder);
        AMQPDefinedTypes.registerAllTypes(decoder, encoder);

        buffer = ByteBuffer.allocate(getMaxBufferSize());

        encoder.setByteBuffer(buffer);
        decoder.setByteBuffer(buffer);
    }

    public int getMaxBufferSize() {
        return DEFAULT_MAX_BUFFER;
    }
}
