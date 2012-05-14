package org.beethoven.jshint;

/**
 * Holds JSHint single error info
 *
 * @author Alexey Sergeev
 */
public class JSHintError {
    private final int row;
    private final int column;
    private final String description;

    public JSHintError(int row, int column, String description) {
        this.row = row;
        this.column = column;
        this.description = description;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "at row '" + row + "' an column '" + column + "' - " + description;
    }
}
