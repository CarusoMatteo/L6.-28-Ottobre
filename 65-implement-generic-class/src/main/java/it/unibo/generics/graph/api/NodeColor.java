package it.unibo.generics.graph.api;

// Possible node statuses during a Search.
public enum NodeColor {
    // Not discovered
    WHITE,
    // Discovered but not fully explored
    GREY,
    // Fully explored
    BLACK
}
