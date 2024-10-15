package com.example.telegrambot.utils;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class NativeSqlHelper {
    private final StringBuilder query = new StringBuilder();
    private final String table;

    public NativeSqlHelper(String table) {
        this.table = table;
        query.append("select * from ").append(this.table);
    }

    public void addWhereClause(String field, Operator op, @NotNull Object value) {
        if (query.toString().contains("where")) {
            query.append(" and ").append(field).append(" ").append(op.getOp()).append(" ").append(format(value));
        } else {
            query.append(" where ").append(field).append(" ").append(op.getOp()).append(" ").append(format(value));
        }
    }

    public String getTotalQuery() {
        return "select count(*) from " + table;
    }

    public static Integer getTotalPages(@NotNull Integer queryTotal, @NotNull Integer pageSize) {
        return (queryTotal + pageSize - 1) / pageSize;
    }

    @Override
    public String toString() {
        return query.toString();
    }

    private Object format(@NotNull Object value) {
        if (value instanceof LocalDateTime) {
            return "TO_TIMESTAMP('" + value + "', 'YYYY-MM-DD\"T\"HH24:MI:SS')";
        }
        return value;
    }
}
