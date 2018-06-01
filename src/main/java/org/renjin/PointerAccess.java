

package org.renjin;

import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

public class PointerAccess {

    private static final int SIZE = 1024*1024;

    @State(Scope.Thread)
    public static class ArrayState {
        public double[] array;

        public ArrayState() {
            array = new double[SIZE];
            for (int i = 0; i < array.length; i++) {
                array[i] = Math.sqrt(i);
            }
        }
    }

    @State(Scope.Thread)
    public static class ByteBufferState {
        public DoubleBuffer buffer;

        public ByteBufferState() {
            buffer = ByteBuffer.allocate(SIZE * 8).asDoubleBuffer();
            for (int i = 0; i < SIZE; i++) {
                buffer.put(i, Math.sqrt(i));
            }
        }
    }



    @State(Scope.Thread)
    public static class DoubleBufferState {
        public DoubleBuffer buffer;

        public DoubleBufferState() {
            buffer = DoubleBuffer.allocate(SIZE * 8);
            for (int i = 0; i < SIZE; i++) {
                buffer.put(i, Math.sqrt(i));
            }
        }
    }


    @State(Scope.Thread)
    public static class DirectBufferState {
        public DoubleBuffer buffer;

        public DirectBufferState() {
            buffer = ByteBuffer.allocateDirect(SIZE * 8).asDoubleBuffer();
            for (int i = 0; i < SIZE; i++) {
                buffer.put(i, Math.sqrt(i));
            }
        }
    }


    @Benchmark
    @Fork(value = 1)
    public double array(ArrayState arrayState) {
        double[] array = arrayState.array;
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    @Benchmark
    @Fork(value = 1)
    public double heapByteBuffer(ByteBufferState state) {
        DoubleBuffer buffer = state.buffer;
        double sum = 0;
        for (int i = 0; i < SIZE; i++) {
            sum += buffer.get(i);
        }
        return sum;
    }

    @Benchmark
    @Fork(value = 1)
    public double heaDoubleBuffer(DoubleBufferState state) {
        DoubleBuffer buffer = state.buffer;
        double sum = 0;
        for (int i = 0; i < SIZE; i++) {
            sum += buffer.get(i);
        }
        return sum;
    }

    @Benchmark
    @Fork(value = 1)
    public double directByteBuffer(DirectBufferState state) {
        DoubleBuffer buffer = state.buffer;
        double sum = 0;
        for (int i = 0; i < SIZE; i++) {
            sum += buffer.get(i);
        }
        return sum;
    }


}
