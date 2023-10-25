package com.solutions.mongekantorovich.util.containers;

/**
 * Cell coordinates
 * @param producer position in producers
 * @param consumer position in consumers
 */
public record Pair(int producer, int consumer) {
}
