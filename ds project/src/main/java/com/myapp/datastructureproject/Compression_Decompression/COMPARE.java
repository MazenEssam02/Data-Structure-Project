package com.myapp.datastructureproject.Compression_Decompression;

import java.util.Comparator;

class COMPARE implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return n1.getFrequency()-n2.getFrequency();

    }
}