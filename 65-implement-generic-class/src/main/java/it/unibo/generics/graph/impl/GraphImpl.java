package it.unibo.generics.graph.impl;

import java.util.*;

import it.unibo.generics.graph.api.Graph;
import it.unibo.generics.graph.api.NodeColor;
import it.unibo.generics.graph.api.SearchAlgorithm;

import static it.unibo.generics.graph.api.NodeColor.*;

public class GraphImpl<N> implements Graph<N> {

    /**
     * Select the Search Method.
     */
    private static final SearchAlgorithm algorithm = SearchAlgorithm.DEPTH_FIRST;

    private final Map<N, Set<N>> edges = new HashMap<>();

    @Override
    public void addNode(final N node) {
        if (node != null) {
            this.edges.put(node, new HashSet<N>());
        }
    }

    @Override
    public void addEdge(final N source, final N target) {
        if (source != null && target != null) {
            this.edges.get(source).add(target);
        }
    }

    @Override
    public Set<N> nodeSet() {
        return Set.copyOf(this.edges.keySet());
    }

    @Override
    public Set<N> linkedNodes(final N node) {
        if (node != null) {
            return Set.copyOf(this.edges.get(node));
        }
        return null;
    }

    @Override
    public List<N> getPath(final N source, final N target) {
        return algorithm == SearchAlgorithm.BREADTH_FIRST
                ? breadthFirstSearch(source, target)
                : depthFirstSearch(source, target);
    }

    private List<N> breadthFirstSearch(final N source, final N target) {
        // Contains the status for each node.
        final Map<N, NodeColor> colors = new HashMap<>();
        // Contains the prevoius node for each node.
        final Map<N, N> previous = new HashMap<>();

        // Step 1:
        // Initialize all nodes' colors.
        for (final N node : this.edges.keySet()) {
            colors.put(node, WHITE);
            previous.put(node, null);
        }

        // Step 2:
        // Set source as first node to check
        // by adding it to a queue and changing its color.
        final Queue<N> queue = new ArrayDeque<>();
        queue.offer(source);
        colors.put(source, GREY);

        // Step 3:
        // Check every adjacent of source,
        // then examine the children's children.
        while (!queue.isEmpty()) {
            final N current = queue.poll();

            for (final N adjacent : this.edges.get(current)) {
                if (colors.get(adjacent) == WHITE) {
                    colors.put(adjacent, GREY);
                    previous.put(adjacent, current);

                    queue.offer(adjacent);
                }
            }
            colors.put(current, BLACK);
        }

        // Step 4:
        // Climb back from the target node until it is null:
        // either no path was found, or the source was reached.
        return getPathFromSourceToTarget(source, target, previous);
    }

    private List<N> depthFirstSearch(final N source, final N target) {
        // Contains the status for each node.
        final Map<N, NodeColor> colors = new HashMap<>();
        // Contains the prevoius node for each node.
        final Map<N, N> previous = new HashMap<>();

        // Step 1:
        // Initialize all nodes' colors.
        for (final N node : this.edges.keySet()) {
            colors.put(node, WHITE);
            previous.put(node, null);
        }

        // Step 2:
        // Set source as first node to check
        // by calling the recursive method deptFirstSearchRec on it.
        depthFirstSearchRec(source, colors, previous);

        // Step 3:
        // Check if during the search we missed any disconnected nodes.
        for (final N node : this.edges.keySet()) {
            if (colors.get(node) == WHITE) {
                depthFirstSearchRec(node, colors, previous);
            }
        }

        return getPathFromSourceToTarget(source, target, previous);
    }

    private void depthFirstSearchRec(final N current, final Map<N, NodeColor> colors, final Map<N, N> previous) {
        colors.put(current, GREY);
        for (final N adjacent : edges.get(current)) {
            if (colors.get(adjacent) == WHITE) {
                previous.put(adjacent, current);
                depthFirstSearchRec(adjacent, colors, previous);
            }
        }
        colors.put(current, BLACK);
    }

    private List<N> getPathFromSourceToTarget(final N source, final N target, final Map<N, N> previous) {
        final ArrayList<N> path = new ArrayList<>();

        for (N current = target; current != null || current == source; current = previous.get(current)) {
            path.add(0, current);
        }
        return path;
    }

}
