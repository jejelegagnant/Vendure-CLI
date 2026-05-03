package org.example;

public record ParsedConfig(
        String subCommand,
        String url,
        OutputFormat format
) {}
