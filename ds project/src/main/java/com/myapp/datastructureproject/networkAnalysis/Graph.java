package com.myapp.datastructureproject.networkAnalysis;

public class Graph {

    private final int vertices;
    private final boolean[][] adjacencyMatrix;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new boolean[vertices][vertices];
    }

    public int getVertices(){

        return vertices;
    }
    // Adds an edge between two vertices
    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = true;
        // For undirected graphs, also add the reverse edge

    }

    // Removes an edge between two vertices
    public void removeEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = false;
        // For undirected graphs, also remove the reverse edge

    }

    // Checks if there's an edge between two vertices
    public boolean isConnected(int source, int destination) {
        return adjacencyMatrix[source][destination];
    }

    // Prints the adjacency matrix representation of the graph
    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

