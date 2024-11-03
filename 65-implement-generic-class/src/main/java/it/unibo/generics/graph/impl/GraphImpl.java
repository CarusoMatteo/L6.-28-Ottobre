package it.unibo.generics.graph.impl;

import java.util.*;

import it.unibo.generics.graph.api.Graph;
import it.unibo.generics.graph.api.NodeColor;

import static it.unibo.generics.graph.api.NodeColor.*;

public class GraphImpl<N> implements Graph<N> {

    /**
     * Select the Search Method.
     */
    // private static final SearchAlgorithm algorithm =
    // SearchAlgorithm.BREADTH_FIRST;

    private final Map<N, Set<N>> edges = new HashMap<>();

    @Override
    public void addNode(final N node) {
        if (node != null) {
            // this.nodes.add(node);
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
        return breadthFirstSearch(source, target);
        // return depthFirstSearchWrapper(source, target);
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

        System.out.println(previous);

        // Step 4:
        // Climb back from the target node until it is null:
        // either no path was found, or the source was reached.
        return getPathFromSourceToTarget(source, target, previous);
    }

    private List<N> getPathFromSourceToTarget(final N source, final N target, final Map<N, N> previous) {
        final ArrayList<N> path = new ArrayList<>();

        for (N current = target; current != null || current == source; current = previous.get(current)) {
            path.add(0, current);
        }
        return path;
    }

}
